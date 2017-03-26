package ma.co.marsamaroc.gestion.decomptes.entite;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the UTILISATEUR database table.
 * 
 */
@Entity
@Table(name="UTILISATEUR")
@NamedQueries( {
	@NamedQuery(name="Utilisateur.findAll", query="SELECT u FROM Utilisateur u"),
	@NamedQuery(name="utilisateur.findUserByUserName", query="SELECT u FROM Utilisateur u where u.username = ?1")
})
public class Utilisateur implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer idUser;
	private Date datCreat;
	private Date datModif;
	private String email;
	private Integer indSupp;
	private String pwd;
	private String pwdClair;
	private String pwdReset;
	private String userCreat;
	private String userModif;
	private String username;
	private Set<Autorisation> autorisations;

	public Utilisateur() {
	}


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID_USER")
	public Integer getIdUser() {
		return this.idUser;
	}

	public void setIdUser(Integer idUser) {
		this.idUser = idUser;
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


	@Column(name="EMAIL")
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


	@Column(name="IND_SUPP")
	public Integer getIndSupp() {
		return this.indSupp;
	}

	public void setIndSupp(Integer indSupp) {
		this.indSupp = indSupp;
	}


	@Column(name="PWD")
	public String getPwd() {
		return this.pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}


	@Column(name="PWD_CLAIR")
	public String getPwdClair() {
		return this.pwdClair;
	}

	public void setPwdClair(String pwdClair) {
		this.pwdClair = pwdClair;
	}


	@Column(name="PWD_RESET")
	public String getPwdReset() {
		return this.pwdReset;
	}

	public void setPwdReset(String pwdReset) {
		this.pwdReset = pwdReset;
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


	@Column(name="USERNAME")
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}


	//bi-directional many-to-one association to Autorisation
	@OneToMany(mappedBy="utilisateur")
	public Set<Autorisation> getAutorisations() {
		return this.autorisations;
	}

	public void setAutorisations(Set<Autorisation> autorisations) {
		this.autorisations = autorisations;
	}

	public Autorisation addAutorisation(Autorisation autorisation) {
		getAutorisations().add(autorisation);
		autorisation.setUtilisateur(this);

		return autorisation;
	}

	public Autorisation removeAutorisation(Autorisation autorisation) {
		getAutorisations().remove(autorisation);
		autorisation.setUtilisateur(null);

		return autorisation;
	}

}