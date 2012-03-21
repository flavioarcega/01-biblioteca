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
	

	//retorna a quantidade de emprestimos de um usuario
	public int findQuantidadeEmprestimoByUsuario(String login) {
		//Query byQuantidadeQuery = this.getEntityManager().createQuery("select count(object(c)) from " + this.getEntityClassName() + " as c where c.aluno.login = ?1 and c.dataDevolucaoEfetiva is null" );
		Query byQuantidadeQuery = this.getEntityManager().createQuery("select count(*) from Emprestimo as c where c.aluno.login = ?1 and c.dataDevolucaoEfetiva is null" );
		byQuantidadeQuery.setParameter(1, login);
		Number countResult = (Number) byQuantidadeQuery.getSingleResult();
		return countResult.intValue();
	} 

	
	//retorna um emprestimo (lista com um unico item) do exemplar xx que n�o foi devolvido
	public List<Emprestimo> findEmprestimoByExemplar(Integer exemplar) {
		Query byExemplarQuery = this.getEntityManager().createQuery("select object(c) from " + this.getEntityClassName() + " as c where c.livro.id = ?1 and c.dataDevolucaoEfetiva is null" );
		byExemplarQuery.setParameter(1, exemplar);
		List<Emprestimo> lista=(List<Emprestimo>) byExemplarQuery.getResultList();
		return lista;
	}
	
	//retorna lista de emprestimos pendentes de um usuario
	public List<Emprestimo> findEmprestimoByUsuario(String login) {
		TypedQuery<Emprestimo> byExemplarQuery = this.getEntityManager().createQuery("select object(c) from " + this.getEntityClassName() + " as c where c.aluno.login = ?1 and c.dataDevolucaoEfetiva is null",Emprestimo.class);
		byExemplarQuery.setParameter(1, login);
		List<Emprestimo> lista=(List<Emprestimo>) byExemplarQuery.getResultList();
		return lista;  
	}

	//retorna lista de emprestimos pendentes de um livro por isbn
	public List<Emprestimo> findEmprestimoByISBN(String isbn) {

		if(isbn.equals("*"))
			isbn="";
		StringBuilder sb = new StringBuilder();
		sb.append("%");
		sb.append(isbn);
		sb.append("%");
		
		Query byExemplarQuery = this.getEntityManager().createQuery("select object(c) from " + this.getEntityClassName() + " as c where c.livro.isbn like ?1 and c.dataDevolucaoEfetiva is null" );
		
		byExemplarQuery.setParameter(1, sb.toString());
		List<Emprestimo> lista=(List<Emprestimo>) byExemplarQuery.getResultList();
		return lista;  
	}
	
	//retorna lista de emprestimos pendentes de um livro por titulo
	public List<Emprestimo> findEmprestimoByTitulo(String login,String titulo) {
		StringBuilder sb = new StringBuilder();
		sb.append("%");
		sb.append(titulo);
		sb.append("%");

		Query byExemplarQuery = this.getEntityManager().createQuery("select object(c) from " + this.getEntityClassName() + " as c where c.livro.titulo like ?1 and c.dataDevolucaoEfetiva is null" );
		byExemplarQuery.setParameter(1, sb.toString());
		List<Emprestimo> lista=(List<Emprestimo>) byExemplarQuery.getResultList();
		return lista;  
	}

	public void deleteAll()
	{
		Query byExemplarQuery = this.getEntityManager().createQuery("delete from Emprestimo e");
		int deleted = byExemplarQuery.executeUpdate ();
	}

	
}
