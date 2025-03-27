package org.log660;

import jakarta.persistence.*;

@Entity
@Table(name="CLIENT")
public class Client{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id; 

	private String nomFamille;

	private String prenom;

	private String courriel; 

	private String motDePasse;

	private String dateNaissance;

	private String telephone; 

	private String adresse; 

	private String ville; 

	private String province;

	private String codePostal;

	@Column(name = "FORFAITLOCATION_CODE")
	private char forfaitLocationCode;

	private String typeCarteCredit; 

	private String numeroCarteCredit;

	private int anneeExp; 

	private int moisExp;

	public int getId() {
		return id;
	}
	
	public String getTypeCarteCredit() {
		return typeCarteCredit;
	}

	public void setTypeCarteCredit(String typeCarteCredit) {
		this.typeCarteCredit = typeCarteCredit;
	}

	public String getNumeroCarteCredit() {
		return numeroCarteCredit;
	}

	public void setNumeroCarteCredit(String numeroCarteCredit) {
		this.numeroCarteCredit = numeroCarteCredit;
	}

	public int getAnneeExp() {
		return anneeExp;
	}

	public void setAnneeExp(int anneeExp) {
		this.anneeExp = anneeExp;
	}

	public int getMoisExp() {
		return moisExp;
	}

	public void setMoisExp(int moisExp) {
		this.moisExp = moisExp;
	}

	public String getCourriel() {
		return courriel;
	}
	public void setCourriel(String courriel) {
		this.courriel = courriel;
	}
	public String getMotDePasse() {
		return motDePasse;
	}
	public void setMotDePasse(String motDePasse) {
		this.motDePasse = motDePasse;
	}
	public String getDateNaissance() {
		return dateNaissance;
	}
	public void setDateNaissance(String dateNaissance) {
		this.dateNaissance = dateNaissance;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getAdresse() {
		return adresse;
	}
	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}
	public String getVille() {
		return ville;
	}
	public void setVille(String ville) {
		this.ville = ville;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCodePostal() {
		return codePostal;
	}
	public void setCodePostal(String codePostal) {
		this.codePostal = codePostal;
	}
	public char getForfaitLocationCode() {
		return forfaitLocationCode;
	}
	public void setForfaitLocationCode(char forfaitLocationCode) {
		this.forfaitLocationCode = forfaitLocationCode;
	}

}
