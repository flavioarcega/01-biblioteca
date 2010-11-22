package biblioteca.bc;

import java.io.Serializable;

import biblioteca.persistence.dao.AutorDAO;

public class AutorBC implements Serializable {
	private static final long serialVersionUID = 1L;
	private AutorDAO autorDAO;
	
	public AutorBC() {
		autorDAO = new AutorDAO();
	}
}
