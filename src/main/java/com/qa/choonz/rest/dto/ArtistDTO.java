package com.qa.choonz.rest.dto;

import java.util.List;
import java.util.Objects;

public class ArtistDTO {

    private int id;
    private String name;
    private List<AlbumArtistRelationshipDTO> albums;
    private List<TrackRelationshipDTO> contributedTracks;

    public ArtistDTO() {
        super();
    }
    
    public ArtistDTO(String name, List<AlbumArtistRelationshipDTO> albums,
            List<TrackRelationshipDTO> contributedTracks) {
        super();
        this.name = name;
        this.albums = albums;
        this.contributedTracks = contributedTracks;
    }

    public ArtistDTO(int id, String name, List<AlbumArtistRelationshipDTO> albums,
            List<TrackRelationshipDTO> contributedTracks) {
        super();
        this.id = id;
        this.name = name;
        this.albums = albums;
        this.contributedTracks = contributedTracks;
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

    public List<AlbumArtistRelationshipDTO> getAlbums() {
        return albums;
    }

    public void setAlbums(List<AlbumArtistRelationshipDTO> albums) {
        this.albums = albums;
    }

    public List<TrackRelationshipDTO> getContributedTracks() {
        return this.contributedTracks;
    }

    public void setContributedTracks(List<TrackRelationshipDTO> contributedTracks) {
        this.contributedTracks = contributedTracks;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ArtistDTO [id=").append(id).append(", name=").append(name).append(", albums=").append(albums)
                .append(", contributedTracks=").append(contributedTracks).append("]");
        return builder.toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, contributedTracks, albums);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ArtistDTO)) {
            return false;
        }
        ArtistDTO other = (ArtistDTO) obj;
        return Objects.equals(albums, other.albums) && id == other.id && Objects.equals(name, other.name)
                && Objects.equals(contributedTracks, other.contributedTracks);
    }

}
