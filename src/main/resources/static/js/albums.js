"use strict";

//Create a Track
const CreateAlbum = () => {

  let AlbumName = document.querySelector("#AlbumName")
  let Cover = document.querySelector("#AlbumCoverArt")
  let ArtistID

  fetch("http://localhost:8082/albums/create", {
      method: "POST",
      headers: {
        "Content-type": "application/json",
      },
      body: JSON.stringify({

      }),
    })
    .then((res) => res.json())
    .then((data) => console.log(data))
    .catch((err) => console.err(err));
};


//Read all Albums
(function () {
  fetch(`http://localhost:8082/albums/read`, {
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
      data.forEach((album) => {
        console.log(JSON.stringify(album))
        var AlbumElement = document.createElement("div");
        var AlbumName = album.name;
        var ArtistName = album.artist.name;
        var AlbumId = album.id;
        var Genres = album.genre
        var GenreString = "";
        let i = 0;
        Genres.forEach((Genre) => {
          if (i == 0) {
            GenreString += Genre.name
          } else {
            GenreString += ", " + Genre.name
          }
          i++;
        })

        let URL = window.location
        let BaseURL = URL.protocol + "//" + URL.host;
        let FinalURL = BaseURL + `/album?AlbumID=${AlbumId}`
        AlbumElement.className = "ListItem col-2 ms-5 mb-5 text-center";
        AlbumElement.style = "border-radius: 12px;";
        AlbumElement.innerHTML = `
            <a href="${FinalURL}">
              <img src="./img/Choonz.png" class="img-fluid mt-3" alt="Album Cover"
                  style="border-radius: 12px;">
              <div class="text-center">
                  <h4>${AlbumName}</h4>
                  <h4>${ArtistName}</h4>
                  <h4>${GenreString}</h4>
              </div>
            </a>
          `;
        document.querySelector("#AlbumsList").append(AlbumElement)

      });
    })
    .catch((err) => {
      alert(
        "There was a problem getting the albums from the system. Please try again later." +
        err
      );
    });
})();

// Get All Artists for the Album Creation and Edit Drop Down Menu

(function () {
  //Artists
  fetch("http://localhost:8082/artists/read", {
      method: "GET",
    })
    .then((response) => {
      if (response.status === 200) {
        return response.json();
      } else {
        throw (
          "Error retreving Artist information. STATUS CODE: " + response.status
        );
      }
    })
    .then((data) => {
      data.forEach((artist) => {
        var ArtistElement = document.createElement("option");
        var ArtistName = artist.name;
        var ArtistId = artist.id;

        ArtistElement.value = ArtistId
        ArtistElement.text = ArtistName

        document.querySelector("#Artists").append(ArtistElement)
      });
    });

  //Genres
  fetch("http://localhost:8082/genres/read", {
      method: "GET",
    })
    .then((response) => {
      if (response.status === 200) {
        return response.json();
      } else {
        throw (
          "Error retreving Artist information. STATUS CODE: " + response.status
        );
      }
    })
    .then((data) => {

      data.forEach((genre) => {
        var GenreElement = document.createElement("option");
        var GenreName = genre.name;
        var GenreId = genre.id;

        GenreElement.value = GenreId
        GenreElement.text = GenreName

        document.querySelector("#Genres").append(GenreElement)
      });
    });
})();