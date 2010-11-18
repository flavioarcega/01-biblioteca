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

@ManagedBean
@ViewScoped
public class ManterUsuarioMB implements Serializable {
	private static final long serialVersionUID = 1L;
	
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
	
	public Object pesquisar() {
		try {
			Usuario usuario = usuarioDAO.findByUserName(this.getLoginPesquisa());
			if (usuario != null) {
				this.setUsuario(usuario);
				this.setRegistroSalvo(true);
				this.setFasePesquisa(false);
			} else {
				this.setFasePesquisa(true);
				this.setUsuario(null);
				FacesContext.getCurrentInstance().addMessage("error", new FacesMessage("Usuário não encontrado!"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return NavigationEnum.SELF;
	}
	
	public Object incluir() {
		this.setUsuario(new Usuario());
		this.setRegistroSalvo(false);
		this.setFasePesquisa(false);
		return NavigationEnum.SELF;
	}
	
	public Object editar() {
		this.setRegistroSalvo(false);
		this.setFasePesquisa(false);
		return NavigationEnum.SELF;
	}
	
	public Object salvar() {
		usuarioDAO.insertOrUpdate(this.getUsuario());
		this.setRegistroSalvo(true);
		return NavigationEnum.SELF;
	}
	
	public Object excluir() {
		usuarioDAO.delete(this.getUsuario());
		this.setUsuario(null);
		this.setRegistroSalvo(true);
		this.setFasePesquisa(true);
		return NavigationEnum.SELF;
	}

	public Object habilitarPesquisa() {
		this.setFasePesquisa(true);
		this.setUsuario(null);
		return NavigationEnum.SELF;
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