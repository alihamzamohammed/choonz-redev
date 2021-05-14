package com.qa.choonz.rest.dto;

import java.util.Objects;

public class GenreRelationshipDTO {

    private int id;
    private String name;
    private String description;

    public GenreRelationshipDTO() {
        super();
    }

    public GenreRelationshipDTO(int id, String name, String description) {
        super();
        this.id = id;
        this.name = name;
        this.description = description;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("GenreRelationshipDTO [id=").append(id).append(", name=").append(name).append(", description=")
                .append(description).append("]");
        return builder.toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, id, name);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof GenreRelationshipDTO)) {
            return false;
        }
        GenreRelationshipDTO other = (GenreRelationshipDTO) obj;
        return Objects.equals(description, other.description) && id == other.id && Objects.equals(name, other.name);
    }

}
