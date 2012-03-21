package biblioteca.persistence.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;


/**
 * The persistent class for the EMPRESTIMO database table.
 * 
 */
@Entity
public class Emprestimo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="EMPRESTIMO_IDEMPRESTIMO_GENERATOR", sequenceName="SQ_EMPRESTIMO_ID_EMPRESTIMO", allocationSize=0)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="EMPRESTIMO_IDEMPRESTIMO_GENERATOR")
	@Column(name="ID_EMPRESTIMO", unique=true, nullable=false)
	private Integer id;

	@Column(name="DATA_DEVOLUCAO_EFETIVA")
	private Date dataDevolucaoEfetiva;

	@Column(name="DATA_DEVOLUCAO_PROGRAMADA")
	private Date dataDevolucaoProgramada;

	@Column(name="DATA_EMPRESTIMO")
	private Date dataEmprestimo;

	@Transient
	private Boolean devolver;

	@Transient
	private Boolean renovar;

	@Transient
	private Boolean excluir;

    @ManyToOne
	@JoinColumn(name="ID_ALUNO")
	private Usuario aluno;

    @ManyToOne
	@JoinColumn(name="ID_OPERADOR_DEVOLUCAO")
	private Usuario operadorDevolucao;

    @ManyToOne
	@JoinColumn(name="ID_OPERADOR_LOCACAO")
	private Usuario operadorLocacao;

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

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer idEmprestimo) {
		this.id = idEmprestimo;
	}

	public Date getDataDevolucaoEfetiva() {
		return this.dataDevolucaoEfetiva;
	}

	public void setDataDevolucaoEfetiva(Date dataDevolucaoEfetiva) {
		this.dataDevolucaoEfetiva = dataDevolucaoEfetiva;
	}

	public Date getDataDevolucaoProgramada() {
		return this.dataDevolucaoProgramada;
	}

	public void setDataDevolucaoProgramada(Date dataDevolucaoProgramada) {
		this.dataDevolucaoProgramada = dataDevolucaoProgramada;
	}

	public Date getDataEmprestimo() {
		return this.dataEmprestimo;
	}

	public void setDataEmprestimo(Date dataEmprestimo) {
		this.dataEmprestimo = dataEmprestimo;
	}

	public Usuario getAluno() {
		return this.aluno;
	}

	public void setAluno(Usuario aluno) {
		this.aluno = aluno;
	}

	public Usuario getOperadorDevolucao() {
		return this.operadorDevolucao;
	}

	public void setOperadorDevolucao(Usuario operadorDevolucao) {
		this.operadorDevolucao = operadorDevolucao;
	}

	public Usuario getOperadorLocacao() {
		return this.operadorLocacao;
	}

	public void setOperadorLocacao(Usuario operadorLocacao) {
		this.operadorLocacao = operadorLocacao;
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

	public Boolean getDevolver() {
		return devolver;
	}

	public void setDevolver(Boolean devolver) {
		this.devolver = devolver;
	}

	public Boolean getRenovar() {
		return renovar;
	}

	public void setRenovar(Boolean renovar) {
		this.renovar = renovar;
	}

	public Boolean getExcluir() {
		return excluir;
	}

	public void setExcluir(Boolean excluir) {
		this.excluir = excluir;
	}
}