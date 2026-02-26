// ===================================
// Dati Globali
// ===================================

let magazzino = [];
let carrello = {};
let prezzoTotale = 0.00;
let isAdminView = false;
const ADMIN_PASSWORD = "admin";
const CART_STORAGE_KEY = "magazzino_cart";

// ===================================
// FUNZIONI API
// ===================================

async function carricaProdotti() {
    const res = await fetch('/api/prodotti');
    magazzino = await res.json();
}

async function apiPUT(codice, body) {
    try {
        const res = await fetch(`/api/prodotti/${encodeURIComponent(codice)}`, {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(body)
        });
        const data = await res.json();
        if (!res.ok) return { ok: false, error: data.error || 'Errore durante il salvataggio' };
        return { ok: true, data };
    } catch (e) {
        return { ok: false, error: e.message };
    }
}

async function apiPOST(body) {
    try {
        const res = await fetch('/api/prodotti', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(body)
        });
        const data = await res.json();
        if (!res.ok) return { ok: false, error: data.error || "Errore durante l'aggiunta" };
        return { ok: true, data };
    } catch (e) {
        return { ok: false, error: e.message };
    }
}

async function apiDELETE(codice) {
    try {
        const res = await fetch(`/api/prodotti/${encodeURIComponent(codice)}`, {
            method: 'DELETE'
        });
        if (!res.ok) {
            const data = await res.json();
            return { ok: false, error: data.error || "Errore durante l'eliminazione" };
        }
        return { ok: true };
    } catch (e) {
        return { ok: false, error: e.message };
    }
}
