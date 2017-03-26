package ma.co.marsamaroc.gestion.decomptes.dto;

import java.io.Serializable;
import java.util.Date;

public class ProfilDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer idProfil;
	private String codProfil;
	private String libProfil;
	private String userCreat;
	private String userModif;
	private Date datCreat;
	private Date datModif;
	
	// UTILISATEUR
	private String username;
	
	// SITE
	private String codSite;

	public ProfilDTO() {
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getCodSite() {
		return codSite;
	}

	public void setCodSite(String codSite) {
		this.codSite = codSite;
	}
	
	

}