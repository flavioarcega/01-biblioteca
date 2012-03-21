package biblioteca.bc;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import biblioteca.persistence.dao.LivroDAO;
import biblioteca.persistence.entity.Editora;
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
					List<Livro> listaLivros=livroDAO.findLivroByEmprestimo(livro);
					if(listaLivros.size()==0)
						livro.setExcluir(livroDAO.delete(livro));
					else
						livro.setExcluir(false);
				}

			List<Livro> listaRetorno = new ArrayList<Livro>();
			for (Livro livro : lista) 
				if (!livro.getExcluir()) {
					livro.setTitulo(livro.getTitulo().toUpperCase());
					livro.setIsbn(livro.getIsbn().toUpperCase());
					livro = livroDAO.insertOrUpdate(livro);
					listaRetorno.add(livro);
				}
			return listaRetorno;
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public Livro buscarLivroPorExemplar(Integer exemplar) {
		if(!exemplar.equals(0))
		{
			Livro livro=livroDAO.findLivroByExemplar(exemplar);
			return livro; 
		}
		else return null;
	}



}
