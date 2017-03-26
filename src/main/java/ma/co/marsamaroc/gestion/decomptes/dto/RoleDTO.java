package ma.co.marsamaroc.gestion.decomptes.dto;

import java.io.Serializable;
import java.util.Date;


public class RoleDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer idRole;
	private String codRole;
	private Date datCreat;
	private String libRole;
	private Integer numOrdre;
	private String userCreat;

	public RoleDTO() {
	}

	public Integer getIdRole() {
		return idRole;
	}

	public void setIdRole(Integer idRole) {
		this.idRole = idRole;
	}

	public String getCodRole() {
		return codRole;
	}

	public void setCodRole(String codRole) {
		this.codRole = codRole;
	}

	public Date getDatCreat() {
		return datCreat;
	}

	public void setDatCreat(Date datCreat) {
		this.datCreat = datCreat;
	}

	public String getLibRole() {
		return libRole;
	}

	public void setLibRole(String libRole) {
		this.libRole = libRole;
	}

	public Integer getNumOrdre() {
		return numOrdre;
	}

	public void setNumOrdre(Integer numOrdre) {
		this.numOrdre = numOrdre;
	}

	public String getUserCreat() {
		return userCreat;
	}

	public void setUserCreat(String userCreat) {
		this.userCreat = userCreat;
	}
	
	

}