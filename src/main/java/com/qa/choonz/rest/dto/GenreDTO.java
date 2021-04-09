package com.qa.choonz.rest.dto;

import java.util.List;
import java.util.Objects;

public class GenreDTO {

    private int id;
    private String name;
    private String description;
    private List<AlbumGenresRelationshipDTO> albums;

    public GenreDTO() {
        super();
        // TODO Auto-generated constructor stub
    }

    public GenreDTO(int id, String name, String description, List<AlbumGenresRelationshipDTO> albums) {
        super();
        this.id = id;
        this.name = name;
        this.description = description;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<AlbumGenresRelationshipDTO> getAlbums() {
        return albums;
    }

    public void setAlbums(List<AlbumGenresRelationshipDTO> albums) {
        this.albums = albums;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("GenreDTO [id=").append(id).append(", name=").append(name).append(", description=")
                .append(description).append(", albums=").append(albums).append("]");
        return builder.toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, id, name, albums);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof GenreDTO)) {
            return false;
        }
        GenreDTO other = (GenreDTO) obj;
        return Objects.equals(description, other.description) && id == other.id && Objects.equals(name, other.name)
                && Objects.equals(albums, other.albums);
    }

}
