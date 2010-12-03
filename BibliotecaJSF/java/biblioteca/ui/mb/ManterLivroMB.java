package biblioteca.ui.mb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import biblioteca.bc.AutorBC;
import biblioteca.bc.EditoraBC;
import biblioteca.bc.LivroBC;
import biblioteca.persistence.entity.Autor;
import biblioteca.persistence.entity.Editora;
import biblioteca.persistence.entity.Livro;

@ManagedBean
@ViewScoped
public class ManterLivroMB implements Serializable {
	private static final long serialVersionUID = 1L;
	private LivroBC 			livroBC;
	private AutorBC 			autorBC;
	private EditoraBC 			editoraBC;
	private List<Livro>			livros;
	private List<SelectItem>	autores;
	private List<SelectItem>	editoras;
	private Boolean 			registroSalvo;

	public ManterLivroMB() {
		autorBC = new AutorBC();
		autores = new ArrayList<SelectItem>();
		for (Autor autor : autorBC.listarAutores())
			autores.add(new SelectItem(autor.getId(), autor.getNome()));
		
		editoraBC = new EditoraBC();
		editoras = new ArrayList<SelectItem>();
		for (Editora editora : editoraBC.listarEditoras())
			editoras.add(new SelectItem(editora.getId(), editora.getNome()));
		
		livroBC = new LivroBC();
		livros = livroBC.listarLivros();
		registroSalvo = true;
		
	}
	
	public Object incluir() {
		Livro livro = new Livro();
		livro.setAutor(new Autor());
		livro.setEditora(new Editora());
		livros.add(livro);
		registroSalvo = false;
		
		return NavigationEnum.SELF;
	}

	public Object editar() {
		registroSalvo = false;
		
		return NavigationEnum.SELF;
	}

	public Object salvar() {
		this.setLivros(livroBC.salvarLivros(this.getLivros()));
		registroSalvo = true;
		
		return NavigationEnum.SELF;
	}

	public List<Livro> getLivros() {
		return livros;
	}

	public void setLivros(List<Livro> livros) {
		this.livros = livros;
	}

	public Boolean getRegistroSalvo() {
		return registroSalvo;
	}

	public void setRegistroSalvo(Boolean registroSalvo) {
		this.registroSalvo = registroSalvo;
	}

	public List<SelectItem> getAutores() {
		return autores;
	}

	public List<SelectItem> getEditoras() {
		return editoras;
	}
}
