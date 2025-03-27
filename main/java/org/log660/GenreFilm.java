package org.log660;

import jakarta.persistence.*;

@Entity
@Table(name="GENREFILM")
public class GenreFilm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int film_id;
    private String genre;

    public String getGenre() {
        return genre;
    }

    public int getFilm_id() {
        return film_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFilm_id(int film_id) {
        this.film_id = film_id;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}
