'use strict'

//Creating Create Fucntionality for Genre
const CreateGenre = (e) => {
  e.preventDefault();
  let GenreName = document.querySelector("#GenreName").value
  let GenreDescription = document.querySelector("#GenreDescription").value
  fetch("http://localhost:8082/genres/create", {
      method: "POST",
      headers: {
        "Content-type": "application/json",
      },
      body: JSON.stringify({
        "name": GenreName,
        "description": GenreDescription
      }),
    })
    .then((res) => {
      if (res.status === 201) {
        location.reload()
      } else {
        throw 'Could not create the Genre please try again later'
      }
    })
    .catch((err) => console.log(err));
};

const SearchGenre = (e) => {
  e.preventDefault();

  var GenreSearch = document.querySelector("#q").value;

  document.querySelector("#GenresTitle").innerHTML = "Search for " + GenreSearch
  document.querySelector("#GenreList").innerHTML = ""

  fetch(`http://localhost:8082/search/genres/${GenreSearch}`, {
    method: "GET"
  }).then((res) => {
    if(res.status === 200){
      return res.json()
    }else{
      throw 'The response was not 200 and the album was not returned.'
    }
  }).then((data) => {
    let i=0;
    data.forEach((genre) => {
      var GenreElement = document.createElement("div");
      var GenreName = genre.name;
      var GenreDescription = genre.description;

      GenreElement.className = "ListItem col-2 ms-5 mb-5 text-center";
      GenreElement.style = "border-radius: 12px;";
      console.log(JSON.stringify(data))
      let ID = data[i].id;
      let URL = window.location
      let BaseURL = URL.protocol + "//" + URL.host;
      let FinalURL = BaseURL + `/genre?GenreID=${ID}`

      GenreElement.innerHTML = `
      <a href="${FinalURL}">
            <div class="text-center">
                <h4>${GenreName}</h4>
                <h4>${GenreDescription}</h4>
            </div>
        </a>
        `;

      document.querySelector("#GenreList").append(GenreElement);
      i++;
    });
  })
  .catch((err) => {
    alert(
      "There was a problem getting the genres from the system. Please try again later." +
      err
    );
  });
}

//Get All Genres
(function () {
  fetch(`http://localhost:8082/genres/read`, {
      method: "GET",
    })
    .then((response) => {
      if (response.status === 200) {
        return response.json();
      } else {
        throw "Response code was not 200 it was " + response.status;
      }
    })
    .then((data) => {
      let i=0;
      data.forEach((genre) => {
        var GenreElement = document.createElement("div");
        var GenreName = genre.name;
        var GenreDescription = genre.description;

        GenreElement.className = "ListItem col-2 ms-5 mb-5 text-center";
        GenreElement.style = "border-radius: 12px;";
        console.log(JSON.stringify(data))
        let ID = data[i].id;
        let URL = window.location
        let BaseURL = URL.protocol + "//" + URL.host;
        let FinalURL = BaseURL + `/genre?GenreID=${ID}`

        GenreElement.innerHTML = `
        <a href="${FinalURL}">
              <div class="text-center">
                  <h4>${GenreName}</h4>
                  <h4>${GenreDescription}</h4>
              </div>
          </a>
          `;

        document.querySelector("#GenreList").append(GenreElement);
        i++;
      });
    })
    .catch((err) => {
      alert(
        "There was a problem getting the genres from the system. Please try again later." +
        err
      );
    });
})();