package krt.grid.data.api;

import java.util.UUID;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

final class HibernateSessionFactory {

	private static SessionFactory sessionFactory;
	private static String ClassId;

	public static String getClassId() {
		return ClassId;
	}

	public static HibernateSessionFactory getInstance() {
		return SingletonHolder.getInstance();
	}

	private HibernateSessionFactory() {
		ClassId = UUID.randomUUID().toString();
	}

	public SessionFactory getSessionFactory() {
		if (sessionFactory == null) {
			try {
				sessionFactory = new Configuration().configure("database.hibernate.cfg.xml")
						.buildSessionFactory();
			} catch (HibernateException exp) {
				System.out.printf("Error initialized Hibernate: %s",
						exp.getMessage());
				throw exp;
			}
		}
		return sessionFactory;
	}

	static private class SingletonHolder {
		static HibernateSessionFactory instance;

		static {
			instance = new HibernateSessionFactory();
		}

		static HibernateSessionFactory getInstance() {
			return instance;
		}
	}
}
