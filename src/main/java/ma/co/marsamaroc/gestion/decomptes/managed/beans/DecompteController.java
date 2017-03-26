package ma.co.marsamaroc.gestion.decomptes.managed.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ma.co.marsamaroc.gestion.decomptes.dto.AttachementDTO;
import ma.co.marsamaroc.gestion.decomptes.dto.BonReceptionDTO;
import ma.co.marsamaroc.gestion.decomptes.dto.BordereauPrixDTO;
import ma.co.marsamaroc.gestion.decomptes.dto.DecompteDTO;
import ma.co.marsamaroc.gestion.decomptes.dto.MarcheDTO;
import ma.co.marsamaroc.gestion.decomptes.dto.PrintDecompteDTO;
import ma.co.marsamaroc.gestion.decomptes.edition.excel.DecompteExcel;
import ma.co.marsamaroc.gestion.decomptes.services.interfaces.IAttachementService;
import ma.co.marsamaroc.gestion.decomptes.services.interfaces.IBonReceptionService;
import ma.co.marsamaroc.gestion.decomptes.services.interfaces.IBordereauPrixService;
import ma.co.marsamaroc.gestion.decomptes.services.interfaces.IDecompteService;
import ma.co.marsamaroc.gestion.decomptes.services.interfaces.IMarcheService;
import ma.co.marsamaroc.gestion.decomptes.utils.Constantes;
import ma.co.marsamaroc.gestion.decomptes.utils.Utilitaires;

@Component
@Scope("session")
public class DecompteController implements Serializable {

	private static final long serialVersionUID = 1L;
	private final static Logger LOGGER = Logger.getLogger(DecompteController.class);
	
	private List<MarcheDTO> listMarches;
	private List<DecompteDTO> listDecomptes;
	private List<AttachementDTO> listAttachements;
	private List<AttachementDTO> listSelectedAttachements;
	private DecompteDTO decompte;
	private MarcheDTO selectedMarche;
	private boolean showMarcheTable;
	
	/**
	 * 
	 * @Autowired
	 * 
	 */
	@Autowired
	private IMarcheService marcheService;
	@Autowired
	private IDecompteService decompteService;
	@Autowired
	private IAttachementService attachementService;
	@Autowired
	private IBonReceptionService bonReceptionService;
	@Autowired
	private IBordereauPrixService bordereauPrixService;
	@Autowired
	private MessageSource messageSource;
	
	
	/**
	 * DESCRIPTION : GET URL  DECOMPTE_SEARSH_MARCHE_PAGE
	 * DATE DERNIERE MODIF : 26 Jan. 2017
	 * PROJET : Gestion Decompte
	 * AUTEUR : LABAL
	 *
	 * @return
	 */
	public String start() {
		showMarcheTable = false;
		try {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
			Integer idSite = (Integer) session.getAttribute(Constantes.GlobalConstant.ID_SITE);
			listMarches = marcheService.findMarches(idSite);
			if(CollectionUtils.isEmpty(listMarches)){
				String msg = Utilitaires.getMessage(messageSource, "message.site.list.marche.vide", null);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,  Constantes.GlobalConstant.ALERT,  msg ));
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,  Constantes.GlobalConstant.ERREUR,  e.getMessage() ));
			LOGGER.error(e);
		}
		return Constantes.Decompte.GEST_MARCHE_DECOMPTE_SEARSH_MARCHE_PAGE;
	}
	
	/**
	 * DESCRIPTION : GET URL PAGE CONSULTER DECOMPTES
	 * DATE DERNIERE MODIF : 01 Fév. 2017
	 * PROJET : Gestion Decompte
	 * AUTEUR : LABAL
	 *
	 * @return
	 */
	public String startConsulterDecomptes() {
		try {
			if(null != selectedMarche){
				listDecomptes = decompteService.findDecomptesByidMarche(selectedMarche.getIdMarche());
				if(CollectionUtils.isNotEmpty(listDecomptes)){
					listDecomptes.get(listDecomptes.size()-1).setLastElement(true);
				}
			}else{
				listDecomptes = new ArrayList<>();
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,  Constantes.GlobalConstant.ERREUR,  e.getMessage() ));
			LOGGER.error(e);
		}
		return Constantes.Decompte.GEST_MARCHE_VIEW_DECOMPTES_PAGE;
	}
	
	/**
	 * DESCRIPTION : GET URL PAGE CONSULTER DECOMPTE
	 * DATE DERNIERE MODIF : 02 Fév. 2017
	 * PROJET : Gestion Decompte
	 * AUTEUR : LABAL
	 *
	 * @return
	 */
	public String startConsulterDecompte() {
		try {
			if(null != decompte){
				listAttachements = attachementService.findAttachementsByidDecompte(decompte.getIdDecompte());
			}
			
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,  Constantes.GlobalConstant.ERREUR,  e.getMessage() ));
			LOGGER.error(e);
		}
		return Constantes.Decompte.GEST_MARCHE_VIEW_DECOMPTE_PAGE;
	}

	/**
	 * DESCRIPTION : GET URL PAGE MODIFIER DECOMPTE
	 * DATE DERNIERE MODIF : 01 Fév. 2017
	 * PROJET : Gestion Decompte
	 * AUTEUR : LABAL
	 *
	 * @return
	 */
	public String startAjouterDecompte() {
		try {
		    listSelectedAttachements = new ArrayList<>();
		    decompte = new DecompteDTO();
			decompte.setRetenuGarantie(0.0);
			decompte.setPenaliteRetard(0.0);
			decompte.setRetenuAvance(0.0);
			decompte.setRevisionPrix(0.0);
			decompte.setAutresRetenues(0.0);
			decompte.setRetenueSource(0.0);
			decompte.setDateEtablissement(new Date());
			
			listAttachements = attachementService.findAttachementsNonDecompteByidMarche(selectedMarche.getIdMarche());
	
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,  Constantes.GlobalConstant.ERREUR,  e.getMessage() ));
			LOGGER.error(e);
		}
		return Constantes.Decompte.GEST_MARCHE_ADD_DECOMPTE_PAGE;
	}  
	
	
	/**
	 * DESCRIPTION : AJOUTER DECOMPTE
	 * DATE DERNIERE MODIF : 07 Fév. 2017
	 * PROJET : Gestion Decompte
	 * AUTEUR : LABAL
	 *
	 * @return
	 */
	public String ajouterDecompte() {
		try {
			if(null != selectedMarche && null != decompte && CollectionUtils.isNotEmpty(listSelectedAttachements)){
				String numero;
				Integer year = 0;
				Integer sequence = 0;
				decompte.setIdMarche(selectedMarche.getIdMarche());
				//Calculer NumDecompte
				if(CollectionUtils.isNotEmpty(listDecomptes)){
					DecompteDTO lastDecompte =  listDecomptes.get(listDecomptes.size()-1);
					numero = lastDecompte.getNumDecompte();
					if(numero.matches("\\d+/\\d+")){
						sequence =  Integer.parseInt(numero.split("/")[0]);
						year = Integer.parseInt(numero.split("/")[1]);
						if(lastDecompte.getFlagDernier().equals(Constantes.GlobalConstant.DECOMPTE_DEFINITIF_PARTIEL)){
							decompte.setNumDecompte("01/"+(year+1));
						}else{
							if(sequence<10){
								decompte.setNumDecompte("0"+(sequence+1)+"/"+year);
							}else{
								decompte.setNumDecompte((sequence+1)+"/"+year);	
							}
						}
					}
				}else{
					if(CollectionUtils.isNotEmpty(listSelectedAttachements)){
						for (AttachementDTO attachementDTO : listSelectedAttachements) {	
							numero = attachementDTO.getNumAttachement();
							if(numero.matches("\\d+/\\d+")){
								if(year<Integer.parseInt(numero.split("/")[1])){
									year= Integer.parseInt(numero.split("/")[1]);
								}
							}
						}
						decompte.setNumDecompte("01/"+year);
					}
				}
				decompteService.createDecompte(decompte, listSelectedAttachements);
				
				if(null != decompte.getDdGlobal() && decompte.getDdGlobal()){
					marcheService.clotureMarche(decompte.getIdMarche());
					selectedMarche.setFlagCloture(Constantes.GlobalConstant.OUI_O);
				}
				
				String msg = messageSource.getMessage("message.succes.ajout", null, Constantes.GlobalConstant.DEFAULT, Utilitaires.getCurrentLocal());
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, null, msg ));
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,  Constantes.GlobalConstant.ERREUR,  e.getMessage() ));
			LOGGER.error(e);
		}
		
		return startConsulterDecomptes();
	}
	
	/**
	 * DESCRIPTION : SUPPRIMER DECOMPTE
	 * DATE DERNIERE MODIF : 07 Fév. 2017
	 * PROJET : Gestion Decompte
	 * AUTEUR : LABAL
	 *
	 * @return
	 */
	public void supprimerDecompte() {
		try {
			decompteService.deleteDecompte(decompte);
			if(decompte.getFlagDernier().equals(Constantes.GlobalConstant.DECOMPTE_DEFINITIF_GLOBAL)){
				selectedMarche.setFlagCloture(Constantes.GlobalConstant.NON_N);
				marcheService.libereMarche(decompte.getIdMarche());
			}
			startConsulterDecomptes();
			String msg = messageSource.getMessage("message.succes.delete", null, Constantes.GlobalConstant.DEFAULT, Utilitaires.getCurrentLocal());
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, null, msg ));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,  Constantes.GlobalConstant.ERREUR,  e.getMessage() ));
			LOGGER.error(e);
		}
	}
	
	/**
	 * DESCRIPTION : EDITER DECOMPTE PROVISOIRE
	 * DATE DERNIERE MODIF : 07 Fév. 2017
	 * PROJET : Gestion Decompte
	 * AUTEUR : LABAL
	 *
	 * @return
	 */
public void editerDecompteProvisoire() {
	try {
			Map<String, BonReceptionDTO> listBrDecompte = new HashMap<>();
			Map<String, BonReceptionDTO> listPreviousBrDecompte = new HashMap<>();
			List<BordereauPrixDTO> listBP = new ArrayList<>();
			List<BordereauPrixDTO> dataMarche = new ArrayList<>();
			List<Integer> listIdsDecomptes = new ArrayList<>();
			List<String> listDesigniation = new ArrayList<>(); 
			String desig;
		
			PrintDecompteDTO printDecompteDTO = new PrintDecompteDTO();
			
			printDecompteDTO.setVille(getVille());
			
			//get Brs décompte
			listIdsDecomptes.add(decompte.getIdDecompte());
			listBrDecompte =bonReceptionService.findBonReceptionsByidsDecompte(listIdsDecomptes);
					
			//get Brs Anciens décompte
			if(CollectionUtils.isNotEmpty(listDecomptes)){
				listIdsDecomptes = new ArrayList<>();
				for (DecompteDTO decompteDTO : listDecomptes) {
					if(decompteDTO.getIdDecompte().equals(decompte.getIdDecompte())){
						break;
					}
					listIdsDecomptes.add(decompteDTO.getIdDecompte());
					printDecompteDTO.getDecompteAncienCumul().AddPenaliteRetard(decompteDTO.getPenaliteRetard());
					printDecompteDTO.getDecompteAncienCumul().AddRetenuAvance(decompteDTO.getRetenuAvance());
					printDecompteDTO.getDecompteAncienCumul().AddRetenuGarantie(decompteDTO.getRetenuGarantie());
					printDecompteDTO.getDecompteAncienCumul().AddRetenueSource(decompteDTO.getRetenueSource());
					printDecompteDTO.getDecompteAncienCumul().AddRevisionPrix(decompteDTO.getRevisionPrix());
				}
				if(CollectionUtils.isNotEmpty(listIdsDecomptes)){
					listPreviousBrDecompte =bonReceptionService.findBonReceptionsByidsDecompte(listIdsDecomptes);	
				}
			}
			
			listDesigniation.addAll(listBrDecompte.keySet());	
			listDesigniation.addAll(listPreviousBrDecompte.keySet());
			listBP = bordereauPrixService.findBordereauPrixByDesigniations(listDesigniation);
			
			//Regroupement des données
			if(CollectionUtils.isNotEmpty(listBP)){
				for (BordereauPrixDTO bPDTO : listBP) {
					desig = bPDTO.getDesigniation();
					if(listBrDecompte.containsKey(desig)){
						bPDTO.setQteDecompte(listBrDecompte.get(desig).getQte());
						bPDTO.setTva(listBrDecompte.get(desig).getTva());
					}
					if(listPreviousBrDecompte.containsKey(desig)){
						bPDTO.setQteAncienCumul(listPreviousBrDecompte.get(desig).getQte());
						bPDTO.setTva(listPreviousBrDecompte.get(desig).getTva());
					}
					
					dataMarche.add(bPDTO);
				}
			}		
			
			//Remplissage printDecompteDTO
			printDecompteDTO.setDecompte(decompte);
			printDecompteDTO.setMarche(selectedMarche);
			printDecompteDTO.setDataMarche(dataMarche);
			
			String XLSX_EXT = Constantes.GlobalConstant.XLSX_EXTENSION;
			String FILE_NAME = getUrl()+"DecompteProvisoire"+Calendar.getInstance().getTimeInMillis()+XLSX_EXT;

			//Print Decompte
			DecompteExcel decompteExcel = new DecompteExcel(printDecompteDTO);
			String filePath =decompteExcel.printDecompteProviore(FILE_NAME);
			String fileName ="Decompte"+decompte.getNumDecompte()+XLSX_EXT;
			Utilitaires.downloadFile(filePath,fileName);
			
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,  Constantes.GlobalConstant.ERREUR, e.getMessage()));
			LOGGER.error(e);
		}
	}
	

	/**
	 * DESCRIPTION : EDITER DECOMPTE PROVISOIRE
	 * DATE DERNIERE MODIF : 07 Fév. 2017
	 * PROJET : Gestion Decompte
	 * AUTEUR : LABAL
	 *
	 * @return
	 */
	public void editerDecomptePartiel() {
	try {
		Map<String, BonReceptionDTO> listBrDecompte = new HashMap<>();
		Map<String, BonReceptionDTO> listPreviousBrDecompte = new HashMap<>();
		List<BordereauPrixDTO> listBP = new ArrayList<>();
		List<BordereauPrixDTO> dataMarche = new ArrayList<>();
		List<Integer> listIdsDecomptes = new ArrayList<>();
		List<String> listDesigniation = new ArrayList<>(); 
		String desig;
	
		PrintDecompteDTO printDecompteDTO = new PrintDecompteDTO();
		
		printDecompteDTO.setVille(getVille());
		
		//get Brs décompte
		listIdsDecomptes.add(decompte.getIdDecompte());
		listBrDecompte =bonReceptionService.findBonReceptionsByidsDecompte(listIdsDecomptes);
				
		//get Brs Anciens décompte
		if(CollectionUtils.isNotEmpty(listDecomptes)){
			listIdsDecomptes = new ArrayList<>();
			String year,yearDecomte;
			year = decompte.getNumDecompte().split("/")[1];
			for (DecompteDTO decompteDTO : listDecomptes) {
				yearDecomte = decompteDTO.getNumDecompte().split("/")[1];
				if(year.equals(yearDecomte)){
					listIdsDecomptes.add(decompteDTO.getIdDecompte());
					printDecompteDTO.getDecompteAncienCumul().AddPenaliteRetard(decompteDTO.getPenaliteRetard());
					printDecompteDTO.getDecompteAncienCumul().AddRetenuAvance(decompteDTO.getRetenuAvance());
					printDecompteDTO.getDecompteAncienCumul().AddRetenuGarantie(decompteDTO.getRetenuGarantie());
					printDecompteDTO.getDecompteAncienCumul().AddRetenueSource(decompteDTO.getRetenueSource());
					printDecompteDTO.getDecompteAncienCumul().AddRevisionPrix(decompteDTO.getRevisionPrix());
				}
			}
			if(CollectionUtils.isNotEmpty(listIdsDecomptes)){
				listPreviousBrDecompte =bonReceptionService.findBonReceptionsByidsDecompte(listIdsDecomptes);	
			}
		}
		
		listDesigniation.addAll(listBrDecompte.keySet());	
		listDesigniation.addAll(listPreviousBrDecompte.keySet());
		listBP = bordereauPrixService.findBordereauPrixByDesigniations(listDesigniation);
		
		//Regroupement des données
		if(CollectionUtils.isNotEmpty(listBP)){
			for (BordereauPrixDTO bPDTO : listBP) {
				desig = bPDTO.getDesigniation();
				if(listBrDecompte.containsKey(desig)){
					bPDTO.setQteDecompte(listBrDecompte.get(desig).getQte());
					bPDTO.setTva(listBrDecompte.get(desig).getTva());
				}
				if(listPreviousBrDecompte.containsKey(desig)){
					bPDTO.setQteAncienCumul(listPreviousBrDecompte.get(desig).getQte());
					bPDTO.setTva(listPreviousBrDecompte.get(desig).getTva());
				}
				
				dataMarche.add(bPDTO);
			}
		}		
		
		//Remplissage printDecompteDTO
		printDecompteDTO.setDecompte(decompte);
		printDecompteDTO.setMarche(selectedMarche);
		printDecompteDTO.setDataMarche(dataMarche);
		
		String XLSX_EXT = Constantes.GlobalConstant.XLSX_EXTENSION;
		String FILE_NAME = getUrl()+"DecomptePartiel"+Calendar.getInstance().getTimeInMillis()+XLSX_EXT;

		//Print Decompte
		DecompteExcel decompteExcel = new DecompteExcel(printDecompteDTO);
		String filePath =decompteExcel.printDecomptePartiel(FILE_NAME);
		String fileName ="Decompte"+decompte.getNumDecompte()+XLSX_EXT;
		Utilitaires.downloadFile(filePath,fileName);
		
	} catch (Exception e) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,  Constantes.GlobalConstant.ERREUR, e.getMessage()));
		LOGGER.error(e);
	}
}


	/**
	 * DESCRIPTION : EDITER DECOMPTE DEFINITIF
	 * DATE DERNIERE MODIF : 24 Fév. 2017
	 * PROJET : Gestion Decompte
	 * AUTEUR : LABAL
	 *
	 * @return
	 */
	public void editerDecompteDefinitif() {
	try {
		Map<String, BonReceptionDTO> listBrDecompte = new HashMap<>();
		Map<String, BonReceptionDTO> listPreviousBrDecompte = new HashMap<>();
		List<BordereauPrixDTO> listBP = new ArrayList<>();
		List<BordereauPrixDTO> dataMarche = new ArrayList<>();
		List<Integer> listIdsDecomptes = new ArrayList<>();
		List<String> listDesigniation = new ArrayList<>(); 
		String desig;
	
		PrintDecompteDTO printDecompteDTO = new PrintDecompteDTO();
		
		printDecompteDTO.setVille(getVille());
		
		//get Brs décompte
		listIdsDecomptes.add(decompte.getIdDecompte());
		listBrDecompte =bonReceptionService.findBonReceptionsByidsDecompte(listIdsDecomptes);
				
		//get Brs Anciens décompte
		if(CollectionUtils.isNotEmpty(listDecomptes)){
			listIdsDecomptes = new ArrayList<>();
			for (DecompteDTO decompteDTO : listDecomptes) {
					listIdsDecomptes.add(decompteDTO.getIdDecompte());
					printDecompteDTO.getDecompteAncienCumul().AddPenaliteRetard(decompteDTO.getPenaliteRetard());
					printDecompteDTO.getDecompteAncienCumul().AddRetenuAvance(decompteDTO.getRetenuAvance());
					printDecompteDTO.getDecompteAncienCumul().AddRetenuGarantie(decompteDTO.getRetenuGarantie());
					printDecompteDTO.getDecompteAncienCumul().AddRetenueSource(decompteDTO.getRetenueSource());
					printDecompteDTO.getDecompteAncienCumul().AddRevisionPrix(decompteDTO.getRevisionPrix());
			}
			if(CollectionUtils.isNotEmpty(listIdsDecomptes)){
				listPreviousBrDecompte =bonReceptionService.findBonReceptionsByidsDecompte(listIdsDecomptes);	
			}
		}
		
		listDesigniation.addAll(listBrDecompte.keySet());	
		listDesigniation.addAll(listPreviousBrDecompte.keySet());
		listBP = bordereauPrixService.findBordereauPrixByDesigniations(listDesigniation);
		
		//Regroupement des données
		if(CollectionUtils.isNotEmpty(listBP)){
			for (BordereauPrixDTO bPDTO : listBP) {
				desig = bPDTO.getDesigniation();
				if(listBrDecompte.containsKey(desig)){
					bPDTO.setQteDecompte(listBrDecompte.get(desig).getQte());
					bPDTO.setTva(listBrDecompte.get(desig).getTva());
				}
				if(listPreviousBrDecompte.containsKey(desig)){
					bPDTO.setQteAncienCumul(listPreviousBrDecompte.get(desig).getQte());
					bPDTO.setTva(listPreviousBrDecompte.get(desig).getTva());
				}
				
				dataMarche.add(bPDTO);
			}
		}		
		
		//Remplissage printDecompteDTO
		printDecompteDTO.setDecompte(decompte);
		printDecompteDTO.setMarche(selectedMarche);
		printDecompteDTO.setDataMarche(dataMarche);
		
		String XLSX_EXT = Constantes.GlobalConstant.XLSX_EXTENSION;
		String FILE_NAME = getUrl()+"DecompteDefinitif"+Calendar.getInstance().getTimeInMillis()+XLSX_EXT;

		//Print Decompte
		DecompteExcel decompteExcel = new DecompteExcel(printDecompteDTO);
		String filePath =decompteExcel.printDecompteDefinitif(FILE_NAME);
		String fileName ="Decompte"+decompte.getNumDecompte()+XLSX_EXT;
		Utilitaires.downloadFile(filePath,fileName);
		
	} catch (Exception e) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,  Constantes.GlobalConstant.ERREUR, e.getMessage()));
		LOGGER.error(e);
	}
}
	/**
	 * 
	 * Get URL
	 * 
	 */

	public String getUrl(){
		String url="";
		try {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
			ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
			String codeSite = (String) session.getAttribute(Constantes.GlobalConstant.CODE_SITE);
			String codPort = (String) session.getAttribute(Constantes.GlobalConstant.CODE_PORT);
			url = Utilitaires.getPathFolder(Constantes.GlobalConstant.DECOMPTE,
					Constantes.GlobalConstant.MARCHE, codPort, codeSite, servletContext.getRealPath("/"));			
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,  Constantes.GlobalConstant.ERREUR,  e.getMessage() ));
			LOGGER.error(e);
		}
		return url;
	}

	
	/**
	 * 
	 * Getters And Setters
	 * 
	 */
	private String getVille(){
		String ville="";
		try {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
			ville = (String) session.getAttribute(Constantes.GlobalConstant.LIB_PORT);
			
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,  Constantes.GlobalConstant.ERREUR,  e.getMessage() ));
			LOGGER.error(e);
		}
		
		return ville;
	}
	
	
	
	/**
	 * 
	 * Getters And Setters
	 * 
	 */

	public List<MarcheDTO> getListMarches() {
		return listMarches;
	}
	public void setListMarches(List<MarcheDTO> listMarches) {
		this.listMarches = listMarches;
	}
	public List<DecompteDTO> getListDecomptes() {
		return listDecomptes;
	}
	public void setListDecomptes(List<DecompteDTO> listDecomptes) {
		this.listDecomptes = listDecomptes;
	}
	public List<AttachementDTO> getListAttachements() {
		return listAttachements;
	}
	public void setListAttachements(List<AttachementDTO> listAttachements) {
		this.listAttachements = listAttachements;
	}
	public List<AttachementDTO> getListSelectedAttachements() {
		return listSelectedAttachements;
	}
	public void setListSelectedAttachements(List<AttachementDTO> listSelectedAttachements) {
		this.listSelectedAttachements = listSelectedAttachements;
	}
	public MarcheDTO getSelectedMarche() {
		return selectedMarche;
	}
	public void setSelectedMarche(MarcheDTO selectedMarche) {
		this.selectedMarche = selectedMarche;
	}
	public DecompteDTO getDecompte() {
		return decompte;
	}
	public void setDecompte(DecompteDTO decompte) {
		this.decompte = decompte;
	}
	public boolean isShowMarcheTable() {
		return showMarcheTable;
	}
	public void setShowMarcheTable(boolean showMarcheTable) {
		this.showMarcheTable = showMarcheTable;
	}
}
