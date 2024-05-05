package org.log660;

import jakarta.persistence.*;

@Entity
@Table(name = "PAYSPRODUCTION")
public class PaysProduction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int film_id;
    private String nompays;

    public String getNompays() {
        return nompays;
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

    public void setNomPays(String pays) {
        this.nompays = pays;
    }
}
