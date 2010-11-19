package biblioteca.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Set;


/**
 * The persistent class for the EDITORA database table.
 * 
 */
@Entity
public class Editora implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="EDITORA_IDEDITORA_GENERATOR", sequenceName="SQ_EDITORA_ID_EDITORA")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="EDITORA_IDEDITORA_GENERATOR")
	@Column(name="ID_EDITORA")
	private int idEditora;

	private String cidade;

	private String nome;

	private String pais;

	//bi-directional many-to-one association to Livro
	@OneToMany(mappedBy="editora")
	private Set<Livro> livros;

    public Editora() {
    }

	public int getIdEditora() {
		return this.idEditora;
	}

	public void setIdEditora(int idEditora) {
		this.idEditora = idEditora;
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
	
}