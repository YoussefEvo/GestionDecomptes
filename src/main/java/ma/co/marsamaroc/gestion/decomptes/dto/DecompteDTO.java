package ma.co.marsamaroc.gestion.decomptes.dto;

import java.io.Serializable;
import java.util.Date;

import ma.co.marsamaroc.gestion.decomptes.utils.Constantes;



public class DecompteDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer idDecompte;
	private String numDecompte;
	private Double autresRetenues;
	private Date dateEtablissement;
	private String flagDernier;
	private boolean dernier;
	private Double montantTtc;
	private Double montantTva;
	private Double penaliteRetard;
	private Double retenuAvance;
	private Double retenuGarantie;
	private Double retenueSource;
	private Double revisionPrix;
	private String userCreat;
	private String userModif;
	private Date datCreat;
	private Date datModif;
	private Integer indSupp;
	private Integer idPeriodeDecompte;
	private Integer idMarche;
	private Boolean ddPartiel;
	private Boolean ddGlobal;
	private Boolean lastElement;
	
	public DecompteDTO() {
	}

	public Integer getIdDecompte() {
		return idDecompte;
	}

	public void setIdDecompte(Integer idDecompte) {
		this.idDecompte = idDecompte;
	}

	public String getNumDecompte() {
		return numDecompte;
	}

	public void setNumDecompte(String numDecompte) {
		this.numDecompte = numDecompte;
	}

	public Double getAutresRetenues() {
		if(null == autresRetenues){
			return 0.0;
		}
		return autresRetenues;
	}

	public void setAutresRetenues(Double autresRetenues) {
		this.autresRetenues = autresRetenues;
	}

	public Date getDateEtablissement() {
		return dateEtablissement;
	}

	public void setDateEtablissement(Date dateEtablissement) {
		this.dateEtablissement = dateEtablissement;
	}

	public String getFlagDernier() {
		return flagDernier;
	}
	
	public String getFlagDernierStr() {
		
		String statut="";
		if(flagDernier.equals(Constantes.GlobalConstant.DECOMPTE_DEFINITIF_PROVISOIRE)){
			statut = Constantes.GlobalConstant.DECOMPTE_DEFINITIF_PROVISOIRE_STR;
		}
		if(flagDernier.equals(Constantes.GlobalConstant.DECOMPTE_DEFINITIF_PARTIEL)){
			statut = Constantes.GlobalConstant.DECOMPTE_DEFINITIF_PARTIEL_STR;
		}
		if(flagDernier.equals(Constantes.GlobalConstant.DECOMPTE_DEFINITIF_GLOBAL)){
			statut = Constantes.GlobalConstant.DECOMPTE_DEFINITIF_GLOBAL_STR;
		}
		return statut;
	}

	public void setFlagDernier(String flagDernier) {
		this.flagDernier = flagDernier;
		
		if(flagDernier.equals(Constantes.GlobalConstant.DECOMPTE_DEFINITIF_PARTIEL)){
			this.setDdPartiel(true);
		}
		if(flagDernier.equals(Constantes.GlobalConstant.DECOMPTE_DEFINITIF_GLOBAL)){
			this.setDdPartiel(true);
			this.setDdGlobal(true);
		}
	}

	public Double getMontantTtc() {
		if(null == montantTtc){
			return 0.0;
		}
		return montantTtc;
	}

	public void setMontantTtc(Double montantTtc) {
		this.montantTtc = montantTtc;
	}

	public Double getPenaliteRetard() {
		if(null == penaliteRetard){
			return 0.0;
		}
		return penaliteRetard;
	}

	public void setPenaliteRetard(Double penaliteRetard) {
		this.penaliteRetard = penaliteRetard;
	}

	public Double getRetenuAvance() {
		if(null == retenuAvance){
			return 0.0;
		}
		return retenuAvance;
	}

	public void setRetenuAvance(Double retenuAvance) {
		this.retenuAvance = retenuAvance;
	}

	public Double getRetenuGarantie() {
		if(null == retenuGarantie){
			return 0.0;
		}
		return retenuGarantie;
	}

	public void setRetenuGarantie(Double retenuGarantie) {
		this.retenuGarantie = retenuGarantie;
	}

	public Double getRetenueSource() {
		if(null == retenueSource){
			return 0.0;
		}
		return retenueSource;
	}

	public void setRetenueSource(Double retenueSource) {
		this.retenueSource = retenueSource;
	}

	public Double getRevisionPrix() {
		if(null == revisionPrix){
			return 0.0;
		}
		return revisionPrix;
	}

	public void setRevisionPrix(Double revisionPrix) {
		this.revisionPrix = revisionPrix;
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

	public Integer getIdPeriodeDecompte() {
		return idPeriodeDecompte;
	}

	public void setIdPeriodeDecompte(Integer idPeriodeDecompte) {
		this.idPeriodeDecompte = idPeriodeDecompte;
	}

	public Integer getIdMarche() {
		return idMarche;
	}

	public void setIdMarche(Integer idMarche) {
		this.idMarche = idMarche;
	}

	public boolean isDernier() {
		return dernier;
	}

	public void setDernier(boolean dernier) {
		this.dernier = dernier;
	}

	public Boolean getDdPartiel() {
		return ddPartiel;
	}

	public void setDdPartiel(Boolean ddPartiel) {
		this.ddPartiel = ddPartiel;
	}

	public Boolean getDdGlobal() {
		return ddGlobal;
	}

	public void setDdGlobal(Boolean ddGlobal) {
		this.ddGlobal = ddGlobal;
	}

	public Boolean getLastElement() {
		return lastElement;
	}

	public void setLastElement(Boolean lastElement) {
		this.lastElement = lastElement;
	}

	public Double getMontantTva() {
		if(null == montantTva){
			return 0.0;
		}
		return montantTva;
	}

	public void setMontantTva(Double montantTva) {
		this.montantTva = montantTva;
	}

	/**
	 * 
	 * Void Add
	 */
	public void AddMontantTtc(Double montantTtc){
		this.setMontantTtc(this.getMontantTtc()+montantTtc);
	}
	
	public void AddMontantTva(Double montantTva){
		this.setMontantTva(this.getMontantTva()+montantTva);
	}
	
	public void AddPenaliteRetard(Double penaliteRetard){
		this.setPenaliteRetard(this.getPenaliteRetard() +penaliteRetard);
	}
	
	public void AddRetenuAvance(Double retenuAvance){
		this.setRetenuAvance(this.getRetenuAvance()+retenuAvance);
	}
	
	public void AddRetenuGarantie(Double retenuGarantie){
		this.setRetenuGarantie(this.getRetenuGarantie()+retenuGarantie);
	}
	
	public void AddRetenueSource(Double retenueSource){
		this.setRetenueSource(this.getRetenueSource()+retenueSource);
	}
	
	public void AddRevisionPrix(Double revisionPrix){
		this.setRevisionPrix(this.getRevisionPrix()+revisionPrix);
	}
	
	
	
	
	
	
	
	
}