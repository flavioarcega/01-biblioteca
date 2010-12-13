package biblioteca.bc;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import biblioteca.persistence.dao.EmprestimoDAO;
import biblioteca.persistence.entity.Emprestimo;
import biblioteca.persistence.entity.Perfil;
import biblioteca.persistence.entity.Usuario;

enum MENSAGEM {
	 EMPRESTIMOSUCESSO("Empr�stimo executado com sucesso"),
	 DEVOLUCAOSUCESSO("Devolu��o executada com sucesso"),
	 RENOVACAOSUCESSO("Renova��o executada com sucesso"),
	 EMPRESTIMOEXISTENTE("Empr�stimo para o exemplar est� pendente de devolu��o"),
	 EMPRESTIMONAODISPONIVEL("N�o existe empr�stimo para os crit�rios selecionados"),
	 QUANTIDADEEMPRESTIMOLIMITE("Quantidade limite de empr�stimos para usu�rio atingida"),
	 OPCAOINVALIDA("Op��es v�lidas: Devolver ou Renovar"),
	 EXEMPLARNAODISPONIVEL("Exemplar est� emprestado"),
	 EXEMPLARDISPONIVEL("Exemplar est� dispon�vel para empr�stimo");
	 

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
	
	public List<Emprestimo> salvarEmprestimos(String login,List<Emprestimo> emprestimos)
	{
		
		List<Emprestimo> listaRetorno = new ArrayList<Emprestimo>();
		for (Emprestimo emprestimo : emprestimos)
		{
			if(emprestimo.getDevolver() && emprestimo.getRenovar())
			{
				mensagemEmprestimo=MENSAGEM.OPCAOINVALIDA.getMensagem();
				continue;
			}
			
			else if (emprestimo.getDevolver())//devolu��o
			{ 
				emprestimo.setDataDevolucaoEfetiva(GregorianCalendar.getInstance().getTime());
				
				emprestimo = emprestimoDAO.insertOrUpdate(emprestimo);
				mensagemEmprestimo=MENSAGEM.DEVOLUCAOSUCESSO.getMensagem();

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

				emprestimo = emprestimoDAO.insertOrUpdate(emprestimo);
				mensagemEmprestimo=MENSAGEM.RENOVACAOSUCESSO.getMensagem();

			}
			else //emprestimo novo -- usa o login da combo de usuario para buscar o perfil
			{
				
				Usuario u=emprestimo.getAluno();
				int qtEmprestimo=emprestimoDAO.findQuantidadeEmprestimoByUsuario(login);
				if (u.isProfessor())
				{
					if (qtEmprestimo>4)
					{
						mensagemEmprestimo=MENSAGEM.QUANTIDADEEMPRESTIMOLIMITE.getMensagem();
						continue;
					}
				}	
				else 
				{
					if(qtEmprestimo>2)
					{
						mensagemEmprestimo=MENSAGEM.QUANTIDADEEMPRESTIMOLIMITE.getMensagem();
						continue;
					}
				}	
					
				
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

				emprestimo = emprestimoDAO.insertOrUpdate(emprestimo);
				mensagemEmprestimo=MENSAGEM.EMPRESTIMOSUCESSO.getMensagem();

			}

			listaRetorno.add(emprestimo);
		}
		return listaRetorno;
	}

	//pesquisa emprestimos existentes
	public List<Emprestimo> pesquisarEmprestimo(String login, Integer exemplar, String isbn, String titulo) {
		
		List<Emprestimo> listaRetorno;
		mensagemEmprestimo=null;
		if(!exemplar.equals(0)) //exemplar fornecido
		{
			listaRetorno=emprestimoDAO.findEmprestimoByExemplar(exemplar);
			if(listaRetorno.size()==0)
				mensagemEmprestimo=MENSAGEM.EMPRESTIMONAODISPONIVEL.getMensagem();
			return listaRetorno; 
		}		 
		else if(isbn != "") //isbn fornecido
		{
			isbn=isbn.toUpperCase();
			if(isbn.equals("DELETEALL")) //easter-egg do mal!!! 
			{
				emprestimoDAO.deleteAll();
				return new ArrayList<Emprestimo>();
			}
	
			listaRetorno=emprestimoDAO.findEmprestimoByISBN(isbn);
			if(listaRetorno.size()==0)
				mensagemEmprestimo=MENSAGEM.EMPRESTIMONAODISPONIVEL.getMensagem();
			return listaRetorno; 
		}
		else if(titulo != "") //titulo fornecido
		{
			titulo=titulo.toUpperCase();
			listaRetorno=emprestimoDAO.findEmprestimoByTitulo(login,titulo);
			if(listaRetorno.size()==0)
				mensagemEmprestimo=MENSAGEM.EMPRESTIMONAODISPONIVEL.getMensagem();
			return listaRetorno; 
			
		}
		else //se todos os campos estiverem vazios entao faz pesquisa somente pelo usuario selecionado na combo
		{
			listaRetorno=emprestimoDAO.findEmprestimoByUsuario(login);
			if(listaRetorno.size()==0)
				mensagemEmprestimo=MENSAGEM.EMPRESTIMONAODISPONIVEL.getMensagem();
			return listaRetorno; 
			
		}

	}


	public List<Emprestimo> consultarExemplar(Integer exemplar)
	{
		List<Emprestimo> listaRetorno;
		mensagemEmprestimo=null;
		if(!exemplar.equals(0)) //exemplar fornecido
		{
			listaRetorno=emprestimoDAO.findEmprestimoByExemplar(exemplar);
			if(listaRetorno.size()>0)
				mensagemEmprestimo=MENSAGEM.EXEMPLARNAODISPONIVEL.getMensagem();
			else
				mensagemEmprestimo=MENSAGEM.EXEMPLARDISPONIVEL.getMensagem();
			return listaRetorno; 
		}
		else
			return new ArrayList<Emprestimo>();

	}
	
	public String getMensagemEmprestimo()
	{
		return mensagemEmprestimo;
	}


	public void setMensagemEmprestimo(MENSAGEM m)
	{
		this.mensagemEmprestimo = m.getMensagem();
	}
	
	
}
