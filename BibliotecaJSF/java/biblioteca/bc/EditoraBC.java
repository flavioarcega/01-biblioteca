package biblioteca.bc;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import biblioteca.persistence.dao.EditoraDAO;
import biblioteca.persistence.entity.Editora;
import biblioteca.persistence.entity.Livro;

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
					//somente exclui autor de não houver livros associados
					List<Editora> listaEditora=editoraDAO.findLivroByEditora(editora);
					if(listaEditora.size()==0)
						editora.setExcluir(editoraDAO.delete(editora));
					else
						editora.setExcluir(false);
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
