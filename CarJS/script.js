// Definizione dell'oggetto con i nuovi attributi
var car = {
    targa: "AB123CD",
    altezza: 156,
    lunghezza: 456,
    colore: "Grigio",
    numero_porte: 5
};

// --- METODI PER LA TARGA ---
function show_targa() {
    document.getElementById("targa_display").innerHTML = car.targa;
}

function cambia_targa() {
    let nuovaTarga = document.getElementById("input_targa").value;
    if (nuovaTarga !== "") {
        car.targa = nuovaTarga;
        alert("Targa aggiornata!");
        show_targa();
    }
}

// --- METODI PER L'ALTEZZA ---
function show_altezza() {
    document.getElementById("altezza_display").innerHTML = car.altezza;
}

function modifica_altezza() {
    car.altezza = document.getElementById("input_altezza").value;
    show_altezza();
}

// --- METODI PER LA LUNGHEZZA ---
function show_lunghezza() {
    document.getElementById("lunghezza_display").innerHTML = car.lunghezza;
}

function modifica_lunghezza() {
    car.lunghezza = document.getElementById("input_lunghezza").value;
    show_lunghezza();
}

// --- METODI PER IL COLORE ---
function show_colore() {
    document.getElementById("colore_display").innerHTML = car.colore;
}

function modifica_colore() {
    car.colore = document.getElementById("input_colore").value;
    show_colore();
}

// --- METODI PER IL NUMERO PORTE ---
function show_porte() {
    document.getElementById("porte_display").innerHTML = car.numero_porte;
}

function modifica_porte() {
    let n = parseInt(document.getElementById("input_porte").value);
    
    // Controllo validità (errore se > 7)
    if (n > 7) {
        alert("Errore: Il numero di porte non può essere superiore a 7!");
    } else if (isNaN(n)) {
        alert("Inserisci un numero valido");
    } else {
        car.numero_porte = n;
        show_porte();
    }
}