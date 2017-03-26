package ma.co.marsamaroc.gestion.decomptes.dto;

import java.io.Serializable;
import java.util.Date;

import ma.co.marsamaroc.gestion.decomptes.utils.Constantes;
import ma.co.marsamaroc.gestion.decomptes.utils.Utilitaires;



public class BonReceptionDTO implements Serializable {
	
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
	private Double qteAttachement;
	private String tvaStr;
	
	
	private int idOrder;

	public BonReceptionDTO() {
	}
	
	public Double getDouble(Double value){
		if (null != value) {
			return value;
		}
		return new Double(0);
	}

	public Integer getIdBonReception() {
		return idBonReception;
	}

	public void setIdBonReception(Integer idBonReception) {
		this.idBonReception = idBonReception;
	}

	public Date getDateCommande() {
		return dateCommande;
	}

	public void setDateCommande(Date dateCommande) {
		this.dateCommande = dateCommande;
	}

	public Date getDateReception() {
		return dateReception;
	}

	public void setDateReception(Date dateReception) {
		this.dateReception = dateReception;
	}

	public String getDelais() {
		return delais;
	}

	public void setDelais(String delais) {
		this.delais = delais;
	}

	public String getDesigniationBonReception() {
		return designiationBonReception;
	}

	public void setDesigniationBonReception(String designiationBonReception) {
		this.designiationBonReception = designiationBonReception;
	}

	public String getMo() {
		return mo;
	}

	public void setMo(String mo) {
		this.mo = mo;
	}

	public Double getMontant() {
		return getDouble(montant);
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

	public String getNumMarche() {
		return numMarche;
	}

	public void setNumMarche(String numMarche) {
		this.numMarche = numMarche;
	}

	public String getOt() {
		return ot;
	}

	public void setOt(String ot) {
		this.ot = ot;
	}

	public Double getPrixUnitaire() {
		return getDouble(prixUnitaire);
	}

	public void setPrixUnitaire(Double prixUnitaire) {
		this.prixUnitaire = prixUnitaire;
	}

	public Double getQte() {
		return getDouble(qte);
	}

	public void setQte(Double qte) {
		this.qte = qte;
	}

	public Double getTva() {
		return getDouble(tva);
	}
	
	public String getTvaStr() {
		if (null != tva) {
			return  (new Double(tva*100).intValue()) + " %";
		}
		return "0 %";
	}

	public void setTva(Double tva) {
		this.tva = tva;
	}

	public String getUnite() {
		return unite;
	}

	public void setUnite(String unite) {
		this.unite = unite;
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

	public String getNumBonReception() {
		return numBonReception;
	}

	public void setNumBonReception(String numBonReception) {
		this.numBonReception = numBonReception;
	}

	public String getDevise() {
		return devise;
	}

	public void setDevise(String devise) {
		this.devise = devise;
	}

	public String getDateReceptionStr() {
		return Utilitaires.getString(dateReception, Constantes.GlobalConstant.DATE_PATTERN_WITHSLASH);
	}
	
	public String getMontantStr() {
		return Utilitaires.getStringFromDouble(montant);
	}
	
	public String getQteStr() {
		return Utilitaires.getStringFromDouble(qte);
	}
	public String getPrixUnitaireStr() {
		return Utilitaires.getStringFromDouble(prixUnitaire);
	}
	
	

	public Double getQteAttachement() {
		return qteAttachement;
	}

	public void setQteAttachement(Double qteAttachement) {
		this.qteAttachement = qteAttachement;
	}

	public int getIdOrder() {
		return idOrder;
	}

	public void setIdOrder(int idOrder) {
		this.idOrder = idOrder;
	}
	
}