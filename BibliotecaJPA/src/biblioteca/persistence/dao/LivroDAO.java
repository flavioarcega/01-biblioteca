package biblioteca.persistence.dao;

import biblioteca.persistence.entity.Livro;

public class LivroDAO extends AbstractDAO<Livro> {
	private static final long serialVersionUID = 1L;

	public LivroDAO() {
		super(Livro.class);
	}
}
