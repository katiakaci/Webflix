package org.log660;

import jakarta.persistence.*;

@Entity
@Table(name = "FORFAITLOCATION")
public class ForfaitLocation {

	@Id
	private char code;
	private int cout;
	private int locationmax;
	private String dureemax;
	private String niveau;

	public char getCode() {
		return code;
	}
	public void setCode(char code) {
		this.code = code;
	}
	public int getCout() {
		return cout;
	}
	public void setCout(int cout) {
		this.cout = cout;
	}
	public int getLocationMax() {
		return locationmax;
	}
	public void setLocationMax(int locationMax) {
		this.locationmax = locationMax;
	}
	public String getDureeMax() {
		return dureemax;
	}
	public void setDureeMax(String dureeMax) {
		this.dureemax = dureeMax;
	}
	public String getNiveau() {
		return niveau;
	}
	public void setNiveau(String niveau) {
		this.niveau = niveau;
	}

}
