package biblioteca.ui.mb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import biblioteca.bc.EmprestimoBC;
import biblioteca.bc.LivroBC;
import biblioteca.bc.UsuarioBC;
import biblioteca.persistence.entity.Emprestimo;
import biblioteca.persistence.entity.Livro;
import biblioteca.persistence.entity.Perfil;
import biblioteca.persistence.entity.Usuario;

@ManagedBean
@ViewScoped
public class ManterEmprestimoMB implements Serializable {
	private static final long serialVersionUID = 1L;
	private EmprestimoBC emprestimoBC;
	private UsuarioBC usuarioBC;
	private LivroBC livroBC;
	private List<Emprestimo> emprestimos;
	//--- campos de pesquisa ---
	private List<SelectItem> usuarios;
	private String  login;
	private Integer exemplar;
	private String  isbn;
	private String  titulo;
	//-- campos de emprestimo
	private Boolean itemSelecionado;
	private Boolean consultandoEmprestimo;
	private Boolean editandoEmprestimo;
	private String mensagemEmprestimo;
	
	public ManterEmprestimoMB()
	{
		usuarioBC = new UsuarioBC();
		usuarios = new ArrayList<SelectItem>();
		for (Usuario usuario : usuarioBC.listarUsuarios()) 
			for (Perfil perfil : usuario.getPerfis())
				if (perfil.getId()!=1)
					usuarios.add(new SelectItem(usuario.getLogin(), usuario.getNome()));
		
		//livroDAO = new LivroDAO();
		livroBC = new LivroBC();
		emprestimoBC = new EmprestimoBC();

		emprestimos=criarEmprestimoVazio();
		
		mensagemEmprestimo = null;

		editandoEmprestimo = false;
		consultandoEmprestimo = true;
	}
	
	
	/*
	 * metodo  invocado somente com um emprestimo (esqueleto previamente colocado no Form)
	 * na lista de emprestimos para consultar exemplar 
	 */
	public Object consultarExemplar()
	{
		if(emprestimos.size()==0 || emprestimos.size()>1)//campo vazio ou lista de emprestimos na tela -> consulta somente um item
		{
			emprestimos=criarEmprestimoVazio();
			mensagemEmprestimo=null;
			editandoEmprestimo = false;
			consultandoEmprestimo = true;
			return NavigationEnum.SELF;
		}

		Integer exemplarEmprestimo=emprestimos.get(0).getLivro().getId();//id do livro preenchido no esqueleto 
		if (exemplarEmprestimo==null)
		{ 
			//campo do exemplar vazio, retorna para consulta
			mensagemEmprestimo="Exemplar não foi fornecido";
			editandoEmprestimo = false;
			consultandoEmprestimo = true;
		}
		else 
		{
			//campo exemplar preenchido
			emprestimos = emprestimoBC.consultarExemplar(exemplarEmprestimo);
			if(emprestimos.size()==0) // nao ha emprestimo para o exemplar solicitado
			{	//esqueleto de emprestimo NOVO
				Livro livro=livroBC.buscarLivroPorExemplar(exemplarEmprestimo);
				if(livro==null)// livro do exemplar nao existe
				{
					emprestimos=criarEmprestimoVazio();

					mensagemEmprestimo = "Item informado não existe no cadastro"; //colocar mensagem do livroBC no futuro caso livro nï¿½o exista
					editandoEmprestimo = false;
					consultandoEmprestimo = true;
				}
				else //livro existe
				{
					emprestimos = criarEmprestimoVazio();
					emprestimos.get(0).setLivro(livro);

					mensagemEmprestimo=null;
					editandoEmprestimo = true;
					consultandoEmprestimo = false;
				}
			}
			else //emprestimo existe para o exemplar
			{	
				//var emprestimos contï¿½m os dados do emprestimo retornados da camada de persistencia
				mensagemEmprestimo = emprestimoBC.getMensagemEmprestimo();
				editandoEmprestimo = true;
				consultandoEmprestimo = false;
			}
		}
		return NavigationEnum.SELF;
	}

	/*
	 * pesquisa emprestimos existentes para os critï¿½rios do Form
	 * retorna lista de emprestimos ou esqueleto vazio
	 */
	public Object pesquisarEmprestimo()
	{
		mensagemEmprestimo=null;
		boolean falhaValidacao=false;
		if(isbn.length()>13)
		{
			mensagemEmprestimo="ISBN maior que 13 caracteres";
			falhaValidacao=true;
		}
		if(titulo.length()>60)
		{
			mensagemEmprestimo="Titulo maior que 60 caracteres";
			falhaValidacao=true;
		}
		if(falhaValidacao)
		{
			emprestimos=criarEmprestimoVazio();
			editandoEmprestimo = false;
			consultandoEmprestimo = true;
			return NavigationEnum.SELF;
		}
		
		emprestimos = emprestimoBC.pesquisarEmprestimo(login, exemplar, isbn, titulo);
		if(emprestimos.size()==0) // nao ha emprestimo para o exemplar solicitado
		{	
			emprestimos=criarEmprestimoVazio();
			editandoEmprestimo = false;
			consultandoEmprestimo = true;
		}
		else 
		{	
			//var emprestimos contem os dados do emprestimo retornados da camada de persistencia
			//preparar para devolver ou renovar
			editandoEmprestimo = true;
			consultandoEmprestimo = false;
		}
		mensagemEmprestimo = emprestimoBC.getMensagemEmprestimo();
		return NavigationEnum.SELF;
	}

	
	
	/*
	 *  Metodo pode ser invocado com um emprestimo novo ou com uma lista de 
	 *  emprestimos retornados da consulta.
	 *  Um emprestimo novo nao tem data de retirada.
	 */
	public Object salvarEmprestimo()
	{
		boolean devolverFlag=false;
		boolean renovarFlag=false;
		

		for (Emprestimo emprestimo : emprestimos)
		{
			//emprestimo novo, Form com dados da consulta
			if(emprestimo.getDataEmprestimo()==null)
			{

				Livro livro=livroBC.buscarLivroPorExemplar(emprestimo.getLivro().getId()); //verificar novamente se livro existe
				if(livro==null)// livro nao existe
				{
					emprestimos=criarEmprestimoVazio();
					mensagemEmprestimo = "Item informado não existe no cadastro"; //colocar mensagem do livroBC no futuro caso livro nï¿½o exista
					editandoEmprestimo = false;
					consultandoEmprestimo = true;
					return NavigationEnum.SELF;
				}
				else //livro existe e emprestimo eh novo
				{
					emprestimo.setRenovar(false); 
					emprestimo.setDevolver(false);
					emprestimo.setLivro(livro);
					emprestimo.setAluno(usuarioBC.pesquisarPorLogin(this.getLogin()));
					emprestimo.setOperadorDevolucao(this.getUsuarioLogado());
					emprestimo.setOperadorLocacao(this.getUsuarioLogado());
				}
			}
			else //emprestimo existente, devolver ou renovar
			{
				devolverFlag=emprestimo.getDevolver()==null?false:emprestimo.getDevolver();
				renovarFlag=emprestimo.getRenovar()==null?false:emprestimo.getRenovar();
				if(devolverFlag && renovarFlag)
				{
					emprestimo.setDevolver(true);
					emprestimo.setRenovar(true); 
				}
				else if(devolverFlag)
				{
					emprestimo.setDevolver(true);
					emprestimo.setRenovar(false); //por garantia....
					emprestimo.setOperadorDevolucao(this.getUsuarioLogado());
					emprestimo.setOperadorLocacao(this.getUsuarioLogado());
				}
				else if(renovarFlag)
				{
					emprestimo.setRenovar(true); 
					emprestimo.setDevolver(false);//por garantia....
					emprestimo.setOperadorDevolucao(this.getUsuarioLogado());
					emprestimo.setOperadorLocacao(this.getUsuarioLogado());
				}
			}	
		}
		emprestimoBC.salvarEmprestimos(login,emprestimos);
		mensagemEmprestimo=emprestimoBC.getMensagemEmprestimo();

		editandoEmprestimo = false;
		consultandoEmprestimo = true;
		return NavigationEnum.SELF;

	}
	
	public Object limpar()
	{
		emprestimos=criarEmprestimoVazio();
		mensagemEmprestimo=null;
		editandoEmprestimo = false;
		consultandoEmprestimo = true;

		return NavigationEnum.SELF;
		
	}
	
	
	private List<Emprestimo> criarEmprestimoVazio()
	{
		exemplar=null;
		emprestimos=null;
		Livro livro=new Livro();
		emprestimos = new ArrayList<Emprestimo>();
		Emprestimo emprestimo = new Emprestimo();
		emprestimo.setLivro(livro);
		emprestimos.add(emprestimo);
		return emprestimos;
	}
	
	
	private Usuario getUsuarioLogado() {
		return ((MenuMB) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("menuMB")).getUsuario();
	}


	public List<Emprestimo> getEmprestimos() {
		return emprestimos;
	}

	public void setEmprestimos(List<Emprestimo> emprestimos) {
		this.emprestimos = emprestimos;
	}

	public Integer getExemplar() {
		return exemplar;
	}

	public void setExemplar(Integer identificador) {
		this.exemplar = identificador;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public List<SelectItem> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<SelectItem> usuarios) {
		this.usuarios = usuarios;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}
	

	public String getMensagemEmprestimo()
	{
		return mensagemEmprestimo;
	}

	public void setMensagemEmprestimo(String mensagemEmprestimo)
	{
		this.mensagemEmprestimo = mensagemEmprestimo;
	}


	public Boolean getConsultandoEmprestimo()
	{
		return consultandoEmprestimo;
	}

	public void setConsultandoEmprestimo(Boolean consultandoEmprestimo)
	{
		this.consultandoEmprestimo = consultandoEmprestimo;
	}

	public Boolean getEditandoEmprestimo()
	{
		return editandoEmprestimo;
	}

	public void setEditandoEmprestimo(Boolean editandoEmprestimo)
	{
		this.editandoEmprestimo = editandoEmprestimo;
	}

	public Boolean getItemSelecionado()
	{
		return itemSelecionado;
	}

	public void setItemSelecionado(Boolean itemSelecionado)
	{
		this.itemSelecionado = itemSelecionado;
	}
	
	
	
	/*
  	public Object consultar() {
	emprestimos = new ArrayList<Emprestimo>();
	Emprestimo emprestimo = new Emprestimo();
	emprestimo.setLivro(new Livro());
	emprestimo.setAluno(usuarioBC.pesquisarPorLogin(this.getLogin()));
	emprestimo.setOperadorDevolucao(this.getUsuarioLogado());
	emprestimo.setOperadorLocacao(this.getUsuarioLogado());
	emprestimos.add(emprestimo);

	mensagemEmprestimo = null;

	editandoEmprestimo = false;
	consultandoEmprestimo = true;

	return NavigationEnum.SELF;
}

 */
 

	/*public void atualizarLivro(AjaxBehaviorEvent ev) {
	Integer id = Integer.valueOf(((HtmlInputText) ev.getComponent()).getValue().toString());
	Livro result = livroDAO.findById(id);
	for (Emprestimo emprestimo : emprestimos)
		if (emprestimo.getId()==null)
			emprestimo.setLivro(result);
}*/
	
	
}
