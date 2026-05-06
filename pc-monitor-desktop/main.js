'use strict';

const { app, BrowserWindow, Tray, Menu, nativeImage, shell } = require('electron');
const path   = require('path');
const http   = require('http');
const { spawn } = require('child_process');

// ── Config ────────────────────────────────────────────────────────────────────
const BACKEND_PORT    = 8080;
const BACKEND_URL     = `http://localhost:${BACKEND_PORT}`;
const POLL_INTERVAL   = 500;   // ms tra un tentativo e il successivo durante startup
const STARTUP_TIMEOUT = 30000; // ms massimi per l'avvio del backend

// ── Stato globale ─────────────────────────────────────────────────────────────
let win          = null;
let tray         = null;
let backendProc  = null;
let isQuitting   = false;

// ── Percorso JAR ─────────────────────────────────────────────────────────────
// In sviluppo: ../pc-monitor/target/pc-monitor-0.0.1-SNAPSHOT.jar
// In produzione (packaged): process.resourcesPath/backend.jar
function getJarPath() {
  if (app.isPackaged) {
    return path.join(process.resourcesPath, 'backend.jar');
  }
  return path.join(__dirname, '..', 'pc-monitor', 'target', 'pc-monitor-0.0.1-SNAPSHOT.jar');
}

// ── Avvio Spring Boot ─────────────────────────────────────────────────────────
function startBackend() {
  const jarPath = getJarPath();
  console.log(`[backend] Avvio JAR: ${jarPath}`);

  backendProc = spawn('java', ['-jar', jarPath], {
    stdio: ['ignore', 'pipe', 'pipe']
  });

  backendProc.stdout.on('data', d => console.log('[backend]', d.toString().trim()));
  backendProc.stderr.on('data', d => console.error('[backend]', d.toString().trim()));

  backendProc.on('exit', (code) => {
    console.log(`[backend] Terminato con codice ${code}`);
    if (!isQuitting) {
      // Backend crashato inaspettatamente
      if (win && !win.isDestroyed()) {
        win.webContents.loadURL(`data:text/html,<h2 style="font-family:sans-serif;color:red;text-align:center;margin-top:40vh">Errore: il backend si è fermato (codice ${code}).<br>Riavvia l'applicazione.</h2>`);
      }
    }
  });
}

// ── Attendi che Spring Boot sia pronto ────────────────────────────────────────
function waitForBackend() {
  return new Promise((resolve, reject) => {
    const deadline = Date.now() + STARTUP_TIMEOUT;

    function tryPing() {
      http.get(`${BACKEND_URL}/api/status`, (res) => {
        if (res.statusCode === 200) {
          resolve();
        } else {
          retry();
        }
        res.resume();
      }).on('error', () => retry());
    }

    function retry() {
      if (Date.now() >= deadline) {
        reject(new Error('Timeout: il backend non ha risposto entro 30 secondi.'));
      } else {
        setTimeout(tryPing, POLL_INTERVAL);
      }
    }

    tryPing();
  });
}

// ── Crea la finestra principale ───────────────────────────────────────────────
function createWindow() {
  const iconPath = path.join(__dirname, 'assets', 'icon.png');

  win = new BrowserWindow({
    width:     1300,
    height:    860,
    minWidth:  900,
    minHeight: 600,
    title:     'PC Monitor',
    icon:      iconPath,
    backgroundColor: '#0d1117',
    webPreferences: {
      contextIsolation: true,
      nodeIntegration:  false,
    },
    show: false,
  });

  // Carica la dashboard servita da Spring Boot
  win.loadURL(BACKEND_URL);

  win.once('ready-to-show', () => win.show());

  // ── Minimizza nella tray invece di chiudere ──────────────────────────────
  win.on('close', (event) => {
    if (!isQuitting) {
      event.preventDefault();
      win.hide();
      tray.displayBalloon({
        iconType: 'info',
        title:    'PC Monitor',
        content:  'L\'app è ancora attiva nella barra delle applicazioni.',
      });
    }
  });
}

// ── Crea l'icona nella tray ───────────────────────────────────────────────────
function createTray() {
  const iconPath = path.join(__dirname, 'assets', 'icon.png');
  const icon = nativeImage.createFromPath(iconPath).resize({ width: 16, height: 16 });

  tray = new Tray(icon);
  tray.setToolTip('PC Monitor');

  const menu = Menu.buildFromTemplate([
    {
      label: '📊 Mostra PC Monitor',
      click: () => { win.show(); win.focus(); }
    },
    { type: 'separator' },
    {
      label: '🌐 Apri nel browser',
      click: () => shell.openExternal(BACKEND_URL)
    },
    { type: 'separator' },
    {
      label: '❌ Esci',
      click: () => {
        isQuitting = true;
        app.quit();
      }
    },
  ]);

  tray.setContextMenu(menu);
  tray.on('double-click', () => { win.show(); win.focus(); });
}

// ── Splash screen mentre Spring Boot si avvia ─────────────────────────────────
function showSplash() {
  win.loadURL(`data:text/html;charset=utf-8,<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8"/>
  <style>
    *{margin:0;padding:0;box-sizing:border-box}
    body{
      background:#0d1117;color:#e6edf3;
      font-family:'Segoe UI',sans-serif;
      display:flex;flex-direction:column;
      align-items:center;justify-content:center;
      height:100vh;gap:24px;
    }
    .logo{font-size:2.5rem}
    h1{font-size:1.4rem;font-weight:600;letter-spacing:-.3px}
    .sub{font-size:.9rem;color:#8b949e}
    .spinner{
      width:36px;height:36px;
      border:3px solid #30363d;
      border-top-color:#58a6ff;
      border-radius:50%;
      animation:spin 1s linear infinite;
    }
    @keyframes spin{to{transform:rotate(360deg)}}
  </style>
</head>
<body>
  <div class="logo">🖥️</div>
  <h1>PC Monitor</h1>
  <p class="sub">Avvio del backend in corso...</p>
  <div class="spinner"></div>
</body>
</html>`);

  win.once('ready-to-show', () => win.show());
}

// ── Teardown ──────────────────────────────────────────────────────────────────
app.on('before-quit', () => {
  isQuitting = true;
  if (backendProc) {
    console.log('[app] Terminazione backend...');
    backendProc.kill();
  }
});

// Non uscire quando si chiudono tutte le finestre (rimane in tray)
app.on('window-all-closed', (e) => e.preventDefault());

// ── Entry point ───────────────────────────────────────────────────────────────
app.whenReady().then(async () => {
  createTray();
  createWindow();
  showSplash();

  try {
    startBackend();
    await waitForBackend();
    console.log('[app] Backend pronto. Carico la dashboard...');
    win.loadURL(BACKEND_URL);
  } catch (err) {
    console.error('[app]', err.message);
    win.loadURL(`data:text/html,<h2 style="font-family:sans-serif;color:red;text-align:center;margin-top:40vh">
      Errore di avvio:<br>${err.message}<br><br>
      Assicurati che Java sia installato e accessibile nel PATH.
    </h2>`);
  }
});
