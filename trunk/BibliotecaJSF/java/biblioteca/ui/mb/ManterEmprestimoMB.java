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
	//private LivroDAO livroDAO;
	private List<Emprestimo> emprestimos;
	//--- campos de pesquisa ---
	private List<SelectItem> usuarios;
	private String  login;
	private Integer exemplar;
	private String  isbn;
	private String  titulo;
	//-- campos de emprestimo
	private Boolean itemSelecionado;
	private Integer livroId;
	private Boolean consultandoEmprestimo;
	//private Boolean mostrandoEmprestimo;
	private Boolean editandoEmprestimo;
	private String mensagemEmprestimo;
	
	public ManterEmprestimoMB() {
		usuarioBC = new UsuarioBC();
		usuarios = new ArrayList<SelectItem>();
		for (Usuario usuario : usuarioBC.listarUsuarios()) 
			for (Perfil perfil : usuario.getPerfis())
				if (perfil.getId()!=1)
					usuarios.add(new SelectItem(usuario.getLogin(), usuario.getNome()));
		
		//livroDAO = new LivroDAO();
		livroBC = new LivroBC();
		emprestimoBC = new EmprestimoBC();

		emprestimos = new ArrayList<Emprestimo>();
		Emprestimo emprestimo = new Emprestimo();
		emprestimos.add(emprestimo);
		
		mensagemEmprestimo = "";

		editandoEmprestimo = false;
		consultandoEmprestimo = true;
	}
	
	public Object pesquisar() {
		emprestimos = emprestimoBC.pesquisarEmprestimo(login, exemplar, isbn, titulo);
		if(emprestimos.size()==0) //não existe emprestimo, criar esqueleto vazio
		{
			emprestimos = new ArrayList<Emprestimo>();
			Emprestimo emprestimo = new Emprestimo();
			emprestimos.add(emprestimo);
			livroId=null;
			editandoEmprestimo = false;
			consultandoEmprestimo = true;
		}
		else //existe emprestimos, alterar para editar
		{
			
			livroId=null;
			editandoEmprestimo = true;
			consultandoEmprestimo = false;
		}
		
		mensagemEmprestimo = emprestimoBC.getMensagemEmprestimo();
		
		return NavigationEnum.SELF;
	}
	
	public Object consultar() {
		Integer exemplarEmprestimo=livroId; 
		if (exemplarEmprestimo==null) //campo do exemplar vazio, retorna para consulta
		{
			mensagemEmprestimo="";
			editandoEmprestimo = false;
			consultandoEmprestimo = true;
		}
		else //campo exemplar preenchido
		{
		
			//busca se existe emprestimo
			emprestimos = emprestimoBC.pesquisarEmprestimo("", exemplarEmprestimo, "", "");
			if(emprestimos.size()==0)// não há empréstimo para o exemplar solicitado
			{	//esqueleto de emprestimo NOVO
				emprestimos = new ArrayList<Emprestimo>();
				Emprestimo emprestimo = new Emprestimo();
				Livro livro=livroBC.buscarLivroPorExemplar(exemplarEmprestimo);
				emprestimo.setLivro(livro);
				emprestimo.setAluno(usuarioBC.pesquisarPorLogin(this.getLogin()));
				emprestimo.setOperadorDevolucao(this.getUsuarioLogado());
				emprestimo.setOperadorLocacao(this.getUsuarioLogado());
				emprestimos.add(emprestimo);
				if(livro==null)// livro do exemplar não existe
				{
					mensagemEmprestimo = "Item informado não existe no cadastro"; //colocar mensagem do livroBC no futuro caso livro não exista
					editandoEmprestimo = false;
					consultandoEmprestimo = true;
				}
				else //livro existe
				{
					mensagemEmprestimo="";
					editandoEmprestimo = true;
					consultandoEmprestimo = false;
				}
			}
			else //emprestimo existente (devolver ou renovar)
			{	
				//var emprestimos contém os dados do emprestimo retornados da persistencia
				mensagemEmprestimo = emprestimoBC.getMensagemEmprestimo();
				editandoEmprestimo = true;
				consultandoEmprestimo = false;
			}
		}
		return NavigationEnum.SELF;
	}


	public Object salvar() {
		boolean devolverFlag=false;
		boolean renovarFlag=false;
		
		if (emprestimos.size()==1)
		{
			Emprestimo emprestimo=emprestimos.get(0);
			Integer exemplarEmprestimo=livroId;  
			if(exemplarEmprestimo==null)
			{
				mensagemEmprestimo="";
				editandoEmprestimo = false;
				consultandoEmprestimo = true;
				return NavigationEnum.SELF;
			}
			else
			{
				devolverFlag=emprestimo.getDevolver()==null?false:emprestimo.getDevolver();
				renovarFlag=emprestimo.getRenovar()==null?false:emprestimo.getRenovar();

				//se campos devolver e renovar vazios então NOVO emprestimo
				if(devolverFlag)
				{
					emprestimo.setDevolver(true);
					emprestimo.setRenovar(false); //por garantia....
				}
				else if(renovarFlag)
				{
					emprestimo.setRenovar(true); 
					emprestimo.setDevolver(false);//por garantia....

				}
				else// emprestimo NOVO
				{
					emprestimos = new ArrayList<Emprestimo>();
					//Emprestimo emprestimo = new Emprestimo();
					Livro livro=livroBC.buscarLivroPorExemplar(exemplarEmprestimo);
					emprestimo.setRenovar(false); 
					emprestimo.setDevolver(false);
					emprestimo.setLivro(livro);
					emprestimo.setAluno(usuarioBC.pesquisarPorLogin(this.getLogin()));
					emprestimo.setOperadorDevolucao(this.getUsuarioLogado());
					emprestimo.setOperadorLocacao(this.getUsuarioLogado());
					emprestimos.add(emprestimo);

				}
				//se não for devolver ou renova então é NOVO emprestimo

				emprestimoBC.salvarEmprestimos(login,emprestimos);
				mensagemEmprestimo=emprestimoBC.getMensagemEmprestimo();

				editandoEmprestimo = false;
				consultandoEmprestimo = true;
				return NavigationEnum.SELF;
			}
		}
		else //mais de um, porém somente um selecionado
		{
			for (Emprestimo emprestimo : emprestimos)
			{
					devolverFlag=emprestimo.getDevolver()==null?false:emprestimo.getDevolver();
					renovarFlag=emprestimo.getRenovar()==null?false:emprestimo.getRenovar();

					if(devolverFlag)
					{
						emprestimo.setDevolver(true);
						emprestimo.setRenovar(false); //por garantia....
					}
					else if(renovarFlag)
					{
						emprestimo.setRenovar(true); 
						emprestimo.setDevolver(false);//por garantia....

					}

					emprestimoBC.salvarEmprestimos(login,emprestimos);
					mensagemEmprestimo=emprestimoBC.getMensagemEmprestimo();

					editandoEmprestimo = false;
					consultandoEmprestimo = true;
					return NavigationEnum.SELF;

				}
			}
		return NavigationEnum.SELF;
	}
	
	public Object limpar()
	{
		emprestimos = new ArrayList<Emprestimo>();
		Emprestimo emprestimo = new Emprestimo();
		emprestimos.add(emprestimo);
		mensagemEmprestimo="";
		livroId=null;
		editandoEmprestimo = false;
		consultandoEmprestimo = true;

		return NavigationEnum.SELF;
		
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

	public Integer getLivroId()
	{
		return livroId;
	}

	public void setLivroId(Integer livroId)
	{
		this.livroId = livroId;
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
 
/*public Object editar() {
	registroSalvo = true;
	fasePesquisa = false;
	editar= true;
	
	return NavigationEnum.SELF;
}*/

	/*public void atualizarLivro(AjaxBehaviorEvent ev) {
	Integer id = Integer.valueOf(((HtmlInputText) ev.getComponent()).getValue().toString());
	Livro result = livroDAO.findById(id);
	for (Emprestimo emprestimo : emprestimos)
		if (emprestimo.getId()==null)
			emprestimo.setLivro(result);
}*/
	
	
}
