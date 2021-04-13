'use strict'

//Creating Create Fucntionality for Genre
const CreateGenre = () => {
    let genre= document.querySelector("#GenreList").value;
    console.log(genre);
    console.log(genreName);
    console.log(genreDescription);
  
  
    const obj = {
      name: genreName,
      description: genreDescription,
     
    };
    fetch("http://localhost:8082/genres", {
    method: "POST",
    headers: {
      "Content-type": "application/json",
    },
    body: JSON.stringify(obj),
  })
    .then((res) => res.json())
    .then((data) => console.log(data))
    .catch((err) => console.err(err));
};


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
        data.forEach((genre) => {
          var GenreElement = document.createElement("div");
          var GenreName = genre.name;
          var GenreDescription = genre.description;
  
          GenreElement.className = "ListItem col-2 ms-5 mb-5 text-center";
          GenreElement.style = "border-radius: 12px;";
  
          GenreElement.innerHTML = `
         
              <div class="text-center">
                  <h4>${GenreName}</h4>
                  <h4>${GenreDescription}</h4>
              </div>
          
          `;
  
          document.querySelector("#GenreList").append(GenreElement);
        });
      })
      .catch((err) => {
        alert(
          "There was a problem getting the genres from the system. Please try again later." +
            err
        );
      });
  })();