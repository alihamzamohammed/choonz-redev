package com.qa.choonz.rest.dto;

import java.util.List;
import java.util.Objects;

public class AlbumDTO {

    private int id;
    private String name;
    private List<TrackRelationshipDTO> tracks;
    private ArtistRelationshipDTO artist;
    private List<GenreRelationshipDTO> genre;
    private String cover;

    public AlbumDTO() {
        super();
        // TODO Auto-generated constructor stub
    }

    public AlbumDTO(int id, String name, List<TrackRelationshipDTO> tracks, List<GenreRelationshipDTO> genre,
            String cover, ArtistRelationshipDTO artist) {
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

    public List<TrackRelationshipDTO> getTracks() {
        return tracks;
    }

    public void setTracks(List<TrackRelationshipDTO> tracks) {
        this.tracks = tracks;
    }

    public ArtistRelationshipDTO getArtist() {
        return artist;
    }

    public void setArtist(ArtistRelationshipDTO artist) {
        this.artist = artist;
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
        builder.append("AlbumDTO [id=").append(id).append(", name=").append(name).append(", tracks=").append(tracks)
                .append(", genre=").append(", artist=").append(artist).append(genre).append(", cover=").append(cover)
                .append("]");
        return builder.toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(cover, artist, genre, id, name, tracks);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AlbumDTO)) {
            return false;
        }
        AlbumDTO other = (AlbumDTO) obj;
        return Objects.equals(artist, other.artist) && Objects.equals(cover, other.cover)
                && Objects.equals(genre, other.genre) && id == other.id && Objects.equals(name, other.name)
                && Objects.equals(tracks, other.tracks);
    }

}
