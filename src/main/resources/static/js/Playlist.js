"use strict";

const createList = document.querySelector("#submit-plbutton");
createList.addEventListener("click", (event) => {
  event.preventDefault();
  pokeListPostRequest();
});

const createList = document.querySelector("#submit-plbutton");
createList.addEventListener("click", (event) => {
  event.preventDefault();
  pokeListPostRequest();
});

const createList = document.querySelector("#submit-plbutton");
createList.addEventListener("click", (event) => {
  event.preventDefault();
  pokeListPostRequest();
});

//Creating Create Fucntionality for Playlists
const pokeListPostRequest = () => {
  let pokeList = document.querySelector("#pokeList").value;
  console.log(pokeList);

  const obj = {
    pokeList: pokeList,
  };

  fetch("http://localhost:8080/pokelist", {
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
let playlistPutRequest = () => {
  let pokeListID = parseInt(document.querySelector("#pokeListId").value);
  console.log("PokeListId " + pokeListID);

  let pokeList = document.querySelector("#pokeList").value;
  console.log("PokeList: " + pokeList);

  const updatedPokeList = {
    pokeList: pokeList,
  };

  fetch(`http://localhost:8082/playlist/${pokeListId}`, {
    method: "PUT",
    headers: {
      "Content-type": "application/json",
    },
    body: JSON.stringify(updatedPokeList),
  })
    .then((res) => res.json())
    .then((data) => console.log(`Success ${data}`))
    .catch((error) => console.log(`Failure ${error}`));
};
// Creating Delete Functionality

let playlistDeleteRequest = async (playlist) => {
  var playlist = parseInt(document.querySelector("#PlaylistList").value);
  const response = await fetch(`http://localhost:8082/playlist/${playlist}`, {
    method: "DELETE",
  });
  if (response.status != 204) {
    alert("The Delete Denied, the Playlist must be valid");
    console.error(`Error: Status code ${reponse.status}\n${response.json}`);
    return response.status;
  }
  alert("Task deleted");
  console.log("PlayList:" + playlist + "deleted");
};

//Get All Playlists
(function() {

  fetch(`http://localhost:8082/playlist/read`, {
    method: "GET"
}).then((response) => {
    if(response.status === 200){
      return response.json()
    }else{
      throw "Response code was not 200 it was " + response.status
    }
   }).then((data) => {
      data.forEach(playlist => {
        PlaylistElement = document.createElement('div')
        TrackName = playlist.track.name;
        ArtistName = playlist.artist.name;
        AlbumName = playlist.album.name;
        Genre = playlist.genre.name;
        PlaylistElement.innerHTML = `
       
        <div class="ListItem col-2 ms-5 mb-5 text-center mt-5" style="border-radius: 12px;">
            <div class="text-center">
                <h4>${TrackName}</h4>
                <h4>${ArtistName} Name</h4>
                <h4>${AlbumName}</h4>
                <h4>${Genre}</h4>
            </div>
          </div>
        
        `

        document.querySelector("#PlaylistList").append(PlaylistElement)

      });
    }).catch((err) => {
      alert('There was a problem getting the albums from the system. Please try again later.' + err) 
    })
      
  })();

 
 
 
 
  //Get Playlist by Id

  
  (function() {

    // var playlist = parseInt(document.querySelector("#PlaylistList").value);

    

    fetch(`http://localhost:8082/playlist/${playlist}`, {
    method: "GET"
}).then((response) => {
    if(response.status === 200){
      return response.json()
    }else{
      throw "Response code was not 200 it was " + response.status
    }
   }).then((playlist) => {
        PlaylistElement = document.createElement('div')
        TrackName = playlist.track.name;
        ArtistName = playlist.artist.name;
        AlbumName = playlist.album.name;
        Genre = playlist.genre.name;
        PlaylistElement.innerHTML = `
       
        <div class="ListItem col-2 ms-5 mb-5 text-center mt-5" style="border-radius: 12px;">
            <div class="text-center">
                <h4>${TrackName}</h4>
                <h4>${ArtistName} Name</h4>
                <h4>${AlbumName}</h4>
                <h4>${Genre}</h4>
            </div>
          </div>
        
        `

        document.querySelector("#PlaylistList").append(PlaylistElement)

    
    }).catch((err) => {
      alert('There was a problem getting the albums from the system. Please try again later.' + err) 
    })
      
  })();
