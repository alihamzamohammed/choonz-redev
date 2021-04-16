'use strict';

//Handle Genre Names
let Genres = [];
let GenreNames = [];
const GenreChanged = (e, self) => {
  var GenreString;
  if (Genres.indexOf(document.querySelector("#Genres").value) == -1) {


    Genres.push(document.querySelector("#Genres").value)
    GenreNames.push(self.options[self.selectedIndex].text)
    self.options[self.selectedIndex].style = "background-color: lightblue;";


  } else {
    //remove it because it already exists
    let GenreIndex = Genres.indexOf(document.querySelector("#Genres").value);
    Genres.splice(GenreIndex, 1);
    GenreNames.splice(GenreIndex, 1);
    self.options[self.selectedIndex].style = "background-color: white;"
  }

  GenreString = ""; //clear it
  var Index = 0;
  GenreNames.forEach(GenreName => {
    if (Index === 0) {
      GenreString += GenreName; //append existing artists
    } else {
      GenreString += ", " + GenreName
    }
    Index++;
  })
  if (GenreString == "") {
    GenreString = "Please choose a genre"
  }

  document.querySelector("#PlaceHolderSelectGenre").innerHTML = "";
  document.querySelector("#PlaceHolderSelectGenre").innerHTML = GenreString
  document.querySelector("#PlaceHolderSelectGenre").selected = "selected"; //select to display the message
  console.log(JSON.stringify(Genres))
}


let Tracks = [];
let TrackNames = [];

const AddTracks = (e, self) => { 

  var TrackString;
  if (Tracks.indexOf(document.querySelector("#Tracks").value) == -1) {


    Tracks.push(document.querySelector("#Tracks").value)
    TrackNames.push(self.options[self.selectedIndex].text)
    self.options[self.selectedIndex].style = "background-color: lightblue;";


  } else {
    //remove it because it already exists
    let GenreIndex = Tracks.indexOf(document.querySelector("#Tracks").value);
    Tracks.splice(GenreIndex, 1);
    TrackNames.splice(GenreIndex, 1);
    self.options[self.selectedIndex].style = "background-color: white;"
  }

  TrackString = ""; //clear it
  var Index = 0;
  TrackNames.forEach(TrackName => {
    if (Index === 0) {
      TrackString += TrackName; //append existing artists
    } else {
      TrackString += ", " + TrackName
    }
    Index++;
  })
  if (TrackString == "") {
    TrackString = "Please choose a genre"
  }

  document.querySelector("#PlaceHolderSelectTrack").innerHTML = "";
  document.querySelector("#PlaceHolderSelectTrack").innerHTML = TrackString
  document.querySelector("#PlaceHolderSelectTrack").selected = "selected"; //select to display the message
  console.log(JSON.stringify(Tracks))
}


const AddTrackEvent = (e) =>{
  e.preventDefault();
}


//edit tracks
const EditAlbumFunc = (e) => {
  e.preventDefault();

  const params = new URLSearchParams(window.location.search);
  let albumParam = params.get("AlbumID");

  let AlbumName = document.querySelector("#AlbumName").value
  let ArtistID = document.querySelector("#Artists").value; //gets selected artist from dropdown value
  let GenresArray = [];
  Genres.forEach((g) => {
    GenresArray.push({
      "id" : parseInt(g)
    })
  })

  var Base64Img;
  var Reader = new FileReader();

  Reader.onloadend = (e) => {
    Base64Img = e.target.result;
    console.log(Base64Img)
    //run once Base64String is made of img
    let Body = JSON.stringify({
      "name": AlbumName,
      "artist": {
        "id": parseInt(ArtistID)
      },
      "genre": GenresArray,
      "cover" : Base64Img
    });
    console.log(Body)
  
    fetch("http://localhost:8082/albums/update/"+albumParam, {
        method: "PUT",
        headers: {
          "Content-type": "application/json",
        },
        body: Body
      })
      .then((res) => {
        
        if(res.status === 202){//Accepted
          res.json()
        }else{
          throw "Couldn't create the album. Please try again later";
        }
        })
      .then((data) => location.reload())//reload the page so that they can see the new album
      .catch((err) => console.log(err));

  }
  try{
  Reader.readAsDataURL(document.querySelector("#AlbumCoverArt").files[0])//calls the loadend func
  }catch(error){
    alert("You have forgotten to add an image to the form. Please submit once an image has been added.")//if no file has been added
    //this will be flagged
  }
};

//Delete the Album
let DeleteAlbum = async () => {

  const params = new URLSearchParams(window.location.search);
  let albumParam = params.get("AlbumID");

  const response = await fetch(`http://localhost:8082/albums/delete/${albumParam}`, {
    method: "DELETE",
  });
  if (response.status != 204) {
    alert("Delete Denied, the Album must be valid");
    console.error(`Error: Status code ${reponse.status}\n${response.json}`);
  }else{
    location.replace("http://localhost:8082/albums")
  }
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

    document.querySelector("#ModalTitle").innerHTML = "Edit " + data.name

    console.log(JSON.stringify(data))
    document.querySelector("#AlbumNameTitle").innerHTML = data.name;

    let URL = window.location
    let BaseURL = URL.protocol + "//" + URL.host;
    let FinalURL = BaseURL + `/artist?ArtistID=${data.artist.id}`
    document.querySelector("#ArtistNameTitle").innerHTML = `<a href="${FinalURL}" class='AddTextDecoration'>${data.artist.name}</a>`

    var Genres = data.genre
    var GenreString = "";
    let i = 0;
    Genres.forEach((Genre) => {
      if (i == 0) {
        GenreString += "<a class='AddTextDecoration' href='http://localhost:8082/genre?GenreID="+Genre.id+"'>"+Genre.name+"</a>"
      } else {
        GenreString += ", " + "<a class='AddTextDecoration' href='http://localhost:8082/genre?GenreID="+Genre.id+"'>"+Genre.name+"</a>"
      }
      i++;
    })
    document.querySelector("#GenresTitle").innerHTML = GenreString

    document.querySelector("#AlbumPic").src = data.cover;
    document.querySelector("#AlbumID").innerHTML = "Album ID: " + data.id;
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


(function () {
  //Artists
  fetch("http://localhost:8082/artists/read", {
      method: "GET",
    })
    .then((response) => {
      if (response.status === 200) {
        return response.json();
      } else {
        throw (
          "Error retreving Artist information. STATUS CODE: " + response.status
        );
      }
    })
    .then((data) => {
      data.forEach((artist) => {
        var ArtistElement = document.createElement("option");
        var ArtistName = artist.name;
        var ArtistId = artist.id;

        ArtistElement.value = ArtistId
        ArtistElement.text = ArtistName

        document.querySelector("#Artists").append(ArtistElement)
      });
    }).catch((err) => console.log(err));

  //Genres
  fetch("http://localhost:8082/genres/read", {
      method: "GET",
    })
    .then((response) => {
      if (response.status === 200) {
        return response.json();
      } else {
        throw (
          "Error retreving Artist information. STATUS CODE: " + response.status
        );
      }
    })
    .then((data) => {
      console.log(JSON.stringify(data) + " genres have been found")
      data.forEach((genre) => {
        console.log("Genres")
        var GenreElement = document.createElement("option");
        var GenreName = genre.name;
        var GenreId = genre.id;

        GenreElement.value = GenreId
        GenreElement.text = GenreName

        document.querySelector("#Genres").append(GenreElement)
      });
    }).catch((err) => console.log(err));

    fetch(`http://localhost:8082/tracks/read`, {//once i read add it to the modal
      method : "GET"
    }).then((res) => {
      if(res.status === 200){
        return res.json()
      }else{
        throw "The tracks couldn't be added to edit system. Please try again later."
      }
    }).then((data) => {
      console.log(JSON.stringify(data))
    }).catch((err) => alert("There was a problem fetching the tracks. " + err))
})();