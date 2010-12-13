package biblioteca.persistence.dao;

import java.util.List;

import javax.persistence.Query;

import biblioteca.persistence.entity.Editora;

public class EditoraDAO extends AbstractDAO<Editora> {
	private static final long serialVersionUID = 1L;

	public EditoraDAO() {
		super(Editora.class);
	}
	
	
	public List<Editora> findLivroByEditora(Editora editora) {
		Query byEditoraQuery = this.getEntityManager().createQuery("select object(c) from Livro as c where c.editora.id=?1" );
		byEditoraQuery.setParameter(1, editora.getId());
		List<Editora> lista= byEditoraQuery.getResultList();
		return lista;
	}
	
}
