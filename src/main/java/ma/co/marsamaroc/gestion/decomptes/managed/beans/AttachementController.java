package ma.co.marsamaroc.gestion.decomptes.managed.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.primefaces.event.SelectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ma.co.marsamaroc.gestion.decomptes.dto.AttachementDTO;
import ma.co.marsamaroc.gestion.decomptes.dto.BonReceptionDTO;
import ma.co.marsamaroc.gestion.decomptes.dto.MarcheDTO;
import ma.co.marsamaroc.gestion.decomptes.services.interfaces.IAttachementService;
import ma.co.marsamaroc.gestion.decomptes.utils.Constantes;
import ma.co.marsamaroc.gestion.decomptes.utils.Utilitaires;

@Component
@Scope("session")
public class AttachementController implements Serializable{
	
	

	private static final long serialVersionUID = 1L;
	private final static Logger LOGGER = Logger.getLogger(AttachementController.class);
	
	/**
	 * 
	 * @Autowired
	 * 
	 */
	@Autowired
	private IAttachementService attachementService;
	
	@Autowired
	private MessageSource messageSource;
	
	
	//Marche
	private String codeMarche;
	private List<MarcheDTO> listMarchesDTO;
	private MarcheDTO selectMarcheDTO;
	
	
	// sequence 
	private String numSequence;
	
	
	// Attachement
	private String libFrn;
	private List<AttachementDTO> lisAttachements;
	private AttachementDTO selectAttachementDTO;
	private List<BonReceptionDTO> listBonReceps;	
	private BonReceptionDTO[] selectlistBonReceps;	
	private List<BonReceptionDTO> filteredBonReceps;	
	private AttachementDTO attachementDTO = new AttachementDTO();
	
	private Boolean listBonRecepsNotEmpty = false;
	private Boolean marcheIsFermeAttDernier = false;
	
	
	/**
	 * DESCRIPTION : Methode qui permet de récupérer la liste des marchés depuis  la base Apipro 
	 * 				 par code site
	 * DATE DERNIERE MODIF : 01 fév. 2017
	 * PROJET : Gestion Decompte
	 * AUTEUR : ZGUIOUAR
	 *
	 * @return
	 */
	public String searchListMarche() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
		try {
			initialiserMarche();
			String codeSite = (String) session.getAttribute(Constantes.GlobalConstant.CODE_SITE);
			listMarchesDTO = attachementService.wsGetListMarche(codeSite);
			if (CollectionUtils.isEmpty(listMarchesDTO)) {
				String msg = Utilitaires.getMessage(messageSource, "label.list.marche.site.vide", null);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, null, msg + " " + codeSite));
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, Constantes.GlobalConstant.ERREUR, e.getMessage()));
			LOGGER.error(e);
		}
		return Constantes.Attachement.Marche.ATT_MARCHE_SEARSH_PAGE;
	}
	
	/**
	 * DESCRIPTION : Methode qui permet de consulter les attachement d'un marché sélectionné 
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
			attachementService.verificationMarche(idSite, selectMarcheDTO);
			if (null != selectMarcheDTO.getIdMarche()) {
				lisAttachements = attachementService.findAttachementByIdMarche(selectMarcheDTO.getIdMarche());
			} 
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, Constantes.GlobalConstant.ERREUR, e.getMessage()));
			LOGGER.error(e);
		}
		return Constantes.Attachement.Marche.ATT_MARCHE_CONSULT_PAGE;
	}
	
	/**
	 * DESCRIPTION : Methode qui permet d'aller à la page de création attachement pour un  marché sélectionné
	 * DATE DERNIERE MODIF : 10 fév. 2017
	 * PROJET : Gestion Decompte
	 * AUTEUR : ZGUIOUAR
	 * 
	 * @return
	 */
	public String goToCreateAtt(){
		attachementDTO = new AttachementDTO();
		selectlistBonReceps = null; 
		filteredBonReceps =  null;
		listBonReceps = null;
		try {
			attachementDTO.setDateEtablissement(new Date());
			attachementDTO.setNumAttachement(attachementService.generateSequenceMarche(selectMarcheDTO.getIdMarche(), selectMarcheDTO.getTypeMarche()));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, Constantes.GlobalConstant.ERREUR, e.getMessage()));
			LOGGER.error(e);
			return goToConsultAtt();
		}
    	return Constantes.Attachement.Marche.ATT_MARCHE_ADD_PAGE;
	}
	
	/**
	 * DESCRIPTION : Methode qui permet de récupérer la liste des bons de reception par 
	 * 				numéro du marché, date debut, date fin	
	 * DATE DERNIERE MODIF : 10 fév. 2017
	 * PROJET : Gestion Decompte
	 * AUTEUR : ZGUIOUAR
	 * 
	 * @param actionEvent
	 * @return
	 */
	public void searchBnRecp(ActionEvent actionEvent) {
		filteredBonReceps =  null;
		selectlistBonReceps = null; 
		listBonReceps = null;
		String msg = null;
		try {
			if (StringUtils.isBlank(selectMarcheDTO.getNumMarche())) {
				msg = Utilitaires.getMessage(messageSource, "javax.faces.component.UIInput.num.marche.REQUIRED", null);
			}else if (null == attachementDTO.getDateDebut()) {
				msg = Utilitaires.getMessage(messageSource, "javax.faces.component.UIInput.date.debut.REQUIRED", null);
			}else if (null == attachementDTO.getDateFin()) {
				msg = Utilitaires.getMessage(messageSource, "javax.faces.component.UIInput.date.fin.REQUIRED", null);
			}else {
				listBonReceps = attachementService.wsGetListBonReceps(selectMarcheDTO.getIdMarche() ,selectMarcheDTO.getNumMarche(), attachementDTO.getDateDebut(), attachementDTO.getDateFin());
				if (CollectionUtils.isEmpty(listBonReceps)) {
					msg = Utilitaires.getMessage(messageSource, "label.list.bon.reception.vide", new Object[]{ attachementDTO.getDateDebutStr(), attachementDTO.getDateFinStr()});
				}
			}
			if (StringUtils.isNotBlank(msg)) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
		                FacesMessage.SEVERITY_ERROR, msg, null));
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, Constantes.GlobalConstant.ERREUR, e.getMessage()));
			LOGGER.error(e);
		}
	}
	
	/**
	 * DESCRIPTION : Methode qui permet de supprimer un attachement selectionné
	 * DATE DERNIERE MODIF : 10 fév. 2017
	 * PROJET : Gestion Decompte
	 * AUTEUR : ZGUIOUAR
	 * 
	 * @return
	 */
	public String deleteAttachement(String numAttachement){
		try {
			attachementService.deleteAttachementById(selectAttachementDTO.getIdAttachement());
			String msg = Utilitaires.getMessage(messageSource, "message.attachement.succes.delete", new Object[]{numAttachement});
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
	                FacesMessage.SEVERITY_INFO, msg, null));
			
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, Constantes.GlobalConstant.ERREUR, e.getMessage()));
			LOGGER.error(e);
		}
		return goToConsultAtt();
	}
	
	/**
	 * DESCRIPTION : Methode qui permet de consulter ou créer les attachement d'un marché sélectionné
	 * DATE DERNIERE MODIF : 10 fév. 2017
	 * PROJET : Gestion Decompte
	 * AUTEUR : ZGUIOUAR
	 * 
	 * @return
	 */
	public String creationAttachementMarche() {
		try {
			String numAttachement = attachementService.createAttachement(selectMarcheDTO, attachementDTO, Arrays.asList(selectlistBonReceps));
			String msg = Utilitaires.getMessage(messageSource, "message.attachement.succes.ajout", new Object[]{numAttachement});
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msg, null));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, Constantes.GlobalConstant.ERREUR, e.getMessage()));
			LOGGER.error(e);
			return Constantes.Attachement.Marche.ATT_MARCHE_ADD_PAGE;
		}
		return goToConsultAtt();
	}
	
	public void exportPdf(String type){
	}
	
	public void exportExcel(String type){
		try {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
			ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
			String codeSite = (String) session.getAttribute(Constantes.GlobalConstant.CODE_SITE);
			String codePort = (String) session.getAttribute(Constantes.GlobalConstant.CODE_PORT);
			String codeIso = (String) session.getAttribute(Constantes.GlobalConstant.CODE_ISO);
			String url = Utilitaires.getPathFolder(Constantes.GlobalConstant.ATTACHEMENT,
					Constantes.GlobalConstant.MARCHE, codePort, codeSite, servletContext.getRealPath("/"));
			String filePath = attachementService.exportExcel(selectAttachementDTO, selectMarcheDTO, codeSite, codeIso, type, url);
			Utilitaires.downloadFile(filePath, null);
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, Constantes.GlobalConstant.ERREUR, e.getMessage()));
			LOGGER.error(e);
		}
	}
	
	
	public String afficherAttachement(){
		try {
			listBonReceps = attachementService.findAllBonsRecepByIdAttachement(selectAttachementDTO.getIdAttachement());
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, Constantes.GlobalConstant.ERREUR, e.getMessage()));
			LOGGER.error(e);
			return Constantes.Attachement.Marche.ATT_MARCHE_ADD_PAGE;
		}
		return Constantes.Attachement.Marche.ATT_MARCHE_AFFICHER_PAGE;
	}
	
	
	
	/**
	 * DESCRIPTION : Methode qui permet de consulter ou créer les attachement d'un marché sélectionné
	 * DATE DERNIERE MODIF : 10 fév. 2017
	 * PROJET : Gestion Decompte
	 * AUTEUR : ZGUIOUAR
	 * 
	 * @return
	 */
	public void initialiserMarche(){
		codeMarche = null;
		listMarchesDTO = null;
		selectMarcheDTO = null;
		initialiserAttachement();
	}
	
	
	/**
	 * DESCRIPTION : Methode qui permet de consulter ou créer les attachement d'un marché sélectionné
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
	
	/**
	 * DESCRIPTION : Methode qui permet de consulter ou créer les attachement d'un marché sélectionné
	 * DATE DERNIERE MODIF : 10 fév. 2017
	 * PROJET : Gestion Decompte
	 * AUTEUR : ZGUIOUAR
	 * 
	 * @return
	 */
	public void calenderChangeListener(SelectEvent event) {
		initialiserListBnRecp();
	}
	
	/**
	 * DESCRIPTION : Methode qui permet de consulter ou créer les attachement d'un marché sélectionné
	 * DATE DERNIERE MODIF : 10 fév. 2017
	 * PROJET : Gestion Decompte
	 * AUTEUR : ZGUIOUAR
	 * 
	 * @return
	 */
	public void calenderChange(AjaxBehaviorEvent event) {
		initialiserListBnRecp();
	}
	
	/**
	 * DESCRIPTION : Methode qui permet de consulter ou créer les attachement d'un marché sélectionné
	 * DATE DERNIERE MODIF : 10 fév. 2017
	 * PROJET : Gestion Decompte
	 * AUTEUR : ZGUIOUAR
	 * 
	 * @return
	 */
	public void  initialiserListBnRecp(){
		filteredBonReceps =  null;
		selectlistBonReceps = null; 
		listBonReceps = new ArrayList<>();
	}

	
	public String getCodeMarche() {
		return codeMarche;
	}

	public void setCodeMarche(String codeMarche) {
		this.codeMarche = codeMarche;
	}

	public String getLibFrn() {
		return libFrn;
	}

	public void setLibFrn(String libFrn) {
		this.libFrn = libFrn;
	}

	public List<MarcheDTO> getListMarchesDTO() {
		return listMarchesDTO;
	}

	public void setListMarchesDTO(List<MarcheDTO> listMarchesDTO) {
		this.listMarchesDTO = listMarchesDTO;
	}

	public MarcheDTO getSelectMarcheDTO() {
		return selectMarcheDTO;
	}

	public void setSelectMarcheDTO(MarcheDTO selectMarcheDTO) {
		this.selectMarcheDTO = selectMarcheDTO;
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

	public IAttachementService getAttachementService() {
		return attachementService;
	}

	public void setAttachementService(IAttachementService attachementService) {
		this.attachementService = attachementService;
	}

	public MessageSource getMessageSource() {
		return messageSource;
	}

	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	public String getNumSequence() {
		return numSequence;
	}

	public void setNumSequence(String numSequence) {
		this.numSequence = numSequence;
	}

	public Boolean getListBonRecepsNotEmpty() {
		if (CollectionUtils.isNotEmpty(listBonReceps)) {
			return true;
		}
		return false;
	}
	
	public Boolean getMarcheIsFermeAttDernier() {
		if (CollectionUtils.isNotEmpty(lisAttachements) && null != selectMarcheDTO) {
			if (!Constantes.GlobalConstant.MARCHE_CADRE.equalsIgnoreCase(selectMarcheDTO.getTypeMarche())) {
				for (AttachementDTO att : lisAttachements) {
					if (Constantes.GlobalConstant.OUI_O.equalsIgnoreCase(att.getFlagDernier())) {
						return true ;
					}
				}
			}
		}
		return false;
	}

}
