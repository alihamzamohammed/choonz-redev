(function () {
    //page ready
    //get all albums
    fetch('http://localhost:8082/albums/read', {
        method: "GET"
    }).then((response) => {
        if (response.status === 200) {
            return response.json()
        } else {
            throw "Response code was not 200 it was " + response.status
        }
    }).then((data) => {
        console.log(JSON.stringify(data))
        data.forEach(album => {
            AlbumElement = document.createElement('div')
            AlbumID = album.id;
            AlbumName = album.name;
            ArtistName = album.artist.name;
            let genres = [];
            album.genre.forEach(g => genres.push(g.name));
            Genre = genres.join(", ");

            AlbumElement.className = "ListItem col-2 ms-5 mb-5 text-center"
            AlbumElement.style = "border-radius: 12px;"
            AlbumElement.innerHTML = `
            <a href="http://localhost:8082/album?AlbumID=${AlbumID}">
                        <img src="${album.cover}" class="img-fluid mt-3" alt="Album Cover"
                            style="border-radius: 12px;">
                        
                            <div class="text-center">
                            <h4>${AlbumName}</h4>
                            <h4>${ArtistName}</h4>
                            <h4>${Genre}</h4>
                        </div>
                        </a>
            `
            document.querySelector("#AlbumsList").append(AlbumElement)
        });
    }).catch((err) => {
        alert('There was a problem getting the albums from the system. Please try again later.' + err)
    })

    //get all playlists
    fetch('http://localhost:8082/playlists/read', {
        method: "GET"
    }).then((response) => {
        if (response.status === 200) {
            return response.json()
        } else {
            throw "Response code was not 200 it was " + response.status
        }
    }).then((data) => {
        console.log(JSON.stringify(data))
        data.forEach(playlist => {
            PlaylistElement = document.createElement('div')

            PlaylistElement.className = "ListItem col-2 ms-5 mb-5 text-center"
            PlaylistElement.style = "border-radius: 12px;"
            PlaylistElement.innerHTML = `
            <a href="http://localhost:8082/playlist?PlaylistID=${playlist.id}">
                        <img src="${playlist.artwork}" class="img-fluid mt-3" alt="Album Cover"
                            style="border-radius: 12px;">
                        <div class="text-center">
                            <h4>${playlist.name}</h4>
                            <h4>${playlist.description}</h4>
                            <h4>Track count: ${playlist.tracks.length}</h4>
                        </div>
                        </a>
            `
            document.querySelector("#PlaylistList").append(PlaylistElement)
        });
    }).catch((err) => {
        alert('There was a problem getting the albums from the system. Please try again later.' + err)
    })

})();