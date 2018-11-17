package krt.grid.data.api;

import java.util.List;

import krt.grid.data.objects.*;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;


public class DatabaseAPI {
	@SuppressWarnings("unchecked")
	public static List<File> GetDocuments() {
		List<File> list;
		SessionFactory factory = HibernateSessionFactory.getInstance()
				.getSessionFactory();
		Session session = null;
		try {
			session = factory.openSession();
			Query q = session.createQuery("from File");
			list = q.list();
			session.close();
		} catch (HibernateException exp) {
			throw exp;
		} finally {
			if ((session != null) && (session.isOpen()))
				session.close();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public static List<File> GetDocumentsByStatus(int documentStatusID) {
		Session session = null;
		List<File> list;
		SessionFactory factory = HibernateSessionFactory.getInstance()
				.getSessionFactory();
		try {
			session = factory.openSession();
			Query q = session
					.createQuery("from File where DocumentStatusID = :documentStatusID");
			q.setParameter("documentStatusID", documentStatusID);
			list = q.list();
			session.close();
		} catch (HibernateException exp) {
			throw exp;
		} finally {
			if ((session != null) && (session.isOpen()))
				session.close();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public static Directory GetDirectory(long directoryID) {
		Session session = null;
		List<Directory> list;
		SessionFactory factory = HibernateSessionFactory.getInstance()
				.getSessionFactory();
		try {
			session = factory.openSession();
			Query q = session
					.createQuery("from Directory where DirectoryID = :directoryID");
			q.setParameter("directoryID", directoryID);
			list = q.list();
			session.close();
		} catch (HibernateException exp) {
			throw exp;
		} finally {
			if ((session != null) && (session.isOpen()))
				session.close();
		}
		if (list.size() == 0)
			return null;

		return (Directory) list.get(0);
	}

	@SuppressWarnings("unchecked")
	public static List<Directory> GetSubDirectories(long directoryID) {
		Session session = null;
		List<Directory> list;
		SessionFactory factory = HibernateSessionFactory.getInstance()
				.getSessionFactory();
		try {
			session = factory.openSession();
			Query q = session
					.createQuery("from Directory where DirectoryID_Parent = :directoryID");
			q.setParameter("directoryID", directoryID);
			list = q.list();
			session.close();
		} catch (HibernateException exp) {
			throw exp;
		} finally {
			if ((session != null) && (session.isOpen()))
				session.close();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public static File GetDocument(long documentID) {
		Session session = null;
		List<File> list;
		SessionFactory factory = HibernateSessionFactory.getInstance()
				.getSessionFactory();
		try {
			session = factory.openSession();
			Query q = session
					.createQuery("from File where DocumentID = :documentID");
			q.setParameter("documentID", documentID);
			list = q.list();
			session.close();
		} catch (HibernateException exp) {
			throw exp;
		} finally {
			if ((session != null) && (session.isOpen()))
				session.close();
		}

		if (list.size() == 0)
			return null;

		return (File) list.get(0);
	}

	@SuppressWarnings("unchecked")
	public static File GetFile(long fileID) {
		Session session = null;
		List<File> list;
		SessionFactory factory = HibernateSessionFactory.getInstance()
				.getSessionFactory();
		try {
			session = factory.openSession();
			Query q = session.createQuery("from File where FileID = :fileID");
			q.setParameter("fileID", fileID);
			list = q.list();
			session.close();
		} catch (HibernateException exp) {
			throw exp;
		} finally {
			if ((session != null) && (session.isOpen()))
				session.close();
		}
		if (list.size() == 0)
			return null;

		return (File) list.get(0);
	}

	@SuppressWarnings("unchecked")
	public static List<File> GetFiles() {
		Session session = null;
		List<File> list;
		SessionFactory factory = HibernateSessionFactory.getInstance()
				.getSessionFactory();
		try {
			session = factory.openSession();
			Query q = session.createQuery("from File");
			list = q.list();
			session.close();
		} catch (HibernateException exp) {
			throw exp;
		} finally {
			if ((session != null) && (session.isOpen()))
				session.close();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public static List<FileStatus> GetFileStatuses() {
		Session session = null;
		List<FileStatus> list;
		SessionFactory factory = HibernateSessionFactory.getInstance()
				.getSessionFactory();
		try {
			session = factory.openSession();
			Query q = session.createQuery("from FileStatus");
			list = q.list();
			session.close();
		} catch (HibernateException exp) {
			throw exp;
		} finally {
			if ((session != null) && (session.isOpen()))
				session.close();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public static List<FileType> GetFileTypes() {
		Session session = null;
		List<FileType> list;
		SessionFactory factory = HibernateSessionFactory.getInstance()
				.getSessionFactory();
		try {
			session = factory.openSession();
			Query q = session.createQuery("from FileType");
			list = q.list();
			session.close();
		} catch (HibernateException exp) {
			throw exp;
		} finally {
			if ((session != null) && (session.isOpen()))
				session.close();
		}
		return list;
	}

	public static long AddDirectory(Directory directory) {
		Session session = null;
		SessionFactory factory = HibernateSessionFactory.getInstance()
				.getSessionFactory();
		try {
			session = factory.openSession();
			Transaction trans = session.beginTransaction();
			session.save(directory);
			trans.commit();
			session.close();
		} catch (HibernateException exp) {
			throw exp;
		} finally {
			if ((session != null) && (session.isOpen()))
				session.close();
		}
		return directory.getDirectoryID();
	}

	public static long AddFile(File file) {
		Session session = null;
		SessionFactory factory = HibernateSessionFactory.getInstance()
				.getSessionFactory();
		try {
			session = factory.openSession();
			Transaction trans = session.beginTransaction();
			session.save(file);
			trans.commit();
			session.close();
		} catch (HibernateException exp) {
			throw exp;
		} finally {
			if ((session != null) && (session.isOpen()))
				session.close();
		}
		return file.getFileID();
	}
}
