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
	@SequenceGenerator(name="LIVRO_IDLIVRO_GENERATOR", sequenceName="SQ_LIVRO_ID_LIVRO", allocationSize=0)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="LIVRO_IDLIVRO_GENERATOR")
	@Column(name="ID_LIVRO", unique=true, nullable=false)
	private String id;

	@Column
	private String isbn;

	@Column
	private String titulo;
	
	@Column
	private short edicao;

	@Column
	private int anoEdicao;

	@Transient
	private Boolean excluir;

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

	public int getAnoEdicao() {
		return this.anoEdicao;
	}

	public void setAnoEdicao(int anoEdicao) {
		this.anoEdicao = anoEdicao;
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

	public Boolean getExcluir() {
		return excluir;
	}

	public void setExcluir(Boolean excluir) {
		this.excluir = excluir;
	}
}