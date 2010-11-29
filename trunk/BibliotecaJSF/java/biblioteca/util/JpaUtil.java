package biblioteca.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JpaUtil {
	private static EntityManagerFactory emf;

	public static EntityManagerFactory getEntityManagerFactory() {
		if (emf==null)
			emf = Persistence.createEntityManagerFactory("BibliotecaJPA");
		return emf;
	}
	
	public static EntityManager createEntityManager() {
		System.out.println("Create EntityManager");
		return getEntityManagerFactory().createEntityManager();
	}
	
//	@SuppressWarnings("deprecation")
//	public static Connection getConnection(EntityManager em) {
//		return ((EntityManagerImpl)em).getSession().connection();
//	}
}
