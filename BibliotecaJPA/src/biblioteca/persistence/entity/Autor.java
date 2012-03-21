package biblioteca.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Set;


/**
 * The persistent class for the AUTOR database table.
 * 
 */
@Entity
public class Autor implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="AUTOR_IDAUTOR_GENERATOR", sequenceName="SQ_AUTOR_ID_AUTOR")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="AUTOR_IDAUTOR_GENERATOR")
	@Column(name="ID_AUTOR")
	private int idAutor;

	private int anonascimento;

	private String nome;

	//bi-directional many-to-one association to Livro
	@OneToMany(mappedBy="autor")
	private Set<Livro> livros;

    public Autor() {
    }

	public int getIdAutor() {
		return this.idAutor;
	}

	public void setIdAutor(int idAutor) {
		this.idAutor = idAutor;
	}

	public int getAnonascimento() {
		return this.anonascimento;
	}

	public void setAnonascimento(int anonascimento) {
		this.anonascimento = anonascimento;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Set<Livro> getLivros() {
		return this.livros;
	}

	public void setLivros(Set<Livro> livros) {
		this.livros = livros;
	}
	
}