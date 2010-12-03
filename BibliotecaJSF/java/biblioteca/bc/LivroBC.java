package biblioteca.bc;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import biblioteca.persistence.dao.LivroDAO;
import biblioteca.persistence.entity.Livro;

public class LivroBC implements Serializable {
	private static final long serialVersionUID = 1L;
	private LivroDAO livroDAO;
	
	public LivroBC() {
		livroDAO = new LivroDAO();
	}

	public List<Livro> listarLivros() {
		return livroDAO.listAll();
	}
	
	public List<Livro> salvarLivros(List<Livro> lista) {
		try {
			for (Livro livro : lista)
				if (livro.getIsbn().isEmpty() || livro.getExcluir()) {
					livroDAO.delete(livro);
					livro.setExcluir(true);
				}
			List<Livro> listaRetorno = new ArrayList<Livro>();
			for (Livro livro : lista) 
				if (!livro.getExcluir()) {
					livro = livroDAO.insertOrUpdate(livro);
					listaRetorno.add(livro);
				}
			return listaRetorno;
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}
}
