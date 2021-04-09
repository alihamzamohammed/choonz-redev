/*
      [
    {​
        "id": 1,
        "name": "goobye",
        "tracks": [],
        "artist": {​
            "id": 1,
            "name": "artist name"        }​,
        "genre": {​
            "id": 1,
            "name": "1",
            "description": "1"        }​,
        "cover": "hello"    }​
]


*/


(function() {
    //page ready
    //get all albums
    fetch('http://localhost:8082/albums/read', {
        method : "GET"
    }).then((response) => {
        if(response.status === 200){
            return response.json()
        }else{
            throw "Response code was not 200 it was " + response.status
        }
    }).then((data) => { 
        console.log(JSON.stringify(data))
        data.forEach(album => {
            AlbumElement = document.createElement('div')
            AlbumID = album.id;
            AlbumName = album.name;
            ArtistName = album.artist.name;
            Genre = album.genre.name;
            
            AlbumElement.className = "ListItem col-2 ms-5 mb-5 text-center"
            AlbumElement.style = "border-radius: 12px;"
            AlbumElement.innerHTML = `
                        <img src="./img/Choonz.png" class="img-fluid mt-3" alt="Album Cover"
                            style="border-radius: 12px;">
                        <div class="text-center">
                            <h4>${AlbumName}</h4>
                            <h4>${ArtistName}</h4>
                            <h4>${Genre}</h4>
                        </div>
            `
            document.querySelector("#AlbumsList").append(AlbumElement)
        });
    }).catch((err) => {
        alert('There was a problem getting the albums from the system. Please try again later.' + err)
    })

    //get all playlists
 
 })();