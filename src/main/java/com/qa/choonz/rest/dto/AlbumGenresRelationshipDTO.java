package com.qa.choonz.rest.dto;

import java.util.List;
import java.util.Objects;

public class AlbumGenresRelationshipDTO {

    private int id;
    private String name;
    private List<TrackRelationshipDTO> tracks;
    private ArtistRelationshipDTO artist;
    private String cover;

    public AlbumGenresRelationshipDTO() {
        super();
        // TODO Auto-generated constructor stub
    }

    public AlbumGenresRelationshipDTO(int id, String name, List<TrackRelationshipDTO> tracks, String cover,
            ArtistRelationshipDTO artist) {
        super();
        this.id = id;
        this.name = name;
        this.tracks = tracks;
        this.artist = artist;
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

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("AlbumGenresRelationshipDTO [id=").append(id).append(", name=").append(name).append(", tracks=")
                .append(tracks).append(", genre=").append(", artist=").append(artist).append(", cover=").append(cover)
                .append("]");
        return builder.toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(cover, artist, id, name, tracks);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AlbumGenresRelationshipDTO)) {
            return false;
        }
        AlbumGenresRelationshipDTO other = (AlbumGenresRelationshipDTO) obj;
        return Objects.equals(artist, other.artist) && Objects.equals(cover, other.cover) && id == other.id
                && Objects.equals(name, other.name) && Objects.equals(tracks, other.tracks);
    }

}
