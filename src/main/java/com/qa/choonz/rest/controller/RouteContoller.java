package com.qa.choonz.rest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RouteContoller {
    @GetMapping(value = "/")
    public String index() {
        return "index.html";
    }

    @GetMapping(value = "/home")
    public String home() {
        return "index.html";
    }

    @GetMapping(value = "/login")
    public String login() {
        return "login.html";
    }

    @GetMapping(value = "/album")
    public String album() {
        return "album.html";
    }

    @GetMapping(value = "/albums")
    public String albums() {
        return "albums.html";
    }

    @GetMapping(value = "/artist")
    public String artist() {
        return "artist.html";
    }

    @GetMapping(value = "/artists")
    public String artists() {
        return "artists.html";
    }

    @GetMapping(value = "/genres")
    public String genres() {
        return "genres.html";
    }

    @GetMapping(value = "/genre")
    public String genre() {
        return "genre.html";
    }

    @GetMapping(value = "/playlists")
    public String playlists() {
        return "playlists.html";
    }

    @GetMapping(value = "/playlist")
    public String playlist() {
        return "playlist.html";
    }

    @GetMapping(value = "/signup")
    public String signup() {
        return "signup.html";
    }

    @GetMapping(value = "/tracks")
    public String tracks() {
        return "tracks.html";
    }

    @GetMapping(value = "/track")
    public String track() {
        return "track.html";
    }

}
