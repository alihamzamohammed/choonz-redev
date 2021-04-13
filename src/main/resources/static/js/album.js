'use strict';

//edit tracks
const EditAlbumFunc = () => {
  const params = new URLSearchParams(window.location.search);
  let album = params.get("AlbumID");

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


(function () {
  const params = new URLSearchParams(window.location.search);
  let AlbumID = params.get("AlbumID");
  fetch(`http://localhost:8082/albums/read/${AlbumID}`, {
    method: "GET"
  }).then((response) => {
    if (response.status === 200) {
      return response.json()
    } else {
      throw "Album data couldn't be loaded please try again later. Thanks"
    }
  }).then((data) => {
    console.log(JSON.stringify(data))
    document.querySelector("#AlbumName").innerHTML = data.name;

    let URL = window.location
    let BaseURL = URL.protocol + "//" + URL.host;
    let FinalURL = BaseURL + `/artist?ArtistID=${data.artist.id}`
    document.querySelector("#ArtistName").innerHTML = `<a href="${FinalURL}">${data.artist.name}</a>`

    var Genres = data.genre
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
    document.querySelector("#Genres").innerHTML = GenreString

    let Tracks = data.tracks;

    Tracks.forEach((Track) => {
      var TrackElement = document.createElement('div')
      TrackElement.className = "SongItem col-1 ms-5 mb-5 text-center"
      TrackElement.style = "border-radius: 12px;";

      let URL = window.location
      let BaseURL = URL.protocol + "//" + URL.host;
      let FinalURL = BaseURL + `/track?TrackID=${Track.id}`
      var TrackSecondsFormat = Track.duration % 60;
      if(TrackSecondsFormat.toString().length == 1){
        TrackSecondsFormat = "0" + TrackSecondsFormat;//pad a zero at the front
      }
      let TrackDuration = ((Math.floor(Track.duration / 60))) + ":" + TrackSecondsFormat;
      TrackElement.innerHTML = `
      <a href ="${FinalURL}">
        <h4>${Track.name}</h4>
        <h4>${TrackDuration}</h4>
      </a>
      `

      document.querySelector("#SongsList").append(TrackElement)
    })

  }).catch((err) => alert("An error was encountered " + err))
})();