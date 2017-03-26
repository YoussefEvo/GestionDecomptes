package ma.co.marsamaroc.gestion.decomptes.entite;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.Set;


/**
 * The persistent class for the PROFIL database table.
 * 
 */
@Entity
@Table(name="PROFIL")
@NamedQuery(name="Profil.findAll", query="SELECT p FROM Profil p")
public class Profil implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer idProfil;
	private String codProfil;
	private Date datCreat;
	private Date datModif;
	private String libProfil;
	private String userCreat;
	private String userModif;
	private Set<Role> roles;
	private Set<Autorisation> autorisations;

	public Profil() {
	}


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID_PROFIL")
	public Integer getIdProfil() {
		return this.idProfil;
	}

	public void setIdProfil(Integer idProfil) {
		this.idProfil = idProfil;
	}


	@Column(name="COD_PROFIL")
	public String getCodProfil() {
		return this.codProfil;
	}

	public void setCodProfil(String codProfil) {
		this.codProfil = codProfil;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DAT_CREAT")
	public Date getDatCreat() {
		return this.datCreat;
	}

	public void setDatCreat(Date datCreat) {
		this.datCreat = datCreat;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DAT_MODIF")
	public Date getDatModif() {
		return this.datModif;
	}

	public void setDatModif(Date datModif) {
		this.datModif = datModif;
	}


	@Column(name="LIB_PROFIL")
	public String getLibProfil() {
		return this.libProfil;
	}

	public void setLibProfil(String libProfil) {
		this.libProfil = libProfil;
	}


	@Column(name="USER_CREAT")
	public String getUserCreat() {
		return this.userCreat;
	}

	public void setUserCreat(String userCreat) {
		this.userCreat = userCreat;
	}


	@Column(name="USER_MODIF")
	public String getUserModif() {
		return this.userModif;
	}

	public void setUserModif(String userModif) {
		this.userModif = userModif;
	}


	//bi-directional many-to-many association to Role
	@ManyToMany
	@JoinTable(
		name="PROFIL_ROLE"
		, joinColumns={
			@JoinColumn(name="ID_PROFIL")
			}
		, inverseJoinColumns={
			@JoinColumn(name="ID_ROLE")
			}
		)
	public Set<Role> getRoles() {
		return this.roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	//bi-directional many-to-one association to Autorisation
	@OneToMany(mappedBy="profil")
	public Set<Autorisation> getAutorisations() {
		return this.autorisations;
	}

	public void setAutorisations(Set<Autorisation> autorisations) {
		this.autorisations = autorisations;
	}

	public Autorisation addAutorisation(Autorisation autorisation) {
		getAutorisations().add(autorisation);
		autorisation.setProfil(this);

		return autorisation;
	}

	public Autorisation removeAutorisation(Autorisation autorisation) {
		getAutorisations().remove(autorisation);
		autorisation.setProfil(null);

		return autorisation;
	}

}