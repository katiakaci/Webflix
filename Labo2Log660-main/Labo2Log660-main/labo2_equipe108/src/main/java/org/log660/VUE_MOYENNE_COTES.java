package org.log660;
import jakarta.persistence.*;

@Entity
@Table(name = "VUE_MOYENNE_COTES")
public class VUE_MOYENNE_COTES {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	
    private int idFilm;
    private double moyenne_cotes;

    public int getIdFilm() {
        return idFilm;
    }

    public void setIdFilm(int idFilm) {
        this.idFilm = idFilm;
    }


    public double getMoyenne_cotes() {
        return moyenne_cotes;
    }

    public void setMoyenne_cotes(double moyenne_cotes) {
        this.moyenne_cotes = moyenne_cotes;
    }

}
