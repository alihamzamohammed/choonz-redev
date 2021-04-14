"use strict";

//Creating Create Fucntionality for Playlists


// Creating Update Functionality for PokeList
let EditPlaylist = () => {
  const params = new URLSearchParams(window.location.search);
  let playlist = params.get("PlaylistID");

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
  //wrong endpoints
  fetch(`http://localhost:8082/playlists/${playlist}`, {
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
  let PlaylistID = params.get("PlaylistID");
  
  const response = await fetch(`http://localhost:8082/playlists/${PlaylistID}`, {
    method: "DELETE",
  });
  if (response.status != 204) {
    alert("The Delete Denied, the Playlist must be valid");
    console.error(`Error: Status code ${reponse.status}\n${response.json}`);
    return response.status;
  }
  alert("Playlist deleted");
  console.log("PlayList:" + PlaylistID + "deleted");
};



//Get Playlist by Id

(function () {
  // var playlist = parseInt(document.querySelector("#PlaylistList").value);

  const params = new URLSearchParams(window.location.search);
  let playlist = params.get("PlaylistID");
  fetch(`http://localhost:8082/playlists/read/${playlist}`, {
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
      
      console.log(JSON.stringify(playlist))

      document.querySelector("#PlaylistName").innerHTML = playlist.name;
      document.querySelector("#Description").innerHTML = playlist.description;
      document.querySelector("#TrackCount").innerHTML = "Track Count: " + playlist.tracks.length;

      
      //continue here when they are added
      playlist.tracks.forEach(track => {
        var PlaylistElement = document.createElement("div");

        PlaylistElement.className = "ListItem col-2 ms-5 mb-5 text-center mt-5";
        PlaylistElement.style = "border-radius: 12px;";

        PlaylistElement.innerHTML = `
       
            <div class="text-center">
                <h4>${track.name}</h4>
                <h4>${ArtistName} Name</h4>
                <h4>${AlbumName}</h4>
                <h4>${Genre}</h4>
            </div>
  
        `;
      });
      

      document.querySelector("#PlaylistList").append(PlaylistElement);
    })
    .catch((err) => {
      alert(
        "There was a problem getting the albums from the system. Please try again later." +
          err
      );
    });
})();
