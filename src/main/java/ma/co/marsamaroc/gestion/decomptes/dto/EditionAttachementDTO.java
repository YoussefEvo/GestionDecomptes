package ma.co.marsamaroc.gestion.decomptes.dto;

import java.util.List;

public class EditionAttachementDTO {
	
	AttachementDTO attachementDTO;
	MarcheDTO MarcheDTO;
	List<BonReceptionDTO> listBonReception;
	String siteCode;
	String codeIso;
	String libPort;
	String typeEdition;
	
	public AttachementDTO getAttachementDTO() {
		return attachementDTO;
	}
	public void setAttachementDTO(AttachementDTO attachementDTO) {
		this.attachementDTO = attachementDTO;
	}
	public List<BonReceptionDTO> getListBonReception() {
		return listBonReception;
	}
	public void setListBonReception(List<BonReceptionDTO> listBonReception) {
		this.listBonReception = listBonReception;
	}
	public MarcheDTO getMarcheDTO() {
		return MarcheDTO;
	}
	public void setMarcheDTO(MarcheDTO marcheDTO) {
		MarcheDTO = marcheDTO;
	}
	public String getSiteCode() {
		return siteCode;
	}
	public void setSiteCode(String siteCode) {
		this.siteCode = siteCode;
	}
	public String getCodeIso() {
		return codeIso;
	}
	public void setCodeIso(String codeIso) {
		this.codeIso = codeIso;
	}
	public String getLibPort() {
		return libPort;
	}
	public void setLibPort(String libPort) {
		this.libPort = libPort;
	}
	public String getTypeEdition() {
		return typeEdition;
	}
	public void setTypeEdition(String typeEdition) {
		this.typeEdition = typeEdition;
	}
	
	
}
