package ma.co.marsamaroc.gestion.decomptes.entite;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
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
 * The persistent class for the DECOMPTE database table.
 * 
 */
@Entity
@Table(name="DECOMPTE")
@NamedQuery(name="Decompte.findAll", query="SELECT d FROM Decompte d")
public class Decompte implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer idDecompte;
	private String numDecompte;
	private Double autresRetenues;
	private Date datCreat;
	private Date datModif;
	private Date dateEtablissement;
	private String flagDernier;
	private Integer indSupp;
	private Double montantTtc;
	private Double penaliteRetard;
	private Double retenuAvance;
	private Double retenuGarantie;
	private Double retenueSource;
	private Double revisionPrix;
	private String userCreat;
	private String userModif;
	private Set<Attachement> attachements;
	private Marche marche;

	public Decompte() {
	}


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID_DECOMPTE")
	public Integer getIdDecompte() {
		return this.idDecompte;
	}

	public void setIdDecompte(Integer idDecompte) {
		this.idDecompte = idDecompte;
	}

	@Column(name="NUM_DECOMPTE")
	public String getNumDecompte() {
		return numDecompte;
	}


	public void setNumDecompte(String numDecompte) {
		this.numDecompte = numDecompte;
	}

	@Column(name="AUTRES_RETENUES")
	public Double getAutresRetenues() {
		return this.autresRetenues;
	}



	public void setAutresRetenues(Double autresRetenues) {
		this.autresRetenues = autresRetenues;
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
	@Column(name="DATE_ETABLISSEMENT")
	public Date getDateEtablissement() {
		return this.dateEtablissement;
	}

	public void setDateEtablissement(Date dateEtablissement) {
		this.dateEtablissement = dateEtablissement;
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


	@Column(name="MONTANT_TTC")
	public Double getMontantTtc() {
		return this.montantTtc;
	}

	public void setMontantTtc(Double montantTtc) {
		this.montantTtc = montantTtc;
	}


	@Column(name="PENALITE_RETARD")
	public Double getPenaliteRetard() {
		return this.penaliteRetard;
	}

	public void setPenaliteRetard(Double penaliteRetard) {
		this.penaliteRetard = penaliteRetard;
	}
	
	@Column(name="RETENUE_AVANCE")
	public Double getRetenuAvance() {
		return retenuAvance;
	}

	public void setRetenuAvance(Double retenuAvance) {
		this.retenuAvance = retenuAvance;
	}

	@Column(name="RETENU_GARANTIE")
	public Double getRetenuGarantie() {
		return this.retenuGarantie;
	}

	public void setRetenuGarantie(Double retenuGarantie) {
		this.retenuGarantie = retenuGarantie;
	}


	@Column(name="RETENUE_SOURCE")
	public Double getRetenueSource() {
		return this.retenueSource;
	}

	public void setRetenueSource(Double retenueSource) {
		this.retenueSource = retenueSource;
	}


	@Column(name="REVISION_PRIX")
	public Double getRevisionPrix() {
		return this.revisionPrix;
	}

	public void setRevisionPrix(Double revisionPrix) {
		this.revisionPrix = revisionPrix;
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
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY,mappedBy="decompte")
	public Set<Attachement> getAttachements() {
		return this.attachements;
	}

	public void setAttachements(Set<Attachement> attachements) {
		this.attachements = attachements;
	}

	public Attachement addAttachement(Attachement attachement) {
		getAttachements().add(attachement);
		attachement.setDecompte(this);

		return attachement;
	}

	public Attachement removeAttachement(Attachement attachement) {
		getAttachements().remove(attachement);
		attachement.setDecompte(null);

		return attachement;
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