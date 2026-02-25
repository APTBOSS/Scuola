const express = require('express');
const { DatabaseSync: Database } = require('node:sqlite');
const path = require('path');

const app = express();
const db = new Database('magazzino.db');

// Init table
db.exec(`
  CREATE TABLE IF NOT EXISTS prodotti (
    codice      TEXT PRIMARY KEY,
    descrizione TEXT NOT NULL,
    prezzo      REAL NOT NULL,
    disponibile INTEGER NOT NULL
  )
`);

// Seed if empty
const count = db.prepare('SELECT COUNT(*) as n FROM prodotti').get().n;
if (count === 0) {
  const insert = db.prepare('INSERT INTO prodotti VALUES (?, ?, ?, ?)');
  insert.run('A234X', 'Penna a sfera blu',      1.50, 1);
  insert.run('B567Y', 'Quaderno A4 a righe',    3.20, 1);
  insert.run('C890Z', 'Evidenziatore giallo',   0.99, 1);
  insert.run('D123W', 'Gomma per cancellare',   0.75, 0);
}

function rowToProdotto(row) {
  return { ...row, disponibile: row.disponibile === 1 };
}

app.use(express.json());
app.use(express.static(__dirname));

// GET /api/prodotti
app.get('/api/prodotti', (req, res) => {
  const rows = db.prepare('SELECT * FROM prodotti').all();
  res.json(rows.map(rowToProdotto));
});

// POST /api/prodotti
app.post('/api/prodotti', (req, res) => {
  const { codice, descrizione, prezzo, disponibile } = req.body;
  if (!codice || !descrizione || prezzo == null) {
    return res.status(400).json({ error: 'Campi obbligatori mancanti' });
  }
  const exists = db.prepare('SELECT 1 FROM prodotti WHERE codice = ?').get(codice);
  if (exists) {
    return res.status(409).json({ error: 'Codice prodotto già esistente' });
  }
  db.prepare('INSERT INTO prodotti VALUES (?, ?, ?, ?)')
    .run(codice, descrizione, prezzo, disponibile ? 1 : 0);
  res.status(201).json(rowToProdotto(
    db.prepare('SELECT * FROM prodotti WHERE codice = ?').get(codice)
  ));
});

// PUT /api/prodotti/:codice
app.put('/api/prodotti/:codice', (req, res) => {
  const oldCodice = req.params.codice;
  const { codice, descrizione, prezzo, disponibile } = req.body;
  if (!codice || !descrizione || prezzo == null) {
    return res.status(400).json({ error: 'Campi obbligatori mancanti' });
  }
  const existing = db.prepare('SELECT 1 FROM prodotti WHERE codice = ?').get(oldCodice);
  if (!existing) {
    return res.status(404).json({ error: 'Prodotto non trovato' });
  }
  db.prepare(`
    UPDATE prodotti SET codice = ?, descrizione = ?, prezzo = ?, disponibile = ?
    WHERE codice = ?
  `).run(codice, descrizione, prezzo, disponibile ? 1 : 0, oldCodice);
  res.json(rowToProdotto(
    db.prepare('SELECT * FROM prodotti WHERE codice = ?').get(codice)
  ));
});

// DELETE /api/prodotti/:codice
app.delete('/api/prodotti/:codice', (req, res) => {
  const { codice } = req.params;
  const existing = db.prepare('SELECT 1 FROM prodotti WHERE codice = ?').get(codice);
  if (!existing) {
    return res.status(404).json({ error: 'Prodotto non trovato' });
  }
  db.prepare('DELETE FROM prodotti WHERE codice = ?').run(codice);
  res.status(204).end();
});

const PORT = 3000;
app.listen(PORT, () => {
  console.log(`Magazzino server avviato su http://localhost:${PORT}`);
});
