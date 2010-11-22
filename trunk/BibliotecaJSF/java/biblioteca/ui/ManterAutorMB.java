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

	public ManterAutorMB() {
		autorBC = new AutorBC();
	}

	public List<Autor> getAutores() {
		return autores;
	}

	public void setAutores(List<Autor> autores) {
		this.autores = autores;
	}
}
