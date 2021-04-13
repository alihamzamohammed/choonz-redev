package com.qa.choonz.persistence.domain;

import java.util.List;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
public class Artist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @Size(max = 100)
    private String name;

    @OneToMany(mappedBy = "artist")
    private List<Album> albums;

    @ManyToMany
    @JoinTable(joinColumns = @JoinColumn(name = "ARTIST_ID", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "TRACK_ID", referencedColumnName = "ID"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Track> contributedTracks;

    public Artist() {
        super();
    }

    public Artist(int id, @NotNull @Size(max = 100) String name, List<Album> albums, List<Track> contributedTracks) {
        super();
        this.id = id;
        this.name = name;
        this.albums = albums;
        this.contributedTracks = contributedTracks;
    }
    
    public Artist(@NotNull @Size(max = 100) String name, List<Album> albums, List<Track> contributedTracks) {
        super();
        this.name = name;
        this.albums = albums;
        this.contributedTracks = contributedTracks;
    }


    public Artist(int id, @NotNull @Size(max = 100) String name) {
        super();
        this.id = id;
        this.name = name;
    }

    public Artist(@NotNull @Size(max = 100) String name, List<Album> albums) {
        super();
        this.name = name;
        this.albums = albums;
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

    public List<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(List<Album> albums) {
        this.albums = albums;
    }

    public List<Track> getContributedTracks() {
        return this.contributedTracks;
    }

    public void setContributedTracks(List<Track> contributedTracks) {
        this.contributedTracks = contributedTracks;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Artist [id=").append(id).append(", name=").append(name).append(", albums=").append(albums)
                .append("]");
        return builder.toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(albums, name);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Artist)) {
            return false;
        }
        Artist other = (Artist) obj;
        return Objects.equals(albums, other.albums) && Objects.equals(name, other.name);
    }

}
