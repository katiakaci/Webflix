package org.test;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class TestConnexion {

    //Classe qui teste la connexion. L'ouvre et la ferme
    private static SessionFactory sessionFactory;

    @BeforeClass
    public static void ouvrirConnexion() {
        Configuration conf = new Configuration();
        conf.configure("hibernate.cfg.xml");
        sessionFactory = conf.buildSessionFactory();
        System.out.println(sessionFactory);
    }

    @AfterClass
    public static void fermerConnection(){
        sessionFactory.close();
    }

    @Test
    public void testDeConnexion() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        System.out.println(sessionFactory);
        session.close();
    }
}
