let n = 2;
let btn_add = document.querySelector("#add");
let btn_remove = document.querySelector("#remove");
let btn_remove_last_item = document.querySelector("#remove_last_item");
let btn_remove_chosen_item = document.querySelector("#remove_chosen_item");
let input_id = document.querySelector("#input_id");

function add_paragraf () {
    n++;
    const para = document.createElement("p");
    para.id = "p" + n;
    para.textContent = "Questo è il nuovo paragrafo con id: " + n;
    document.getElementById("div1").appendChild(para);
}

function remove_paragraf () {
    document.querySelectorAll("#div1 p").forEach(p => p.remove());
    n = 0;
}

function remove_last_item () {
    document.getElementById("p" + n).remove();
    n--;
}

function remove_chosen_item() {
    document.getElementById("p" + input_id.value).remove();    
}

btn_add.addEventListener("click", add_paragraf);
btn_remove.addEventListener("click", remove_paragraf);
btn_remove_last_item.addEventListener("click", remove_last_item);
btn_remove_chosen_item.addEventListener("click", remove_chosen_item);