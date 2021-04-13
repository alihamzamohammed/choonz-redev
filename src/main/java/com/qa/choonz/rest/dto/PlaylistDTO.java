package com.qa.choonz.rest.dto;

import java.util.List;
import java.util.Objects;

public class PlaylistDTO {

    private int id;
    private String name;
    private String description;
    private String artwork;
    private List<TrackRelationshipDTO> tracks;

    public PlaylistDTO() {
        super();

    }

    public PlaylistDTO(int id, String name, String description, String artwork, List<TrackRelationshipDTO> tracks) {
        super();
        this.id = id;
        this.name = name;
        this.description = description;
        this.artwork = artwork;
        this.tracks = tracks;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the artwork
     */
    public String getArtwork() {
        return artwork;
    }

    /**
     * @param artwork the artwork to set
     */
    public void setArtwork(String artwork) {
        this.artwork = artwork;
    }

    /**
     * @return the tracks
     */
    public List<TrackRelationshipDTO> getTracks() {
        return this.tracks;
    }

    /**
     * @param tracks the tracks to set
     */
    public void setTracks(List<TrackRelationshipDTO> tracks) {
        this.tracks = tracks;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("PlaylistDTO [id=").append(id).append(", name=").append(name).append(", description=")
                .append(description).append(", artwork=").append(artwork).append(", tracks=").append(tracks)
                .append("]");
        return builder.toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(artwork, description, id, name, tracks);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof PlaylistDTO)) {
            return false;
        }
        PlaylistDTO other = (PlaylistDTO) obj;
        return Objects.equals(artwork, other.artwork) && Objects.equals(description, other.description)
                && id == other.id && Objects.equals(name, other.name) && Objects.equals(tracks, other.tracks);
    }

}
