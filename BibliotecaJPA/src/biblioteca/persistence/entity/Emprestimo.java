package biblioteca.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;


/**
 * The persistent class for the EMPRESTIMO database table.
 * 
 */
@Entity
public class Emprestimo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="EMPRESTIMO_IDEMPRESTIMO_GENERATOR", sequenceName="SQ_EMPRESTIMO_ID_EMPRESTIMO")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="EMPRESTIMO_IDEMPRESTIMO_GENERATOR")
	@Column(name="ID_EMPRESTIMO")
	private int idEmprestimo;

	@Column(name="DATA_DEVOLUCAO_EFETIVA")
	private Timestamp dataDevolucaoEfetiva;

	@Column(name="DATA_DEVOLUCAO_PROGRAMADA")
	private Timestamp dataDevolucaoProgramada;

	@Column(name="DATA_EMPRESTIMO")
	private Timestamp dataEmprestimo;

	@Column(name="ID_ALUNO")
	private int idAluno;

	@Column(name="ID_OPERADOR_DEVOLUCAO")
	private int idOperadorDevolucao;

	@Column(name="ID_OPERADOR_LOCACAO")
	private int idOperadorLocacao;

	//bi-directional many-to-one association to Emprestimo
    @ManyToOne
	@JoinColumn(name="ID_RENOVACAO")
	private Emprestimo emprestimo;

	//bi-directional many-to-one association to Emprestimo
	@OneToMany(mappedBy="emprestimo")
	private Set<Emprestimo> emprestimos;

	//bi-directional many-to-one association to Livro
    @ManyToOne
	@JoinColumn(name="ID_LIVRO")
	private Livro livro;

    public Emprestimo() {
    }

	public int getIdEmprestimo() {
		return this.idEmprestimo;
	}

	public void setIdEmprestimo(int idEmprestimo) {
		this.idEmprestimo = idEmprestimo;
	}

	public Timestamp getDataDevolucaoEfetiva() {
		return this.dataDevolucaoEfetiva;
	}

	public void setDataDevolucaoEfetiva(Timestamp dataDevolucaoEfetiva) {
		this.dataDevolucaoEfetiva = dataDevolucaoEfetiva;
	}

	public Timestamp getDataDevolucaoProgramada() {
		return this.dataDevolucaoProgramada;
	}

	public void setDataDevolucaoProgramada(Timestamp dataDevolucaoProgramada) {
		this.dataDevolucaoProgramada = dataDevolucaoProgramada;
	}

	public Timestamp getDataEmprestimo() {
		return this.dataEmprestimo;
	}

	public void setDataEmprestimo(Timestamp dataEmprestimo) {
		this.dataEmprestimo = dataEmprestimo;
	}

	public int getIdAluno() {
		return this.idAluno;
	}

	public void setIdAluno(int idAluno) {
		this.idAluno = idAluno;
	}

	public int getIdOperadorDevolucao() {
		return this.idOperadorDevolucao;
	}

	public void setIdOperadorDevolucao(int idOperadorDevolucao) {
		this.idOperadorDevolucao = idOperadorDevolucao;
	}

	public int getIdOperadorLocacao() {
		return this.idOperadorLocacao;
	}

	public void setIdOperadorLocacao(int idOperadorLocacao) {
		this.idOperadorLocacao = idOperadorLocacao;
	}

	public Emprestimo getEmprestimo() {
		return this.emprestimo;
	}

	public void setEmprestimo(Emprestimo emprestimo) {
		this.emprestimo = emprestimo;
	}
	
	public Set<Emprestimo> getEmprestimos() {
		return this.emprestimos;
	}

	public void setEmprestimos(Set<Emprestimo> emprestimos) {
		this.emprestimos = emprestimos;
	}
	
	public Livro getLivro() {
		return this.livro;
	}

	public void setLivro(Livro livro) {
		this.livro = livro;
	}
	
}