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
 * The persistent class for the BON_COMMANDE database table.
 * 
 */
@Entity
@Table(name="BON_COMMANDE")
@NamedQuery(name="BonCommande.findAll", query="SELECT b FROM BonCommande b")
public class BonCommande implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer idBonCommande;
	private String codFournisseur;
	private Date datCreat;
	private Date datModif;
	private Date dateCommande;
	private String devise;
	private Integer indSupp;
	private String libBonCommande;
	private String libFournisseur;
	private Double montant;
	private String numBonCommande;
	private String userCreat;
	private String userModif;
	private Set<Attachement> attachements;
	private Site site;

	public BonCommande() {
	}


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID_BON_COMMANDE")
	public Integer getIdBonCommande() {
		return this.idBonCommande;
	}

	public void setIdBonCommande(Integer idBonCommande) {
		this.idBonCommande = idBonCommande;
	}


	@Column(name="COD_FOURNISSEUR")
	public String getCodFournisseur() {
		return this.codFournisseur;
	}

	public void setCodFournisseur(String codFournisseur) {
		this.codFournisseur = codFournisseur;
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


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATE_COMMANDE")
	public Date getDateCommande() {
		return this.dateCommande;
	}

	public void setDateCommande(Date dateCommande) {
		this.dateCommande = dateCommande;
	}


	@Column(name="DEVISE")
	public String getDevise() {
		return this.devise;
	}

	public void setDevise(String devise) {
		this.devise = devise;
	}


	@Column(name="IND_SUPP")
	public Integer getIndSupp() {
		return this.indSupp;
	}

	public void setIndSupp(Integer indSupp) {
		this.indSupp = indSupp;
	}


	@Column(name="LIB_BON_COMMANDE")
	public String getLibBonCommande() {
		return this.libBonCommande;
	}

	public void setLibBonCommande(String libBonCommande) {
		this.libBonCommande = libBonCommande;
	}


	@Column(name="LIB_FOURNISSEUR")
	public String getLibFournisseur() {
		return this.libFournisseur;
	}

	public void setLibFournisseur(String libFournisseur) {
		this.libFournisseur = libFournisseur;
	}


	@Column(name="MONTANT")
	public Double getMontant() {
		return this.montant;
	}

	public void setMontant(Double montant) {
		this.montant = montant;
	}


	@Column(name="NUM_BON_COMMANDE")
	public String getNumBonCommande() {
		return this.numBonCommande;
	}

	public void setNumBonCommande(String numBonCommande) {
		this.numBonCommande = numBonCommande;
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


	//bi-directional many-to-one association to Attachement
	@OneToMany(mappedBy="bonCommande")
	public Set<Attachement> getAttachements() {
		return this.attachements;
	}

	public void setAttachements(Set<Attachement> attachements) {
		this.attachements = attachements;
	}

	public Attachement addAttachement(Attachement attachement) {
		getAttachements().add(attachement);
		attachement.setBonCommande(this);

		return attachement;
	}

	public Attachement removeAttachement(Attachement attachement) {
		getAttachements().remove(attachement);
		attachement.setBonCommande(null);

		return attachement;
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

}