"use strict";



//edit artists
const EditArtist = () => {
  const params = new URLSearchParams(window.location.search);
  let artist = params.get("ArtistID");

  let artistName = document.querySelector("#ArtistName").value;
  console.log("Artist Name: " + artistName);

  let genre = document.querySelector("#genre").value;
  console.log("Genre: " + genre);

  const updatedArtist = {
    name: artistName,
    genre: genre,
  };

  fetch(`http://localhost:8082/playlist/${artist}`, {
    method: "PUT",
    headers: {
      "Content-type": "application/json",
    },
    body: JSON.stringify(updatedArtist),
  })
    .then((res) => res.json())
    .then((data) => console.log(`Success ${data}`))
    .catch((error) => console.log(`Failure ${error}`));
};

const DeleteArtist = async (artists) => {
  //  var playlist = parseInt(document.querySelector("#PlaylistList").value);

  const params = new URLSearchParams(window.location.search);
  const artist = params.get("ArtistID");

  const response = await fetch(`http://localhost:8082/artists/${artist}`, {
    method: "DELETE",
  });
  if (response.status != 204) {
    alert("The Delete Denied, the Artist must be valid");
    console.error(`Error: Status code ${reponse.status}\n${response.json}`);
    return response.status;
  }
  alert("Artist deleted");
  console.log("Artist:" + artist + "artist");
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
        console.log("200 achieved")
        return response.json();
      } else {
        throw "Response code was not 200 it was " + response.status;
      }
    })
    .then((data) => {
      console.log(JSON.stringify(data))
      let ArtistName = data.name;

      document.querySelector("#ArtistName").text = ArtistName;
      data.albums.forEach(album => {
        console.log(JSON.stringify(album))
        let albumDiv = document.createElement('div')
        
        let AlbumName = data.name;
        let ArtistName = data.

        //need to continue here but wait for ft artist to be added
        albumDiv.className = "ListItem col-2 ms-5 mb-5 text-center"
        albumDiv.style = "border-radius: 12px;"

        albumDiv.innerHTML = `
        <img src="./img/Choonz.png" class="img-fluid mt-3" alt="Album Cover"
        style="border-radius: 12px;">
        <div class="text-center">
            <h4>${AlbumName}</h4>
            <h4>${ArtistName}</h4>
            <h4>${Genres}</h4>
        </div>
        `
      });

    })
    .catch((err) => {
      alert(
        "There was a problem getting the artist from the system. Please try again later." +
          err
      );
    });
})();
