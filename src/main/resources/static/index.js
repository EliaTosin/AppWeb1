
var search = document.getElementById("searchField");
search.addEventListener("input", searchItems);
function searchItems(event) {
    if (search.value.length > 2) {
        var context = document.querySelector('base').getAttribute('href');
        var url = context + "search/" + event.target.value;
        var options = {method : "GET"};

        fetch(url, options)
            .then(function (response) {
                if (!response.ok) {

                }
                return response.json()
            })
            .then(function (elements) {
                var title = '<h2 class="pb-2 border-bottom text-white" id="searchTitle">Risultati della ricerca: '+ search.value +'</h2>' +
                            '<div class="row row-cols-1 row-cols-lg-3 align-items-stretch g-4 py-5">';
                var articles = "";
                for (var i = 0; i < elements.length; i++) {
                    var fullDate = elements[i].date;
                    var dataSplit = fullDate.split("-");
                    var day = dataSplit[2].split("T")[0]; //extract the day from the datetime format
                    var dateParse =  day + "/" + dataSplit[1] + "/" + dataSplit[0];
                    articles += '<article>\n' +
                        '                        <div class="col">\n' +
                        '                            <div class="card card-cover h-100 overflow-hidden text-white bg-dark rounded-5 shadow-lg">\n' +
                        '                                <img src="' + context + 'item/'+ elements[i].id +'/image" alt="Immagine Item" width="390" height="300" style="align-self: center; padding: 10px; resize: none">\n' +
                        '                                <div class="d-flex flex-column h-100 p-5 pb-3 text-white text-shadow-1">\n' +
                        '                                    <h2 class="mb-5 display-6 lh-1 fw-bold">'+ elements[i].title +'</h2>\n' +
                        '                                    <ul class="d-flex list-unstyled mt-auto">\n' +
                        '                                        <a class="btn btn-primary h-50 w-50" href="' + context + 'item/'+ elements[i].id +'" role="button">Informazioni</a>\n' +
                        '                                        <li class="d-flex align-items-center me-3 ps-5">\n' +
                        '                                            <time>'+ dateParse +'</time>\n' +
                        '                                        </li>\n' +
                        '                                        <li class="d-flex align-items-center ">\n' +
                        '                                            <label>'+ elements[i].author.username +'</label>\n' +
                        '                                        </li>\n' +
                        '                                    </ul>\n' +
                        '                                </div>\n' +
                        '                            </div>\n' +
                        '                        </div>\n' +
                        '</article>';
                }

                articles += '</div>';
                document.getElementById("buyItems").innerHTML = "";
                document.getElementById("sellItems").innerHTML = "";
                document.getElementById("sellItems").innerHTML = title + articles;
            })
    }
}