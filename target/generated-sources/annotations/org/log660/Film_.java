package org.log660;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Film.class)
@Generated("org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
public abstract class Film_ {

	
	/**
	 * @see org.log660.Film#resumeScenario
	 **/
	public static volatile SingularAttribute<Film, String> resumeScenario;
	
	/**
	 * @see org.log660.Film#titre
	 **/
	public static volatile SingularAttribute<Film, String> titre;
	
	/**
	 * @see org.log660.Film#qteDisponible
	 **/
	public static volatile SingularAttribute<Film, Integer> qteDisponible;
	
	/**
	 * @see org.log660.Film#duree
	 **/
	public static volatile SingularAttribute<Film, Integer> duree;
	
	/**
	 * @see org.log660.Film#id
	 **/
	public static volatile SingularAttribute<Film, Integer> id;
	
	/**
	 * @see org.log660.Film#annee
	 **/
	public static volatile SingularAttribute<Film, Integer> annee;
	
	/**
	 * @see org.log660.Film#langue
	 **/
	public static volatile SingularAttribute<Film, String> langue;
	
	/**
	 * @see org.log660.Film
	 **/
	public static volatile EntityType<Film> class_;
	
	/**
	 * @see org.log660.Film#poster
	 **/
	public static volatile SingularAttribute<Film, String> poster;
	
	/**
	 * @see org.log660.Film#idRealisateur
	 **/
	public static volatile SingularAttribute<Film, Integer> idRealisateur;
	
	/**
	 * @see org.log660.Film#qteInitiale
	 **/
	public static volatile SingularAttribute<Film, Integer> qteInitiale;

	public static final String RESUME_SCENARIO = "resumeScenario";
	public static final String TITRE = "titre";
	public static final String QTE_DISPONIBLE = "qteDisponible";
	public static final String DUREE = "duree";
	public static final String ID = "id";
	public static final String ANNEE = "annee";
	public static final String LANGUE = "langue";
	public static final String POSTER = "poster";
	public static final String ID_REALISATEUR = "idRealisateur";
	public static final String QTE_INITIALE = "qteInitiale";

}

