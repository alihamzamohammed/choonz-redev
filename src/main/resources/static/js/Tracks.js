"use strict";

//Create a Track
const CreateTrack = () => {
  let track = document.querySelector("#Track").value;
  console.log(track);
  console.log(trackName);
  console.log(trackDuration);
  console.log(trackLyrics);

  const obj = {
    name: trackName,
    duration: trackDuration,
    lyrics: trackLyrics
  };

  fetch("http://localhost:8082/tracks", {
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


//Read all tracks
(function () {
  fetch(`http://localhost:8082/tracks/read`, {
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
      data.forEach((Track) => {
        console.log(JSON.stringify(Track))
        var TrackElement = document.createElement("div");
        var TrackName = Track.name;
        var TrackArtist = Track.artist.name;
        var Index = 0;
        if (Track.contributingArtists.length > 0) {

          Track.contributingArtists.forEach((CA) => {
            if (Index == 0) {
              TrackArtist += " f.t " + CA.name;
            }else{
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

        document.querySelector("#TracksList").append(TrackElement);
      });
    })
    .catch((err) => {
      alert(
        "There was a problem getting the albums from the system. Please try again later." +
        err
      );
    });
})();