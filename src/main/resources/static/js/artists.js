'use strict'

const createArtist = document.querySelector("#CreateArtistButton");
createArtist.addEventListener("click", (event) => {
  event.preventDefault();
  CreateArtist();
});

//Creating Create Functionality for Artists
const CreateArtist = () => {
    let artist = document.querySelector("#ArtistList").value;
    console.log(artist);
    console.log(artistName);
    console.log(genre);
  
    const obj = {
      name: artistName,
      genre: genre,
    };
  
    fetch("http://localhost:8082/artist", {
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

  // read all artists
(function () {
    fetch(`http://localhost:8082/artists/read`, {
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
          var ArtistElement = document.createElement("div");
          var name = artist.name;
          var genre = artist.genre;
  
          ArtistElement.className = "ListItem col-2 ms-5 mb-5 text-center mt-5";
          ArtistElement.style = "border-radius: 12px;";
  
          ArtistElement.innerHTML = `
             
                  <div class="text-center">
                      <h4>${name}</h4>
                      <h4>${genre} Name</h4>
                  </div>
              
              `;
  
          document.querySelector("#ArtistList").append(ArtistElement);
        });
      })
      .catch((err) => {
        alert(
          "There was a problem getting the albums from the system. Please try again later." +
            err
        );
      });
  })();

  