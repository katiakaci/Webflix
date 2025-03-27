package org.log660;

import jakarta.persistence.*;

@Entity
@Table(name = "SCENARISTE")
public class Scenariste {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int film_id;
    private String nom;

    public String getNom() {
        return nom;
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

    public void setNom(String nom) {
        this.nom = nom;
    }
}
