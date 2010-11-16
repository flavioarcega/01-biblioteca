package biblioteca.persistence.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="PERFIL", catalog="PUBLIC")
public class Perfil implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final String PERFIL = "ROLE_" ;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="PERFIL_GEN")
	@SequenceGenerator(name="PERFIL_GEN", sequenceName="SQ_PERFIL_ID_PERFIL", initialValue=1, allocationSize=0)
	@Column(name="ID_PERFIL", unique=true, nullable=false)
	private int id;

	@Column(name="DESCRICAO", length=60)
	private String descricao;

    public Perfil() {
    }

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

    public String getRole() {
        return PERFIL+this.getDescricao().toUpperCase();
    }
}