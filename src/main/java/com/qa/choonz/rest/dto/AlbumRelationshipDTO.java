package com.qa.choonz.rest.dto;

import java.util.List;
import java.util.Objects;

public class AlbumRelationshipDTO {

    private int id;
    private String name;
    // private List<TrackRelationshipDTO> tracks;
    private GenreRelationshipDTO genre;
    private String cover;

    public AlbumRelationshipDTO() {
        super();
        // TODO Auto-generated constructor stub
    }

    public AlbumRelationshipDTO(int id, String name,
            /* List<TrackRelationshipDTO> tracks, */ GenreRelationshipDTO genre, String cover) {
        super();
        this.id = id;
        this.name = name;
        // this.tracks = tracks;
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

    // public List<TrackRelationshipDTO> getTracks() {
    // return tracks;
    // }

    // public void setTracks(List<TrackRelationshipDTO> tracks) {
    // this.tracks = tracks;
    // }

    public GenreRelationshipDTO getGenre() {
        return genre;
    }

    public void setGenre(GenreRelationshipDTO genre) {
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
        builder.append("AlbumRelationshipDTO [id=").append(id).append(", name=").append(name)
                /*
                 * .append(", tracks=") .append(tracks)
                 */.append(", genre=").append(genre).append(", cover=").append(cover).append("]");
        return builder.toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(cover, genre, id, name /* ,tracks */);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AlbumRelationshipDTO)) {
            return false;
        }
        AlbumRelationshipDTO other = (AlbumRelationshipDTO) obj;
        return Objects.equals(cover, other.cover) && Objects.equals(genre, other.genre) && id == other.id
                && Objects.equals(name, other.name)/* && Objects.equals(tracks, other.tracks) */;
    }

}
