package ma.co.marsamaroc.gestion.decomptes.managed.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.client.RestTemplate;

import ma.co.marsamaroc.gestion.decomptes.dto.MarcheDTO;
import ma.co.marsamaroc.gestion.decomptes.utils.Constantes;

@Named
@SessionScoped
public class MarcheController  implements Serializable{

	private static final long serialVersionUID = 1L;
	private final static Logger LOGGER = Logger.getLogger(MarcheController.class);
	
	
	
		/**
		 * 
		 * @Autowired
		 * 
		 */
		@Autowired
		private MessageSource messageSource;
		@Autowired
		private RestTemplate restTemplate;
		
		
		
		
		List<MarcheDTO> listMarches;
	
	
	
		/**
		 * DESCRIPTION : GET URL
		 * DATE DERNIERE MODIF : 24 Jan. 2017
		 * PROJET : Gestion Decompte
		 * AUTEUR : LABAL
		 *
		 * @return
		 */
		public String start() {
		
			try {
				
				listMarches = new ArrayList<>();
				MarcheDTO marche;			
				
				marche = new MarcheDTO();
				marche.setNumMarche("02/DG/2015");
				marche.setDescription("Réal trvx d'entretien des gros oeuvres");
				marche.setCodFournisseur("CBI");
				listMarches.add(marche);
				
				marche = new MarcheDTO();
				marche.setNumMarche("03-02/07/DT");
				marche.setDescription("LOCATION LONGUE DUREE VEHICULES");
				marche.setCodFournisseur("CGI");
				listMarches.add(marche);
				
				
				marche = new MarcheDTO();
				marche.setNumMarche("44/DG/2017");
				marche.setDescription("MAINTENANCE DES TREMIES EN EXPLOITA LOT1");
				marche.setCodFournisseur("CGI");
				listMarches.add(marche);
				
				marche = new MarcheDTO();
				marche.setNumMarche("18/DG/2016");
				marche.setDescription("S-TRAITANCE PRESTATIONS LIEES MANU NAVIR");
				marche.setCodFournisseur("SQLI");
				listMarches.add(marche);
				
				marche = new MarcheDTO();
				marche.setNumMarche("08/DG/2017");
				marche.setDescription("MAINTENANCE DE 10 CHARIOT CAVALIER NOELL");
				marche.setCodFournisseur("CBI");
				listMarches.add(marche);
				
				marche = new MarcheDTO();
				marche.setNumMarche("66/DG/2016");
				marche.setDescription("FOURNITURES DES ACCUMULATEURS(BATTERIES)");
				marche.setCodFournisseur("cgi");
				listMarches.add(marche);
				
				
			} catch (Exception e) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,  Constantes.GlobalConstant.ERREUR,  e.getMessage() ));
				LOGGER.error(e);
			}
			return Constantes.GlobalConstant_Page.GEST_MARCHE_SEARSH_PAGE;
		}


		/**
		 * DESCRIPTION : RECHERCHER MARCHES
		 * DATE DERNIERE MODIF : 31 Jan. 2017
		 * PROJET : Gestion Decompte
		 * AUTEUR : LABAL
		 *
		 * @return
		 */
		public String rechercher() {
		
			try {
				
				
				
			} catch (Exception e) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,  Constantes.GlobalConstant.ERREUR,  e.getMessage() ));
				LOGGER.error(e);
			}
			return Constantes.GlobalConstant_Page.GEST_MARCHE_SEARSH_PAGE;
		}
		
	    /**
	     * 
	     * Setters and Getters
	     * 
	     */
		public MessageSource getMessageSource() {
			return messageSource;
		}
		public RestTemplate getRestTemplate() {
			return restTemplate;
		}


		public List<MarcheDTO> getListMarches() {
			return listMarches;
		}


		public void setListMarches(List<MarcheDTO> listMarches) {
			this.listMarches = listMarches;
		}
		
		
}
