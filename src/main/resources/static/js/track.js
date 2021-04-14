'use strict'

(function () {
    // var playlist = parseInt(document.querySelector("#PlaylistList").value);

    const params = new URLSearchParams(window.location.search);
    let track = params.get("TrackId");

    fetch(`http://localhost:8082/track/${track}`, {
            method: "GET",
        })
        .then((response) => {
            if (response.status === 200) {
                return response.json();
            } else {
                throw "Response code was not 200 it was " + response.status;
            }
        })
        .then((playlist) => {
            TrackElement = document.createElement("div");
            TrackName = track.name;
            TrackDuration = track.duration;
            TrackLyrics = track.lyrics;

            TrackElement.className = "ListItem col-2 ms-5 mb-5 text-center mt-5";
            TrackElement.style = "border-radius: 12px;";

            TrackElement.innerHTML = `
           
                <div class="text-center">
                <h4>${TrackName}</h4>
                <h4>${TrackDuration} Name</h4>
                <h4>${TrackLyrics}</h4>
                </div>
      
            `;

            document.querySelector("#TrackList").append(TrackElement);
        })
        .catch((err) => {
            alert(
                "There was a problem getting the track from the system. Please try again later." +
                err
            );
        });
})();


let EditTrack = () => {
    const params = new URLSearchParams(window.location.search);
    let track = params.get("TrackId");

    let trackName = document.querySelector("#PlaylistName").value;
    console.log("Track Name: " + trackName);

    let trackDuration = document.querySelector("#TrackDuration")
        .value;
    console.log("Track Duration: " + trackDuration);

    let trackLyrics = document.querySelector("#TrackLyrics").value;
    console.log("Track Lyrics: " + trackLyrics);

    const updatedTrack = {
        name: trackName,
        duration: trackDuration,
        lyrics: trackLyrics
    };

    fetch(`http://localhost:8082/playlist/${playlist}`, {
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

//Delete a Track
let DeleteTrack = async (track) => {
    //  var playlist = parseInt(document.querySelector("#PlaylistList").value);

    const params = new URLSearchParams(window.location.search);
    let TrackID = params.get("TrackID");

    const response = await fetch(`http://localhost:8082/playlist/${track}`, {
        method: "DELETE",
    });
    if (response.status != 204) {
        alert("The Delete Denied, the Track must be valid");
        console.error(`Error: Status code ${reponse.status}\n${response.json}`);
        return response.status;
    }
    alert("Track deleted");
    console.log("Track:" + track + "track");
};