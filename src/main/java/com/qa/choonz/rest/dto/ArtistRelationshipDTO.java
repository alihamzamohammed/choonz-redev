package com.qa.choonz.rest.dto;

import java.util.Objects;

public class ArtistRelationshipDTO {

    private int id;
    private String name;

    public ArtistRelationshipDTO() {
        super();
    }

    public ArtistRelationshipDTO(int id, String name) {
        super();
        this.id = id;
        this.name = name;
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

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ArtistRelationshipDTO [id=").append(id).append(", name=").append(name).append("]");
        return builder.toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ArtistRelationshipDTO)) {
            return false;
        }
        ArtistRelationshipDTO other = (ArtistRelationshipDTO) obj;
        return id == other.id && Objects.equals(name, other.name);
    }

}
