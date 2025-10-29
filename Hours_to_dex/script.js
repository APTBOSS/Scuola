function aggiornaOra() {
    let time = new Date();
  
    // Estrai ore, minuti, secondi
    let ore = time.getHours().toString().padStart(2, '0');
    let minuti = time.getMinutes().toString().padStart(2, '0');
    let secondi = time.getSeconds().toString().padStart(2, '0');
  
    // Mostra l’ora in formato HH:MM:SS
    let oraAttuale = `${ore}:${minuti}:${secondi}`;
    document.getElementById('time').textContent = oraAttuale;
  
    // Conversione in esadecimale
    let oreHex = parseInt(ore).toString(16).padStart(2, '0');
    let minutiHex = parseInt(minuti).toString(16).padStart(2, '0');
    let secondiHex = parseInt(secondi).toString(16).padStart(2, '0');
  
    // Componi il codice colore (#RRGGBB)
    let coloreHex = `#${oreHex}${minutiHex}${secondiHex}`.substring(0, 7);
  
    // Mostra il codice esadecimale
    document.getElementById('hex').textContent = coloreHex.toUpperCase();
  
    // Cambia colore di sfondo
    document.body.style.backgroundColor = coloreHex;
  
    // Richiama la funzione ogni secondo
    setTimeout(aggiornaOra, 1000);
  }
  
  aggiornaOra();
  