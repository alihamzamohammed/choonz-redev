"use strict";

/*let Artist;
let FTArtist = [];
let FTArtistNames = [];
let PreviousArtistIndex;//stores the index before onchange is called
const FTArtistPreChange = (e, self) => {
  PreviousArtistIndex = self.selectedIndex
  //need to store the changes before the onchange is called
}

const ArtistChanged = (e, self) => {
  var ArtistIsFeatured = false;
  FTArtist.forEach((Artist) => {
    if (Artist == document.querySelector("#Artists").value) {
      ArtistIsFeatured = true;
      alert("Can't have artist as main artist and featured artist")
    }
  })
  if (!ArtistIsFeatured) {
    Artist = document.querySelector("#Artists").value;
  }else{
    self.selectedIndex = PreviousArtistIndex;
  }
}
const FTArtistChanged = (e, self) => {

  if (FTArtist.indexOf(document.querySelector("#FTArtists").value) == -1) {
    if (document.querySelector("#FTArtists").value === document.querySelector("#Artists").value) {
      alert("Main artist and featured artist can't be the same")
    } else {
      //if not already added
      var ArtistString;
      FTArtist.push(document.querySelector("#FTArtists").value)
      console.log(self.options[self.selectedIndex].text)
      FTArtistNames.push(self.options[self.selectedIndex].text)
      self.options[self.selectedIndex].style = "background-color: lightblue;";
    }

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
*/

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

//Create a Track
const CreateAlbum = (e) => {
  e.preventDefault();
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
  
    fetch("http://localhost:8082/albums/create", {
        method: "POST",
        headers: {
          "Content-type": "application/json",
        },
        body: Body
      })
      .then((res) => {
        
        if(res.status === 201){
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


//Read all Albums
(function () {
  fetch(`http://localhost:8082/albums/read`, {
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
      data.forEach((album) => {
        console.log(JSON.stringify(album))
        var AlbumElement = document.createElement("div");
        var AlbumName = album.name;
        var ArtistName = album.artist.name;
        var AlbumId = album.id;
        var Genres = album.genre
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
        console.log(album.cover)
        let URL = window.location
        let BaseURL = URL.protocol + "//" + URL.host;
        let FinalURL = BaseURL + `/album?AlbumID=${AlbumId}`
        AlbumElement.className = "ListItem col-2 ms-5 mb-5 text-center";
        AlbumElement.style = "border-radius: 12px;";
        AlbumElement.innerHTML = `
            <a href="${FinalURL}">
              <img src="${album.cover}" class="img-fluid mt-3" alt="Album Cover"
                  style="border-radius: 12px;">
              <div class="text-center">
                  <h4>${AlbumName}</h4>
                  <h4>${ArtistName}</h4>
                  <h4>${GenreString}</h4>
              </div>
            </a>
          `;
        document.querySelector("#AlbumsList").append(AlbumElement)

      });
    })
    .catch((err) => {
      alert(
        "There was a problem getting the albums from the system. Please try again later." +
        err
      );
    });
})();

// Get All Artists for the Album Creation and Edit Drop Down Menu

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
    });

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
      data.forEach((genre) => {
        var GenreElement = document.createElement("option");
        var GenreName = genre.name;
        var GenreId = genre.id;

        GenreElement.value = GenreId
        GenreElement.text = GenreName

        document.querySelector("#Genres").append(GenreElement)
      });
    });
})();


const SearchAlbum = (e) => {
  e.preventDefault();

  var AlbumSearch = document.querySelector("#q").value;

  fetch(`http://localhost:8082/search/albums/${AlbumSearch}`, {
    method: "GET"
  }).then((res) => {
    if(res.status === 200){
      return res.json()
    }else{
      throw 'The response was not 200 and the album was not returned.'
    }
  }).then((data) => {
    document.querySelector("#AlbumsList").innerHTML = "";//empty out list of albums div removes the div
    document.querySelector("#AlbumsListTitle").innerHTML = "Search results for " + AlbumSearch
    data.forEach((album) => {
      var AlbumElement = document.createElement("div");
      var AlbumName = album.name;
      var ArtistName = album.artist.name;
      var AlbumId = album.id;
      var Genres = album.genre
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
      console.log(album.cover)
      let URL = window.location
      let BaseURL = URL.protocol + "//" + URL.host;
      let FinalURL = BaseURL + `/album?AlbumID=${AlbumId}`
      AlbumElement.className = "ListItem col-2 ms-5 mb-5 text-center";
      AlbumElement.style = "border-radius: 12px;";
      AlbumElement.innerHTML = `
          <a href="${FinalURL}">
            <img src="${album.cover}" class="img-fluid mt-3" alt="Album Cover"
                style="border-radius: 12px;">
            <div class="text-center">
                <h4>${AlbumName}</h4>
                <h4>${ArtistName}</h4>
                <h4>${GenreString}</h4>
            </div>
          </a>
        `;
      document.querySelector("#AlbumsList").append(AlbumElement)

    });
  }).catch((err) => {alert("There was a problem Searching for that Album." + err)})
}