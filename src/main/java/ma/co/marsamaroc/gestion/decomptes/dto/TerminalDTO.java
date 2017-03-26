package ma.co.marsamaroc.gestion.decomptes.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


public class TerminalDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer idTerminal;
	private String codTerminal;
	private String libTerminal;
	private String userCreat;
	private Date datCreat;
	private List<SiteDTO> listSite;
	
	
	// PORT
	private String codPort;

	public TerminalDTO() {
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

	public String getUserCreat() {
		return userCreat;
	}

	public void setUserCreat(String userCreat) {
		this.userCreat = userCreat;
	}

	public Date getDatCreat() {
		return datCreat;
	}

	public void setDatCreat(Date datCreat) {
		this.datCreat = datCreat;
	}

	public String getCodPort() {
		return codPort;
	}

	public void setCodPort(String codPort) {
		this.codPort = codPort;
	}

	public List<SiteDTO> getListSite() {
		return listSite;
	}

	public void setListSite(List<SiteDTO> listSite) {
		this.listSite = listSite;
	}
	
}