package biblioteca.bc;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import biblioteca.persistence.dao.UsuarioDAO;
import biblioteca.persistence.entity.Emprestimo;
import biblioteca.persistence.entity.Usuario;

public class UsuarioBC implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private UsuarioDAO usuarioDAO;
	
	public UsuarioBC() {
		usuarioDAO = new UsuarioDAO();
	}

	public Usuario pesquisarPorLogin(String login) {
		if (login.isEmpty()) {
			FacesContext.getCurrentInstance().addMessage("error", new FacesMessage("Informe o usuário para a pesquisa!"));
			return null;
		}
		Usuario u = usuarioDAO.findByUserName(login);
		if (u==null)
			FacesContext.getCurrentInstance().addMessage("error", new FacesMessage("Usuário não encontrado!"));
		return u; 
	}

	public Boolean salvar(Usuario usuario) {
		try {
			usuario = usuarioDAO.insertOrUpdate(usuario);
			return true;
		} catch (Throwable e) {
			e.printStackTrace();
			return null;
		}
	}

	public Boolean excluir(Usuario usuario) {
		List<Emprestimo> listaEmprestimo=usuarioDAO.findEmprestimoByUsuario(usuario);
		if(listaEmprestimo.size()==0)
		{	
			return usuarioDAO.delete(usuario);
		}
		else
			return false; 
	}
	
	public List<Usuario> listarUsuarios() {
		return usuarioDAO.listAll();
	}
}
