package com.qa.choonz.persistence.domain;

import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
    @Column(unique = true)
    private String name;

    @ManyToOne
    private Album album;

    @OneToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    @JoinColumn(name = "fk_artist_id")
    private Artist artist;

    // in seconds
    private int duration;

    private String lyrics;

    public Track() {
        super();
    }

    public Track(int id, @NotNull @Size(max = 100) String name, Album album, int duration, String lyrics) {
        super();
        this.id = id;
        this.name = name;
        this.album = album;
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

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getLyrics() {
        return lyrics;
    }

    public void setLyrics(String lyrics) {
        this.lyrics = lyrics;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Track [id=").append(id).append(", name=").append(name).append(", album=").append(album)
                .append(", duration=").append(duration).append(", lyrics=").append(lyrics).append(", artist=")
                .append(artist).append("]");
        return builder.toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(artist, album, duration, id, lyrics, name);
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
                && Objects.equals(artist, other.artist);
    }

}
