package ma.co.marsamaroc.gestion.decomptes.managed.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ma.co.marsamaroc.gestion.decomptes.dto.AttachementDTO;
import ma.co.marsamaroc.gestion.decomptes.dto.BonCommandeDTO;
import ma.co.marsamaroc.gestion.decomptes.dto.BonReceptionDTO;
import ma.co.marsamaroc.gestion.decomptes.services.interfaces.IBonCommandeService;
import ma.co.marsamaroc.gestion.decomptes.utils.Constantes;
import ma.co.marsamaroc.gestion.decomptes.utils.Utilitaires;

@Component
@Scope("session")
public class AttachementBnCmdController implements Serializable{
	
	

	private static final long serialVersionUID = 1L;
	private final static Logger LOGGER = Logger.getLogger(AttachementBnCmdController.class);
	
	/**
	 * 
	 * @Autowired
	 * 
	 */
	@Autowired
	private IBonCommandeService bonCommandeService;
	
	@Autowired
	private MessageSource messageSource;
	
	
	// Bon de commande
	private String codeBonCommande;
	private List<BonCommandeDTO> listBonCommandeDTO;
	private BonCommandeDTO selectBonCommandeDTO;
	
	// Attachement
	private String libFrn;
	private List<AttachementDTO> lisAttachements;
	private AttachementDTO selectAttachementDTO;
	private List<BonReceptionDTO> listBonReceps;	
	private BonReceptionDTO[] selectlistBonReceps;	
	private List<BonReceptionDTO> filteredBonReceps;	
	private AttachementDTO attachementDTO = new AttachementDTO();
	
	/**
	 * DESCRIPTION : Methode qui permet de récupérer la liste des marchés depuis  la base Apipro 
	 * 				 par code site
	 * DATE DERNIERE MODIF : 01 fév. 2017
	 * PROJET : Gestion Decompte
	 * AUTEUR : ZGUIOUAR
	 *
	 * @return
	 */
	public String searchListBonCmd() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
		try {
			initialiserBonCmd();
			String codeSite = (String) session.getAttribute(Constantes.GlobalConstant.CODE_SITE);
			listBonCommandeDTO = bonCommandeService.wsGetListBonCommandeDTO(codeSite);
			if (CollectionUtils.isEmpty(listBonCommandeDTO)) {
				String msg = Utilitaires.getMessage(messageSource, "label.list.marche.site.vide", null);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, null, msg + " " + codeSite));
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, Constantes.GlobalConstant.ERREUR, e.getMessage()));
			LOGGER.error(e);
		}
		return Constantes.Attachement.BonCommande.ATT_BN_CMD_SEARSH_PAGE;
	}
	
	/**
	 * DESCRIPTION : Methode qui permet de consulter les attachement d'un bon de commande sélectionné 
	 * DATE DERNIERE MODIF : 10 fév. 2017
	 * PROJET : Gestion Decompte
	 * AUTEUR : ZGUIOUAR
	 * 
	 * @return
	 */
	public String goToConsultAtt() {
		lisAttachements = new ArrayList<>();
		try {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
			Integer idSite  = (Integer) session.getAttribute(Constantes.GlobalConstant.ID_SITE);
			bonCommandeService.verificationMarche(idSite, selectBonCommandeDTO);
			if (null != selectBonCommandeDTO.getIdBonCommande()) {
				lisAttachements = bonCommandeService.findAttachementByIdCommande(selectBonCommandeDTO.getIdBonCommande());
			} 
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, Constantes.GlobalConstant.ERREUR, e.getMessage()));
			LOGGER.error(e);
		}
		return Constantes.Attachement.BonCommande.ATT_BN_CMD_CONSULT_PAGE;
	}
	
	/**
	 * DESCRIPTION : Methode qui permet de consulter ou créer les attachement d'un bon de commande sélectionné
	 * DATE DERNIERE MODIF : 10 fév. 2017
	 * PROJET : Gestion Decompte
	 * AUTEUR : ZGUIOUAR
	 * 
	 * @return
	 */
	public void initialiserBonCmd(){
		codeBonCommande = null;
		listBonCommandeDTO = null;
		selectBonCommandeDTO = null;
		initialiserAttachement();
	}
	
	
	/**
	 * DESCRIPTION : Methode qui permet de consulter ou créer les attachement d'un bon de commande sélectionné
	 * DATE DERNIERE MODIF : 10 fév. 2017
	 * PROJET : Gestion Decompte
	 * AUTEUR : ZGUIOUAR
	 * 
	 * @return
	 */
	public void initialiserAttachement(){
		libFrn = null;
		selectAttachementDTO = null;
		lisAttachements = null;
		listBonReceps = null;
		attachementDTO = null;
		selectlistBonReceps = null; 
		filteredBonReceps =  null;
	}
	
	
	public String getLibFrn() {
		return libFrn;
	}

	public void setLibFrn(String libFrn) {
		this.libFrn = libFrn;
	}

	public List<AttachementDTO> getLisAttachements() {
		return lisAttachements;
	}

	public void setLisAttachements(List<AttachementDTO> lisAttachements) {
		this.lisAttachements = lisAttachements;
	}

	public AttachementDTO getSelectAttachementDTO() {
		return selectAttachementDTO;
	}

	public void setSelectAttachementDTO(AttachementDTO selectAttachementDTO) {
		this.selectAttachementDTO = selectAttachementDTO;
	}

	public List<BonReceptionDTO> getListBonReceps() {
		return listBonReceps;
	}

	public void setListBonReceps(List<BonReceptionDTO> listBonReceps) {
		this.listBonReceps = listBonReceps;
	}

	public AttachementDTO getAttachementDTO() {
		return attachementDTO;
	}

	public void setAttachementDTO(AttachementDTO attachementDTO) {
		this.attachementDTO = attachementDTO;
	}

	public BonReceptionDTO[] getSelectlistBonReceps() {
		return selectlistBonReceps;
	}


	public void setSelectlistBonReceps(BonReceptionDTO[] selectlistBonReceps) {
		this.selectlistBonReceps = selectlistBonReceps;
	}


	public List<BonReceptionDTO> getFilteredBonReceps() {
		return filteredBonReceps;
	}


	public void setFilteredBonReceps(List<BonReceptionDTO> filteredBonReceps) {
		this.filteredBonReceps = filteredBonReceps;
	}

	public String getCodeBonCommande() {
		return codeBonCommande;
	}

	public void setCodeBonCommande(String codeBonCommande) {
		this.codeBonCommande = codeBonCommande;
	}

	public List<BonCommandeDTO> getListBonCommandeDTO() {
		return listBonCommandeDTO;
	}

	public void setListBonCommandeDTO(List<BonCommandeDTO> listBonCommandeDTO) {
		this.listBonCommandeDTO = listBonCommandeDTO;
	}

	public BonCommandeDTO getSelectBonCommandeDTO() {
		return selectBonCommandeDTO;
	}

	public void setSelectBonCommandeDTO(BonCommandeDTO selectBonCommandeDTO) {
		this.selectBonCommandeDTO = selectBonCommandeDTO;
	}


	public IBonCommandeService getBonCommandeService() {
		return bonCommandeService;
	}


	public void setBonCommandeService(IBonCommandeService bonCommandeService) {
		this.bonCommandeService = bonCommandeService;
	}


	public MessageSource getMessageSource() {
		return messageSource;
	}


	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
}
