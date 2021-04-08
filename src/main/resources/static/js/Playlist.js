"use strict";

const createList = document.querySelector("#CreatePlaylistButton");
createList.addEventListener("click", (event) => {
  event.preventDefault();
  CreatePlaylist();
});

const EdiList = document.querySelector("#EditPlaylistButton");
createList.addEventListener("click", (event) => {
  event.preventDefault();
  EditPlaylist();
});

const deletePlaylist = document.querySelector("#DeletePlaylistButton");
createList.addEventListener("click", (event) => {
  event.preventDefault();
  DeletePlaylist();
});

//Creating Create Fucntionality for Playlists
const CreatePlaylist = () => {
  let playlist = document.querySelector("#PlaylistList").value;
  console.log(playlist);
  console.log(playlistName);
  console.log(playlistDescription);
  console.log(PlaylistCoverArt);

  const obj = {
    PlaylistName: playlistName,
    PlaylistDescription: playlistDescription,
    PlaylistCoverArt: PlaylistCoverArt,
  };

  fetch("http://localhost:8082/playlist", {
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

// Creating Update Functionality for PokeList
let EditPlaylist = () => {
  const params = new URLSearchParams(window.location.search);
  let playlist = params.get("PlaylistId");

  let playlistName = document.querySelector("#PlaylistName").value;
  console.log("Playlist Name: " + playlistName);

  let playlistDescription = document.querySelector("#PlaylistDescription")
    .value;
  console.log("Playlist Description: " + playlistDescription);

  let playlistCoverArt = document.querySelector("#PlaylistCoverArt").value;
  console.log("Playlist Cover Art: " + playlistCoverArt);

  const updatedPlaylist = {
    PlaylistName: playlistName,
    PlaylistDescription: playlistDescription,
    PlaylistCoverArt: PlaylistCoverArt,
  };

  fetch(`http://localhost:8082/playlist/${playlist}`, {
    method: "PUT",
    headers: {
      "Content-type": "application/json",
    },
    body: JSON.stringify(updatedPlaylist),
  })
    .then((res) => res.json())
    .then((data) => console.log(`Success ${data}`))
    .catch((error) => console.log(`Failure ${error}`));
};
// Creating Delete Functionality

let DeletePlaylist = async (playlist) => {
  //  var playlist = parseInt(document.querySelector("#PlaylistList").value);
  
  const params = new URLSearchParams(window.location.search);
  let playlist = params.get("PlaylistId");
  
  const response = await fetch(`http://localhost:8082/playlist/${playlist}`, {
    method: "DELETE",
  });
  if (response.status != 204) {
    alert("The Delete Denied, the Playlist must be valid");
    console.error(`Error: Status code ${reponse.status}\n${response.json}`);
    return response.status;
  }
  alert("Playlist deleted");
  console.log("PlayList:" + playlist + "deleted");
};

//Get All Playlists
(function () {
  fetch(`http://localhost:8082/playlist/read`, {
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
      data.forEach((playlist) => {
        PlaylistElement = document.createElement("div");
        TrackName = playlist.track.name;
        ArtistName = playlist.artist.name;
        AlbumName = playlist.album.name;
        Genre = playlist.genre.name;

        PlaylistElement.className = "ListItem col-2 ms-5 mb-5 text-center mt-5";
        PlaylistElement.style = "border-radius: 12px;";

        PlaylistElement.innerHTML = `
       
            <div class="text-center">
                <h4>${TrackName}</h4>
                <h4>${ArtistName} Name</h4>
                <h4>${AlbumName}</h4>
                <h4>${Genre}</h4>
            </div>
        
        `;

        document.querySelector("#PlaylistList").append(PlaylistElement);
      });
    })
    .catch((err) => {
      alert(
        "There was a problem getting the albums from the system. Please try again later." +
          err
      );
    });
})();

//Get Playlist by Id

(function () {
  // var playlist = parseInt(document.querySelector("#PlaylistList").value);

  const params = new URLSearchParams(window.location.search);
  let playlist = params.get("PlaylistId");

  fetch(`http://localhost:8082/playlist/${playlist}`, {
    method: "GET",
  })
    .then((response) => {
      if (response.status === 200) {
        return response.json();
      } else {
        throw "Response code was not 200 it was " + response.status;
      }
    })
    .then((playlist) => {
      PlaylistElement = document.createElement("div");
      TrackName = playlist.track.name;
      ArtistName = playlist.artist.name;
      AlbumName = playlist.album.name;
      Genre = playlist.genre.name;

      PlaylistElement.className = "ListItem col-2 ms-5 mb-5 text-center mt-5";
      PlaylistElement.style = "border-radius: 12px;";

      PlaylistElement.innerHTML = `
       
            <div class="text-center">
                <h4>${TrackName}</h4>
                <h4>${ArtistName} Name</h4>
                <h4>${AlbumName}</h4>
                <h4>${Genre}</h4>
            </div>
  
        `;

      document.querySelector("#PlaylistList").append(PlaylistElement);
    })
    .catch((err) => {
      alert(
        "There was a problem getting the albums from the system. Please try again later." +
          err
      );
    });
})();
