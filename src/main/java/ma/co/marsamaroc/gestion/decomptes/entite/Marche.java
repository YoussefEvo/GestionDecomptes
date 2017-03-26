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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the MARCHE database table.
 * 
 */
@Entity
@Table(name="MARCHE")
@NamedQuery(name="Marche.findAll", query="SELECT m FROM Marche m")
public class Marche implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer idMarche;
	private String codFournisseur;
	private String typeMarche;
	private Date datCreat;
	private Date datModif;
	private Date dateDemarage;
	private Date dateFin;
	private String description;
	private String budget;
	private String devise;
	private Double montant;
	private Integer indSupp;
	private String flagCloture;
	private String libFournisseur;
	private String libMarche;
	private String numMarche;
	private String userCreat;
	private String userModif;
	private Set<Attachement> attachements;
	private Set<Decompte> decomptes;
	private Set<BordereauPrix> bordereauPrixs;
	private Site site;

	public Marche() {
	}


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID_MARCHE")
	public Integer getIdMarche() {
		return this.idMarche;
	}

	public void setIdMarche(Integer idMarche) {
		this.idMarche = idMarche;
	}


	@Column(name="COD_FOURNISSEUR")
	public String getCodFournisseur() {
		return this.codFournisseur;
	}

	public void setCodFournisseur(String codFournisseur) {
		this.codFournisseur = codFournisseur;
	}
	
	@Column(name="TYPE_MARCHE")
	public String getTypeMarche() {
		return typeMarche;
	}

	public void setTypeMarche(String typeMarche) {
		this.typeMarche = typeMarche;
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


	@Temporal(TemporalType.DATE)
	@Column(name="DATE_DEMARAGE")
	public Date getDateDemarage() {
		return this.dateDemarage;
	}

	public void setDateDemarage(Date dateDemarage) {
		this.dateDemarage = dateDemarage;
	}


	@Temporal(TemporalType.DATE)
	@Column(name="DATE_FIN")
	public Date getDateFin() {
		return this.dateFin;
	}

	public void setDateFin(Date dateFin) {
		this.dateFin = dateFin;
	}

	@Lob
	@Column(name="DESCRIPTION")
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@Column(name="BUDGET")
	public String getBudget() {
		return this.budget;
	}

	public void setBudget(String budget) {
		this.budget = budget;
	}

	@Column(name="DEVISE")
	public String getDevise() {
		return this.devise;
	}

	public void setDevise(String devise) {
		this.devise = devise;
	}
	
	@Column(name="MONTANT")
	public Double getMontant() {
		return this.montant;
	}

	public void setMontant(Double montant) {
		this.montant = montant;
	}


	@Column(name="IND_SUPP")
	public Integer getIndSupp() {
		return this.indSupp;
	}

	public void setIndSupp(Integer indSupp) {
		this.indSupp = indSupp;
	}
	
	@Column(name="FLAG_CLOTURE")
	public String getFlagCloture() {
		return flagCloture;
	}


	public void setFlagCloture(String flagCloture) {
		this.flagCloture = flagCloture;
	}


	@Column(name="LIB_FOURNISSEUR")
	public String getLibFournisseur() {
		return this.libFournisseur;
	}

	public void setLibFournisseur(String libFournisseur) {
		this.libFournisseur = libFournisseur;
	}


	@Column(name="LIB_MARCHE")
	public String getLibMarche() {
		return this.libMarche;
	}

	public void setLibMarche(String libMarche) {
		this.libMarche = libMarche;
	}


	@Column(name="NUM_MARCHE")
	public String getNumMarche() {
		return this.numMarche;
	}

	public void setNumMarche(String numMarche) {
		this.numMarche = numMarche;
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
	@OneToMany(mappedBy="marche")
	public Set<Attachement> getAttachements() {
		return this.attachements;
	}

	public void setAttachements(Set<Attachement> attachements) {
		this.attachements = attachements;
	}

	public Attachement addAttachement(Attachement attachement) {
		getAttachements().add(attachement);
		attachement.setMarche(this);

		return attachement;
	}

	public Attachement removeAttachement(Attachement attachement) {
		getAttachements().remove(attachement);
		attachement.setMarche(null);

		return attachement;
	}


	//bi-directional many-to-one association to Decompte
	@OneToMany(mappedBy="marche")
	public Set<Decompte> getDecomptes() {
		return this.decomptes;
	}

	public void setDecomptes(Set<Decompte> decomptes) {
		this.decomptes = decomptes;
	}

	public Decompte addDecompte(Decompte decompte) {
		getDecomptes().add(decompte);
		decompte.setMarche(this);

		return decompte;
	}

	public Decompte removeDecompte(Decompte decompte) {
		getDecomptes().remove(decompte);
		decompte.setMarche(null);

		return decompte;
	}

	//bi-directional many-to-one association to Decompte
	@OneToMany(mappedBy="marche")
	public Set<BordereauPrix> getBordereauPrixs() {
		return this.bordereauPrixs;
	}

	public void setBordereauPrixs(Set<BordereauPrix> bordereauPrixs) {
		this.bordereauPrixs = bordereauPrixs;
	}

	public BordereauPrix addBordereauPrix(BordereauPrix bordereauPrix) {
		getBordereauPrixs().add(bordereauPrix);
		bordereauPrix.setMarche(this);

		return bordereauPrix;
	}

	public BordereauPrix removeBordereauPrix(BordereauPrix bordereauPrix) {
		getBordereauPrixs().remove(bordereauPrix);
		bordereauPrix.setMarche(null);

		return bordereauPrix;
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