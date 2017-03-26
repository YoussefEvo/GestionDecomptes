package ma.co.marsamaroc.gestion.decomptes.dto;

import java.util.Date;
import java.util.Set;

public class ParamWsApiproDTO {
	
	private String codeMarche;
	private String libFourbisseur;
	private String CodeSite;
	
	private Date dateEtablissement;
	private Date dateDebut;
	private Date dateFin;
	
	private Set<String> listBnRecpIgnore;
	
	public String getCodeMarche() {
		return codeMarche;
	}
	public void setCodeMarche(String codeMarche) {
		this.codeMarche = codeMarche;
	}
	public String getLibFourbisseur() {
		return libFourbisseur;
	}
	public void setLibFourbisseur(String libFourbisseur) {
		this.libFourbisseur = libFourbisseur;
	}
	public String getCodeSite() {
		return CodeSite;
	}
	public void setCodeSite(String codeSite) {
		CodeSite = codeSite;
	}
	public Date getDateEtablissement() {
		return dateEtablissement;
	}
	public void setDateEtablissement(Date dateEtablissement) {
		this.dateEtablissement = dateEtablissement;
	}
	public Date getDateDebut() {
		return dateDebut;
	}
	public void setDateDebut(Date dateDebut) {
		this.dateDebut = dateDebut;
	}
	public Date getDateFin() {
		return dateFin;
	}
	public void setDateFin(Date dateFin) {
		this.dateFin = dateFin;
	}
	public Set<String> getListBnRecpIgnore() {
		return listBnRecpIgnore;
	}
	public void setListBnRecpIgnore(Set<String> listBnRecpIgnore) {
		this.listBnRecpIgnore = listBnRecpIgnore;
	}
	
	
	
	

	
}
