let person_info_first = document.querySelector("#person_info_first");
let button_modify = document.querySelector("#button_modify");

const person = {
    name : "John",
    surname : "Doe",
    age : 30,
    date: "31/1/2005"
}
person_info_first.innerHTML = person.name + " " + person.surname + ", Age: " + person.age;

button_modify.addEventListener("click", function() {
    let x = person;
    x.name = "Jane";
    document.querySelector("#person_info_second").innerHTML = person.name + " " + person.surname + ", Age: " + person.age;
});

console.log(person.name); // Output: Jane 