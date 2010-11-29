package biblioteca.bc;

import java.io.Serializable;
import java.util.List;

import biblioteca.persistence.dao.AutorDAO;
import biblioteca.persistence.entity.Autor;

public class AutorBC implements Serializable {
	private static final long serialVersionUID = 1L;
	private AutorDAO autorDAO;
	
	public AutorBC() {
		autorDAO = new AutorDAO();
	}

	public List<Autor> listarAutores() {
		return autorDAO.listAll();
	}
	
	public Boolean salvarAutores(List<Autor> lista) {
		try {
			boolean isExcluido;
			for (Autor autorDB : autorDAO.listAll()) {
				isExcluido = true;
				for (Autor autor : lista) 
					if (autorDB.getId() == autor.getId()) isExcluido = false;
				if (isExcluido) 
					if (!autorDAO.delete(autorDB))
						return false;
			}
			for (Autor autor : lista) 
				if (!autorDAO.insertOrUpdate(autor)) return false;
		} catch (Throwable e) {
			e.printStackTrace();
		}
			
		return true;
	}
}
