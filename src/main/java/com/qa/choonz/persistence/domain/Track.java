package com.qa.choonz.persistence.domain;

import java.util.List;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
public class Track {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @Size(max = 100)
    private String name;

    @ManyToOne
    private Album album;

    @OneToOne
    private Artist artist;

    // in seconds
    private Integer duration;

    private String lyrics;

    @ManyToMany(mappedBy = "tracks")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Playlist> playlists;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable
    private List<Artist> contributingArtists;

    public Track() {
        super();
    }

    public Track(int id, @NotNull @Size(max = 100) String name, Album album, Integer duration, String lyrics,
            List<Artist> contributingArtists) {
        super();
        this.id = id;
        this.name = name;
        this.album = album;
        this.duration = duration;
        this.lyrics = lyrics;
        this.contributingArtists = contributingArtists;
    }

    public Track(int id, @NotNull @Size(max = 100) String name, Integer duration, String lyrics) {
        super();
        this.id = id;
        this.name = name;
        this.duration = duration;
        this.lyrics = lyrics;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Artist getArtist() {
        return this.artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getLyrics() {
        return lyrics;
    }

    public void setLyrics(String lyrics) {
        this.lyrics = lyrics;
    }

    public List<Artist> getContributingArtists() {
        return this.contributingArtists;
    }

    public void setContributingArtists(List<Artist> contributingArtists) {
        this.contributingArtists = contributingArtists;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Track [id=").append(id).append(", name=").append(name).append(", album=").append(album)
                .append(", duration=").append(duration).append(", lyrics=").append(lyrics).append(", artist=")
                .append(artist).append(", contributingArtists=").append(contributingArtists).append("]");
        return builder.toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(artist, album, duration, id, lyrics, name, contributingArtists);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Track)) {
            return false;
        }
        Track other = (Track) obj;
        return Objects.equals(album, other.album) && duration == other.duration && id == other.id
                && Objects.equals(lyrics, other.lyrics) && Objects.equals(name, other.name)
                && Objects.equals(artist, other.artist)
                && Objects.equals(contributingArtists, other.contributingArtists);
    }

}