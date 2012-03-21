package biblioteca.util;

import java.sql.Connection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.hibernate.ejb.EntityManagerImpl;

public class JpaUtil {
	private static EntityManagerFactory emf;
	private static EntityManager em;

	public static EntityManager getEntityManager() {
		if (emf==null)
			emf = Persistence.createEntityManagerFactory("BibliotecaJPA");
		
		if (em==null || !em.isOpen())
			em = emf.createEntityManager();
		
		return em;
	}
	
	@SuppressWarnings("deprecation")
	public static Connection getConnection() {
		return ((EntityManagerImpl)getEntityManager()).getSession().connection();
	}	
}
