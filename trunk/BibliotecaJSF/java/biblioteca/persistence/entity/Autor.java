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
	@SequenceGenerator(name="AUTOR_IDAUTOR_GENERATOR", sequenceName="SQ_AUTOR_ID_AUTOR", allocationSize=0)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="AUTOR_IDAUTOR_GENERATOR")
	@Column(name="ID_AUTOR", unique=true, nullable=false)
	private int id;

	@Column(name="ANONASCIMENTO", nullable=false)
	private int anoNascimento;

	@Column(name="NOME", nullable=false, length=60)
	private String nome;

	//bi-directional many-to-one association to Livro
	@OneToMany(mappedBy="autor")
	private Set<Livro> livros;

    public Autor() {
    }

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAnoNascimento() {
		return this.anoNascimento;
	}

	public void setAnoNascimento(int ano) {
		this.anoNascimento = ano;
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