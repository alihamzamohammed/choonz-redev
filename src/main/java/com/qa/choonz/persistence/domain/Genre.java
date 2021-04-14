package com.qa.choonz.persistence.domain;

import java.util.List;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @Size(max = 100)
    private String name;

    @NotNull
    @Size(max = 250)
    private String description;

    @ManyToMany(mappedBy = "genre", fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Album> albums;

    public Genre() {
        super();
    }

    public Genre(@NotNull @Size(max = 100) String name, @NotNull @Size(max = 250) String description,
            List<Album> albums) {

        super();
        this.name = name;
        this.description = description;
        this.albums = albums;
    }

    public Genre(@NotNull @Size(max = 100) String name, @NotNull @Size(max = 250) String description) {

        super();
        this.name = name;
        this.description = description;
    }

    public Genre(int id, @NotNull @Size(max = 100) String name, @NotNull @Size(max = 250) String description,
            List<Album> albums) {
        super();
        this.id = id;
        this.name = name;
        this.description = description;
        this.albums = albums;
    }

    public Genre(int id, @NotNull @Size(max = 100) String name, @NotNull @Size(max = 250) String description) {
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

    public List<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(List<Album> albums) {
        this.albums = albums;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Genre [id=").append(id).append(", name=").append(name).append(", description=")
                .append(description).append(", albums=").append(albums).append("]");

        return builder.toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(albums, description, name);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Genre)) {
            return false;
        }
        Genre other = (Genre) obj;
        return Objects.equals(albums, other.albums) && Objects.equals(description, other.description)
                && Objects.equals(name, other.name);
    }

}
