const form = document.querySelector("#todo-form");
const input = document.querySelector("#todo-input");
const list = document.querySelector("#todo-list");

form.addEventListener("submit", function(event) {
    event.preventDefault();
    const text = input.value.trim();
    if (text !== "") {
        addItem(text);
        input.value = "";
    }
});

// Funzione per aggiungere un nuovo elemento
function addItem(text) {
    const li = document.createElement("li");

    const span = document.createElement("span");
    span.textContent = text;

    const delBtn = document.createElement("button");
    delBtn.textContent = "Elimina";
    delBtn.classList.add("delete-btn");

    delBtn.addEventListener("click", () => {
        li.remove();
        updateNumbers();
    });

    li.appendChild(span);
    li.appendChild(delBtn);
    list.appendChild(li);

    updateNumbers();
}

// Aggiorna la numerazione dopo ogni modifica
function updateNumbers() {
    const items = list.querySelectorAll("li");
    items.forEach((item, index) => {
        //Aggiungiamo il numero prima del testo
        const span = item.querySelector("span");
        span.textContent = `${index + 1}. ${span.textContent.replace(/^\d+\. /, '')}`;
    });
}