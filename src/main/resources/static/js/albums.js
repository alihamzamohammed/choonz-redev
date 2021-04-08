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


//Read all Albunms
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
        AlbumElement = document.createElement("option");
        AlbumName = album.name;
        AlbumId = album.id;

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
  let dropdown = document.getElementById("#Artists");
  dropdown.length = 0;

  let defaultOption = document.createElement("Option");

  fetch(`http://localhost:8082/artists/read`, {
    method: "GET",
  })
    .then((response) => {
      console.log(resonse.status);
      if (response.status === 200) {
        return response.json();
      } else {
        throw (
          "Error retreving Artist information. STATUS CODE: " + response.status
        );
      }
    })
    .then((artist) => {
        console.log(JSON.stringify(artist))
      data.forEach((artist) => {
        ArtistElement = document.createElement("option");
        ArtistName = artist.name;
        ArtistId = artist.id;
      });
    });
});
