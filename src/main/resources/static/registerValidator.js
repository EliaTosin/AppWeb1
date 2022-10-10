var validName = false;
var nome = document.getElementById("name");
nome.addEventListener("input", validateName);
nome.addEventListener("input", validateAll);

function validateName() {
    if (nome.value.length == 0) {
        nome.style.border = "2px solid red";
        nome.style.color= "red";
        validName = false;
    } else {
        var regex = /^[a-zA-Z]+$/;
        if(regex.test(nome.value)) {
            nome.style.border = "";
            nome.style.color= "black";
            validName = true;
        } else {
            nome.style.border = "2px solid red";
            nome.style.color= "red";
            validName = false;
        }
    }
}

var validSurname = false;
var cognome = document.getElementById("surname");
cognome.addEventListener("input", validateSurname);
cognome.addEventListener("input", validateAll);

function validateSurname() {
    if (cognome.value.length == 0) {
        cognome.style.border = "2px solid red";
        cognome.style.color= "red";
        validSurname = false;
    } else {
        var regex = /^[a-zA-Z]+$/;
        if(regex.test(cognome.value)) {
            cognome.style.border = "";
            cognome.style.color= "black";
            validSurname = true;
        } else {
            cognome.style.border = "2px solid red";
            cognome.style.color= "red";
            validSurname = false;
        }
    }
}

var validUsername = false;
var username = document.getElementById("username");
username.addEventListener("input", validateUsername);
username.addEventListener("input", validateAll);

function validateUsername() {
    if (username.value.length == 0) {
        username.style.border = "2px solid red";
        username.style.color= "red";
        validUsername = false;
    } else {
        var regex = /^[\w]+$/;
        if(regex.test(username.value)) {
            username.style.border = "";
            username.style.color= "black";
            validUsername = true;
        } else {
            username.style.border = "2px solid red";
            username.style.color= "red";
            validUsername = false;
        }
    }
}

var validPsw = false;
var password = document.getElementById("password");
password.addEventListener("input", validatePassword);
password.addEventListener("input", validateAll);

function validatePassword() {
    if (password.value.length < 8 || password.value.length > 15) {
        password.style.border = "2px solid red";
        password.style.color= "red";
        validPsw = false;
    } else {
        var regex = /^[\w]+$/;
        if(regex.test(password.value)) {
            password.style.border = "";
            password.style.color= "black";
        } else {
            password.style.border = "2px solid red";
            password.style.color= "red";
            validPsw = false;
        }
    }
}


var password2 = document.getElementById("password2");
password2.addEventListener("input", validatePassword2);
password2.addEventListener("input", validateAll);
function validatePassword2() {
    if (password2.value === password.value) {
        password2.style.border = "";
        password2.style.color= "black";
        validPsw = true;
    } else {
        password2.style.border = "2px solid red";
        password2.style.color= "red";
        validPsw = false;
    }
}

var submit = document.getElementById("btnSubmit");
function validateAll() {
    if (validName && validSurname && validUsername && validPsw) {
        submit.style.display= "block";
    } else {
        submit.style.display= "none";
    }
}
