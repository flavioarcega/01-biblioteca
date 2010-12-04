package biblioteca.bc;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import biblioteca.persistence.dao.EmprestimoDAO;
import biblioteca.persistence.entity.Emprestimo;

public class EmprestimoBC implements Serializable {
	private static final long serialVersionUID = 1L;
	private EmprestimoDAO emprestimoDAO;
	
	public EmprestimoBC() {
		emprestimoDAO = new EmprestimoDAO();
	}

	public List<Emprestimo> listarEmprestimos() {
		return emprestimoDAO.listAll();
	}
	
	public List<Emprestimo> salvarEmprestimos(List<Emprestimo> emprestimos) {
		for (Emprestimo emprestimo : emprestimos)
			if (emprestimo.getLivro().getId()==null || emprestimo.getLivro().getId().equals(0))
				emprestimo.setExcluir(true);
			else
				emprestimo.setExcluir(false);
		List<Emprestimo> listaRetorno = new ArrayList<Emprestimo>();
		for (Emprestimo emprestimo : emprestimos) 
			if (!emprestimo.getExcluir()) {
				emprestimo.setDataEmprestimo(GregorianCalendar.getInstance().getTime());
				
				Calendar data = GregorianCalendar.getInstance();
				if (emprestimo.getAluno().isProfessor())
					data.add(GregorianCalendar.DAY_OF_MONTH, 15);
				else
					data.add(GregorianCalendar.DAY_OF_MONTH, 7);
				emprestimo.setDataDevolucaoProgramada(data.getTime());
				
				emprestimo = emprestimoDAO.insertOrUpdate(emprestimo);
				listaRetorno.add(emprestimo);
			}
		return listaRetorno;
	}

	public List<Emprestimo> pesquisarEmprestimo(String login, Integer exemplar, String isbn, String titulo) {
		return null;
	}
}
