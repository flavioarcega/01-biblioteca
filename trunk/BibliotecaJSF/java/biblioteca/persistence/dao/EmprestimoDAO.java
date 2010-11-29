package biblioteca.persistence.dao;

import biblioteca.persistence.entity.Emprestimo;

public class EmprestimoDAO extends AbstractDAO<Emprestimo> {
	private static final long serialVersionUID = 1L;

	public EmprestimoDAO() {
		super(Emprestimo.class);
	}
}
