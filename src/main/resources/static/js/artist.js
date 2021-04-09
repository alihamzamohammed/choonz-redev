"use strict";



const editArtist = document.querySelector("#EditArtistButton");
editArtist.addEventListener("click", (event) => {
  event.preventDefault();
  EditArtist();
});

const deleteArtist = document.querySelector("#DeleteArtistButton");
deleteArtist.addEventListener("click", (event) => {
  event.preventDefault();
  DeleteArtist();
});


//edit artists
const EditArtist = () => {
  const params = new URLSearchParams(window.location.search);
  let artist = params.get("ArtistId");

  let artistName = document.querySelector("#ArtistName").value;
  console.log("Artist Name: " + artistName);

  let genre = document.querySelector("#genre").value;
  console.log("Genre: " + genre);

  const updatedArtist = {
    name: artistName,
    genre: genre,
  };

  fetch(`http://localhost:8082/playlist/${artist}`, {
    method: "PUT",
    headers: {
      "Content-type": "application/json",
    },
    body: JSON.stringify(updatedArtist),
  })
    .then((res) => res.json())
    .then((data) => console.log(`Success ${data}`))
    .catch((error) => console.log(`Failure ${error}`));
};

const DeleteArtist = async (artists) => {
  //  var playlist = parseInt(document.querySelector("#PlaylistList").value);

  const params = new URLSearchParams(window.location.search);
  const artist = params.get("ArtistId");

  const response = await fetch(`http://localhost:8082/artists/${artist}`, {
    method: "DELETE",
  });
  if (response.status != 204) {
    alert("The Delete Denied, the Artist must be valid");
    console.error(`Error: Status code ${reponse.status}\n${response.json}`);
    return response.status;
  }
  alert("Artist deleted");
  console.log("Artist:" + artist + "artist");
};



// read by id
(function () {
  const params = new URLSearchParams(window.location.search);
  let artist = params.get("ArtistId");

  fetch(`http://localhost:8082/artists/${artist}`, {
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
      var ArtistElement = document.createElement("div");
      var artistName = artist.name;
      var artistGenre = artist.genre;

      ArtistElement.className = "ListItem col-2 ms-5 mb-5 text-center mt-5";
      ArtistElement.style = "border-radius: 12px;";

      ArtistElement.innerHTML = `
           
                <div class="text-center">
                    <h4>${artistName}</h4>
                    <h4>${artistGenre} Name</h4>
                </div>
            
            `;

      document.querySelector("#ArtistList").append(ArtistElement);
    })
    .catch((err) => {
      alert(
        "There was a problem getting the artist from the system. Please try again later." +
          err
      );
    });
})();
