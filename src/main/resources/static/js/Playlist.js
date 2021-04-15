"use strict";
let CurrentPlaylistTracks = [];
let Tracks = [];
let TrackNames = [];

let GlobalPlaylistName;//these are needed for when i update the playlist to include the tracks i need them avaliable globally
let GlobalPlaylistDescription;
let GlobalArtwork;

const TracksChanged = (e, self) => {
  var TrackString;
  if (Tracks.indexOf(document.querySelector("#Tracks").value) == -1) {


    Tracks.push(document.querySelector("#Tracks").value)
    TrackNames.push(self.options[self.selectedIndex].text)
    self.options[self.selectedIndex].style = "background-color: lightblue;";


  } else {
    //remove it because it already exists
    let TrackIndex = Tracks.indexOf(document.querySelector("#Tracks").value);
    Tracks.splice(TrackIndex, 1);
    TrackNames.splice(TrackIndex, 1);
    self.options[self.selectedIndex].style = "background-color: white;"
  }

  TrackString = ""; //clear it
  var Index = 0;
  TrackNames.forEach(TrackName => {
    if (Index === 0) {
      TrackString += TrackName; //append existing tracks
    } else {
      TrackString += ", " + TrackName
    }
    Index++;
  })
  if (TrackString == "") {
    TrackString = "Please choose a track"
  }

  document.querySelector("#PlaceHolderSelectTrack").innerHTML = "";
  document.querySelector("#PlaceHolderSelectTrack").innerHTML = TrackString
  document.querySelector("#PlaceHolderSelectTrack").selected = "selected"; //select to display the message
  console.log(JSON.stringify(Tracks))
}

let AddSongs = (e) => {
  e.preventDefault();
  const params = new URLSearchParams(window.location.search);
  let PlaylistParamID = params.get("PlaylistID");
  var TrackAlreadyAdded = false;//set to false originally
  //track ids are in CurrentPlaylistTracks etc
  console.log(JSON.stringify(CurrentPlaylistTracks))
  CurrentPlaylistTracks.forEach((CT) => {//if there are no existing tracks this will be skipped and allowed to contionue
    Tracks.forEach((T) => {
      if(parseInt(CT) == parseInt(T)){
        //if an id is in the added tracks and already has been added dont allow it to pass on
        TrackAlreadyAdded = true;
      }
    })
  })
  if(!TrackAlreadyAdded){
    var UpdatedTracksArray = [];
    CurrentPlaylistTracks.forEach((C) => {
      UpdatedTracksArray.push({
        id : C
      })
    })
    Tracks.forEach((T) => {
      UpdatedTracksArray.push({
        id : T
      })
    })
    console.log(JSON.stringify(UpdatedTracksArray))

    //if none of teh tracks have been added
    fetch("http://localhost:8082/playlists/update/" + PlaylistParamID, {
      method : "PUT",
      headers: {
        "Content-type": "application/json",
      },
      body : JSON.stringify({
        "name" : GlobalPlaylistName,
        "description" : GlobalPlaylistDescription,
        "artwork" : GlobalArtwork,
        "tracks" : UpdatedTracksArray
      })//will send the new array of tracks to be updated
    }).then((res) => {
      if(res.status === 202){
        return res.json();
      }else{
        throw "The response code was " + res.status;
      }
    }).then((data) => location.reload())//restart the page to show the new tracks
    .catch((err) => alert("There was a problem adding the songs to the playlist. "+err))
  }else{
    alert("One or more of those tracks is already in the playlist please remove them and try again. Thanks")
  }
}

let EditPlaylist = (e) => {
  e.preventDefault();
  const params = new URLSearchParams(window.location.search);
  let PlaylistParamID = params.get("PlaylistID");

  let playlistName = document.querySelector("#PlaylistName").value;

  let playlistDescription = document.querySelector("#PlaylistDescription").value;

  

  var Base64Img;
  var Reader = new FileReader();

  Reader.onloadend = (e) => {
    Base64Img = e.target.result;
    //run once Base64String is made of img
    let Body = JSON.stringify({
      "name" : playlistName,
      "description" : playlistDescription,
      "artwork" : Base64Img
    });

    fetch(`http://localhost:8082/playlists/update/${PlaylistParamID}`, {
    method: "PUT",
    headers: {
      "Content-type": "application/json",
    },
    body: Body,
  })
    .then((res) => {
      if(res.status === 202){
        return res.json()
      }else{
        throw "Couldn't modify the playlists status was " + res.status;
      }
    })
    .then((data) => location.reload())
    .catch((error) => alert("The playlist couldn't be modified. " + error));
    
  }

  try{
    Reader.readAsDataURL(document.querySelector("#PlaylistCoverArt").files[0])//calls the loadend func
    }catch(error){
      alert("You have forgotten to add an image to the form. Please submit once an image has been added.")//if no file has been added
      //this will be flagged
    }

  
};
// Creating Delete Functionality

let DeletePlaylist = async () => {
  //  var playlist = parseInt(document.querySelector("#PlaylistList").value);
  
  const params = new URLSearchParams(window.location.search);
  let PlaylistID = params.get("PlaylistID");
  
  const response = await fetch(`http://localhost:8082/playlists/delete/${PlaylistID}`, {
    method: "DELETE",
  });
  if (response.status != 204) {
    alert("The Delete Denied, the Playlist must be valid");
    console.error(`Error: Status code ${reponse.status}\n${response.json}`);
    return response.status;
  }else{
    location.replace("http://localhost:8082/playlists")
  }
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
      GlobalPlaylistName = playlist.name;
      GlobalPlaylistDescription = playlist.description;
      GlobalArtwork = playlist.artwork;//set these globally to use later
      
      console.log(JSON.stringify(playlist))

      document.querySelector("#PlaylistNameDisplay").innerHTML = playlist.name;
      document.querySelector("#PlaylistDescriptionDisplay").innerHTML = playlist.description;
      document.querySelector("#PlaylistTrackCountDisplay").innerHTML = "Track Count: " + playlist.tracks.length;
      document.querySelector("#PlaylistPic").src = playlist.artwork;
      document.querySelector("#PlaylistIDDisplay").innerHTML = "Playlist ID: " + playlist.id;

      
      
      playlist.tracks.forEach((Track) => {
        CurrentPlaylistTracks.push(Track.id)
        console.log(JSON.stringify(Track))
        var TrackElement = document.createElement("div");
        var TrackName = Track.name;
        var TrackArtist = Track.artist.name; //artist is null
        var Index = 0;
        if (Track.contributingArtists.length > 0) {
  
          Track.contributingArtists.forEach((CA) => {
            if (Index == 0) {
              TrackArtist += " f.t " + CA.name;
            } else {
              TrackArtist += " ," + CA.name;
            }
            Index += 1;
          })
        }
        var AlbumName = Track.album.name;
  
        var Genres = Track.album.genre
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
  
        TrackElement.className = "ListItem col-2 ms-5 mb-5 text-center mt-5";
        TrackElement.style = "border-radius: 12px;";
  
        let URL = window.location
        let BaseURL = URL.protocol + "//" + URL.host;
        let FinalURL = BaseURL + `/track?TrackID=${Track.id}`
        TrackElement.innerHTML = `
              <a href="${FinalURL}">
              <div class="text-center">
                <h4>${TrackName}</h4>
                <h4>${TrackArtist}</h4>
                <h4>${AlbumName}</h4>
                <h4>${GenreString}</h4>
              </div>
            </a>
          `;
  
        document.querySelector("#PlaylistList").append(TrackElement);
      });
      

      
    })
    .catch((err) => {
      alert(
        "There was a problem getting the tracks from the system. Please try again later." +
          err
      );
    });
})();

(function () {
  //load tracks add them to select
  fetch("http://localhost:8082/tracks/read", {
    method : "GET"
  }).then((res) => {
    if(res.status === 200){
      return res.json()
    }else{
      throw "There was a problem the status code is " + res.status
    }
  }).then((data) => {
    data.forEach((tracks) => {
      var TrackElement = document.createElement('option');
      TrackElement.value = tracks.id;
      TrackElement.innerHTML = tracks.name;
      document.querySelector("#Tracks").append(TrackElement)
    })
    
  }).catch((err) => alert("There was a problem loading the tracks into the system. "+err))

})();
