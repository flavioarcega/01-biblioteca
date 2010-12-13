package biblioteca.persistence.dao;


import java.util.List;

import javax.persistence.Query;

import biblioteca.persistence.entity.Autor;
import biblioteca.persistence.entity.Editora;
import biblioteca.persistence.entity.Livro;

public class LivroDAO extends AbstractDAO<Livro> {
	private static final long serialVersionUID = 1L;

	public LivroDAO() {
		super(Livro.class);
	}
	
	public Livro findLivroByExemplar(Integer exemplar) {
		Query byExemplarQuery = this.getEntityManager().createQuery("select object(c) from " + this.getEntityClassName() + " as c where c.id = ?1" );
		byExemplarQuery.setParameter(1, exemplar);
		List<Livro> lista= byExemplarQuery.getResultList();
		if (lista.size()==0)
			return null;
		else
			return lista.get(0);
	}

	public List<Livro> findLivroByEmprestimo(Livro livro) {
		Query byEmprestimoLivroQuery = this.getEntityManager().createQuery("select object(c) from Emprestimo as c where c.livro.id=?1" );
		byEmprestimoLivroQuery.setParameter(1, livro.getId());
		List<Livro> lista= byEmprestimoLivroQuery.getResultList();
		return lista;
	}

}
