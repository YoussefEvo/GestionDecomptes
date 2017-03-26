package ma.co.marsamaroc.gestion.decomptes.dto;

import java.io.Serializable;
import java.util.Date;

import ma.co.marsamaroc.gestion.decomptes.utils.Constantes;
import ma.co.marsamaroc.gestion.decomptes.utils.Utilitaires;



public class BonCommandeDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer idBonCommande;
	private String codFournisseur;
	private Date dateCommande;
	private String devise;
	private String libBonCommande;
	private String libFournisseur;
	private Double montant;
	private String numBonCommande;
	private String userCreat;
	private String userModif;
	private Date datCreat;
	private Date datModif;
	private Integer indSupp;

	public BonCommandeDTO() {
	}

	public Integer getIdBonCommande() {
		return idBonCommande;
	}

	public void setIdBonCommande(Integer idBonCommande) {
		this.idBonCommande = idBonCommande;
	}

	public String getCodFournisseur() {
		return codFournisseur;
	}

	public void setCodFournisseur(String codFournisseur) {
		this.codFournisseur = codFournisseur;
	}

	public Date getDateCommande() {
		return dateCommande;
	}

	public void setDateCommande(Date dateCommande) {
		this.dateCommande = dateCommande;
	}

	public String getDevise() {
		return devise;
	}

	public void setDevise(String devise) {
		this.devise = devise;
	}

	public String getLibBonCommande() {
		return libBonCommande;
	}

	public void setLibBonCommande(String libBonCommande) {
		this.libBonCommande = libBonCommande;
	}

	public String getLibFournisseur() {
		return libFournisseur;
	}

	public void setLibFournisseur(String libFournisseur) {
		this.libFournisseur = libFournisseur;
	}

	public Double getMontant() {
		return montant;
	}

	public void setMontant(Double montant) {
		this.montant = montant;
	}

	public String getNumBonCommande() {
		return numBonCommande;
	}

	public void setNumBonCommande(String numBonCommande) {
		this.numBonCommande = numBonCommande;
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

	public Integer getIndSupp() {
		return indSupp;
	}

	public void setIndSupp(Integer indSupp) {
		this.indSupp = indSupp;
	}
	
	public String getDateCommandeStr() {
		return Utilitaires.getString(dateCommande, Constantes.GlobalConstant.DATE_PATTERN_WITHSLASH);
	}

}