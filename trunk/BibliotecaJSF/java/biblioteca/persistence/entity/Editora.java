package biblioteca.persistence.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;


/**
 * The persistent class for the EDITORA database table.
 * 
 */
@Entity
public class Editora implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="EDITORA_IDEDITORA_GENERATOR", sequenceName="SQ_EDITORA_ID_EDITORA", allocationSize=0)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="EDITORA_IDEDITORA_GENERATOR")
	@Column(name="ID_EDITORA", unique=true, nullable=false)
	private int id;

	@Column
	private String nome;

	@Column
	private String cidade;

	@Column
	private String pais;
	
	@Transient
	private Boolean excluir;

	//bi-directional many-to-one association to Livro
	@OneToMany(mappedBy="editora")
	private Set<Livro> livros;

    public Editora() {
    }

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCidade() {
		return this.cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getPais() {
		return this.pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public Set<Livro> getLivros() {
		return this.livros;
	}

	public void setLivros(Set<Livro> livros) {
		this.livros = livros;
	}

	public Boolean getExcluir() {
		return excluir;
	}

	public void setExcluir(Boolean excluir) {
		this.excluir = excluir;
	}
}