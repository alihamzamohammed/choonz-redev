package com.qa.choonz.rest.dto;

import java.util.List;
import java.util.Objects;

public class AlbumArtistRelationshipDTO {

    private int id;
    private String name;
    private List<TrackRelationshipDTO> tracks;
    private List<GenreRelationshipDTO> genre;
    private String cover;

    public AlbumArtistRelationshipDTO() {
        super();
    }

    public AlbumArtistRelationshipDTO(int id, String name, List<TrackRelationshipDTO> tracks,
            List<GenreRelationshipDTO> genre, String cover) {
        super();
        this.id = id;
        this.name = name;
        this.tracks = tracks;
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

    public List<TrackRelationshipDTO> getTracks() {
        return tracks;
    }

    public void setTracks(List<TrackRelationshipDTO> tracks) {
        this.tracks = tracks;
    }

    public List<GenreRelationshipDTO> getGenre() {
        return genre;
    }

    public void setGenre(List<GenreRelationshipDTO> genre) {
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
        builder.append("AlbumArtistRelationshipDTO [id=").append(id).append(", name=").append(name).append(", tracks=")
                .append(tracks).append(", genre=").append(genre).append(", cover=").append(cover).append("]");
        return builder.toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(cover, genre, id, name, tracks);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AlbumArtistRelationshipDTO)) {
            return false;
        }
        AlbumArtistRelationshipDTO other = (AlbumArtistRelationshipDTO) obj;
        return Objects.equals(cover, other.cover) && Objects.equals(genre, other.genre) && id == other.id
                && Objects.equals(name, other.name) && Objects.equals(tracks, other.tracks);
    }

}
