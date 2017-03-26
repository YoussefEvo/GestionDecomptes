package ma.co.marsamaroc.gestion.decomptes.dto;

import java.io.Serializable;
import java.util.Date;


public class SiteDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer idSite;
	private String codSite;
	private String libSite;
	private String codIso;
	private Integer indSupp;
	private String userCreat;
	private String userModif;
	private Date datCreat;
	private Date datModif;
	// TERMINAL
	private String codTerminal;
	// PROFILE
	private Integer idProfil;

	public SiteDTO() {
	}

	public Integer getIdSite() {
		return idSite;
	}

	public void setIdSite(Integer idSite) {
		this.idSite = idSite;
	}

	public String getCodSite() {
		return codSite;
	}

	public void setCodSite(String codSite) {
		this.codSite = codSite;
	}

	public String getLibSite() {
		return libSite;
	}

	public void setLibSite(String libSite) {
		this.libSite = libSite;
	}

	public String getCodIso() {
		return codIso;
	}

	public void setCodIso(String codIso) {
		this.codIso = codIso;
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

	public String getCodTerminal() {
		return codTerminal;
	}

	public void setCodTerminal(String codTerminal) {
		this.codTerminal = codTerminal;
	}

	public Integer getIdProfil() {
		return idProfil;
	}

	public void setIdProfil(Integer idProfil) {
		this.idProfil = idProfil;
	}
	
}