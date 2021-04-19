"use strict";



//edit artists
const EditArtist = (e) => {
  e.preventDefault(); //in a form if i dont prevent it will launch a get request and remove the paramters
  const params = new URLSearchParams(window.location.search);
  let artist = params.get("ArtistID");

  var artistNameEdit = document.querySelector("#ArtistNameModal").value;
  fetch(`http://localhost:8082/artists/update/${artist}`, {
      method: "PUT",
      headers: {
        "Content-type": "application/json",
      },
      body: JSON.stringify({
        name: artistNameEdit
      }),
    })
    .then((res) => {
      if (res.status === 202) {
        return res.json();
      } else {
        throw "The artist information couldn't be upated please try again later";
      }
    })
    .then((data) => location.reload())
    .catch((error) => console.log(`Failure ${error}`));
};

const DeleteArtist = async () => {
  //  var playlist = parseInt(document.querySelector("#PlaylistList").value);

  const params = new URLSearchParams(window.location.search);
  const artist = params.get("ArtistID");

  const response = await fetch(`http://localhost:8082/artists/delete/${artist}`, {
    method: "DELETE",
  });
  if (response.status != 204) {
    alert("The Delete Denied, the Artist must be valid");
    console.error(`Error: Status code ${reponse.status}\n${response.json}`);
    return response.status;
  }else{
    location.replace("http://localhost:8082/artists")
  }
  
};



// read by id
(function () {
  const params = new URLSearchParams(window.location.search);
  let artist = params.get("ArtistID");

  fetch(`http://localhost:8082/artists/read/${artist}`, {
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
      console.log(JSON.stringify(data))
      let ArtistName = data.name;

      document.querySelector("#ArtistName").innerHTML = ArtistName;
      document.querySelector("#ArtistID").innerHTML = "Artist ID: " + data.id;
      document.querySelector("#ModalTitle").innerHTML = "Edit " + data.name
      data.albums.forEach(album => {
        console.log(JSON.stringify(album))

        data.albums.forEach(album => {

          var albumDiv = document.createElement('div')
          var AlbumName = album.name;
          var Genres = album.genre
          var GenreString = "";
          var i = 0;
          Genres.forEach((Genre) => {
            if (i == 0) {
              GenreString += Genre.name
            } else {
              GenreString += ", " + Genre.name
            }
            i++;
          })
          //need to continue here but wait for ft artist to be added
          albumDiv.className = "ListItem col-2 ms-5 mb-5 text-center"
          albumDiv.style = "border-radius: 12px;"

          let URL = window.location
          let BaseURL = URL.protocol + "//" + URL.host;
          let FinalURL = BaseURL + `/album?AlbumID=${album.id}`
          albumDiv.innerHTML = `
          <a href="${FinalURL}">
        <img src="${album.cover}" class="img-fluid mt-3" alt="Album Cover"
        style="border-radius: 12px;">
        <div class="text-center">
            <h4>${AlbumName}</h4>
            <h4>${GenreString}</h4>
        </div>
        </a>
        `
          document.querySelector("#AlbumsList").append(albumDiv)
        })
      });

    })
    .catch((err) => {
      alert(
        "There was a problem getting the artist from the system. Please try again later." +
        err
      );
    });
})();