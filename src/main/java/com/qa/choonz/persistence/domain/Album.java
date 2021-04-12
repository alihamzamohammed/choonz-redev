package com.qa.choonz.persistence.domain;

import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
public class Album {

    // Cascade on Delete.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @Size(max = 100)
    @Column(unique = true)
    private String name;

    @OneToMany(mappedBy = "album") // , cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Track> tracks;

    @ManyToOne
    private Artist artist;

    @ManyToMany(/*
                 * cascade = { CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.REFRESH },
                 */ fetch = FetchType.EAGER)
    @JoinTable
    private List<Genre> genre;

    @Size(max = 50)
    @NotNull
    @Column(unique = true)
    private String cover;

    public Album() {
        super();
        // TODO Auto-generated constructor stub
    }

    public Album(int id, @NotNull @Size(max = 100) String name, List<Track> tracks, Artist artist, List<Genre> genre,
            @NotNull @Size(max = 50) String cover) {
        super();
        this.id = id;
        this.name = name;
        this.tracks = tracks;
        this.artist = artist;
        this.genre = genre;
        this.cover = cover;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Track> getTracks() {
        return tracks;
    }

    public void setTracks(List<Track> tracks) {
        this.tracks = tracks;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public List<Genre> getGenre() {
        return genre;
    }

    public void setGenre(List<Genre> genre) {
        this.genre = genre;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Album [id=").append(id).append(", name=").append(name).append(", tracks=").append(tracks)
                .append(", artist=").append(artist).append(", genre=").append(genre).append(", cover=").append(cover)
                .append("]");
        return builder.toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(artist, cover, genre, id, name, tracks);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Album)) {
            return false;
        }
        Album other = (Album) obj;
        return Objects.equals(artist, other.artist) && Objects.equals(cover, other.cover)
                && Objects.equals(genre, other.genre) && id == other.id && Objects.equals(name, other.name)
                && Objects.equals(tracks, other.tracks);
    }

}
