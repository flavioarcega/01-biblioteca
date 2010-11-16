package biblioteca.persistence.dao;

import biblioteca.persistence.entity.Perfil;

/**
 * Classe de acesso a tabela Perfil.
 */
public class PerfilDAO extends AbstractDAO<Perfil> {
	/**
	 * Construtor padrao
	 */
	public PerfilDAO() {
		super(Perfil.class);
	}

}
