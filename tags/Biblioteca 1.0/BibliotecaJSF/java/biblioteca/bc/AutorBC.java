package biblioteca.bc;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import biblioteca.persistence.dao.AutorDAO;
import biblioteca.persistence.entity.Autor;
import biblioteca.persistence.entity.Livro;

public class AutorBC implements Serializable {
	private static final long serialVersionUID = 1L;
	private AutorDAO autorDAO;
	
	public AutorBC() {
		autorDAO = new AutorDAO();
	}

	public List<Autor> listarAutores() {
		return autorDAO.listAll();
	}
	
	public List<Autor> salvarAutores(List<Autor> lista) {
		for (Autor autor : lista)
			if (autor.getNome().isEmpty() || autor.getExcluir())
			{
				//somente exclui autor de não houver livros associados
				List<Livro> listaLivro=autorDAO.findLivroByAutor(autor);
				if(listaLivro.size()==0)
					autor.setExcluir(autorDAO.delete(autor));
				else
					autor.setExcluir(false);
			}
		List<Autor> listaRetorno = new ArrayList<Autor>();
		for (Autor autor : lista) 
			if (!autor.getExcluir()) {
				autor = autorDAO.insertOrUpdate(autor);
				listaRetorno.add(autor);
			}
		return listaRetorno;
	}
}
