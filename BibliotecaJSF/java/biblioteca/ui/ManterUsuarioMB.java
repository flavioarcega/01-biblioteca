package biblioteca.ui;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import biblioteca.persistence.dao.PerfilDAO;
import biblioteca.persistence.dao.UsuarioDAO;
import biblioteca.persistence.entity.Perfil;
import biblioteca.persistence.entity.Usuario;

@ViewScoped
@ManagedBean(name="manterUsuarioMB")
public class ManterUsuarioMB implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final String NAVIGATION_RULE = null;
	
	private PerfilDAO  perfilDAO;
	private UsuarioDAO usuarioDAO;
	
	private String     loginPesquisa;
	private Usuario    usuario;
	
	private boolean    fasePesquisa;
	private boolean    registroSalvo;
	
	public ManterUsuarioMB () {
		perfilDAO 	= new PerfilDAO();
		usuarioDAO = new UsuarioDAO();
		
		this.setFasePesquisa(true);
	}
	
	public String pesquisar() {
		try {
			System.out.println("Login Pesquisa: "+this.getLoginPesquisa());
			Usuario usuario = usuarioDAO.findByUserName(this.getLoginPesquisa());
			if (usuario != null) {
				this.setUsuario(usuario);
				this.setRegistroSalvo(true);
				this.setFasePesquisa(false);
			} else {
				this.setFasePesquisa(true);
				this.setUsuario(null);
				FacesContext.getCurrentInstance().addMessage("error", new FacesMessage("Operador não encontrado!"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return NAVIGATION_RULE;
	}
	
	public String incluir() {
		this.setUsuario(new Usuario());
		this.setRegistroSalvo(false);
		this.setFasePesquisa(false);
		return NAVIGATION_RULE;
	}
	
	public String editar() {
		this.setRegistroSalvo(false);
		this.setFasePesquisa(false);
		return NAVIGATION_RULE;
	}
	
	public String salvar() {
		usuarioDAO.insertOrUpdate(this.getUsuario());
		this.setRegistroSalvo(true);
		return NAVIGATION_RULE;
	}
	
	public String excluir() {
		usuarioDAO.delete(this.getUsuario());
		this.setUsuario(null);
		this.setRegistroSalvo(true);
		this.setFasePesquisa(true);
		return NAVIGATION_RULE;
	}

	public String habilitarPesquisa() {
		this.setFasePesquisa(true);
		this.setUsuario(null);
		return NAVIGATION_RULE;
	}
	
	public List<SelectItem> getPerfilList() {
		List<Perfil> perfilList = perfilDAO.getList();
		List<SelectItem> lista = new ArrayList<SelectItem>();
		for (Perfil perfil : perfilList)
			lista.add(new SelectItem(perfil.getId(), perfil.getDescricao()));
		
		return lista;
	}
	
	public String getLoginPesquisa() {
		return loginPesquisa;
	}

	public void setLoginPesquisa(String loginPesquisa) {
		this.loginPesquisa = loginPesquisa;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public void perfilChanged(ValueChangeEvent event) {
		usuario.setPerfis(this.usuario.getPerfis());
    }
	
	public List<Integer> getPerfis() {
		List<Integer> retorno = new ArrayList<Integer>();
		if (this.getUsuario().getPerfis()!=null)
			for (Perfil perfil : this.getUsuario().getPerfis())
				retorno.add(perfil.getId());
		return retorno;
	}

	public void setPerfis(List<String> itens) {
		this.getUsuario().setPerfis(new ArrayList<Perfil>());
		for (String item : itens)
			this.getUsuario().getPerfis().add(perfilDAO.findById(Integer.parseInt(item)));
	}

	public boolean isFasePesquisa() {
		return fasePesquisa;
	}

	public void setFasePesquisa(boolean fasePesquisa) {
		this.fasePesquisa = fasePesquisa;
	}

	public boolean isRegistroSalvo() {
		return registroSalvo;
	}

	public void setRegistroSalvo(boolean registroSalvo) {
		this.registroSalvo = registroSalvo;
	}
}