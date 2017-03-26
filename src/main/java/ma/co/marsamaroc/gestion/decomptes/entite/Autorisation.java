package ma.co.marsamaroc.gestion.decomptes.entite;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the AUTORISATION database table.
 * 
 */
@Entity
@Table(name="AUTORISATION")
@NamedQuery(name="Autorisation.findAll", query="SELECT a FROM Autorisation a")
public class Autorisation implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer idAutorisation;
	private Profil profil;
	private Site site;
	private Utilisateur utilisateur;

	public Autorisation() {
	}


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID_AUTORISATION")
	public Integer getIdAutorisation() {
		return this.idAutorisation;
	}

	public void setIdAutorisation(Integer idAutorisation) {
		this.idAutorisation = idAutorisation;
	}


	//bi-directional many-to-one association to Profil
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_PROFIL")
	public Profil getProfil() {
		return this.profil;
	}

	public void setProfil(Profil profil) {
		this.profil = profil;
	}


	//bi-directional many-to-one association to Site
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_SITE")
	public Site getSite() {
		return this.site;
	}

	public void setSite(Site site) {
		this.site = site;
	}


	//bi-directional many-to-one association to Utilisateur
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_USER")
	public Utilisateur getUtilisateur() {
		return this.utilisateur;
	}

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

}