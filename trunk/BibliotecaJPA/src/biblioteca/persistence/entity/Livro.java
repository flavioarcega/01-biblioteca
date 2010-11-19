package biblioteca.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Set;


/**
 * The persistent class for the LIVRO database table.
 * 
 */
@Entity
public class Livro implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="LIVRO_IDLIVRO_GENERATOR", sequenceName="SQ_LIVRO_ID_LIVRO")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="LIVRO_IDLIVRO_GENERATOR")
	@Column(name="ID_LIVRO")
	private String id;

	private int anoedicao;

	private short edicao;

	private String isbn;

	private String titulo;

	//bi-directional many-to-one association to Emprestimo
	@OneToMany(mappedBy="livro")
	private Set<Emprestimo> emprestimos;

	//bi-directional many-to-one association to Autor
    @ManyToOne
	@JoinColumn(name="ID_AUTOR")
	private Autor autor;

	//bi-directional many-to-one association to Editora
    @ManyToOne
	@JoinColumn(name="ID_EDITORA")
	private Editora editora;

    public Livro() {
    }

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getAnoedicao() {
		return this.anoedicao;
	}

	public void setAnoedicao(int anoedicao) {
		this.anoedicao = anoedicao;
	}

	public short getEdicao() {
		return this.edicao;
	}

	public void setEdicao(short edicao) {
		this.edicao = edicao;
	}

	public String getIsbn() {
		return this.isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getTitulo() {
		return this.titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public Set<Emprestimo> getEmprestimos() {
		return this.emprestimos;
	}

	public void setEmprestimos(Set<Emprestimo> emprestimos) {
		this.emprestimos = emprestimos;
	}
	
	public Autor getAutor() {
		return this.autor;
	}

	public void setAutor(Autor autor) {
		this.autor = autor;
	}
	
	public Editora getEditora() {
		return this.editora;
	}

	public void setEditora(Editora editora) {
		this.editora = editora;
	}
	
}