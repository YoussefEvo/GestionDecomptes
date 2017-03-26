package ma.co.marsamaroc.gestion.decomptes.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;



public class PortDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer idPort;
	private String codPort;
	private String libPort;
	private String userCreat;
	private Date datCreat;
	private List<TerminalDTO> listTerminal;

	public PortDTO() {
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

	public List<TerminalDTO> getListTerminal() {
		return listTerminal;
	}

	public void setListTerminal(List<TerminalDTO> listTerminal) {
		this.listTerminal = listTerminal;
	}

}