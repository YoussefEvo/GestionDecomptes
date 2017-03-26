package ma.co.marsamaroc.gestion.decomptes.entite;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the TERMINAL database table.
 * 
 */
@Entity
@Table(name="TERMINAL")
@NamedQuery(name="Terminal.findAll", query="SELECT t FROM Terminal t")
public class Terminal implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer idTerminal;
	private String codTerminal;
	private Date datCreat;
	private String libTerminal;
	private String userCreat;
	private Set<Site> sites;
	private Port port;

	public Terminal() {
	}


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID_TERMINAL")
	public Integer getIdTerminal() {
		return this.idTerminal;
	}

	public void setIdTerminal(Integer idTerminal) {
		this.idTerminal = idTerminal;
	}


	@Column(name="COD_TERMINAL")
	public String getCodTerminal() {
		return this.codTerminal;
	}

	public void setCodTerminal(String codTerminal) {
		this.codTerminal = codTerminal;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DAT_CREAT")
	public Date getDatCreat() {
		return this.datCreat;
	}

	public void setDatCreat(Date datCreat) {
		this.datCreat = datCreat;
	}


	@Column(name="LIB_TERMINAL")
	public String getLibTerminal() {
		return this.libTerminal;
	}

	public void setLibTerminal(String libTerminal) {
		this.libTerminal = libTerminal;
	}


	@Column(name="USER_CREAT")
	public String getUserCreat() {
		return this.userCreat;
	}

	public void setUserCreat(String userCreat) {
		this.userCreat = userCreat;
	}


	//bi-directional many-to-one association to Site
	@OneToMany(mappedBy="terminal")
	public Set<Site> getSites() {
		return this.sites;
	}

	public void setSites(Set<Site> sites) {
		this.sites = sites;
	}

	public Site addSite(Site site) {
		getSites().add(site);
		site.setTerminal(this);

		return site;
	}

	public Site removeSite(Site site) {
		getSites().remove(site);
		site.setTerminal(null);

		return site;
	}


	//bi-directional many-to-one association to Port
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_PORT")
	public Port getPort() {
		return this.port;
	}

	public void setPort(Port port) {
		this.port = port;
	}

}