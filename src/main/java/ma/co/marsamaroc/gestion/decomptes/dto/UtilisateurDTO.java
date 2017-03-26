package ma.co.marsamaroc.gestion.decomptes.dto;

import java.io.Serializable;
import java.util.Date;

public class UtilisateurDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer idUser;
	private String username;
	private String email;
	private String pwd;
	private String pwdClair;
	private String pwdReset;
	private String userCreat;
	private String userModif;
	private Date datCreat;
	private Date datModif;
	private Integer indSupp;
	
	
	public Integer getIdUser() {
		return idUser;
	}
	public void setIdUser(Integer idUser) {
		this.idUser = idUser;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getPwdClair() {
		return pwdClair;
	}
	public void setPwdClair(String pwdClair) {
		this.pwdClair = pwdClair;
	}
	public String getPwdReset() {
		return pwdReset;
	}
	public void setPwdReset(String pwdReset) {
		this.pwdReset = pwdReset;
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
}
