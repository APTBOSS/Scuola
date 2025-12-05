// ===================================
// Struttura dei Dati
// ===================================

let magazzino = [
    {
        codice: "A234X",
        descrizione: "Penna a sfera blu",
        prezzo: 1.50,
        disponibile: true
    },
    {
        codice: "B567Y",
        descrizione: "Quaderno A4 a righe",
        prezzo: 3.20,
        disponibile: true
    },
    {
        codice: "C890Z",
        descrizione: "Evidenziatore giallo",
        prezzo: 0.99,
        disponibile: true
    },
    {
        codice: "D123W",
        descrizione: "Gomma per cancellare",
        prezzo: 0.75,
        disponibile: false 
    }
];

let carrello = {};
let prezzoTotale = 0.00;
let isAdminView = false;
const ADMIN_PASSWORD = "admin"; // Password di esempio

// ===================================
// FUNZIONI PRINCIPALI DI VISTA (AGGIORNATE)
// ===================================

/**
 * Mostra il campo di input per la password.
 */
function mostraFormPassword() {
    document.getElementById('access-admin-btn').style.display = 'none';
    document.getElementById('password-form-container').style.display = 'flex';
    document.getElementById('admin-password').focus();
}

/**
 * Controlla la password e, in caso di successo, accede alla modalità Admin.
 */
function checkPassword() {
    const passwordInput = document.getElementById('admin-password');
    const enteredPassword = passwordInput.value;

    if (enteredPassword === ADMIN_PASSWORD) {
        alert("Accesso Admin effettuato con successo!");
        loginAdmin();
    } else {
        alert("Password errata!");
        passwordInput.value = ''; // Pulisce il campo
        passwordInput.focus();
    }
}

/**
 * Attiva la modalità Admin e aggiorna la vista.
 */
function loginAdmin() {
    isAdminView = true;
    
    // Nasconde il form di login e il pulsante di accesso
    document.getElementById('password-form-container').style.display = 'none';

    // Aggiorna le viste
    document.getElementById('client-view').style.display = 'none';
    document.getElementById('admin-view').style.display = 'flex';
    visualizzaProdottiAdmin();
}

/**
 * Disattiva la modalità Admin e torna alla vista Client.
 */
function logoutAdmin() {
    isAdminView = false;

    // Aggiorna le viste
    document.getElementById('admin-view').style.display = 'none';
    document.getElementById('client-view').style.display = 'flex';
    
    // Mostra di nuovo il pulsante di accesso
    document.getElementById('access-admin-btn').style.display = 'block';
    
    // Pulisce il campo password
    document.getElementById('admin-password').value = ''; 
    visualizzaProdottiClient();
}


// ===================================
// LOGICA CLIENT (CARRELLO) 
// ... (Tutte le funzioni del carrello restano uguali) ...
// ===================================

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
                    ${articolo.descrizione} (${articolo.codice}) - 
                    ${quantita} x €${articolo.prezzo.toFixed(2)} = 
                    **€${subtotale.toFixed(2)}**
                </div>
                <button class="btn-elimina-carrello" 
                        onclick="rimuoviDaCarrello('${codiceArticolo}')">
                    &#10005; Rimuovi
                </button>
            </li>`;
        }
    }

    prezzoTotale = totale;
    
    document.getElementById('carrello-lista').innerHTML = elencoCarrelloHTML;
    document.getElementById('totale-prezzo').textContent = prezzoTotale.toFixed(2);
}

function rimuoviDaCarrello(codiceArticolo) {
    if (carrello[codiceArticolo]) {
        carrello[codiceArticolo] -= 1; 

        if (carrello[codiceArticolo] <= 0) {
            delete carrello[codiceArticolo];
        }
    }
    aggiornaCarrelloTotale();
}

function aggiungiAlCarrello(codiceArticolo) {
    const articolo = magazzino.find(a => a.codice === codiceArticolo);

    if (articolo && articolo.disponibile) {
        carrello[codiceArticolo] = (carrello[codiceArticolo] || 0) + 1;
        aggiornaCarrelloTotale();
    } else if (articolo && !articolo.disponibile) {
        alert(`Ci dispiace, il prodotto "${articolo.descrizione}" non è attualmente disponibile.`);
    }
}

function visualizzaProdottiClient() {
    const listaProdottiDiv = document.getElementById('lista-prodotti-client');
    let prodottiHTML = "";

    magazzino.forEach(articolo => {
        const statoClasse = articolo.disponibile ? 'disponibile' : 'non-disponibile';
        
        const bottoneCarrello = articolo.disponibile
            ? `<button class="btn-carrello" onclick="aggiungiAlCarrello('${articolo.codice}')">Aggiungi al Carrello</button>`
            : `<button disabled>Non Disponibile</button>`;
        
        prodottiHTML += `<div class="prodotto ${statoClasse}" data-codice="${articolo.codice}">
            <div>
                <h3>${articolo.descrizione}</h3>
                <p>Codice: ${articolo.codice} | Prezzo: **€${articolo.prezzo.toFixed(2)}**</p>
            </div>
            <div class="azioni">
                ${bottoneCarrello}
            </div>
        </div>`;
    });

    listaProdottiDiv.innerHTML = prodottiHTML;
}


// ===================================
// LOGICA ADMIN (MODIFICA PRODOTTI)
// ... (Tutte le funzioni Admin restano uguali, tranne l'aggiunta del logout) ...
// ===================================

function visualizzaProdottiAdmin() {
    const listaProdottiDiv = document.getElementById('lista-prodotti-admin');
    let prodottiHTML = "";

    magazzino.forEach(articolo => {
        const statoClasse = articolo.disponibile ? 'disponibile' : 'non-disponibile';
        
        prodottiHTML += `<div class="prodotto ${statoClasse}" data-codice="${articolo.codice}">
            <div>
                <h3>${articolo.descrizione}</h3>
                <p>Codice: ${articolo.codice} | Prezzo: **€${articolo.prezzo.toFixed(2)}**</p>
            </div>
            <div class="azioni">
                <button class="btn-modifica" onclick="modificaProdotto('${articolo.codice}')">Modifica</button>
                <button class="btn-elimina" onclick="eliminaProdotto('${articolo.codice}')">Elimina</button>
            </div>
        </div>`;
    });

    listaProdottiDiv.innerHTML = prodottiHTML;
}

function modificaProdotto(codiceArticolo) {
    const articolo = magazzino.find(a => a.codice === codiceArticolo);
    if (!articolo) return;

    const prodottoDiv = document.querySelector(`#lista-prodotti-admin .prodotto[data-codice="${codiceArticolo}"]`);
    if (!prodottoDiv) return;

    const formHTML = `
        <form class="admin-form" onsubmit="salvaModifiche(event, '${codiceArticolo}')">
            <label>Codice: <input type="text" name="codice" value="${articolo.codice}" required></label>
            <label>Descrizione: <input type="text" name="descrizione" value="${articolo.descrizione}" required></label>
            <label>Prezzo: <input type="number" name="prezzo" step="0.01" value="${articolo.prezzo.toFixed(2)}" required></label>
            <label>Disponibile: <input type="checkbox" name="disponibile" ${articolo.disponibile ? 'checked' : ''}></label>
            <div class="form-actions">
                <button type="submit" class="btn-modifica">Salva</button>
                <button type="button" onclick="visualizzaProdottiAdmin()">Annulla</button>
            </div>
        </form>
    `;

    prodottoDiv.innerHTML = formHTML;
}

function salvaModifiche(event, oldCodice) {
    event.preventDefault(); 

    const form = event.target;
    const formData = new FormData(form);
    
    const index = magazzino.findIndex(a => a.codice === oldCodice);

    if (index !== -1) {
        const nuovoCodice = formData.get('codice');
        
        magazzino[index] = {
            codice: nuovoCodice,
            descrizione: formData.get('descrizione'),
            prezzo: parseFloat(formData.get('prezzo')),
            disponibile: form.elements.disponibile.checked
        };
        
        visualizzaProdottiAdmin();
        visualizzaProdottiClient(); 
        aggiornaCarrelloTotale(); 
    }
}

function eliminaProdotto(codiceArticolo) {
    if (confirm(`Sei sicuro di voler eliminare il prodotto ${codiceArticolo} dal magazzino?`)) {
        magazzino = magazzino.filter(a => a.codice !== codiceArticolo);
        
        delete carrello[codiceArticolo];

        visualizzaProdottiAdmin();
        visualizzaProdottiClient();
        aggiornaCarrelloTotale();
    }
}

function mostraFormAggiunta() {
    const contenitore = document.getElementById('gestione-prodotti');
    
    if (document.getElementById('add-form')) return;

    const formHTML = `
        <form id="add-form" class="admin-form" onsubmit="aggiungiNuovoProdotto(event)">
            <h3>Aggiungi Nuovo Prodotto</h3>
            <label>Codice: <input type="text" name="codice" required></label>
            <label>Descrizione: <input type="text" name="descrizione" required></label>
            <label>Prezzo: <input type="number" name="prezzo" step="0.01" required></label>
            <label>Disponibile: <input type="checkbox" name="disponibile" checked></label>
            <div class="form-actions">
                <button type="submit" class="btn-carrello">Aggiungi</button>
                <button type="button" onclick="document.getElementById('add-form').remove()">Annulla</button>
            </div>
        </form>
    `;
    document.getElementById('lista-prodotti-admin').insertAdjacentHTML('beforebegin', formHTML);
}

function aggiungiNuovoProdotto(event) {
    event.preventDefault();
    const form = event.target;
    const formData = new FormData(form);

    const nuovoArticolo = {
        codice: formData.get('codice'),
        descrizione: formData.get('descrizione'),
        prezzo: parseFloat(formData.get('prezzo')),
        disponibile: form.elements.disponibile.checked
    };

    if (magazzino.some(a => a.codice === nuovoArticolo.codice)) {
        alert("Errore: Codice prodotto già esistente!");
        return;
    }

    magazzino.push(nuovoArticolo);
    
    form.remove();
    visualizzaProdottiAdmin();
    visualizzaProdottiClient();
}


// ===================================
// Esecuzione all'avvio della Pagina
// ===================================

window.onload = function() {
    visualizzaProdottiClient(); 
    aggiornaCarrelloTotale();
    document.getElementById('admin-view').style.display = 'none'; 
    document.getElementById('client-view').style.display = 'flex';
};  