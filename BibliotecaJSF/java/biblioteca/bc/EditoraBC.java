package biblioteca.bc;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import biblioteca.persistence.dao.EditoraDAO;
import biblioteca.persistence.entity.Editora;

public class EditoraBC implements Serializable {
	private static final long serialVersionUID = 1L;
	private EditoraDAO editoraDAO;
	
	public EditoraBC() {
		editoraDAO = new EditoraDAO();
	}

	public List<Editora> listarEditoras() {
		return editoraDAO.listAll();
	}
	
	public List<Editora> salvarEditoras(List<Editora> lista) {
		try {
			for (Editora editora : lista)
				if (editora.getNome().isEmpty() || editora.getExcluir()) {
					editoraDAO.delete(editora);
					editora.setExcluir(true);
				}
			List<Editora> listaRetorno = new ArrayList<Editora>();
			for (Editora editora : lista) 
				if (!editora.getExcluir()) {
					editora = editoraDAO.insertOrUpdate(editora);
					listaRetorno.add(editora);
				}
			return listaRetorno;
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}
}
