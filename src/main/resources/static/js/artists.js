'use strict'

//Creating Create Functionality for Artists
const CreateArtist = () => {
  console.log(" I have tried to Submitted")
  let ArtistName = document.querySelector("#ArtistName").value
  console.log(ArtistName + " hello")
  fetch("http://localhost:8082/artists/create", {
      method: "POST",
      headers: {
        "Content-type": "application/json",
      },
      body: JSON.stringify({
        "name": ArtistName
      }),
    })
    .then((res) => {
      if (res.status === 201) {
        location.reload()
      } else {
        throw "The artist wasn't created error"
      }
    })
    .catch((err) => console.log(err));
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
      data.forEach((Artist) => {
        var ArtistElement = document.createElement("div");
        var name = Artist.name;
        console.log(JSON.stringify(data))
        ArtistElement.className = "ListItem col-2 ms-5 mb-5 text-center mt-5";
        ArtistElement.style = "border-radius: 12px;";
        let ID = data[0].id;
        let URL = window.location
        let BaseURL = URL.protocol + "//" + URL.host;
        let FinalURL = BaseURL + `/artist?ArtistID=${ID}`
        ArtistElement.innerHTML = `
                  <a href="${FinalURL}">
                  <div class="text-center">
                      <h4>${name}</h4>
                      <h4>Album Count</h4>
                      <h4>Most Popular Genre</h4>
                  </div>
              </a>
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