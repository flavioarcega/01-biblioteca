package biblioteca.persistence.dao;

import biblioteca.persistence.entity.Autor;

public class AutorDAO extends AbstractDAO<Autor> {
	private static final long serialVersionUID = 1L;

	public AutorDAO() {
		super(Autor.class);
	}
}
