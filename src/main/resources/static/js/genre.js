"use strict";

const createGenre = document.querySelector("#CreateGenreButton");
createList.addEventListener("click", (event) => {
  event.preventDefault();
  CreateGenre();
});

const EditGenre = document.querySelector("#EditGenreButton");
createList.addEventListener("click", (event) => {
  event.preventDefault();
  EditGenre();
});

const deleteGenre = document.querySelector("#DeleteGenreButton");
createList.addEventListener("click", (event) => {
  event.preventDefault();
  DeleteGenre();
});

//Creating Create Fucntionality for Playlists
const CreateGenre = () => {
  let genre= document.querySelector("#PlaylistList").value;
  console.log(genre);
  console.log(genreName);
  console.log(genreDescription);


  const obj = {
    name: genreName,
    description: genreDescription,
   
  };

  fetch("http://localhost:8082/genres", {
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

// Creating Update Functionality for PokeList
let EditGenre = () => {
  const params = new URLSearchParams(window.location.search);
  let genre = params.get("GenreId");

  let genreName = document.querySelector("#GenreName").value;
  console.log("Genre Name: " + genreName);

  let genreDescription = document.querySelector("#GenreDescription")
    .value;
  console.log("Genre Description: " + genreDescription);

  const updatedPlaylist = {
    GenereName: genreName,
    GenreDescription: genreDescription
  };

  fetch(`http://localhost:8082/genres/${genre}`, {
    method: "PUT",
    headers: {
      "Content-type": "application/json",
    },
    body: JSON.stringify(updatedPlaylist),
  })
    .then((res) => res.json())
    .then((data) => console.log(`Success ${data}`))
    .catch((error) => console.log(`Failure ${error}`));
};
// Creating Delete Functionality

let DeleteGenre = async (genre) => {
  //  var playlist = parseInt(document.querySelector("#PlaylistList").value);
  
  const params = new URLSearchParams(window.location.search);
  let genre = params.get("GenreId");
  
  const response = await fetch(`http://localhost:8082/genres/${genre}`, {
    method: "DELETE",
  });
  if (response.status != 204) {
    alert("The Delete Denied, the Playlist must be valid");
    console.error(`Error: Status code ${reponse.status}\n${response.json}`);
    return response.status;
  }
  alert("Genre deleted");
  console.log("Genre:" + genre + "deleted");
};

//Get All Playlists
(function () {
  fetch(`http://localhost:8082/genres/read`, {
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
      data.forEach((genre) => {
        GenreElement = document.createElement("div");
        GenreName = genre.name;
        GenreDescription = genre.description;

        PlaylistElement.className = "ListItem col-2 ms-5 mb-5 text-center mt-5";
        PlaylistElement.style = "border-radius: 12px;";

        PlaylistElement.innerHTML = `
       
            <div class="text-center">
                <h4>${GenreName}</h4>
                <h4>${GenreDescription} Name</h4>
            </div>
        
        `;

        document.querySelector("GenreList").append(GenreElement);
      });
    })
    .catch((err) => {
      alert(
        "There was a problem getting the genres from the system. Please try again later." +
          err
      );
    });
})();

//Get Genre by Id

(function () {
  // var  genre = parseInt(document.querySelector("#GenreList").value);

  const params = new URLSearchParams(window.location.search);
  let genre = params.get("GenreId");

  fetch(`http://localhost:8082/genres/${genre}`, {
    method: "GET",
  })
    .then((response) => {
      if (response.status === 200) {
        return response.json();
      } else {
        throw "Response code was not 200 it was " + response.status;
      }
    })
    .then((genre) => {
      PlaylistElement = document.createElement("div");
      GenreName = genre.name;
      GenreDescription = genre.description;
     

      PlaylistElement.className = "ListItem col-2 ms-5 mb-5 text-center mt-5";
      PlaylistElement.style = "border-radius: 12px;";

      PlaylistElement.innerHTML = `
       
            <div class="text-center">
                <h4>${GenreName}</h4>
                <h4>${GenreDescription} Name</h4>
            </div>
  
        `;

      document.querySelector("#GenreList").append(GenreElement);
    })
    .catch((err) => {
      alert(
        "There was a problem getting the genre from the system. Please try again later." +
          err
      );
    });
})();
