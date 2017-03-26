package ma.co.marsamaroc.gestion.decomptes.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

public class PeriodeDecompteDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Integer idPeriodeDecompte;
	private Date datCreat;
	private Date datModif;
	private Date dateDebut;
	private Date dateFin;
	private String flagDernier;
	private Integer indSupp;
	private String userCreat;
	private String userModif;
	private Set<DecompteDTO> decomptes;
	
	
	
	public Integer getIdPeriodeDecompte() {
		return idPeriodeDecompte;
	}
	public void setIdPeriodeDecompte(Integer idPeriodeDecompte) {
		this.idPeriodeDecompte = idPeriodeDecompte;
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
	public Set<DecompteDTO> getDecomptes() {
		return decomptes;
	}
	public void setDecomptes(Set<DecompteDTO> decomptes) {
		this.decomptes = decomptes;
	}
	
	
	

}
