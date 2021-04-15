"use strict";

let Artist;
let FTArtist = [];
let FTArtistNames = [];
let PreviousArtistIndex; //stores the index before onchange is called
const FTArtistPreChange = (e, self) => {
  PreviousArtistIndex = self.selectedIndex
  //need to store the changes before the onchange is called
}


const FTArtistChanged = (e, self) => {

  if (FTArtist.indexOf(document.querySelector("#FTArtists").value) == -1) {

    //if not already added
    var ArtistString;
    FTArtist.push(document.querySelector("#FTArtists").value)
    console.log(self.options[self.selectedIndex].text)
    FTArtistNames.push(self.options[self.selectedIndex].text)
    self.options[self.selectedIndex].style = "background-color: lightblue;";


  } else {
    //remove it because it already exists
    let FTIndex = FTArtist.indexOf(document.querySelector("#FTArtists").value);
    FTArtist.splice(FTIndex, 1);
    FTArtistNames.splice(FTIndex, 1);
    self.options[self.selectedIndex].style = "background-color: white;"
  }

  ArtistString = ""; //clear it
  var Index = 0;
  FTArtistNames.forEach(ArtistName => {
    if (Index === 0) {
      ArtistString += ArtistName; //append existing artists
    } else {
      ArtistString += ", " + ArtistName
    }
    Index++;
  })
  if (ArtistString == "") {
    ArtistString = "Please choose a featured artist"
  }

  document.querySelector("#PlaceHolderSelectFTArtist").innerHTML = "";
  document.querySelector("#PlaceHolderSelectFTArtist").innerHTML = ArtistString
  document.querySelector("#PlaceHolderSelectFTArtist").selected = "selected"; //select to display the message
}

//Create a Track
const CreateTrack = (e) => {
  e.preventDefault();
  var TrackName = document.querySelector("#TrackName").value;
  var TrackDuration = document.querySelector("#TrackDuration").value;
  var TrackLyrics = document.querySelector("#TrackLyrics").value;
  var AlbumID = document.querySelector("#Album").value;
  //FTArtist contains the ids
  //Get ArtistID from Album

  //Make sure artist ID isn't already within the 

  fetch("http://localhost:8082/albums/read/" + AlbumID, {
    method: "GET"
  }).then((res) => {
    if (res.status === 200) {
      return res.json();
    } else {
      throw "The response code wasn't 200 it was " + res.status
    }
  }).then((data) => {
    //check the album id againt the ft artist ids make sure its all good if so continue with the system
    var ArtistIsMainAndFT = false;
    console.log(JSON.stringify(data))
    let ArtistID = data.artist.id
    console.log(JSON.stringify(FTArtist))
    FTArtist.forEach((FT) => {
      console.log(FT + " " + ArtistID)
      if (FT == ArtistID) { //if artist and FT artist ids are the same == not the same type
        ArtistIsMainAndFT = true;
        console.log("Its invalid aghhhhhh")
      }
    })
    if (!ArtistIsMainAndFT) { //if the artists are valid then continue
      var FTArtistID = [];
      FTArtist.forEach((FTID) => {
        FTArtistID.push({
          "id" : parseInt(FTID)
        })
      })
      //check up on how to add this 
      console.log(JSON.stringify(FTArtistID))
      fetch("http://localhost:8082/tracks/create", {
          method: "POST",
          headers: {
            "Content-type": "application/json",
          },
          body: JSON.stringify({
            "name": TrackName,
            "album": {
              "id": AlbumID
            },
            "duration": TrackDuration,
            "lyrics": TrackLyrics,
            "artist": {
              "id": ArtistID
            },
            "contributingArtists" : FTArtistID
          }),
        })
        .then((res) => {
          if (res.status === 201) {
            return res.json()
          } else {
            throw "The track couldn't be created at this time. Please try again later"
          }
        })
        .then((data) => location.reload()) //reload page
        .catch((err) => alert("There was a problem creating the track." + err));
    } else {
      alert("The Featured artist that you choose is also the artist of the album. Please choose a diffrent Featured artist")
    }
  }).catch((err) => alert("There was a problem validating your album choice. Please try again later. " + err))


};


const SearchTracks = (e) => {
  e.preventDefault();

  var TrackParam = document.querySelector("#q").value;

  fetch(`http://localhost:8082/search/tracks/${TrackParam}`, {
    method: "GET"
  }).then((res) => {
    if(res.status === 200){
      return res.json()
    }else{
      throw 'The response was not 200 and the album was not returned.'
    }
  }).then((data) => {
    document.querySelector("#TracksList").innerHTML = "";//empty current results
    document.querySelector("#SearchTitle").innerHTML = "Results for " + TrackParam
    data.forEach((Track) => {
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

      document.querySelector("#TracksList").append(TrackElement);
    });
  })
  .catch((err) => {
    alert(
      "There was a problem getting the albums from the system. Please try again later." +
      err
    );
  });
}

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

(function () {
  fetch("http://localhost:8082/albums/read", {
    method: "GET"
  }).then((res) => {
    if (res.status === 200) {
      return res.json();
    } else {
      throw "The albums couldn't be fetched the response code wasn't 200"
    }
  }).then((data) => {
    data.forEach((album) => {
      var AlbumElement = document.createElement("option");
      AlbumElement.value = album.id;
      AlbumElement.text = album.name;

      document.querySelector("#Album").append(AlbumElement)

    })
  }).catch((err) => alert("Albums couldn't be collected. Please try again later. " + err));

  fetch("http://localhost:8082/artists/read", {
    method: "GET"
  }).then((res) => {
    if (res.status === 200) {
      return res.json()
    } else {
      throw "The artist information couldn't be gathered the response code was " + res.status
    }
  }).then((data) => {
    data.forEach((Artist) => {
      var ArtistElement = document.createElement("option");
      ArtistElement.value = Artist.id;
      ArtistElement.text = Artist.name;

      document.querySelector("#FTArtists").append(ArtistElement)

    })
  }).catch((err) => alert("There was a problem getting the artists information"))
})();


