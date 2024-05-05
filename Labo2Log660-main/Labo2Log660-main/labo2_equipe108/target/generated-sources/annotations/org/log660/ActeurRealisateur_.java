package org.log660;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(ActeurRealisateur.class)
@Generated("org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
public abstract class ActeurRealisateur_ {

	
	/**
	 * @see org.log660.ActeurRealisateur#biographie
	 **/
	public static volatile SingularAttribute<ActeurRealisateur, String> biographie;
	
	/**
	 * @see org.log660.ActeurRealisateur#dateNaissance
	 **/
	public static volatile SingularAttribute<ActeurRealisateur, String> dateNaissance;
	
	/**
	 * @see org.log660.ActeurRealisateur#photo
	 **/
	public static volatile SingularAttribute<ActeurRealisateur, String> photo;
	
	/**
	 * @see org.log660.ActeurRealisateur#lieuDeNaissance
	 **/
	public static volatile SingularAttribute<ActeurRealisateur, String> lieuDeNaissance;
	
	/**
	 * @see org.log660.ActeurRealisateur#id
	 **/
	public static volatile SingularAttribute<ActeurRealisateur, Integer> id;
	
	/**
	 * @see org.log660.ActeurRealisateur
	 **/
	public static volatile EntityType<ActeurRealisateur> class_;
	
	/**
	 * @see org.log660.ActeurRealisateur#nom
	 **/
	public static volatile SingularAttribute<ActeurRealisateur, String> nom;

	public static final String BIOGRAPHIE = "biographie";
	public static final String DATE_NAISSANCE = "dateNaissance";
	public static final String PHOTO = "photo";
	public static final String LIEU_DE_NAISSANCE = "lieuDeNaissance";
	public static final String ID = "id";
	public static final String NOM = "nom";

}

