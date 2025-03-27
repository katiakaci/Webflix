package org.log660;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.time.LocalDate;
import java.util.*;

public class FacadeSysteme {

	private static FacadeSysteme facadeInstance = new FacadeSysteme();

	/**
	 * Constructeur privé (patron Singleton)
	 */
	private FacadeSysteme() {}

	public static FacadeSysteme getInstance() {
		return facadeInstance;
	}

	public void rentMovie(Client client, Film film) {
		Session session =  HibernateUtils.getInstance().getSessionFactory().openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();

			LocationClient locationClient = new LocationClient();
			locationClient.setClientId(client.getId());
			locationClient.setFilmId(film.getId());
			locationClient.setDateEmprunt(LocalDate.now().toString());
			UUID uu = (UUID.randomUUID());
			long id = uu.getMostSignificantBits();
			locationClient.setId((int) id);

			// Set la date de retour selon forfait et date actuelle
			Query queryNbLocationMax = session.createQuery("SELECT dureemax FROM ForfaitLocation f WHERE f.code = :code");
			queryNbLocationMax.setParameter("code", client.getForfaitLocationCode());

			String nbMaxJoursLocation = (String) queryNbLocationMax.getSingleResult();

			if(nbMaxJoursLocation.contains("illimitée"))locationClient.setDateRetour("");
			else locationClient.setDateRetour(LocalDate.now().plusDays(Integer.parseInt(nbMaxJoursLocation.substring(0, 2))).toString());

			film.setQteDisponible(film.getQteDisponible()-1);

			session.save(locationClient);
			session.update(film);
			transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	public boolean isMaximumNumberOfRentalsReached(Client client) {
		Session session =  HibernateUtils.getInstance().getSessionFactory().openSession();

		Query queryNbLocationEnCours = session.createQuery("SELECT count(*) FROM LocationClient l WHERE l.client_id = :idClient");
		queryNbLocationEnCours.setParameter("idClient", client.getId());
		long nbLocationEnCours = (long) queryNbLocationEnCours.getSingleResult();

		Query queryNbLocationMax = session.createQuery("SELECT locationmax FROM ForfaitLocation f WHERE f.code = :code");
		queryNbLocationMax.setParameter("code", client.getForfaitLocationCode());
		int nbMaxLocation = (int) queryNbLocationMax.getSingleResult();

		session.close();

		if(nbLocationEnCours < nbMaxLocation) return false;
		else return true;
	}

	public Client connecterUtilisateur(String email, String motDePasse) throws Exception {
		Session session =  HibernateUtils.getInstance().getSessionFactory().openSession();
		Query query = session.createQuery("FROM Client C WHERE C.courriel = :courriel");
		query.setParameter("courriel", email);

		Client clientRechercher = (Client) query.getSingleResult();

		if(clientRechercher.getMotDePasse().equals(motDePasse)) {
			session.close();
			return clientRechercher;
		}
		session.close();
		return null;
	}

	public List<Film> recupererTousLesFilms() {
		Session session =  HibernateUtils.getInstance().getSessionFactory().openSession();
		Query query = session.createQuery("FROM Film f");
		List<Film> films = query.getResultList();
		session.close();
		return films;
	}

	public List<Film> filtrerFilm(String titre, String annee, String langue, String nomRealisateur, String paysProduction, String  genre, String acteur) {
		String condition = "";
		Session session =  HibernateUtils.getInstance().getSessionFactory().openSession();
		Query query;
		List<Film> films;
		String realisateurId = "";

		if(titre.length()>0) condition += " UPPER(f.titre) LIKE '%' || :titre || '%'";

		if(annee.length()>0) {
			// Si c'est une intervalle de temps (2000,2006 ou 2000-2006)
			if(annee.contains(",") || annee.contains("-")) {
				if(condition.length()>0) condition += " AND";
				condition += " f.annee BETWEEN :anneeDebut AND :anneeFin";
			}
			// Si c'est juste une année (2000)
			else {
				if(condition.length()>0) condition += " AND";
				condition += " f.annee = :annee";
			}
		}

		if(langue.length()>0) {
			if(condition.length()>0) condition += " AND";
			condition += queryForLangue(langue, session);
		}

		if(nomRealisateur.length()>0) {
			for(ActeurRealisateur r : this.recupererRealisateurs()) {
				if(r.getNom().toUpperCase().contains(nomRealisateur.toUpperCase().trim())) {
					realisateurId += r.getId();
				}
			}
			if(condition.length()>0) condition += " AND";
			condition += " f.idRealisateur = :realisateur";
		}

		if(paysProduction.length()>0) {			
			if(condition.length()>0) condition += " AND";
			condition += queryForPays(paysProduction, session);
		}

		if(genre.length()>0) {			
			if(condition.length()>0) condition += " AND";
			condition += queryForGenre(genre, session);
		}

		if(acteur.length()>0) {
			if(condition.length()>0) condition += " AND";
			condition += queryForActeur(acteur, session);
		}

		if(condition.length()>0) {
			condition = " WHERE "+ condition;
			query = session.createQuery("FROM Film f"+ condition);

			if(titre.length() > 0) query.setParameter("titre", titre.trim().toUpperCase());

			if(annee.length() > 0) {
				if(annee.contains(",") || annee.contains("-")) {
					String[] anneesDebutEtFin = annee.split("[,-]");
					query.setParameter("anneeDebut", Integer.parseInt(anneesDebutEtFin[0].trim()));
					query.setParameter("anneeFin", Integer.parseInt(anneesDebutEtFin[1].trim()));
				}
				else query.setParameter("annee", annee.trim());
			}

			if(langue.length() > 0 && !langue.contains("-") && !langue.contains(",")) query.setParameter("langue", langue.trim().toUpperCase());
			if(nomRealisateur.length() > 0) query.setParameter("realisateur", realisateurId);
		}
		else query = session.createQuery("FROM Film f");
		films = query.getResultList();
		session.close();
		return films;
	}

	private String queryForLangue(String langue, Session session) {
		if(langue.contains("-") || langue.contains(",")) {
			List<Integer> filmsAvecLangues = new ArrayList<>();
			Query queryLangue = session.createQuery("SELECT id FROM Film f WHERE UPPER(f.langue) = :langue");

			String[] langues = langue.split("[-,]");
			for(int i=0; i<langues.length; i++) {
				queryLangue.setParameter("langue", langues[i].trim().toUpperCase());
				filmsAvecLangues.addAll(queryLangue.getResultList());
			}
			String conditionIN = "";
			for(int i=0; i<filmsAvecLangues.size(); i++) {
				if(i!=filmsAvecLangues.size()-1) conditionIN += filmsAvecLangues.get(i).toString() +", ";
				else conditionIN += filmsAvecLangues.get(i).toString();
			}
			return " f.id IN ("+conditionIN+")";		
		}
		else {
			return " UPPER(f.langue) = :langue";
		}
	}

	private String queryForPays(String paysProduction, Session session) {
		List<Integer> filmsAvecPays = new ArrayList<>();
		Query queryPays = session.createQuery("SELECT film_id FROM PaysProduction p WHERE UPPER(p.nompays) = :pays");

		if(paysProduction.contains("-") || paysProduction.contains(",")) {
			String[] pays = paysProduction.split("[-,]");
			for(int i=0; i<pays.length; i++) {
				queryPays.setParameter("pays", pays[i].trim().toUpperCase());
				filmsAvecPays.addAll(queryPays.getResultList());
			}
		}
		else {
			queryPays.setParameter("pays", paysProduction.trim().toUpperCase());
			filmsAvecPays = queryPays.getResultList();
		}

		String conditionIN = "";
		for(int i=0; i<filmsAvecPays.size(); i++) {
			if(i!=filmsAvecPays.size()-1) conditionIN += filmsAvecPays.get(i).toString() +", ";
			else conditionIN += filmsAvecPays.get(i).toString();
		}

		return " f.id IN ("+conditionIN+")";		
	}

	private String queryForActeur(String acteur, Session session) {
		List<Integer> filmsAvecActeur = new ArrayList<>();
		Query queryActeur = session.createQuery("SELECT film_id FROM RoleFilm r INNER JOIN ActeurRealisateur a ON a.id=r.acteur_id WHERE UPPER(a.nom)  LIKE '%' || :acteur || '%'");

		if(acteur.contains("-") || acteur.contains(",")) {
			String[] acteurs = acteur.split("[-,]");
			for(int i=0; i<acteurs.length; i++) {
				queryActeur.setParameter("acteur", acteurs[i].trim().toUpperCase());
				filmsAvecActeur.addAll(queryActeur.getResultList());
			}
		}
		else {
			queryActeur.setParameter("acteur", acteur.trim().toUpperCase());
			filmsAvecActeur = queryActeur.getResultList();
		}

		String conditionIN = "";
		for(int i=0; i<filmsAvecActeur.size(); i++) {
			if(i!=filmsAvecActeur.size()-1) conditionIN += filmsAvecActeur.get(i).toString() +", ";
			else conditionIN += filmsAvecActeur.get(i).toString();
		}

		return " f.id IN ("+conditionIN+")";
	}

	private String queryForGenre(String genre, Session session) {
		List<Integer> filmsAvecGenre = new ArrayList<>();
		Query queryGenre = session.createQuery("SELECT film_id FROM GenreFilm g WHERE UPPER(g.genre) = :genre");

		if(genre.contains("-") || genre.contains(",")) {
			String[] genres = genre.split("[-,]");
			for(int i=0; i<genres.length; i++) {
				queryGenre.setParameter("genre", genres[i].trim().toUpperCase());
				filmsAvecGenre.addAll(queryGenre.getResultList());
			}
		}
		else {
			queryGenre.setParameter("genre", genre.trim().toUpperCase());
			filmsAvecGenre = queryGenre.getResultList();
		}

		String conditionIN = "";
		for(int i=0; i<filmsAvecGenre.size(); i++) {
			if(i!=filmsAvecGenre.size()-1) conditionIN += filmsAvecGenre.get(i).toString() +", ";
			else conditionIN += filmsAvecGenre.get(i).toString();
		}
		return " f.id IN ("+conditionIN+")";
	}

	private List<ActeurRealisateur> recupererRealisateurs() {
		SessionFactory sessionFactory = HibernateUtils.getInstance().getSessionFactory();
		Session session =  sessionFactory.openSession();

		Query query = session.createQuery("FROM ActeurRealisateur ar WHERE ar.id NOT IN (SELECT acteur_id FROM RoleFilm)");
		List<ActeurRealisateur> realisateurs = query.getResultList();

		session.close();
		return realisateurs;
	}

	public ActeurRealisateur getActeurRealisateur(int idPersonne) {
		SessionFactory sessionFactory = HibernateUtils.getInstance().getSessionFactory();
		Session session =  sessionFactory.openSession();

		Query query = session.createQuery("FROM ActeurRealisateur ar WHERE ar.id = :idPersonne");
		query.setParameter("idPersonne", idPersonne);
		ActeurRealisateur acteurRealisateur = (ActeurRealisateur) query.getSingleResult();

		session.close();
		return acteurRealisateur;
	}

	public ActeurRealisateur getRealisateur(int idRealisateur) {
		SessionFactory sessionFactory = HibernateUtils.getInstance().getSessionFactory();
		Session session =  sessionFactory.openSession();
		Query query = session.createQuery("FROM ActeurRealisateur ar WHERE ar.id = :idRealisateur AND ar.id NOT IN (SELECT acteur_id FROM RoleFilm)");
		query.setParameter("idRealisateur", idRealisateur);
		ActeurRealisateur realisateur = (ActeurRealisateur) query.getSingleResult();
		session.close();
		return  realisateur;
	}

	public String getGenresFilm(int idFilm) {
		SessionFactory sessionFactory = HibernateUtils.getInstance().getSessionFactory();
		Session session =  sessionFactory.openSession();
		Query query = session.createQuery("SELECT genre FROM GenreFilm g WHERE g.film_id = :idFilm");
		query.setParameter("idFilm", idFilm);		
		List<String> genres = query.getResultList();
		session.close();

		String genresString = "";
		for(int i=0; i<genres.size(); i++) {
			// Ne pas mettre de virgule pour le dernier élément
			if(i!=genres.size()-1) genresString += genres.get(i)+", ";
			else genresString += genres.get(i);
		}
		return genresString;
	}

	public StringBuilder getPaysProduction(int idFilm) {
		SessionFactory sessionFactory = HibernateUtils.getInstance().getSessionFactory();
		Session session =  sessionFactory.openSession();
		Query query = session.createQuery("SELECT nompays FROM PaysProduction p WHERE p.film_id = :idFilm");
		query.setParameter("idFilm", idFilm);		
		List<String> pays = query.getResultList();
		session.close();

		StringBuilder paysString = new StringBuilder();
		for(int i=0; i<pays.size(); i++) {
			// Ne pas mettre de virgule pour le dernier élément
			if(i!=pays.size()-1) paysString.append(pays.get(i)+", ");
			else paysString.append(pays.get(i));
		}
		return paysString;
	}

	public StringBuilder getScenaristes(int idFilm) {
		SessionFactory sessionFactory = HibernateUtils.getInstance().getSessionFactory();
		Session session =  sessionFactory.openSession();
		Query query = session.createQuery("SELECT nom FROM Scenariste s WHERE s.film_id = :idFilm");
		query.setParameter("idFilm", idFilm);		
		List<String> scenaristes = query.getResultList();
		session.close();

		StringBuilder scenaristesString = new StringBuilder();
		for(int i=0; i<scenaristes.size(); i++) {
			if(i!=scenaristes.size()-1) scenaristesString.append(scenaristes.get(i)+", ");
			else scenaristesString.append(scenaristes.get(i));
		}
		return scenaristesString;
	}

	public String getBandesAnnonces(int idFilm) {
		SessionFactory sessionFactory = HibernateUtils.getInstance().getSessionFactory();
		Session session =  sessionFactory.openSession();
		Query query = session.createQuery("SELECT annonce FROM BandeAnnoncesFilm b WHERE b.film_id = :idFilm");
		query.setParameter("idFilm", idFilm);		
		List<String> ba = query.getResultList();
		session.close();

		if(ba.isEmpty()) return "Aucune bande-annonce disponible";
		String baString = "";
		for(int i=0; i<ba.size(); i++) {
			baString += ba.get(i)+"<br>";
		}
		return baString;
	}	

	public List<ActeurRealisateur> getActeurs(int idFilm) {
		SessionFactory sessionFactory = HibernateUtils.getInstance().getSessionFactory();
		Session session =  sessionFactory.openSession();
		Query query = session.createQuery("FROM ActeurRealisateur a INNER JOIN RoleFilm r ON a.id=r.acteur_id WHERE r.film_id = :idFilm");
		query.setParameter("idFilm", idFilm);		
		List<ActeurRealisateur> acteurs = query.getResultList();
		session.close();
		return acteurs;
	}

	public String getRoleForActor(int idActeur, int idFilm) {
		SessionFactory sessionFactory = HibernateUtils.getInstance().getSessionFactory();
		Session session =  sessionFactory.openSession();
		Query query = session.createQuery("SELECT personnage FROM RoleFilm r WHERE r.acteur_id = :idActeur AND r.film_id = :idFilm");
		query.setParameter("idActeur", idActeur);	
		query.setParameter("idFilm", idFilm);
		String personnage = (String) query.getSingleResult();
		session.close();
		return personnage;
	}

	public String getCote(int idFilm) {
		SessionFactory sessionFactory = HibernateUtils.getInstance().getSessionFactory();
		Session session =  sessionFactory.openSession();
		System.out.println("idfilm: "+idFilm);
		Query query = session.createQuery("SELECT v.moyenne_cotes FROM VUE_MOYENNE_COTES v WHERE v.idFilm = :idFilm");
		query.setParameter("idFilm", idFilm);
		List<Double> cote = query.getResultList();
		session.close();
		if (cote.isEmpty()) return "Ce film n'a pas de côte.";
		else return  ""+(Math.round(cote.get(0) * 100.0) / 100.0)+ "/5";
	}

	public List<String> getAutresFilms(int idFilm, Client client) {
		SessionFactory sessionFactory = HibernateUtils.getInstance().getSessionFactory();
		Session session =  sessionFactory.openSession();
		String queryString = "SELECT CASE WHEN f1.id = :idFilm THEN f2.titre WHEN f2.id = :idFilm THEN f1.titre END AS titre FROM VUE_INDICE_CORRELATION v INNER JOIN Film f1 ON f1.id = v.film_1 INNER JOIN Film f2 ON f2.id = v.film_2 WHERE (f1.id = :idFilm AND (f2.id NOT IN (SELECT film_id FROM LocationClient WHERE client_id = :idClient))) OR (f2.id = :idFilm AND (f1.id NOT IN (SELECT film_id FROM LocationClient WHERE client_id = :idClient))) ORDER BY v.indice_correlation DESC FETCH FIRST 3 ROWS ONLY";
		Query query = session.createQuery(queryString);		
		query.setParameter("idFilm", idFilm);
		query.setParameter("idClient", client.getId());
		List<String> meilleursFilms = query.getResultList();
		session.close();
		return meilleursFilms;
	}

	public String getPosterFilmSimilaire(String titreFilm) {
		SessionFactory sessionFactory = HibernateUtils.getInstance().getSessionFactory();
		Session session =  sessionFactory.openSession();
		String queryString = "SELECT poster FROM Film WHERE titre LIKE :titreFilm";
		Query query = session.createQuery(queryString);		
		query.setParameter("titreFilm", titreFilm);
		List<String> poster = query.getResultList();
		session.close();
		if (poster.isEmpty()) return "https://png.pngtree.com/element_our/png/20181113/clapperboard-film-logo-icon-design-template-vector-isolated-png_236642.jpg";
		else return  ""+poster.get(0);
	}

	public long getNbLocationTotal(){
		long total = 0;
		try {
			SessionFactory sessionFactory = HibernateUtils.getInstance().getSessionFactory();
			Session session =  sessionFactory.openSession();
			Query query = session.createQuery("SELECT COUNT(*) AS total FROM LocationClient");
			total = (long) query.getSingleResult();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return total;
	}

	public long getNbLocationTotalFiltrer(String age, String province, String mois, String jour ){
		long totalFiltrer =0;
		try{
			SessionFactory sessionFactory = HibernateUtils.getInstance().getSessionFactory();
			Session session =  sessionFactory.openSession();
			String condition ="Where ";
			int test = 84;
			if(0 < age.length() && !age.toUpperCase().equals("TOUS")){
				String [] ages = age.split("-");
				condition =  condition +"(EXTRACT(YEAR FROM  SYSDATE) - EXTRACT(YEAR FROM  to_date(c.dateNaissance))) BETWEEN "+ ages[0]+ " AND "+ages[1];
			}
			if(0 < province.length() && !province.toUpperCase().equals("TOUS")){
				String [] provinces = province.split("-");
				String req = "c.province IN(";
				for (int i=0; i< provinces.length; i++){
					if(i == 0) req= req +"'" +provinces[i].toUpperCase()+"'";
					else req = req+ "," +"'"+ provinces[i].toUpperCase()+"'";
				}
				req = req+")";
				if(condition.trim().length() == 5) condition = condition+req;
				else condition = condition + " AND "+ req;
			}
			if(0 < mois.length() && !mois.toUpperCase().equals("TOUS")){
				String [] listeMois = mois.split("-");
				String req = "UPPER(TRIM(TO_CHAR(to_date(lc.date_emprunt),'Month'))) IN(";
				//condition
				for (int i=0; i< listeMois.length; i++){
					if(i == 0) req= req +"'" +listeMois[i].toUpperCase()+"'";
					else req = req+ "," +"'"+ listeMois[i].toUpperCase()+"'";
				}
				req = req+")";
				if(condition.trim().length() == 5) condition = condition+req;
				else condition = condition + " AND "+ req;
			}
			if(0 < jour.length() && !jour.toUpperCase().equals("TOUS")){
				String [] jours = jour.split("-");
				String req = "UPPER(TRIM(TO_CHAR(to_date(lc.date_emprunt),'Day'))) IN(";
				//condition
				for (int i=0; i< jours.length; i++){
					if(i == 0) req= req +"'" +jours[i].toUpperCase()+"'";
					else req = req+ "," +"'"+ jours[i].toUpperCase()+"'";
				}
				req = req+")";
				if(condition.trim().length() == 5) condition = condition+req;
				else condition = condition + " AND "+ req;
			}

			Query query;
			if(condition.trim().length() ==5 ){
				query = session.createQuery("SELECT COUNT(*) AS total FROM LocationClient");
			}
			else{
				System.out.println("SELECT COUNT(*) AS total FROM LocationClient lc INNER JOIN Client c ON c.id = lc.client_id "+condition);
				System.out.println(condition);
				query = session.createQuery("SELECT COUNT(*) AS total FROM LocationClient lc INNER JOIN Client c ON c.id = lc.client_id "+condition);
			}
			totalFiltrer = (long) query.getSingleResult();
		}catch(Exception e){
			e.printStackTrace();
		}
		return totalFiltrer;
	}

}