"use strict";

const createAlbum = document.querySelector("#CreateAlbumButton");
createAlbum.addEventListener("click", (event) => {
  event.preventDefault();
  CreateAlbum();
});

const EditAlbum = document.querySelector("#EditAlbumButton");
EditTrack.addEventListener("click", (event) => {
  event.preventDefault();
  EditAlbum();
});

const deleteTrack = document.querySelector("#DeleteAlbumButton");
deleteTrack.addEventListener("click", (event) => {
  event.preventDefault();
  DeleteAlbum();
});

//Create a Track
const CreateAlbum = () => {
    let album = document.querySelector("#Track").value;
    console.log(album);
    console.log(abumName);
    console.log(albumCoverArt)
   ;
  
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

  //edit tracks
  let EditAlbum = () => {
    const params = new URLSearchParams(window.location.search);
    let album = params.get("AlbumId");
  
    let albumName = document.querySelector("#AlbumName").value;
    console.log("Album Name: " + albumName);
  
    let artistName = document.querySelector("#AlbumDuration").value;
    console.log("Artist Name: " + artistName);
  
    let genre = document.querySelector("#Genre").value;
    console.log("Track Lyrics: " + trackLyrics);
  
    const updatedAlbum = {
        name: trackName,
        duration: trackDuration,
        lyrics: trackLyrics
    };
  
    fetch(`http://localhost:8082/albums/${album}`, {
      method: "PUT",
      headers: {
        "Content-type": "application/json",
      },
      body: JSON.stringify(updatedAlbum),
    })
      .then((res) => res.json())
      .then((data) => console.log(`Success ${data}`))
      .catch((error) => console.log(`Failure ${error}`));
  };

  //Delete a Track
  let DeleteTrack = async (album) => {
    //  var playlist = parseInt(document.querySelector("#PlaylistList").value);
    
    const params = new URLSearchParams(window.location.search);
    let album = params.get("TrackId");
    
    const response = await fetch(`http://localhost:8082/albums/${album}`, {
      method: "DELETE",
    });
    if (response.status != 204) {
      alert("Delete Denied, the Album must be valid");
      console.error(`Error: Status code ${reponse.status}\n${response.json}`);
      return response.status;
    }
    alert("Album deleted");
    console.log("Album:" + album + "deleted");
  };

  //Read all tracks
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
      .then((data)=> {
        data.forEach(album => {
          AlbumElement = document.createElement("div");
          AlbumName = album.name;
          AlbumArtistName = album.artist.name;
          AlbumGenre = album.genre.name;
         
  
          TrackElement.className = "ListItem col-2 ms-5 mb-5 text-center mt-5";
          TrackElement.style = "border-radius: 12px;";
  
          TrackElement.innerHTML = `
         
              <div class="text-center">
                  <h4>${TrackName}</h4>
                  <h4>${TrackDuration} Name</h4>
                  <h4>${TrackLyrics}</h4>
              </div>
          
          `;
  
          document.querySelector("#TrackList").append(AlbumElement);
        });
      })
      .catch((err) => {
        alert(
          "There was a problem getting the albums from the system. Please try again later." +
            err
        );
      });
  })();

  // read by id
  (function () {
    // var playlist = parseInt(document.querySelector("#PlaylistList").value);
  
    const params = new URLSearchParams(window.location.search);
    let track = params.get("TrackId");
  
    fetch(`http://localhost:8082/albums/${album}`, {
      method: "GET",
    })
      .then((response) => {
        if (response.status === 200) {
          return response.json();
        } else {
          throw "Response code was not 200 it was " + response.status;
        }
      })
      .then((album) => { 
        AlbumElement = document.createElement("div");
        AlbumName = album.name;
        AlbumArtistName = album.artist.name;
        AlbumGenre = album.genre.name;
  
        AlbumElement.className = "ListItem col-2 ms-5 mb-5 text-center mt-5";
        AlbumElement.style = "border-radius: 12px;";
  
        TrackElement.innerHTML = `
         
              <div class="text-center">
              <h4>${AlbumName}</h4>
              <h4>${AlbumArtistName} Name</h4>
              <h4>${AlbumGenre}</h4>
              </div>
    
          `;
  
        document.querySelector("#AlbumList").append(AlbumElement);
      })
      .catch((err) => {
        alert(
          "There was a problem getting the album from the system. Please try again later." +
            err
        );
      });
  })();

// Get All Artists for the Album Creation and Edit Drop Down Menu

(function() {

    let dropdown = document.getElementById('Artist Dropdown');
    dropdown.length = 0;

    let defaultOption = document.createElement('Artists');
    defaultOption.text = "Choose an Artist for Album";

    dropdown.addEventListener(defaultOption);
    dropdown.selectedIndex = 0;
   
    fetch(`http:localhost:8082/artists`, {
        method : "GET",
    })
    .then(response) => {
    if (response.status === 200) {
        return response.json() 
    } else {
        return "Error retreving Artist information. STATUS CODE: " + response.status;
    }
    .then((artist) => {
        data.forEach(artist => {
        ArtistElement = document.createElement("div");
        ArtistName = artist.name;

})
