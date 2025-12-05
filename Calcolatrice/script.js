let number1 = document.querySelector("#input1_number");
let number2 = document.querySelector("#input2_number");
let calculate_botton = document.querySelector("#calculate_button");
let clear = document.querySelector("#clear");
let result = document.querySelector("#result");
calculate_botton.addEventListener("click", function() {
    let operator = document.querySelector("#operator").value;
    // controllo degli input numerici se sono pieni
    if(number1.value === '' || number2.value === '') {
        alert("Almeno uno dei due campi numerici è vuoto. Per favore, inserisci dei numeri.");
        return;
    }

    switch (operator) {
        case "addizzione":
            result.textContent = parseFloat(number1.value) + parseFloat(number2.value);
            break;
        case "sottrazione":
            result.textContent = parseFloat(number1.value) - parseFloat(number2.value);
            break;
        case "moltiplicazione":
            result.textContent = parseFloat(number1.value) * parseFloat(number2.value);
            break;
        case "divisione":
            // controllo divisione per zero
            if (number2.value != "0") {
                console.log(number2.value);
                result.textContent = parseFloat(number1.value) / parseFloat(number2.value);
            } else {
                result.textContent = "Errore: Divisione per zero";
            }
            break;   
    }
});


clear.addEventListener("click", function() {
    number1.value = '';
    number2.value = '';
    result.textContent = '';
});