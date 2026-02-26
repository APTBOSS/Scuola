// ===================================
// UX — Toast, Auth, Vista Client
// ===================================

function mostraToast(messaggio, tipo = 'info') {
    const container = document.getElementById('toast-container');
    if (!container) return;

    const toast = document.createElement('div');
    toast.className = `toast toast-${tipo}`;
    toast.textContent = messaggio;
    container.appendChild(toast);

    requestAnimationFrame(() => toast.classList.add('toast-visible'));

    setTimeout(() => {
        toast.classList.remove('toast-visible');
        toast.classList.add('toast-hide');
        setTimeout(() => toast.remove(), 400);
    }, 4000);
}

function mostraFormPassword() {
    document.getElementById('access-admin-btn').style.display = 'none';
    document.getElementById('password-form-container').style.display = 'flex';
    document.getElementById('admin-password').focus();
}

function checkPassword() {
    const passwordInput = document.getElementById('admin-password');
    const enteredPassword = passwordInput.value;

    if (enteredPassword === ADMIN_PASSWORD) {
        mostraToast('Accesso Admin effettuato!', 'success');
        loginAdmin();
    } else {
        mostraToast('Password errata!', 'error');
        passwordInput.value = '';
        passwordInput.focus();
    }
}

async function loginAdmin() {
    isAdminView = true;
    document.getElementById('password-form-container').style.display = 'none';
    document.getElementById('client-view').style.display = 'none';
    document.getElementById('admin-view').style.display = 'flex';
    await visualizzaProdottiAdmin();
}

async function logoutAdmin() {
    isAdminView = false;
    document.getElementById('admin-view').style.display = 'none';
    document.getElementById('client-view').style.display = 'flex';
    document.getElementById('access-admin-btn').style.display = 'block';
    document.getElementById('admin-password').value = '';
    await visualizzaProdottiClient();
}

async function visualizzaProdottiClient() {
    const spinner = document.getElementById('spinner-client');
    if (spinner) spinner.style.display = 'flex';

    await carricaProdotti();
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
                <p>Codice: ${articolo.codice} | Prezzo: <strong>&euro;${articolo.prezzo.toFixed(2)}</strong></p>
            </div>
            <div class="azioni">
                ${bottoneCarrello}
            </div>
        </div>`;
    });

    listaProdottiDiv.innerHTML = prodottiHTML;
    if (spinner) spinner.style.display = 'none';
}

window.onload = async function () {
    cartLoad();
    await visualizzaProdottiClient();
    aggiornaCarrelloTotale();
    aggiornaCartBadge();
    document.getElementById('admin-view').style.display = 'none';
    document.getElementById('client-view').style.display = 'flex';
};
