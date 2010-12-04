package biblioteca.ui.mb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.html.HtmlInputText;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;

import biblioteca.bc.EmprestimoBC;
import biblioteca.bc.UsuarioBC;
import biblioteca.persistence.dao.LivroDAO;
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
	private LivroDAO livroDAO;
	private List<Emprestimo> emprestimos;
	private List<SelectItem> usuarios;
	private String  login;
	private Integer exemplar;
	private String  isbn;
	private String  titulo;
	private Boolean fasePesquisa;
	private Boolean registroSalvo;
	private Boolean editar;

	public ManterEmprestimoMB() {
		usuarioBC = new UsuarioBC();
		usuarios = new ArrayList<SelectItem>();
		for (Usuario usuario : usuarioBC.listarUsuarios()) 
			for (Perfil perfil : usuario.getPerfis())
				if (perfil.getId()!=1)
					usuarios.add(new SelectItem(usuario.getLogin(), usuario.getNome()));
		
		emprestimoBC = new EmprestimoBC();
		emprestimos = new ArrayList<Emprestimo>();
		
		livroDAO = new LivroDAO();
		
		fasePesquisa = true; 
		registroSalvo = true;
	}
	
	public Object pesquisar() {
		emprestimos = emprestimoBC.pesquisarEmprestimo(login, exemplar, isbn, titulo);

		return null;
	}
	
	public Object incluir() {
		emprestimos = new ArrayList<Emprestimo>();
		Emprestimo emprestimo = new Emprestimo();
		emprestimo.setLivro(new Livro());
		emprestimo.setAluno(usuarioBC.pesquisarPorLogin(this.getLogin()));
		emprestimo.setOperadorDevolucao(this.getUsuarioLogado());
		emprestimo.setOperadorLocacao(this.getUsuarioLogado());
		emprestimos.add(emprestimo);
		registroSalvo = false;
		fasePesquisa = false;
		editar = false;
		
		return NavigationEnum.SELF;
	}

	public Object editar() {
		registroSalvo = true;
		fasePesquisa = false;
		editar= true;
		
		return NavigationEnum.SELF;
	}

	public Object salvar() {
		emprestimoBC.salvarEmprestimos(emprestimos);
		
		registroSalvo = true;
		fasePesquisa = true;
		editar=false;
		
		return NavigationEnum.SELF;
	}
	
	public void atualizarLivro(AjaxBehaviorEvent ev) {
		Integer id = Integer.valueOf(((HtmlInputText) ev.getComponent()).getValue().toString());
		Livro result = livroDAO.findById(id);
		for (Emprestimo emprestimo : emprestimos)
			if (emprestimo.getId()==null)
				emprestimo.setLivro(result);
	}
	
	private Usuario getUsuarioLogado() {
		return ((MenuMB) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("menuMB")).getUsuario();
	}

	public Boolean getRegistroSalvo() {
		return registroSalvo;
	}

	public void setRegistroSalvo(Boolean registroSalvo) {
		this.registroSalvo = registroSalvo;
	}

	public Boolean getFasePesquisa() {
		return fasePesquisa;
	}

	public void setFasePesquisa(Boolean fasePesquisa) {
		this.fasePesquisa = fasePesquisa;
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
	
	public void setEditar(Boolean editar) {
		this.editar = editar;
	}
	
	public Boolean getEditar() {
		return editar;
	}
}
