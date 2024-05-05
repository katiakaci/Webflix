package org.log660;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateUtils {
	private static HibernateUtils instance = new HibernateUtils();
	private static SessionFactory sessionFactory;	

	/**
	 * Constructeur privé (patron Singleton)
	 */
	private HibernateUtils(){
		final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
		try {
			sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
		}
		catch (Exception e) {
			System.out.println(e);
		}
	}

	public static HibernateUtils getInstance() {
		return instance;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

}