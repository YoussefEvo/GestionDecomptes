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
 * The persistent class for the ATTACHEMENT database table.
 * 
 */
@Entity
@Table(name="ATTACHEMENT")
@NamedQuery(name="Attachement.findAll", query="SELECT a FROM Attachement a")
public class Attachement implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer idAttachement;
	private String numAttachement;
	private Date datCreat;
	private Date datModif;
	private Date dateDebut;
	private Date dateEtablissement;
	private Date dateFin;
	private String flagDernier;
	private Integer indSupp;
	private Double montant;
	private String userCreat;
	private String userModif;
	private BonCommande bonCommande;
	private Decompte decompte;
	private Marche marche;
	private Set<BonReception> bonReceptions;

	public Attachement() {
	}


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID_ATTACHEMENT")
	public Integer getIdAttachement() {
		return this.idAttachement;
	}

	public void setIdAttachement(Integer idAttachement) {
		this.idAttachement = idAttachement;
	}

	@Column(name="NUM_ATTACHEMENT")
	public String getNumAttachement() {
		return numAttachement;
	}

	public void setNumAttachement(String numAttachement) {
		this.numAttachement = numAttachement;
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
	@Column(name="DATE_DEBUT")
	public Date getDateDebut() {
		return this.dateDebut;
	}

	public void setDateDebut(Date dateDebut) {
		this.dateDebut = dateDebut;
	}


	@Temporal(TemporalType.DATE)
	@Column(name="DATE_ETABLISSEMENT")
	public Date getDateEtablissement() {
		return this.dateEtablissement;
	}

	public void setDateEtablissement(Date dateEtablissement) {
		this.dateEtablissement = dateEtablissement;
	}


	@Temporal(TemporalType.DATE)
	@Column(name="DATE_FIN")
	public Date getDateFin() {
		return this.dateFin;
	}

	public void setDateFin(Date dateFin) {
		this.dateFin = dateFin;
	}


	@Column(name="FLAG_DERNIER")
	public String getFlagDernier() {
		return this.flagDernier;
	}

	public void setFlagDernier(String flagDernier) {
		this.flagDernier = flagDernier;
	}


	@Column(name="IND_SUPP")
	public Integer getIndSupp() {
		return this.indSupp;
	}

	public void setIndSupp(Integer indSupp) {
		this.indSupp = indSupp;
	}


	@Column(name="MONTANT")
	public Double getMontant() {
		return this.montant;
	}

	public void setMontant(Double montant) {
		this.montant = montant;
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


	//bi-directional many-to-one association to BonCommande
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_BON_COMMANDE")
	public BonCommande getBonCommande() {
		return this.bonCommande;
	}

	public void setBonCommande(BonCommande bonCommande) {
		this.bonCommande = bonCommande;
	}


	//bi-directional many-to-one association to Decompte
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_DECOMPTE")
	public Decompte getDecompte() {
		return this.decompte;
	}

	public void setDecompte(Decompte decompte) {
		this.decompte = decompte;
	}


	//bi-directional many-to-one association to Marche
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_MARCHE")
	public Marche getMarche() {
		return this.marche;
	}

	public void setMarche(Marche marche) {
		this.marche = marche;
	}
	
	//bi-directional many-to-one association to BonReception
	@OneToMany(mappedBy="attachement")
	public Set<BonReception> getBonReceptions() {
		return this.bonReceptions;
	}

	public void setBonReceptions(Set<BonReception> bonReceptions) {
		this.bonReceptions = bonReceptions;
	}

	public BonReception addBonReception(BonReception bonReception) {
		getBonReceptions().add(bonReception);
		bonReception.setAttachement(this);

		return bonReception;
	}

	public BonReception removeBonReception(BonReception bonReception) {
		getBonReceptions().remove(bonReception);
		bonReception.setAttachement(null);

		return bonReception;
	}

}