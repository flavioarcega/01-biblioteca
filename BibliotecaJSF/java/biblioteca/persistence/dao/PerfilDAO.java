package biblioteca.persistence.dao;

import biblioteca.persistence.entity.Perfil;

/**
 * Classe de acesso a tabela Perfil.
 */
public class PerfilDAO extends AbstractDAO<Perfil> {
	private static final long serialVersionUID = 1L;
	/**
	 * Construtor padrao
	 */
	public PerfilDAO() {
		super(Perfil.class);
	}

}
