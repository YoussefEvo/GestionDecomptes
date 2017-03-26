package ma.co.marsamaroc.gestion.decomptes.dto;

import java.io.Serializable;
import java.util.Date;


/**
 * The persistent class for the BORDEREAU_PRIX database table.
 * 
 */
public class BordereauPrixDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer idBordereauPrix;
	private String numMarche;
	private Date datCreat;
	private Date datModif;
	private String designiation;
	private Integer indSupp;
	private Double prixUnitaire;
	private Double qte;
	private String unite;
	private String userCreat;
	private String userModif;
	private Double qteDecompte;
	private Double qteAncienCumul;
	private Double tva;

	public BordereauPrixDTO() {
	}


	public Integer getIdBordereauPrix() {
		return this.idBordereauPrix;
	}

	public void setIdBordereauPrix(Integer idBordereauPrix) {
		this.idBordereauPrix = idBordereauPrix;
	}


	public Date getDatCreat() {
		return this.datCreat;
	}

	public void setDatCreat(Date datCreat) {
		this.datCreat = datCreat;
	}


	public Date getDatModif() {
		return this.datModif;
	}

	public void setDatModif(Date datModif) {
		this.datModif = datModif;
	}


	public String getDesigniation() {
		return this.designiation;
	}

	public void setDesigniation(String designiation) {
		this.designiation = designiation;
	}


	public Integer getIndSupp() {
		return this.indSupp;
	}

	public void setIndSupp(Integer indSupp) {
		this.indSupp = indSupp;
	}

	public Double getPrixUnitaire() {
		return this.prixUnitaire;
	}

	public void setPrixUnitaire(Double prixUnitaire) {
		this.prixUnitaire = prixUnitaire;
	}

	public Double getQte() {
		return qte;
	}

	public void setQte(Double qte) {
		this.qte = qte;
	}


	public String getUnite() {
		return this.unite;
	}

	public void setUnite(String unite) {
		this.unite = unite;
	}


	public String getUserCreat() {
		return this.userCreat;
	}

	public void setUserCreat(String userCreat) {
		this.userCreat = userCreat;
	}


	public String getUserModif() {
		return this.userModif;
	}

	public void setUserModif(String userModif) {
		this.userModif = userModif;
	}


	public String getNumMarche() {
		return numMarche;
	}


	public void setNumMarche(String numMarche) {
		this.numMarche = numMarche;
	}


	public Double getQteDecompte() {
		if(null == qteDecompte){
			return 0.0;
		}
		return qteDecompte;
	}


	public void setQteDecompte(Double qteDecompte) {
		this.qteDecompte = qteDecompte;
	}


	public Double getQteAncienCumul() {
		if(null == qteAncienCumul){
			return 0.0;
		}
		return qteAncienCumul;
	}


	public void setQteAncienCumul(Double qteAncienCumul) {
		this.qteAncienCumul = qteAncienCumul;
	}


	public Double getTva() {
		return tva;
	}


	public void setTva(Double tva) {
		this.tva = tva;
	}
	
	

	
}