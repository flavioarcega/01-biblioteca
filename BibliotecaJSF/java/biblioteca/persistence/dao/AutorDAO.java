package biblioteca.persistence.dao;

import java.util.List;

import javax.persistence.Query;

import biblioteca.persistence.entity.Autor;
import biblioteca.persistence.entity.Livro;

public class AutorDAO extends AbstractDAO<Autor> {
	private static final long serialVersionUID = 1L;

	public AutorDAO() {
		super(Autor.class);
	}


public List<Livro> findLivroByAutor(Autor autor) {
	Query byAutorQuery = this.getEntityManager().createQuery("select object(c) from Livro as c where c.autor.id=?1" );
	byAutorQuery.setParameter(1, autor.getId());
	List<Livro> lista= byAutorQuery.getResultList();
	return lista;
}

}