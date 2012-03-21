package biblioteca.persistence.dao;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import biblioteca.persistence.entity.Usuario;
import biblioteca.util.JpaUtil;

/**
 * Classe de acesso a tabela USUARIO.
 */
public class UsuarioDAO extends AbstractDAO<Usuario> {
	private static final long serialVersionUID = 1L;

	/**
	 * Construtor padrao.
	 */
	public UsuarioDAO() {
		super(Usuario.class);
	}
	
	public Usuario findByUserName(String userName) {
		EntityManager em = JpaUtil.getEntityManager();
		try {
			Query byIdQuery = em.createQuery("select object(c) from " + entityClassName + " as c where c.login = ?1" );
			byIdQuery.setParameter(1, userName);
			return (Usuario) byIdQuery.getSingleResult();
		} catch(Exception e) {
			return null;
		}
	}
}
