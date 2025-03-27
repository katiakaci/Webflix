package org.log660;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Client.class)
@Generated("org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
public abstract class Client_ {

	
	/**
	 * @see org.log660.Client#ville
	 **/
	public static volatile SingularAttribute<Client, String> ville;
	
	/**
	 * @see org.log660.Client#nomFamille
	 **/
	public static volatile SingularAttribute<Client, String> nomFamille;
	
	/**
	 * @see org.log660.Client#dateNaissance
	 **/
	public static volatile SingularAttribute<Client, String> dateNaissance;
	
	/**
	 * @see org.log660.Client#numeroCarteCredit
	 **/
	public static volatile SingularAttribute<Client, String> numeroCarteCredit;
	
	/**
	 * @see org.log660.Client#telephone
	 **/
	public static volatile SingularAttribute<Client, String> telephone;
	
	/**
	 * @see org.log660.Client#codePostal
	 **/
	public static volatile SingularAttribute<Client, String> codePostal;
	
	/**
	 * @see org.log660.Client#courriel
	 **/
	public static volatile SingularAttribute<Client, String> courriel;
	
	/**
	 * @see org.log660.Client#anneeExp
	 **/
	public static volatile SingularAttribute<Client, Integer> anneeExp;
	
	/**
	 * @see org.log660.Client#motDePasse
	 **/
	public static volatile SingularAttribute<Client, String> motDePasse;
	
	/**
	 * @see org.log660.Client#province
	 **/
	public static volatile SingularAttribute<Client, String> province;
	
	/**
	 * @see org.log660.Client#adresse
	 **/
	public static volatile SingularAttribute<Client, String> adresse;
	
	/**
	 * @see org.log660.Client#forfaitLocationCode
	 **/
	public static volatile SingularAttribute<Client, Character> forfaitLocationCode;
	
	/**
	 * @see org.log660.Client#id
	 **/
	public static volatile SingularAttribute<Client, Integer> id;
	
	/**
	 * @see org.log660.Client
	 **/
	public static volatile EntityType<Client> class_;
	
	/**
	 * @see org.log660.Client#prenom
	 **/
	public static volatile SingularAttribute<Client, String> prenom;
	
	/**
	 * @see org.log660.Client#typeCarteCredit
	 **/
	public static volatile SingularAttribute<Client, String> typeCarteCredit;
	
	/**
	 * @see org.log660.Client#moisExp
	 **/
	public static volatile SingularAttribute<Client, Integer> moisExp;

	public static final String VILLE = "ville";
	public static final String NOM_FAMILLE = "nomFamille";
	public static final String DATE_NAISSANCE = "dateNaissance";
	public static final String NUMERO_CARTE_CREDIT = "numeroCarteCredit";
	public static final String TELEPHONE = "telephone";
	public static final String CODE_POSTAL = "codePostal";
	public static final String COURRIEL = "courriel";
	public static final String ANNEE_EXP = "anneeExp";
	public static final String MOT_DE_PASSE = "motDePasse";
	public static final String PROVINCE = "province";
	public static final String ADRESSE = "adresse";
	public static final String FORFAIT_LOCATION_CODE = "forfaitLocationCode";
	public static final String ID = "id";
	public static final String PRENOM = "prenom";
	public static final String TYPE_CARTE_CREDIT = "typeCarteCredit";
	public static final String MOIS_EXP = "moisExp";

}

