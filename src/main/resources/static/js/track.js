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
const EditTrack = (e) => {
  e.preventDefault();

  const params = new URLSearchParams(window.location.search);
  let TrackIDParam = params.get("TrackID");

  var TrackNameEdit = document.querySelector("#TrackName").value;
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
      fetch("http://localhost:8082/tracks/update/"+TrackIDParam, {
          method: "PUT",
          headers: {
            "Content-type": "application/json",
          },
          body: JSON.stringify({
            "name": TrackNameEdit,
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
          if (res.status === 202) {
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

(function () {
    // var playlist = parseInt(document.querySelector("#PlaylistList").value);

    const params = new URLSearchParams(window.location.search);
    let track = params.get("TrackID");

    fetch(`http://localhost:8082/tracks/read/${track}`, {
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
            console.log(JSON.stringify(data))
            var AlbumName = "<a href='http://localhost:8082/album?AlbumID=" + data.album.id + "'>" + data.album.name + "</a>";
            var ArtistName = "<a href='http://localhost:8082/artist?ArtistID=" + data.artist.id + "'>" + data.artist.name + "</a>";
            var Genres = data.album.genre;
            var GenreString = "";
            var TrackNameText = data.name;
            var i = 0;
            Genres.forEach((Genre) => {
                if (i == 0) {
                    GenreString += Genre.name
                } else {
                    GenreString += ", " + Genre.name
                }
                i++;
            })
            var TrackID = data.id;
            var AlbumPic = data.album.cover;
            var FeaturedArtists = data.contributingArtists;
            var Lyrics = data.lyrics;
            var FeaturedString = "";
            var j = 0;
            FeaturedArtists.forEach((Artist) => {
                if (j == 0) {
                    FeaturedString += "<a href='http://localhost:8082/artist?ArtistID=" + Artist.id + "'>" + Artist.name + "</a>"
                } else {
                    FeaturedString += ", " + "<a href='http://localhost:8082/artist?ArtistID=" + Artist.id + "'>" + Artist.name + "</a>"
                }
                j++;
            })
            document.querySelector("#AlbumNameTitle").innerHTML = AlbumName;
            if (FeaturedString != "") {
                document.querySelector("#ArtistNameTitle").innerHTML = ArtistName + " f.t " + FeaturedString;
            }else{
                document.querySelector("#ArtistNameTitle").innerHTML = ArtistName
            }
            document.querySelector("#GenresTitle").innerHTML = GenreString;

            document.querySelector("#TrackIDTitle").innerHTML = "Track ID: " + TrackID;
            document.querySelector("#TrackAlbumPicDisplay").src = AlbumPic;
            document.querySelector("#LyricsDisplay").innerHTML = Lyrics;
            document.querySelector("#TrackNameTitle").innerHTML = TrackNameText;

        })
        .catch((err) => {
            alert(
                "There was a problem getting the track from the system. Please try again later." +
                err
            );
        });
})();


//Delete a Track
let DeleteTrack = async () => {

    const params = new URLSearchParams(window.location.search);
    let TrackID = params.get("TrackID");

    const response = await fetch(`http://localhost:8082/tracks/delete/${TrackID}`, {
        method: "DELETE",
    });
    if (response.status != 204) {
        alert("The Delete Denied, the Track must be valid");
        console.error(`Error: Status code ${reponse.status}\n${response.json}`);
        return response.status;
    }else{
        location.replace("http://localhost:8082/tracks")
    }
};


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