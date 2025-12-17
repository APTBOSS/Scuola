const express = require('express');
const fs = require('fs');
const cors = require('cors');
const path = require('path'); // Modulo nativo per gestire i percorsi dei file

const app = express();
const PORT = 3000;

// 1. MIDDLEWARE
app.use(cors());
app.use(express.json());

// 2. SERVIRE I FILE STATICI (Soluzione 2)
// Questa riga dice a Express di rendere pubblica la cartella superiore (la root del progetto)
// In questo modo index.html, style.css e le cartelle frontend saranno accessibili via browser
app.use(express.static(path.join(__dirname, '../')));

const DATA_FILE = path.join(__dirname, 'data', 'macchine.json');
const ADMIN_PASS = "admin123";

// 3. ROTTE API (Dati)

// Login
app.post('/api/login', (req, res) => {
    if (req.body.password === ADMIN_PASS) {
        res.json({ success: true });
    } else {
        res.status(401).json({ success: false, message: "Password Errata" });
    }
});

// Ottieni dati
app.get('/api/macchina', (req, res) => {
    try {
        const data = JSON.parse(fs.readFileSync(DATA_FILE, 'utf8'));
        res.json(data);
    } catch (err) {
        res.status(500).json({ error: "Errore nella lettura del database" });
    }
});

// Modifica dati
app.post('/api/macchina', (req, res) => {
    const newCar = req.body;
    if (newCar.numero_porte > 7) {
        return res.status(400).json({ error: "Il numero di porte non può superare 7!" });
    }
    try {
        fs.writeFileSync(DATA_FILE, JSON.stringify(newCar, null, 2));
        res.json({ message: "Dati salvati con successo!" });
    } catch (err) {
        res.status(500).json({ error: "Errore nel salvataggio dei dati" });
    }
});

// 4. AVVIO SERVER
app.listen(PORT, () => {
    console.log(`==================================================`);
    console.log(`Sito Web disponibile su: http://localhost:${PORT}`);
    console.log(`Server API attivo su:    http://localhost:${PORT}/api/macchina`);
    console.log(`==================================================`);
});