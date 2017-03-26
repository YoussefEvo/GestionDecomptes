package ma.co.marsamaroc.gestion.decomptes.entite;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the [ROLE] database table.
 * 
 */
@Entity
@Table(name="[ROLE]")
@NamedQuery(name="Role.findAll", query="SELECT r FROM Role r")
public class Role implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer idRole;
	private String codRole;
	private Date datCreat;
	private String libRole;
	private Integer numOrdre;
	private String userCreat;
	private Set<Profil> profils;

	public Role() {
	}


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID_ROLE")
	public Integer getIdRole() {
		return this.idRole;
	}

	public void setIdRole(Integer idRole) {
		this.idRole = idRole;
	}


	@Column(name="COD_ROLE")
	public String getCodRole() {
		return this.codRole;
	}

	public void setCodRole(String codRole) {
		this.codRole = codRole;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DAT_CREAT")
	public Date getDatCreat() {
		return this.datCreat;
	}

	public void setDatCreat(Date datCreat) {
		this.datCreat = datCreat;
	}


	@Column(name="LIB_ROLE")
	public String getLibRole() {
		return this.libRole;
	}

	public void setLibRole(String libRole) {
		this.libRole = libRole;
	}


	@Column(name="NUM_ORDRE")
	public Integer getNumOrdre() {
		return this.numOrdre;
	}

	public void setNumOrdre(Integer numOrdre) {
		this.numOrdre = numOrdre;
	}


	@Column(name="USER_CREAT")
	public String getUserCreat() {
		return this.userCreat;
	}

	public void setUserCreat(String userCreat) {
		this.userCreat = userCreat;
	}


	//bi-directional many-to-many association to Profil
	@ManyToMany(mappedBy="roles")
	public Set<Profil> getProfils() {
		return this.profils;
	}

	public void setProfils(Set<Profil> profils) {
		this.profils = profils;
	}


}