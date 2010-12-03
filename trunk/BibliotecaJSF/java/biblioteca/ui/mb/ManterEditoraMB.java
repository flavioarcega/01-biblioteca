package biblioteca.ui.mb;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import biblioteca.bc.EditoraBC;
import biblioteca.persistence.entity.Editora;

@ManagedBean
@ViewScoped
public class ManterEditoraMB implements Serializable {
	private static final long serialVersionUID = 1L;
	private EditoraBC editoraBC;
	private List<Editora> editoras;
	private Boolean registroSalvo;

	public ManterEditoraMB() {
		editoraBC = new EditoraBC();
		editoras = editoraBC.listarEditoras();
		registroSalvo = true;
	}
	
	public Object incluir() {
		editoras.add(new Editora());
		registroSalvo = false;
		
		return NavigationEnum.SELF;
	}

	public Object editar() {
		registroSalvo = false;
		
		return NavigationEnum.SELF;
	}

	public Object salvar() {
		this.setEditoraes(editoraBC.salvarEditoras(this.getEditoraes()));
		registroSalvo = true;
		
		return NavigationEnum.SELF;
	}

	public List<Editora> getEditoraes() {
		return editoras;
	}

	public void setEditoraes(List<Editora> editoras) {
		this.editoras = editoras;
	}

	public Boolean getRegistroSalvo() {
		return registroSalvo;
	}

	public void setRegistroSalvo(Boolean registroSalvo) {
		this.registroSalvo = registroSalvo;
	}
}
