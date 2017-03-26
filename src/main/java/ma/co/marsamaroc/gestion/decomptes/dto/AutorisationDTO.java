package ma.co.marsamaroc.gestion.decomptes.dto;

import java.io.Serializable;



public class AutorisationDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// Site
	private Integer idSite;
	private String codSite;
	private String libSite;
	private String codIso;
	
	
	// Terminal 
	private Integer idTerminal;
	private String codTerminal;
	private String libTerminal;
	
	// Port
	private Integer idPort;
	private String codPort;
	private String libPort;
	
	
	// Profile
	private Integer idProfil;
	private String codProfil;
	private String libProfil;
	
	
	
	
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
	public Integer getIdTerminal() {
		return idTerminal;
	}
	public void setIdTerminal(Integer idTerminal) {
		this.idTerminal = idTerminal;
	}
	public String getCodTerminal() {
		return codTerminal;
	}
	public void setCodTerminal(String codTerminal) {
		this.codTerminal = codTerminal;
	}
	public String getLibTerminal() {
		return libTerminal;
	}
	public void setLibTerminal(String libTerminal) {
		this.libTerminal = libTerminal;
	}
	public Integer getIdPort() {
		return idPort;
	}
	public void setIdPort(Integer idPort) {
		this.idPort = idPort;
	}
	public String getCodPort() {
		return codPort;
	}
	public void setCodPort(String codPort) {
		this.codPort = codPort;
	}
	public String getLibPort() {
		return libPort;
	}
	public void setLibPort(String libPort) {
		this.libPort = libPort;
	}
	public Integer getIdProfil() {
		return idProfil;
	}
	public void setIdProfil(Integer idProfil) {
		this.idProfil = idProfil;
	}
	public String getCodProfil() {
		return codProfil;
	}
	public void setCodProfil(String codProfil) {
		this.codProfil = codProfil;
	}
	public String getLibProfil() {
		return libProfil;
	}
	public void setLibProfil(String libProfil) {
		this.libProfil = libProfil;
	}

}