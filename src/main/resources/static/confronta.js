var search = document.getElementById("addConfrontaBtn");

search.addEventListener("click", addItem);
function addItem(event) {
    var context = document.querySelector('base').getAttribute('href');
    var str = window.location.pathname;
    var idItem = str.split("/")[2];
    var username = document.getElementById("username").innerText;


    var url = context + "confronta/?q=" + username + "-" + idItem;
    var options = {method : "POST"};

    fetch(url, options)
        .then(function (response) {
            if (!response.ok) {

            }
            return response
        })
        .then(function (ret) {
            // if (ret > 0) {
                document.getElementById("linkConfronta").innerHTML = "";
                document.getElementById("linkConfronta").innerHTML = "Confronta (" + ret.body + ")";
            // }
        })
}