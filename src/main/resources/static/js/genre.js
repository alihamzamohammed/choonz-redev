"use strict";

let EditGenre = () => {
  const params = new URLSearchParams(window.location.search);
  let genre = params.get("GenreID");

  let genreName = document.querySelector("#GenreName").value;
  console.log("Genre Name: " + genreName);

  let genreDescription = document.querySelector("#GenreDescription")
    .value;
  console.log("Genre Description: " + genreDescription);

  const updatedGenre = {
    GenereName: genreName,
    GenreDescription: genreDescription
  };

  fetch(`http://localhost:8082/genres/${genre}`, {
    method: "PUT",
    headers: {
      "Content-type": "application/json",
    },
    body: JSON.stringify(updatedGenre),
  })
    .then((res) => res.json())
    .then((data) => console.log(`Success ${data}`))
    .catch((error) => console.log(`Failure ${error}`));
};
// Creating Delete Functionality

let DeleteGenre = async (genre) => {
  //  var genre = parseInt(document.querySelector("#GenreList").value);
  
  const params = new URLSearchParams(window.location.search);
  let genreID = params.get("GenreID");
  
  const response = await fetch(`http://localhost:8082/genres/${genreID}`, {
    method: "DELETE",
  });
  if (response.status != 204) {
    alert("The Delete Denied, the Genre must be valid");
    console.error(`Error: Status code ${reponse.status}\n${response.json}`);
    return response.status;
  }
  alert("Genre deleted");
  console.log("Genre:" + genreID + " deleted");
};

//Get Genre by Id
(function () {

  const params = new URLSearchParams(window.location.search);
  let genre = params.get("GenreID");

  fetch(`http://localhost:8082/genres/read/${genre}`, {
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
      var Track = document.createElement("div");
      var GenreName = data.name;
      console.log(JSON.stringify(data))
     
      document.querySelector("#GenreNameTitle").innerHTML = GenreName;

      Track.className = "ListItem col-2 ms-5 mb-5 text-center mt-5";
      Track.style = "border-radius: 12px;";
      //need to finish this ask if possible to include the genre in album or track rather than making a seperate request
      Track.innerHTML = `

            <div class="text-center">
                <h4>Track name</h4>
                <h4>Artist Name</h4>
                <h4>Album name</h4>
                <h4>Genre(s)</h4>
            </div>
  
        `;

      document.querySelector("#TrackList").append(Track);
    })
    .catch((err) => {
      alert(
        "There was a problem getting the genre from the system. Please try again later." +
          err
      );
    });
})();
