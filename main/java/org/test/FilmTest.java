package org.test;
import org.junit.BeforeClass;
import org.junit.Test;
import org.log660.Film;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class FilmTest {

    //Classe qui teste la connexion et le select d'un film pour un id donne
    private static SessionFactory sessionFactory;

    @BeforeClass
    public static void ouvrirConnexion() {
        Configuration conf = new Configuration();
        conf.configure("hibernate.cfg.xml");
        sessionFactory = conf.buildSessionFactory();
    }
    @Test
    public void SelectFilm() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Film film = session.get(Film.class, 120647);
        System.out.println(film.getTitre());
        session.close();
    }
}
