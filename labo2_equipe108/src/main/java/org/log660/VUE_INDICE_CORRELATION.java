package org.log660;
import jakarta.persistence.*;

@Entity
@Table(name = "VUE_INDICE_CORRELATION")

public class VUE_INDICE_CORRELATION {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	
    private int film_1;
    private int film_2;
    private int indice_correlation;

    public int getFilm_1() {
        return film_1;
    }

    public void setFilm_1(int film_1) {
        this.film_1 = film_1;
    }

    public int getFilm_2() {
        return film_2;
    }
    public void setFilm_2(int film_2) {
        this.film_2 = film_2;
    }
    
    public int getIndice_correlation() {
        return indice_correlation;
    }

    public void setIndice_correlation(int indice_correlation) {
        this.indice_correlation = indice_correlation;
    }

}
