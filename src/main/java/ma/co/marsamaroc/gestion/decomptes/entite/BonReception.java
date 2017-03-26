package ma.co.marsamaroc.gestion.decomptes.entite;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the BON_RECEPTION database table.
 * 
 */
@Entity
@Table(name="BON_RECEPTION")
@NamedQuery(name="BonReception.findAll", query="SELECT b FROM BonReception b")
public class BonReception implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer idBonReception;
	private String numBonCommande;
	private String numMarche;
	private String numBonReception;
	private Date datCreat;
	private Date datModif;
	private Date dateCommande;
	private Date dateReception;
	private String delais;
	private String designiationBonReception;
	private Integer indSupp;
	private String mo;
	private Double montant;
	private String ot;
	private Double prixUnitaire;
	private Double qte;
	private Double tva;
	private String unite;
	private String devise;
	private String userCreat;
	private String userModif;
	private Attachement attachement;

	public BonReception() {
	}


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID_BON_RECEPTION")
	public Integer getIdBonReception() {
		return this.idBonReception;
	}

	public void setIdBonReception(Integer idBonReception) {
		this.idBonReception = idBonReception;
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
	@Column(name="DATE_COMMANDE")
	public Date getDateCommande() {
		return this.dateCommande;
	}

	public void setDateCommande(Date dateCommande) {
		this.dateCommande = dateCommande;
	}

	@Temporal(TemporalType.DATE)
	@Column(name="DATE_RECEPTION")
	public Date getDateReception() {
		return this.dateReception;
	}

	public void setDateReception(Date dateReception) {
		this.dateReception = dateReception;
	}


	@Column(name="DELAIS")
	public String getDelais() {
		return this.delais;
	}

	public void setDelais(String delais) {
		this.delais = delais;
	}


	@Column(name="DESIGNIATION_BON_RECEPTION")
	public String getDesigniationBonReception() {
		return this.designiationBonReception;
	}

	public void setDesigniationBonReception(String designiationBonReception) {
		this.designiationBonReception = designiationBonReception;
	}


	@Column(name="IND_SUPP")
	public Integer getIndSupp() {
		return this.indSupp;
	}

	public void setIndSupp(Integer indSupp) {
		this.indSupp = indSupp;
	}


	@Column(name="MO")
	public String getMo() {
		return this.mo;
	}

	public void setMo(String mo) {
		this.mo = mo;
	}


	@Column(name="MONTANT")
	public Double getMontant() {
		return this.montant;
	}

	public void setMontant(Double montant) {
		this.montant = montant;
	}


	@Column(name="OT")
	public String getOt() {
		return this.ot;
	}

	public void setOt(String ot) {
		this.ot = ot;
	}


	@Column(name="PRIX_UNITAIRE")
	public Double getPrixUnitaire() {
		return this.prixUnitaire;
	}

	public void setPrixUnitaire(Double prixUnitaire) {
		this.prixUnitaire = prixUnitaire;
	}


	@Column(name="QTE")
	public Double getQte() {
		return this.qte;
	}

	public void setQte(Double qte) {
		this.qte = qte;
	}


	@Column(name="TVA")
	public Double getTva() {
		return this.tva;
	}

	public void setTva(Double tva) {
		this.tva = tva;
	}


	@Column(name="UNITE")
	public String getUnite() {
		return this.unite;
	}

	public void setUnite(String unite) {
		this.unite = unite;
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
	
	@Column(name="NUM_BON_RECEPTION")
	public String getNumBonReception() {
		return this.numBonReception;
	}

	public void setNumBonReception(String numBonReception) {
		this.numBonReception = numBonReception;
	}
	
	@Column(name="NUM_BON_COMMANDE")
	public String getNumBonCommande() {
		return this.numBonCommande;
	}

	public void setNumBonCommande(String numBonCommande) {
		this.numBonCommande = numBonCommande;
	}


	@Column(name="NUM_MARCHE")
	public String getNumMarche() {
		return this.numMarche;
	}

	public void setNumMarche(String numMarche) {
		this.numMarche = numMarche;
	}

	@Column(name="DEVISE")
	public String getDevise() {
		return this.devise;
	}

	public void setDevise(String devise) {
		this.devise = devise;
	}
	
	

	//bi-directional many-to-one association to Attachement
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_ATTACHEMENT")
	public Attachement getAttachement() {
		return this.attachement;
	}

	public void setAttachement(Attachement attachement) {
		this.attachement = attachement;
	}


}