package org.log660;

import jakarta.persistence.*;

@Entity
@Table(name = "ROLEFILM")
public class RoleFilm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int acteur_id;
    private int film_id;
    private String personnage;

    public String getPersonnage() {
        return personnage;
    }

    public int getActeur_id() {
        return acteur_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setActeur_id(int acteur_id) {
        this.acteur_id = acteur_id;
    }

    public void setPersonnage(String personnage) {
        this.personnage = personnage;
    }

    public void setFilm_id(int film_id) {
        this.film_id = film_id;
    }

    public int getFilm_id() {
        return film_id;
    }
}
