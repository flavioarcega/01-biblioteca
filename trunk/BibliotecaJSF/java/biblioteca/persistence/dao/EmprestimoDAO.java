package biblioteca.persistence.dao;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import biblioteca.persistence.entity.Emprestimo;
import biblioteca.persistence.entity.Livro;

public class EmprestimoDAO extends AbstractDAO<Emprestimo> {
	private static final long serialVersionUID = 1L;

	public EmprestimoDAO() {
		super(Emprestimo.class);
	}
	
	//retorna um emprestimo (lista com um unico item) do exemplar xx que não foi devolvido
	public List<Emprestimo> findEmprestimoByExemplar(String login, Integer exemplar) {
		//Query byExemplarQuery = this.getEntityManager().createQuery("delete from Emprestimo e");
		Query byExemplarQuery = this.getEntityManager().createQuery("select object(c) from " + this.getEntityClassName() + " as c where c.aluno.login = ?1 and c.livro.id = ?2 and c.dataDevolucaoEfetiva is null" );
		byExemplarQuery.setParameter(1, login);
		byExemplarQuery.setParameter(2, exemplar);
		List<Emprestimo> lista=(List<Emprestimo>) byExemplarQuery.getResultList();
		return lista;
		//int deleted = byExemplarQuery.executeUpdate ();
	}
	
	//retorna lista de emprestimos pendentes de um usuario
	public List<Emprestimo> findEmprestimoByUsuario(String login) {
		TypedQuery<Emprestimo> byExemplarQuery = this.getEntityManager().createQuery("select object(c) from " + this.getEntityClassName() + " as c where c.aluno.login = ?1 and c.dataDevolucaoEfetiva is null",Emprestimo.class);
		byExemplarQuery.setParameter(1, login);
		List<Emprestimo> lista=(List<Emprestimo>) byExemplarQuery.getResultList();
		return lista;  
	}

	//retorna lista de emprestimos pendentes de um livro por isbn
	public List<Emprestimo> findEmprestimoByISBN(String login, String isbn) {
		StringBuilder sb = new StringBuilder();
		sb.append("%");
		sb.append(isbn);
		sb.append("%");
		
		Query byExemplarQuery = this.getEntityManager().createQuery("select object(c) from " + this.getEntityClassName() + " as c where c.aluno.login = ?1 and c.livro.isbn like ?2 and c.dataDevolucaoEfetiva is null" );
		
		byExemplarQuery.setParameter(1, login);
		byExemplarQuery.setParameter(2, sb.toString());
		List<Emprestimo> lista=(List<Emprestimo>) byExemplarQuery.getResultList();
		return lista;  
	}
	
	//retorna lista de emprestimos pendentes de um livro por titulo
	public List<Emprestimo> findEmprestimoByTitulo(String login,String titulo) {
		StringBuilder sb = new StringBuilder();
		sb.append("%");
		sb.append(titulo);
		sb.append("%");

		Query byExemplarQuery = this.getEntityManager().createQuery("select object(c) from " + this.getEntityClassName() + " as c where c.aluno.login = ?1 and c.livro.titulo like ?2 and c.dataDevolucaoEfetiva is null" );
		byExemplarQuery.setParameter(1, login);
		byExemplarQuery.setParameter(2, sb.toString());
		List<Emprestimo> lista=(List<Emprestimo>) byExemplarQuery.getResultList();
		return lista;  
	}

}
