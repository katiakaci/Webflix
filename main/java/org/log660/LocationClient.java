package org.log660;

import jakarta.persistence.*;

@Entity
@Table(name = "LOCATIONCLIENT")
public class LocationClient {

    @Id
    private int id;

    //@Column(name = "CLIENT_ID")
    private int client_id;

    //@Column(name = "FILM_ID")
    private int film_id;

    //@Column(name = "DATE_EMPRUNT")
    private String date_emprunt;

    ///@Column(name = "DATE_RETOUR")
    private String date_retour;

    public int getClientId() {
        return client_id;
    }
    public void setClientId(int clientId) {
        this.client_id = clientId;
    }
    public int getFilmId() {
        return film_id;
    }
    public void setFilmId(int filmId) {
        this.film_id = filmId;
    }
    public String getDateEmprunt() {
        return date_emprunt;
    }
    public void setDateEmprunt(String dateEmprunt) {
        this.date_emprunt = dateEmprunt;
    }
    public String getDateRetour() {
        return date_retour;
    }
    public void setDateRetour(String dateRetour) {
        this.date_retour = dateRetour;
    }
    public int getId() { return id;	}
    public void setId(int id) {	this.id = id; }

}