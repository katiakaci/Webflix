package org.log660;

import jakarta.persistence.*;

import java.util.*;

@Entity
@Table(name="FILM")
public class Film {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private int	annee, duree;

	@Column(name = "REALISATEUR_ID")
	private int idRealisateur;

	private int	qteInitiale;

	private int qteDisponible;

	private String titre, langue, resumeScenario, poster;


	public int getId() {
		return id;
	}

	public int getAnnee() {
		return annee;
	}

	public int getDuree() {
		return duree;
	}

	public int getRealisateur() {
		return idRealisateur;
	}

	public int getQteInitiale() {
		return qteInitiale;
	}

	public int getQteDisponible() {
		return qteDisponible;
	}

	public void setQteDisponible(int qteDisponible) {
		this.qteDisponible = qteDisponible;
	}

	public String getTitre() {
		return titre;
	}

	public String getLangue() {
		return langue;
	}

	public String getResumeScenario() {
		return resumeScenario;
	}

	public String getPoster() {
		return poster;
	}

	public String toString(){
		return this.getTitre()+ " ("+ this.getAnnee()+ ")";
	}

}
