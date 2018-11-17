package krt.grid.persistence;

import krt.grid.exception.PersistenceException;
import krt.grid.objects.File;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;


public class FilePersistence {
	public FilePersistence() {}

	public File get(long id) throws PersistenceException {
		Session session = null;
		File file;

		try {
			SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
			session = sessionFactory.openSession();

		 	System.out.println("Retrieving Record");
            file = (File)session.get(File.class, id);
			System.out.println("Done");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new PersistenceException(e.getMessage());
		} finally {
			session.flush();
			session.close();
		}
		
		return file;
	}

	public void insert(File file) throws PersistenceException {
		Session session = null;

		try {
			SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
			session = sessionFactory.openSession();
		 	System.out.println("Creating Record");
			session.save(file);
			System.out.println("Done");
		} catch (Exception e) {
			throw new PersistenceException(e.getMessage());
		} finally {
			session.flush();
			session.close();
		}
	}
	
	public void update(File file) throws PersistenceException {
	   	SessionFactory sessions = new Configuration().configure().buildSessionFactory();
	   	Session session = sessions.openSession();
	  
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			
			System.out.println("Inserting Record");
			session.save(file);
			       
			tx.commit();
			tx = null;
		} catch ( HibernateException e ) {
			if ( tx != null ) tx.rollback();
			{
				e.printStackTrace();
				throw new PersistenceException(e.getMessage());
			}
		} finally {
			session.close();
		}
	}

	public void delete(File file) throws PersistenceException {
		Session session = null;

		try {
			SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
			session = sessionFactory.openSession();

		 	System.out.println("Deleting Record");
			session.delete(file);
			System.out.println("Done");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new PersistenceException(e.getMessage());
		} finally {
			session.flush();
			session.close();
		}
	}
}
