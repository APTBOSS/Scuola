// ===================================
// CARRELLO — Persistenza e Logica
// ===================================

function cartLoad() {
    try {
        const saved = localStorage.getItem(CART_STORAGE_KEY);
        if (saved) {
            const parsed = JSON.parse(saved);
            if (parsed && typeof parsed === 'object') {
                carrello = parsed;
            }
        }
    } catch (e) {
        carrello = {};
    }
}

function cartSave() {
    localStorage.setItem(CART_STORAGE_KEY, JSON.stringify(carrello));
}

function aggiungiAlCarrello(codiceArticolo) {
    const articolo = magazzino.find(a => a.codice === codiceArticolo);

    if (articolo && articolo.disponibile) {
        carrello[codiceArticolo] = (carrello[codiceArticolo] || 0) + 1;
        cartSave();
        aggiornaCarrelloTotale();
        mostraToast('Aggiunto al carrello!', 'success');
    } else if (articolo && !articolo.disponibile) {
        mostraToast('Prodotto non disponibile.', 'warning');
    }
}

function rimuoviDaCarrello(codiceArticolo) {
    if (carrello[codiceArticolo]) {
        carrello[codiceArticolo] -= 1;
        if (carrello[codiceArticolo] <= 0) {
            delete carrello[codiceArticolo];
        }
    }
    cartSave();
    aggiornaCarrelloTotale();
}

function aggiornaCarrelloTotale() {
    let totale = 0;
    let elencoCarrelloHTML = "";

    for (const codiceArticolo in carrello) {
        const quantita = carrello[codiceArticolo];
        const articolo = magazzino.find(a => a.codice === codiceArticolo);

        if (articolo && quantita > 0) {
            const subtotale = articolo.prezzo * quantita;
            totale += subtotale;

            elencoCarrelloHTML += `<li class="carrello-item">
                <div class="item-info">
                    ${articolo.descrizione} (${articolo.codice}) &mdash;
                    ${quantita} x &euro;${articolo.prezzo.toFixed(2)} =
                    <strong>&euro;${subtotale.toFixed(2)}</strong>
                </div>
                <button class="btn-elimina-carrello"
                        onclick="rimuoviDaCarrello('${codiceArticolo}')">
                    &#10005; Rimuovi
                </button>
            </li>`;
        }
    }

    prezzoTotale = totale;

    const lista = document.getElementById('carrello-lista');
    if (elencoCarrelloHTML === '') {
        lista.innerHTML = '<li class="cart-empty-msg">Il carrello è vuoto</li>';
    } else {
        lista.innerHTML = elencoCarrelloHTML;
    }

    document.getElementById('totale-prezzo').textContent = prezzoTotale.toFixed(2);
    cartSave();
    aggiornaCartBadge();
}

function aggiornaCartBadge() {
    let totalQty = 0;
    for (const codice in carrello) {
        totalQty += carrello[codice];
    }

    const badge = document.getElementById('cart-badge');
    if (!badge) return;

    if (totalQty > 0) {
        badge.textContent = totalQty;
        badge.style.display = 'inline-block';
        badge.classList.remove('bump');
        void badge.offsetWidth; // reflow to restart animation
        badge.classList.add('bump');
    } else {
        badge.style.display = 'none';
    }
}
