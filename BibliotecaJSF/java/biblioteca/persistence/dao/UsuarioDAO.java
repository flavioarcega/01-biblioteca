package biblioteca.persistence.dao;

import javax.persistence.Query;

import biblioteca.persistence.entity.Usuario;

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
		Query byIdQuery = this.getEntityManager().createQuery("select object(c) from " + this.getEntityClassName() + " as c where c.login = ?1" );
		byIdQuery.setParameter(1, userName);
		return (Usuario) byIdQuery.getSingleResult();
	}
}
