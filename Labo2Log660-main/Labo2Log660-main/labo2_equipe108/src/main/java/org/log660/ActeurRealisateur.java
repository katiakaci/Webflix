package org.log660;

import jakarta.persistence.*;


@Entity
@Table(name="ACTEURREALISATEUR")
public class ActeurRealisateur { //extends Personne {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String dateNaissance;

	private String lieuDeNaissance;

	private String biographie;

	private String photo;

	private String nom;

	public String getLieuDeNaissance() {
		return lieuDeNaissance;
	}
	public void setLieuDeNaissance(String lieuDeNaissance) {
		this.lieuDeNaissance = lieuDeNaissance;
	}
	public String getBiographie() {
		return biographie;
	}
	public void setBiographie(String biographie) {
		this.biographie = biographie;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setDateNaissance(String dateNaissance) {
		this.dateNaissance = dateNaissance;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public int getId() {
		return id;
	}
	public String getDateNaissance() {
		return dateNaissance;
	}
	public String getNom() {
		return nom;
	}
}