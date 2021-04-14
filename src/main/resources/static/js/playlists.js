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

const CreatePlaylist = () => {
    let playlist = document.querySelector("#PlaylistList").value;
    console.log(playlist);
    console.log(playlistName);
    console.log(playlistDescription);
    console.log(PlaylistCoverArt);
  
    const obj = {
      name: playlistName,
      description: playlistDescription,
      artwork: PlaylistCoverArt
    };
  
    fetch("http://localhost:8082/playlists", {
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