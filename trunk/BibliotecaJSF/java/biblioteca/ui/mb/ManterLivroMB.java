package biblioteca.ui.mb;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import biblioteca.bc.LivroBC;
import biblioteca.persistence.entity.Livro;

@ManagedBean
@ViewScoped
public class ManterLivroMB implements Serializable {
	private static final long serialVersionUID = 1L;
	private LivroBC livroBC;
	private List<Livro> livros;
	private Boolean registroSalvo;

	public ManterLivroMB() {
		livroBC = new LivroBC();
		livros = livroBC.listarLivros();
		registroSalvo = true;
	}
	
	public Object incluir() {
		livros.add(new Livro());
		registroSalvo = false;
		
		return NavigationEnum.SELF;
	}

	public Object editar() {
		registroSalvo = false;
		
		return NavigationEnum.SELF;
	}

	public Object salvar() {
		this.setLivroes(livroBC.salvarLivros(this.getLivroes()));
		registroSalvo = true;
		
		return NavigationEnum.SELF;
	}

	public List<Livro> getLivroes() {
		return livros;
	}

	public void setLivroes(List<Livro> livros) {
		this.livros = livros;
	}

	public Boolean getRegistroSalvo() {
		return registroSalvo;
	}

	public void setRegistroSalvo(Boolean registroSalvo) {
		this.registroSalvo = registroSalvo;
	}
}
