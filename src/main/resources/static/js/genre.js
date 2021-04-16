"use strict";

let EditGenre = (e) => {
  e.preventDefault();
  const params = new URLSearchParams(window.location.search);
  let genre = params.get("GenreID");

  var GenreName = document.querySelector("#GenreNameModal").value;
  var GenreDescription = document.querySelector("#GenreDescriptionModal").value;
  console.log(GenreName + " " + GenreDescription)

  fetch(`http://localhost:8082/genres/update/${genre}`, {
      method: "PUT",
      headers: {
        "Content-type": "application/json",
      },
      body: JSON.stringify({
        "name" : GenreName,
        "description" : GenreDescription
      }),
    })
    .then((res) => {
      if(res.status === 202){
        return res.json();
      }else{
        throw "Status was " + res.status
      }
    })
    .then((data) => location.reload())
    .catch((error) => alert("Unable to edit the genre. "+error));
};
// Creating Delete Functionality

let DeleteGenre = async () => {

  const params = new URLSearchParams(window.location.search);
  let genreID = params.get("GenreID");

  const response = await fetch(`http://localhost:8082/genres/delete/${genreID}`, {
    method: "DELETE",
  });
  if (response.status != 204) {
    alert("The Delete Denied, the Genre must be valid");
    console.error(`Error: Status code ${reponse.status}\n${response.json}`);
    return response.status;
  }else{
    location.replace("http://localhost:8082/genres");
  }
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
      document.querySelector("#DisplayGenreID").innerHTML = "Genre ID: " + data.id;
      var GenreName = data.name;
      console.log(JSON.stringify(data))

      document.querySelector("#GenreNameTitle").innerHTML = GenreName;
      document.querySelector("#GenreDescription").innerHTML = data.description;
      data.albums.forEach((album) => {
        var ArtistName = album.artist.name;
        album.tracks.forEach((track) => {
          var Track = document.createElement("div");
          Track.className = "ListItem col-2 ms-5 mb-5 text-center mt-5";
          Track.style = "border-radius: 12px;";
          //need to finish this ask if possible to include the genre in album or track rather than making a seperate request
          Track.innerHTML = `
          <a href='http://localhost:8082/track?TrackID=${track.id}'>
            <div class="text-center">
                <h4>${track.name}</h4>
                <h4>${ArtistName}</h4>
                <h4>${album.name}</h4>
                <h4>${data.name}</h4>
            </div>
            </a>
  
        `;

          document.querySelector("#TrackList").append(Track);
        })
      })

    })
    .catch((err) => {
      alert(
        "There was a problem getting the genre from the system. Please try again later." +
        err
      );
    });
})();