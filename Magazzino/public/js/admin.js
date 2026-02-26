// ===================================
// LOGICA ADMIN (MODIFICA PRODOTTI)
// ===================================

async function visualizzaProdottiAdmin() {
    const spinner = document.getElementById('spinner-admin');
    if (spinner) spinner.style.display = 'flex';

    await carricaProdotti();
    const listaProdottiDiv = document.getElementById('lista-prodotti-admin');
    let prodottiHTML = "";

    magazzino.forEach(articolo => {
        const statoClasse = articolo.disponibile ? 'disponibile' : 'non-disponibile';

        prodottiHTML += `<div class="prodotto ${statoClasse}" data-codice="${articolo.codice}">
            <div>
                <h3>${articolo.descrizione}</h3>
                <p>Codice: ${articolo.codice} | Prezzo: <strong>&euro;${articolo.prezzo.toFixed(2)}</strong></p>
            </div>
            <div class="azioni">
                <button class="btn-modifica" onclick="modificaProdotto('${articolo.codice}')">Modifica</button>
                <button class="btn-elimina" onclick="eliminaProdotto('${articolo.codice}')">Elimina</button>
            </div>
        </div>`;
    });

    listaProdottiDiv.innerHTML = prodottiHTML;
    if (spinner) spinner.style.display = 'none';
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

async function salvaModifiche(event, oldCodice) {
    event.preventDefault();

    const form = event.target;
    const formData = new FormData(form);

    const body = {
        codice: formData.get('codice'),
        descrizione: formData.get('descrizione'),
        prezzo: parseFloat(formData.get('prezzo')),
        disponibile: form.elements.disponibile.checked
    };

    const result = await apiPUT(oldCodice, body);
    if (!result.ok) {
        mostraToast(result.error, 'error');
        return;
    }

    mostraToast('Prodotto modificato con successo!', 'success');
    await visualizzaProdottiAdmin();
    aggiornaCarrelloTotale();
}

async function eliminaProdotto(codiceArticolo) {
    if (confirm(`Sei sicuro di voler eliminare il prodotto ${codiceArticolo} dal magazzino?`)) {
        const result = await apiDELETE(codiceArticolo);
        if (!result.ok) {
            mostraToast(result.error, 'error');
            return;
        }

        delete carrello[codiceArticolo];
        cartSave();
        mostraToast('Prodotto eliminato.', 'success');
        await visualizzaProdottiAdmin();
        aggiornaCarrelloTotale();
    }
}

function mostraFormAggiunta() {
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

async function aggiungiNuovoProdotto(event) {
    event.preventDefault();
    const form = event.target;
    const formData = new FormData(form);

    const body = {
        codice: formData.get('codice'),
        descrizione: formData.get('descrizione'),
        prezzo: parseFloat(formData.get('prezzo')),
        disponibile: form.elements.disponibile.checked
    };

    const result = await apiPOST(body);
    if (!result.ok) {
        mostraToast(result.error, 'error');
        return;
    }

    mostraToast('Prodotto aggiunto con successo!', 'success');
    form.remove();
    await visualizzaProdottiAdmin();
}
