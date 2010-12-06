package biblioteca.bc;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import biblioteca.persistence.dao.EmprestimoDAO;
import biblioteca.persistence.entity.Emprestimo;

enum MENSAGEM {
	 EMPRESTIMOSUCESSO("Empr�stimo executado com sucesso"),
	 EMPRESTIMOEXISTENTE("Empr�stimo para o exemplar est� pendente de devolu��o"),
	 EMPRESTIMONAODISPONIVEL("N�o existe empr�stimo para os crit�rios selecionados");
	 

	 private String textoMensagem;

	 private MENSAGEM(String m) {
		 textoMensagem = m;
	 }

	 public String getMensagem() {
	   return textoMensagem;
	 }		
}

public class EmprestimoBC implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private EmprestimoDAO emprestimoDAO;
	private String mensagemEmprestimo;

	
	public EmprestimoBC() {
		emprestimoDAO = new EmprestimoDAO();
	}

	
	public List<Emprestimo> listarEmprestimos() {
		return emprestimoDAO.listAll();
	}
	
	public List<Emprestimo> salvarEmprestimos(String login, List<Emprestimo> emprestimos) {
		
		List<Emprestimo> listaRetorno = new ArrayList<Emprestimo>();
		for (Emprestimo emprestimo : emprestimos)
		{
			
			if (emprestimo.getDevolver())//devolu��o
			{ 
				emprestimo.setDataDevolucaoEfetiva(GregorianCalendar.getInstance().getTime());
			}
			else if (emprestimo.getRenovar()) //renova��o
			{
				emprestimo.setDataEmprestimo(GregorianCalendar.getInstance().getTime());
				
				Calendar data = GregorianCalendar.getInstance();
				if (emprestimo.getAluno().isProfessor())
					data.add(GregorianCalendar.DAY_OF_MONTH, 15);
				else
					data.add(GregorianCalendar.DAY_OF_MONTH, 7);
				emprestimo.setDataDevolucaoProgramada(data.getTime());
			}
			else //emprestimo novo
			{
				if(!emprestimoDAO.findEmprestimoByExemplar(login, emprestimo.getLivro().getId()).isEmpty())
				{
					mensagemEmprestimo=MENSAGEM.EMPRESTIMOEXISTENTE.getMensagem();
					return listaRetorno;
				}
				
				emprestimo.setDataEmprestimo(GregorianCalendar.getInstance().getTime());
				
				Calendar data = GregorianCalendar.getInstance();
				if (emprestimo.getAluno().isProfessor())
					data.add(GregorianCalendar.DAY_OF_MONTH, 15);
				else
					data.add(GregorianCalendar.DAY_OF_MONTH, 7);
				emprestimo.setDataDevolucaoProgramada(data.getTime());
				
			}

			emprestimo = emprestimoDAO.insertOrUpdate(emprestimo);

			mensagemEmprestimo=MENSAGEM.EMPRESTIMOSUCESSO.getMensagem();
			listaRetorno.add(emprestimo);
		}
		return listaRetorno;
	}

	public List<Emprestimo> pesquisarEmprestimo(String login, Integer exemplar, String isbn, String titulo) {
		List<Emprestimo> listaRetorno;
		mensagemEmprestimo="";
		if(!exemplar.equals(0))
		{
			listaRetorno=emprestimoDAO.findEmprestimoByExemplar(login,exemplar);
			if(listaRetorno.size()==0)
				mensagemEmprestimo=MENSAGEM.EMPRESTIMONAODISPONIVEL.getMensagem();
			return listaRetorno; 
		}		 
		else if(isbn != "")
		{
			listaRetorno=emprestimoDAO.findEmprestimoByISBN(login,isbn);
			if(listaRetorno.size()==0)
				mensagemEmprestimo=MENSAGEM.EMPRESTIMONAODISPONIVEL.getMensagem();
			return listaRetorno; 
		}
		else if(titulo != "")
		{
			listaRetorno=emprestimoDAO.findEmprestimoByTitulo(login,titulo);
			if(listaRetorno.size()==0)
				mensagemEmprestimo=MENSAGEM.EMPRESTIMONAODISPONIVEL.getMensagem();
			return listaRetorno; 
			
		}
		else //se todos os campos estiverem vazios ent�o faz pesquisa somente pelo usuario selecionado na combo
		{
			listaRetorno=emprestimoDAO.findEmprestimoByUsuario(login);
			if(listaRetorno.size()==0)
				mensagemEmprestimo=MENSAGEM.EMPRESTIMONAODISPONIVEL.getMensagem();
			return listaRetorno; 
			
		}

	}


	public String getMensagemEmprestimo()
	{
		return mensagemEmprestimo;
	}


	public void setMensagemEmprestimo(MENSAGEM m)
	{
		this.mensagemEmprestimo = m.getMensagem();
	}
	
	
	/*
	 	public List<Emprestimo> salvarEmprestimos(List<Emprestimo> emprestimos) {
		for (Emprestimo emprestimo : emprestimos)
			if (emprestimo.getLivro().getId()==null || emprestimo.getLivro().getId().equals(0))
				emprestimo.setExcluir(true);
			else
				emprestimo.setExcluir(false);
		
		List<Emprestimo> listaRetorno = new ArrayList<Emprestimo>();
		for (Emprestimo emprestimo : emprestimos) 
			if (!emprestimo.getExcluir()) {
				
				if(!emprestimoDAO.findEmprestimoByExemplar(emprestimo.getLivro().getId()).isEmpty())
				{
					mensagemEmprestimo=MENSAGEM.EMPRESTIMOEXISTENTE.getMensagem();
					return listaRetorno;
				}
				
				emprestimo.setDataEmprestimo(GregorianCalendar.getInstance().getTime());
				
				Calendar data = GregorianCalendar.getInstance();
				if (emprestimo.getAluno().isProfessor())
					data.add(GregorianCalendar.DAY_OF_MONTH, 15);
				else
					data.add(GregorianCalendar.DAY_OF_MONTH, 7);
				emprestimo.setDataDevolucaoProgramada(data.getTime());
				
				//emprestimo = emprestimoDAO.insertOrUpdate(emprestimo);
				
				mensagemEmprestimo=MENSAGEM.EMPRESTIMOSUCESSO.getMensagem();
				listaRetorno.add(emprestimo);
			}
		return listaRetorno;
	}
 
	 */
}
