package biblioteca.ui;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import biblioteca.bc.AutorBC;
import biblioteca.persistence.entity.Autor;

@ManagedBean
@ViewScoped
public class ManterAutorMB implements Serializable {
	private static final long serialVersionUID = 1L;
	private AutorBC autorBC;
	private List<Autor> autores;
	private Boolean registroSalvo;

	public ManterAutorMB() {
		autorBC = new AutorBC();
		autores = autorBC.listarAutores();
		registroSalvo = true;
	}
	
	public Object incluir() {
		autores.add(new Autor());
		registroSalvo = false;
		
		return NavigationEnum.SELF;
	}

	public Object editar() {
		registroSalvo = false;
		
		return NavigationEnum.SELF;
	}

	public Object salvar() {
		this.setAutores(autorBC.salvarAutores(this.getAutores()));
		registroSalvo = true;
		
		return NavigationEnum.SELF;
	}

	public List<Autor> getAutores() {
		return autores;
	}

	public void setAutores(List<Autor> autores) {
		this.autores = autores;
	}

	public Boolean getRegistroSalvo() {
		return registroSalvo;
	}

	public void setRegistroSalvo(Boolean registroSalvo) {
		this.registroSalvo = registroSalvo;
	}
}
