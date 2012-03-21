package biblioteca.persistence.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * The persistent class for the USUARIO database table.
 */
@Entity
@Table(name="USUARIO", catalog="PUBLIC")
public class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="USUARIO_GEN")
	@SequenceGenerator(name="USUARIO_GEN", sequenceName="SQ_USUARIO_ID_USUARIO", allocationSize=0)
	@Column(name="ID_USUARIO", unique=true, nullable=false)
	private Integer id;
	
	@Column(name="LOGIN", unique=true, nullable=false, length=16)
	private String login;

	@Column(name="NOME", nullable=false, length=60)
	private String nome;

	@Column(name="SENHA", nullable=false, length=60)
	private String senha;

    @ManyToMany
	@JoinTable( name="USUARIO_PERFIL", catalog="PUBLIC", 
				joinColumns={@JoinColumn(name="ID_USUARIO")}, 
				inverseJoinColumns={@JoinColumn(name="ID_PERFIL", nullable=false)})
	private List<Perfil> perfis;

    public Usuario() {
    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLogin() {
		return this.login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSenha() {
		return this.senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public List<Perfil> getPerfis() {
		return this.perfis;
	}

	public void setPerfis(List<Perfil> perfis) {
		this.perfis = perfis;
	}
	
	public Boolean isProfessor() {
		for (Perfil perfil : this.perfis)
			if (perfil.getId()==2) return true;
		
		return false;
	}
}