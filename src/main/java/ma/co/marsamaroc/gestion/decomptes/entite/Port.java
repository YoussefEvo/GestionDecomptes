package ma.co.marsamaroc.gestion.decomptes.entite;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.Set;


/**
 * The persistent class for the PORT database table.
 * 
 */
@Entity
@Table(name="PORT")
@NamedQuery(name="Port.findAll", query="SELECT p FROM Port p")
public class Port implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer idPort;
	private String codPort;
	private Date datCreat;
	private String libPort;
	private String userCreat;
	private Set<Terminal> terminals;

	public Port() {
	}


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID_PORT")
	public Integer getIdPort() {
		return this.idPort;
	}

	public void setIdPort(Integer idPort) {
		this.idPort = idPort;
	}


	@Column(name="COD_PORT")
	public String getCodPort() {
		return this.codPort;
	}

	public void setCodPort(String codPort) {
		this.codPort = codPort;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DAT_CREAT")
	public Date getDatCreat() {
		return this.datCreat;
	}

	public void setDatCreat(Date datCreat) {
		this.datCreat = datCreat;
	}


	@Column(name="LIB_PORT")
	public String getLibPort() {
		return this.libPort;
	}

	public void setLibPort(String libPort) {
		this.libPort = libPort;
	}


	@Column(name="USER_CREAT")
	public String getUserCreat() {
		return this.userCreat;
	}

	public void setUserCreat(String userCreat) {
		this.userCreat = userCreat;
	}


	//bi-directional many-to-one association to Terminal
	@OneToMany(mappedBy="port")
	public Set<Terminal> getTerminals() {
		return this.terminals;
	}

	public void setTerminals(Set<Terminal> terminals) {
		this.terminals = terminals;
	}

	public Terminal addTerminal(Terminal terminal) {
		getTerminals().add(terminal);
		terminal.setPort(this);

		return terminal;
	}

	public Terminal removeTerminal(Terminal terminal) {
		getTerminals().remove(terminal);
		terminal.setPort(null);

		return terminal;
	}

}