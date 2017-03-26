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
 * The persistent class for the BORDEREAU_PRIX database table.
 * 
 */
@Entity
@Table(name="BORDEREAU_PRIX")
@NamedQuery(name="BordereauPrix.findAll", query="SELECT b FROM BordereauPrix b")
public class BordereauPrix implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer idBordereauPrix;
	private Date datCreat;
	private Date datModif;
	private String designiation;
	private Integer indSupp;
	private Double prixUnitaire;
	private Double qte;
	private String unite;
	private String userCreat;
	private String userModif;
	private Marche marche;

	public BordereauPrix() {
	}


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID_BORDEREAU_PRIX")
	public Integer getIdBordereauPrix() {
		return this.idBordereauPrix;
	}

	public void setIdBordereauPrix(Integer idBordereauPrix) {
		this.idBordereauPrix = idBordereauPrix;
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


	@Column(name="DESIGNIATION")
	public String getDesigniation() {
		return this.designiation;
	}

	public void setDesigniation(String designiation) {
		this.designiation = designiation;
	}


	@Column(name="IND_SUPP")
	public Integer getIndSupp() {
		return this.indSupp;
	}

	public void setIndSupp(Integer indSupp) {
		this.indSupp = indSupp;
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
		return qte;
	}

	public void setQte(Double qte) {
		this.qte = qte;
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


	//bi-directional many-to-one association to Marche
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_MARCHE")
	public Marche getMarche() {
		return this.marche;
	}

	public void setMarche(Marche marche) {
		this.marche = marche;
	}

}