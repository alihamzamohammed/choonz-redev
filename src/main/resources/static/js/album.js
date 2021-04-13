'use strict';

const EditAlbum = document.querySelector("#EditAlbumButton");
EditTrack.addEventListener("click", (event) => {
  event.preventDefault();
  EditAlbumFunc();
});

const deleteTrack = document.querySelector("#DeleteAlbumButton");
deleteTrack.addEventListener("click", (event) => {
  event.preventDefault();
  DeleteAlbum();
});

//edit tracks
const EditAlbumFunc = () => {
    const params = new URLSearchParams(window.location.search);
    let album = params.get("AlbumId");
  
    let albumName = document.querySelector("#AlbumName").value;
    console.log("Album Name: " + albumName);
  
    let coverArt = document.querySelector("#AlbumCoverArt").value
    console.log("Cover art: " + coverArt)

    let artistName = document.querySelector("#AlbumDuration").value;
    console.log("Artist Name: " + artistName);
  
    let genre = document.querySelector("#Genre").value;
    console.log("Genre: " + genre);
  
    const updatedAlbum = {
     name: albumName,
     cover: coverArt,
     artist: artistName,
     genre: genre
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
    let albumParam = params.get("TrackId");
  
    const response = await fetch(`http://localhost:8082/albums/${albumParam}`, {
      method: "DELETE",
    });
    if (response.status != 204) {
      alert("Delete Denied, the Album must be valid");
      console.error(`Error: Status code ${reponse.status}\n${response.json}`);
      return response.status;
    }
    alert("Album deleted");
    console.log("Album:" + albumParam + "deleted");
  };
  
