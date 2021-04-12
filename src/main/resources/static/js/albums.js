"use strict";

const createAlbum = document.querySelector("#CreateAlbum");
createAlbum.addEventListener("click", (event) => {
  event.preventDefault();
  CreateAlbum();
});


//Create a Track
const CreateAlbum = () => {
  let album = document.querySelector("#Track").value;
  console.log(album);
  console.log(abumName);
  console.log(albumCoverArt);

  const obj = {
    name: albumName,
    duration: albumCoverArt,
    //Need to insetr
  };

  fetch("http://localhost:8082/albums", {
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
        var AlbumElement = document.createElement("option");
        var AlbumName = album.name;
        var AlbumId = album.id;

        AlbumElement.value = AlbumId;
        AlbumElement.text = AlbumName;

        document.querySelector("#Artists")

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
