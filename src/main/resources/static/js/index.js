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


const Search = (e) => {
    e.preventDefault();
    var Query = document.querySelector("#q").value;
    var SearchType = document.querySelector("#SearchType").value;
    var SearchString = "http://localhost:8082/search/"; //need to add e.g tracks/track and if its a no FT search
    var IncludeFeatured = !document.querySelector("#FeaturedArtists").checked; //reverse since its easier to ask
    //should i include ft rather than should i not include 
    //?contributingartists=false to stop them appearing
    //when we get a result clear the albums and playlist divs
    //change the albums title to search results for
    //load the elements in there

    if (SearchType == "Album") {
        SearchString += "albums/" + Query; //no ft option
        fetch(SearchString, {
            method: "GET"
        }).then((res) => {
            if(res.status === 200){
                return res.json(0)
            }else{
                throw "The search query couldn't be completed. Status: "+res.status;
            }
        }).then((data) => {
            document.querySelector("#AlbumsList").innerHTML = "";//empty out
            document.querySelector("#PlaylistList").innerHTML = "";
            document.querySelector("#PlaylistsTitle").innerHTML = "";
            document.querySelector("#LoadAlbumsBtn").innerHTML = "";
            document.querySelector("#LoadPlaylistsBtn").innerHTML = "";
            document.querySelector("#AlbumsSearchResult").innerHTML = "Search results for albums called " + Query; 
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
          .catch((err) => alert("There was a problem with the search query."+err));
    } else if (SearchType == "Artists") {
        SearchString += "artists/" + Query;
        fetch(SearchString, {
            method: "GET"
        }).then((res) => {
            if(res.status === 200){
                return res.json(0)
            }else{
                throw "The search query couldn't be completed. Status: "+res.status;
            }
        }).then((data) => {
            document.querySelector("#AlbumsList").innerHTML = "";//empty out
            document.querySelector("#PlaylistList").innerHTML = "";
            
            document.querySelector("#PlaylistsTitle").innerHTML = "";
            document.querySelector("#LoadAlbumsBtn").innerHTML = "";
            document.querySelector("#LoadPlaylistsBtn").innerHTML = "";
            document.querySelector("#AlbumsSearchResult").innerHTML = "Search results for artists called " + Query; 
            var i =0;
            data.forEach((Artist) => {
              var ArtistElement = document.createElement("div");
              var name = Artist.name;
              console.log(JSON.stringify(data))
              ArtistElement.className = "ListItem col-2 ms-5 mb-5 text-center mt-5";
              ArtistElement.style = "border-radius: 12px;";
              let ID = data[i].id;
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
      
              document.querySelector("#AlbumsList").append(ArtistElement);
              i++
            });
          })
          .catch((err) => alert("There was a problem with the search query."+err));

    } else if (SearchType == "Genre") {
        SearchString += "genres/" + Query;

        fetch(SearchString, {
            method: "GET"
        }).then((res) => {
            if(res.status === 200){
                return res.json(0)
            }else{
                throw "The search query couldn't be completed. Status: "+res.status;
            }
        }).then((data) => {
            document.querySelector("#AlbumsList").innerHTML = "";//empty out
            document.querySelector("#PlaylistList").innerHTML = "";
            
            document.querySelector("#PlaylistsTitle").innerHTML = "";
            document.querySelector("#LoadAlbumsBtn").innerHTML = "";
            document.querySelector("#LoadPlaylistsBtn").innerHTML = "";
            document.querySelector("#AlbumsSearchResult").innerHTML = "Search results for genres called " + Query; 
            let i=0;
            data.forEach((genre) => {
              var GenreElement = document.createElement("div");
              var GenreName = genre.name;
              var GenreDescription = genre.description;
      
              GenreElement.className = "ListItem col-2 ms-5 mb-5 text-center";
              GenreElement.style = "border-radius: 12px;";
              console.log(JSON.stringify(data))
              let ID = data[i].id;
              let URL = window.location
              let BaseURL = URL.protocol + "//" + URL.host;
              let FinalURL = BaseURL + `/genre?GenreID=${ID}`
      
              GenreElement.innerHTML = `
              <a href="${FinalURL}">
                    <div class="text-center">
                        <h4>${GenreName}</h4>
                        <h4>${GenreDescription}</h4>
                    </div>
                </a>
                `;
      
              document.querySelector("#AlbumsList").append(GenreElement);
              i++;
            });
          })
          .catch((err) => alert("There was a problem with the search query."+err));


    } else if (SearchType == "Playlists") {
        if (IncludeFeatured) {
            SearchString += "playlists/" + Query;
        } else {
            SearchString += "playlists/" + Query + "?contributingartists=false"
        }

        fetch(SearchString, {
            method: "GET"
        }).then((res) => {
            if(res.status === 200){
                return res.json(0)
            }else{
                throw "The search query couldn't be completed. Status: "+res.status;
            }
        }).then((data) => {
            document.querySelector("#AlbumsList").innerHTML = "";//empty out
            document.querySelector("#PlaylistList").innerHTML = "";
            
            document.querySelector("#PlaylistsTitle").innerHTML = "";
            document.querySelector("#LoadAlbumsBtn").innerHTML = "";
            document.querySelector("#LoadPlaylistsBtn").innerHTML = "";
            document.querySelector("#AlbumsSearchResult").innerHTML = "Search results for playlists called " + Query; 
            data.forEach((playlist) => {
              var PlaylistElement = document.createElement("div");
              console.log(JSON.stringify(playlist))
      
              var PlaylistName = playlist.name;
              var Description = playlist.description;
              var TrackCount = playlist.tracks.length;
      
      
              PlaylistElement.className = "ListItem col-2 ms-5 mb-5 text-center mt-5";
              PlaylistElement.style = "border-radius: 12px;";
      
              let URL = window.location
              let BaseURL = URL.protocol + "//" + URL.host;
              let FinalURL = BaseURL + `/playlist?PlaylistID=${playlist.id}`
      
              PlaylistElement.innerHTML = `
                  <a href="${FinalURL}">
                  <img src="${playlist.artwork}" class="img-fluid mt-3" alt="Album Cover"
                        style="border-radius: 12px;">
                    <div class="text-center">
                      <h4>${PlaylistName}</h4>
                      <h4>${Description}</h4>
                      <h4>Track count: ${TrackCount}</h4>
                    </div>
                  </a>
                `;
      
              document.querySelector("#AlbumsList").append(PlaylistElement);
            });
          })
          .catch((err) => alert("There was a problem with the search query."+err));

    } else if (SearchType == "Tracks") {
        if (IncludeFeatured) {
            SearchString += "tracks/" + Query;
        } else {//cont include ft tracks
            SearchString += "tracks/" + Query + "?contributingartists=false"
        }

        fetch(SearchString, {
            method: "GET"
        }).then((res) => {
            if(res.status === 200){
                return res.json(0)
            }else{
                throw "The search query couldn't be completed. Status: "+res.status;
            }
        }).then((data) => {
            document.querySelector("#AlbumsList").innerHTML = "";//empty out
            document.querySelector("#PlaylistList").innerHTML = "";
            
            document.querySelector("#PlaylistsTitle").innerHTML = "";
            document.querySelector("#LoadAlbumsBtn").innerHTML = "";
            document.querySelector("#LoadPlaylistsBtn").innerHTML = "";
            document.querySelector("#AlbumsSearchResult").innerHTML = "Search results for tracks called " + Query; 
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
      
              document.querySelector("#AlbumsList").append(TrackElement);
            });
          })
          .catch((err) => alert("There was a problem with the search query."+err));

    } else if (SearchType == "All") {
        document.querySelector("#AlbumsList").innerHTML = "";//empty out
        document.querySelector("#AlbumsSearchResult").innerHTML = "Search results for tracks, playlists, albums, artists and genres called " + Query; 
        //Tracks starts
        SearchString = "http://localhost:8082/search/";
        if (IncludeFeatured) {
            SearchString += "tracks/" + Query;
        } else {//cont include ft tracks
            SearchString += "tracks/" + Query + "?contributingartists=false"
        }

        fetch(SearchString, {
            method: "GET"
        }).then((res) => {
            if(res.status === 200){
                return res.json(0)
            }else{
                throw "The search query couldn't be completed. Status: "+res.status;
            }
        }).then((data) => {
            
            document.querySelector("#PlaylistList").innerHTML = "";
            
            document.querySelector("#PlaylistsTitle").innerHTML = "";
            document.querySelector("#LoadAlbumsBtn").innerHTML = "";
            document.querySelector("#LoadPlaylistsBtn").innerHTML = "";
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
      
              document.querySelector("#AlbumsList").append(TrackElement);
            });
          })
          .catch((err) => alert("There was a problem with the search query."+err));

        //tracks ends

        //playlists starts
        SearchString = "http://localhost:8082/search/";
        if (IncludeFeatured) {
            SearchString += "playlists/" + Query;
        } else {
            SearchString += "playlists/" + Query + "?contributingartists=false"
        }

        fetch(SearchString, {
            method: "GET"
        }).then((res) => {
            if(res.status === 200){
                return res.json(0)
            }else{
                throw "The search query couldn't be completed. Status: "+res.status;
            }
        }).then((data) => {
            document.querySelector("#PlaylistList").innerHTML = "";
            
            document.querySelector("#PlaylistsTitle").innerHTML = "";
            document.querySelector("#LoadAlbumsBtn").innerHTML = "";
            document.querySelector("#LoadPlaylistsBtn").innerHTML = "";
            data.forEach((playlist) => {
              var PlaylistElement = document.createElement("div");
              console.log(JSON.stringify(playlist))
      
              var PlaylistName = playlist.name;
              var Description = playlist.description;
              var TrackCount = playlist.tracks.length;
      
      
              PlaylistElement.className = "ListItem col-2 ms-5 mb-5 text-center mt-5";
              PlaylistElement.style = "border-radius: 12px;";
      
              let URL = window.location
              let BaseURL = URL.protocol + "//" + URL.host;
              let FinalURL = BaseURL + `/playlist?PlaylistID=${playlist.id}`
      
              PlaylistElement.innerHTML = `
                  <a href="${FinalURL}">
                  <img src="${playlist.artwork}" class="img-fluid mt-3" alt="Album Cover"
                        style="border-radius: 12px;">
                    <div class="text-center">
                      <h4>${PlaylistName}</h4>
                      <h4>${Description}</h4>
                      <h4>Track count: ${TrackCount}</h4>
                    </div>
                  </a>
                `;
      
              document.querySelector("#AlbumsList").append(PlaylistElement);
            });
          })
          .catch((err) => alert("There was a problem with the search query."+err));


        //playlists ends

        //genre starts
        SearchString = "http://localhost:8082/search/";
        SearchString += "genres/" + Query;

        fetch(SearchString, {
            method: "GET"
        }).then((res) => {
            if(res.status === 200){
                return res.json(0)
            }else{
                throw "The search query couldn't be completed. Status: "+res.status;
            }
        }).then((data) => {
            document.querySelector("#PlaylistList").innerHTML = "";
            
            document.querySelector("#PlaylistsTitle").innerHTML = "";
            document.querySelector("#LoadAlbumsBtn").innerHTML = "";
            document.querySelector("#LoadPlaylistsBtn").innerHTML = "";
            let i=0;
            data.forEach((genre) => {
              var GenreElement = document.createElement("div");
              var GenreName = genre.name;
              var GenreDescription = genre.description;
      
              GenreElement.className = "ListItem col-2 ms-5 mb-5 text-center";
              GenreElement.style = "border-radius: 12px;";
              console.log(JSON.stringify(data))
              let ID = data[i].id;
              let URL = window.location
              let BaseURL = URL.protocol + "//" + URL.host;
              let FinalURL = BaseURL + `/genre?GenreID=${ID}`
      
              GenreElement.innerHTML = `
              <a href="${FinalURL}">
                    <div class="text-center">
                        <h4>${GenreName}</h4>
                        <h4>${GenreDescription}</h4>
                    </div>
                </a>
                `;
      
              document.querySelector("#AlbumsList").append(GenreElement);
              i++;
            });
          })
          .catch((err) => alert("There was a problem with the search query."+err));


        //genre ends

        //artists starts
        SearchString = "http://localhost:8082/search/";
        SearchString += "artists/" + Query;
        fetch(SearchString, {
            method: "GET"
        }).then((res) => {
            if(res.status === 200){
                return res.json(0)
            }else{
                throw "The search query couldn't be completed. Status: "+res.status;
            }
        }).then((data) => {
            document.querySelector("#PlaylistList").innerHTML = "";
            
            document.querySelector("#PlaylistsTitle").innerHTML = "";
            document.querySelector("#LoadAlbumsBtn").innerHTML = "";
            document.querySelector("#LoadPlaylistsBtn").innerHTML = "";
            var i =0;
            data.forEach((Artist) => {
              var ArtistElement = document.createElement("div");
              var name = Artist.name;
              console.log(JSON.stringify(data))
              ArtistElement.className = "ListItem col-2 ms-5 mb-5 text-center mt-5";
              ArtistElement.style = "border-radius: 12px;";
              let ID = data[i].id;
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
      
              document.querySelector("#AlbumsList").append(ArtistElement);
              i++
            });
          })
          .catch((err) => alert("There was a problem with the search query."+err));

        //artists ends

        //album starts
        SearchString = "http://localhost:8082/search/";
        SearchString += "albums/" + Query; //no ft option
        fetch(SearchString, {
            method: "GET"
        }).then((res) => {
            if(res.status === 200){
                return res.json(0)
            }else{
                throw "The search query couldn't be completed. Status: "+res.status;
            }
        }).then((data) => {
            document.querySelector("#PlaylistList").innerHTML = "";
            document.querySelector("#PlaylistsTitle").innerHTML = "";
            document.querySelector("#LoadAlbumsBtn").innerHTML = "";
            document.querySelector("#LoadPlaylistsBtn").innerHTML = "";
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
          .catch((err) => alert("There was a problem with the search query."+err));

        //album ends
    }
}