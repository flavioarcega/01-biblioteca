package biblioteca.ui.mb;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import biblioteca.bc.UsuarioBC;
import biblioteca.persistence.entity.Usuario;

@ManagedBean
@SessionScoped
public class MenuMB {
	private String    login;
	private String    senha;
	private Usuario   usuario;
	private UsuarioBC usuarioBC;
	
	public MenuMB() {
		usuarioBC = new UsuarioBC();
	}
	
	public String getNavigationRule(String navigationEnum) {
		return NavigationEnum.valueOf(navigationEnum).getNavigationRule();
	}
	public Usuario getUsuario() {
		return this.usuario;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public Object efetuarLogin() {
		usuario = usuarioBC.pesquisarPorLogin(login);
		return null;
	}
}
