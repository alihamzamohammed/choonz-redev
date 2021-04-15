'use strict';

//Get All Playlists
(function () {
  fetch(`http://localhost:8082/playlists/read`, {
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
        var PlaylistElement = document.createElement("div");
        console.log(JSON.stringify(playlist))

        var PlaylistName = playlist.name;
        var Description = playlist.description;
        var TrackCount = playlist.tracks.length;


        PlaylistElement.className = "ListItem col-2 ms-5 mb-5 text-center mt-5";
        PlaylistElement.style = "border-radius: 12px;";

        let URL = window.location
        let BaseURL = URL.protocol + "//" + URL.host;
        let FinalURL = BaseURL + `/playlist?PlaylistID=${playlist.id}`

        PlaylistElement.innerHTML = `
            <a href="${FinalURL}">
            <img src="${playlist.artwork}" class="img-fluid mt-3" alt="Album Cover"
                  style="border-radius: 12px;">
              <div class="text-center">
                <h4>${PlaylistName}</h4>
                <h4>${Description}</h4>
                <h4>Track count: ${TrackCount}</h4>
              </div>
            </a>
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

const CreatePlaylist = (e) => {
  e.preventDefault();
  var PlaylistName = document.querySelector("#PlaylistName").value;
  var PlaylistDescription = document.querySelector("#PlaylistDescription").value;


  var Base64Img;
  var Reader = new FileReader();

  Reader.onloadend = (e) => {
    Base64Img = e.target.result;
    //run once Base64String is made of img
    let Body = JSON.stringify({
      "name" : PlaylistName,
      "description" : PlaylistDescription,
      "artwork" : Base64Img
    });

    fetch("http://localhost:8082/playlists/create", {
        method: "POST",
        headers: {
          "Content-type": "application/json",
        },
        body: Body,
      })
      .then((res) => {
        if(res.status === 201){
          return res.json()
        }else{
          throw "The response code was " + res.status 
        }
      })
      .then((data) => location.reload())
      .catch((err) => alert("There was a problem creating the playlist. Please try again later." + err));
  }

  try{
    Reader.readAsDataURL(document.querySelector("#PlaylistCoverArt").files[0])//calls the loadend func
    }catch(error){
      alert("You have forgotten to add an image to the form. Please submit once an image has been added.")//if no file has been added
      //this will be flagged
    }
};




const SearchPlaylists = (e) => {
  e.preventDefault();

  var PlaylistParam = document.querySelector("#q").value;

  fetch(`http://localhost:8082/search/playlists/${PlaylistParam}`, {
      method: "GET"
    }).then((res) => {
      if (res.status === 200) {
        return res.json()
      } else {
        throw 'The response was not 200 and the album was not returned.'
      }
    }).then((data) => {
      console.log(JSON.stringify(data))
      document.querySelector("#PlaylistList").innerHTML = "";
      document.querySelector("#PlaylistTitleDisplay").innerHTML = "Results for " + PlaylistParam
      data.forEach((playlist) => {
        var PlaylistElement = document.createElement("div");
        console.log(JSON.stringify(playlist))

        var PlaylistName = playlist.name;
        var Description = playlist.description;
        var TrackCount = playlist.tracks.length;


        PlaylistElement.className = "ListItem col-2 ms-5 mb-5 text-center mt-5";
        PlaylistElement.style = "border-radius: 12px;";

        let URL = window.location
        let BaseURL = URL.protocol + "//" + URL.host;
        let FinalURL = BaseURL + `/playlist?PlaylistID=${playlist.id}`

        PlaylistElement.innerHTML = `
            <a href="${FinalURL}">
            <img src="${playlist.artwork}" class="img-fluid mt-3" alt="Album Cover"
                  style="border-radius: 12px;">
              <div class="text-center">
                <h4>${PlaylistName}</h4>
                <h4>${Description}</h4>
                <h4>Track count: ${TrackCount}</h4>
              </div>
            </a>
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
}