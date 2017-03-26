package ma.co.marsamaroc.gestion.decomptes.dto;

import java.io.Serializable;
import java.util.Date;

import ma.co.marsamaroc.gestion.decomptes.utils.Constantes;
import ma.co.marsamaroc.gestion.decomptes.utils.Utilitaires;



public class AttachementDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer idAttachement;
	private String numAttachement;
	private Date dateDebut;
	private Date dateEtablissement;
	private Date dateFin;
	private String flagDernier;
	private String flagSupprime;
	private Double montant;
	private String userCreat;
	private String userModif;
	private Integer indSupp;
	private Date datCreat;
	private Date datModif;
	private String numDecompte;
	private String flagCloture;
	private boolean isDernier;

	public AttachementDTO() {
	}

	public Integer getIdAttachement() {
		return idAttachement;
	}

	public void setIdAttachement(Integer idAttachement) {
		this.idAttachement = idAttachement;
	}

	public String getNumAttachement() {
		return numAttachement;
	}

	public void setNumAttachement(String numAttachement) {
		this.numAttachement = numAttachement;
	}

	public Date getDatCreat() {
		return datCreat;
	}

	public void setDatCreat(Date datCreat) {
		this.datCreat = datCreat;
	}

	public Date getDatModif() {
		return datModif;
	}

	public void setDatModif(Date datModif) {
		this.datModif = datModif;
	}

	public Date getDateDebut() {
		return dateDebut;
	}

	public void setDateDebut(Date dateDebut) {
		this.dateDebut = dateDebut;
	}

	public Date getDateEtablissement() {
		return dateEtablissement;
	}

	public void setDateEtablissement(Date dateEtablissement) {
		this.dateEtablissement = dateEtablissement;
	}

	public Date getDateFin() {
		return dateFin;
	}

	public void setDateFin(Date dateFin) {
		this.dateFin = dateFin;
	}

	public String getFlagDernier() {
		return flagDernier;
	}

	public void setFlagDernier(String flagDernier) {
		this.flagDernier = flagDernier;
	}

	public Integer getIndSupp() {
		return indSupp;
	}

	public void setIndSupp(Integer indSupp) {
		this.indSupp = indSupp;
	}

	public Double getMontant() {
		return montant;
	}

	public void setMontant(Double montant) {
		this.montant = montant;
	}

	public String getUserCreat() {
		return userCreat;
	}

	public void setUserCreat(String userCreat) {
		this.userCreat = userCreat;
	}

	public String getUserModif() {
		return userModif;
	}

	public void setUserModif(String userModif) {
		this.userModif = userModif;
	}
	
	
	public String getDateDebutStr() {
		return Utilitaires.getString(dateDebut, Constantes.GlobalConstant.DATE_PATTERN_WITHSLASH);
	}
	
	public String getDateFinStr() {
		return Utilitaires.getString(dateFin, Constantes.GlobalConstant.DATE_PATTERN_WITHSLASH);
	}

	public String getDateEtablissementStr() {
		return Utilitaires.getString(dateEtablissement, Constantes.GlobalConstant.DATE_PATTERN_WITHSLASH);
	}
	public String getMontantStr() {
		return Utilitaires.getStringFromDouble(montant);
	}

	public String getNumDecompte() {
		return numDecompte;
	}

	public void setNumDecompte(String numDecompte) {
		this.numDecompte = numDecompte;
	}
	public String getFlagDernierStr() {
		if (Constantes.GlobalConstant.OUI_O.equalsIgnoreCase(flagDernier)) {
			return Constantes.GlobalConstant.FLAG_ET_DERNIER;
		}
		return Constantes.GlobalConstant.CHAINE_VIDE;
	}

	public String getFlagSupprime() {
		return flagSupprime;
	}

	public void setFlagSupprime(String flagSupprime) {
		this.flagSupprime = flagSupprime;
	}

	public String getFlagCloture() {
		return flagCloture;
	}

	public void setFlagCloture(String flagCloture) {
		this.flagCloture = flagCloture;
	}

	public boolean getIsDernier() {
		return isDernier;
	}

	public void setIsDernier(boolean isDernier) {
		this.isDernier = isDernier;
	}
	
	
}