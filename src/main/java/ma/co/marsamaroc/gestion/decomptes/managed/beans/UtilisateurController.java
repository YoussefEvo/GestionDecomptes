package ma.co.marsamaroc.gestion.decomptes.managed.beans;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import ma.co.marsamaroc.gestion.decomptes.dto.PortDTO;
import ma.co.marsamaroc.gestion.decomptes.dto.SiteDTO;
import ma.co.marsamaroc.gestion.decomptes.dto.TerminalDTO;
import ma.co.marsamaroc.gestion.decomptes.services.interfaces.IUtilisateurService;
import ma.co.marsamaroc.gestion.decomptes.spring.security.GrantedAuthorityImpl;
import ma.co.marsamaroc.gestion.decomptes.utils.Constantes;


@Component
@Scope("request")
public class UtilisateurController implements Serializable{

	private static final long serialVersionUID = 1L;
	private final static Logger LOGGER = Logger.getLogger(UtilisateurController.class);

	/**
	 * 
	 * @Autowired
	 * 
	 */
	@Autowired
	private IUtilisateurService utilisateurService;
	
	
	/**
     * DESCRIPTION : Fonction pour DropDownList Port
     * DATE DERNIERE MODIF : 27 Jan. 2017
	 * PROJET : Gestion Decompte
     * AUTEUR : ZGUIOUAR
     *
     */
    @SuppressWarnings("unchecked")
	public void onPortChange() {
    	try{
    		FacesContext facesContext = FacesContext.getCurrentInstance();
    		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
    		Integer idPort = (Integer) session.getAttribute(Constantes.GlobalConstant.ID_SITE);
    		List<PortDTO> listPort = (List<PortDTO>) session.getAttribute(Constantes.GlobalConstant.LIST_PORTS);
    		for (PortDTO portDTO : listPort) {
				if(idPort.equals(portDTO.getIdPort())){
					session.setAttribute(Constantes.GlobalConstant.LIST_TERMINAUX, portDTO.getListTerminal());
					session.setAttribute(Constantes.GlobalConstant.ID_TERMINAL, portDTO.getListTerminal().get(0).getIdTerminal());
					session.setAttribute(Constantes.GlobalConstant.CODE_PORT, portDTO.getCodPort());
					session.setAttribute(Constantes.GlobalConstant.LIB_PORT, portDTO.getLibPort());
					onTerminalChange();
					break;
				}
			}
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,  Constantes.GlobalConstant.ERREUR,  e.getMessage() ));
            LOGGER.error(e);
        }
    }
    
	 /**
     * DESCRIPTION : Fonction pour DropDownList Port
     * DATE DERNIERE MODIF : 27 Jan. 2017
	 * PROJET : Gestion Decompte
	 * AUTEUR : ZGUIOUAR
     *
     */
    @SuppressWarnings("unchecked")
	public void onTerminalChange() {
    	try{
    		FacesContext facesContext = FacesContext.getCurrentInstance();
    		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
    		Integer idTerminal = (Integer) session.getAttribute(Constantes.GlobalConstant.ID_TERMINAL);
    		List<TerminalDTO> listTerminal = (List<TerminalDTO>) session.getAttribute(Constantes.GlobalConstant.LIST_TERMINAUX);
    		for (TerminalDTO terminalDTO : listTerminal) {
    			if(idTerminal.equals(terminalDTO.getIdTerminal())){
					session.setAttribute(Constantes.GlobalConstant.LIST_SITES, terminalDTO.getListSite());
					session.setAttribute(Constantes.GlobalConstant.ID_SITE, terminalDTO.getListSite().get(0).getIdSite());
					session.setAttribute(Constantes.GlobalConstant.CODE_SITE,  terminalDTO.getListSite().get(0).getCodSite());
					onSiteChange();
					break;
				}
			}
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,  Constantes.GlobalConstant.ERREUR,  e.getMessage() ));
            LOGGER.error(e);
        }
    }
	
	
	
	/**
     * DESCRIPTION : Fonction pour DropDownList Port
     * DATE DERNIERE MODIF : 27 Jan. 2017
	 * PROJET : Gestion Decompte
	 * AUTEUR : ZGUIOUAR
     *
     */
    @SuppressWarnings("unchecked")
	public void onSiteChange() {
    	try{
    		FacesContext facesContext = FacesContext.getCurrentInstance();
    		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
    		Integer idSite = (Integer) session.getAttribute(Constantes.GlobalConstant.ID_SITE);
    		List<SiteDTO> listSite = (List<SiteDTO>) session.getAttribute(Constantes.GlobalConstant.LIST_SITES);
    		for (SiteDTO siteDTO : listSite) {
    			if(idSite.equals(siteDTO.getIdSite())){
    				session.setAttribute(Constantes.GlobalConstant.CODE_SITE,  siteDTO.getCodSite());
    				session.setAttribute(Constantes.GlobalConstant.CODE_ISO,  siteDTO.getCodIso());
    				Authentication authp = SecurityContextHolder.getContext().getAuthentication();
    				Set<String> roles = utilisateurService.findAllRoleCodeSiteByIdProfil(siteDTO.getIdProfil());
    				if (CollectionUtils.isNotEmpty(roles)) {
						Set<GrantedAuthority> updatedAuthorities = new HashSet<GrantedAuthority>();
						for (String role : roles) {
							updatedAuthorities.add(new GrantedAuthorityImpl(role));
						}
						Authentication newAuth = new UsernamePasswordAuthenticationToken(authp.getPrincipal(), authp.getCredentials(),updatedAuthorities);
						SecurityContextHolder.getContext().setAuthentication(newAuth);
					}else{
						LOGGER.info("L'utilisateur n'a pas d'autorité reconnue. - User : " + authp.getName() + " - Site : " + siteDTO.getCodSite());
					}
					break;
				}
			}
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,  Constantes.GlobalConstant.ERREUR,  e.getMessage() ));
            LOGGER.error(e);
        }
    	FacesContext context = FacesContext.getCurrentInstance();
    	NavigationHandler nh = FacesContext
				.getCurrentInstance().getApplication().getNavigationHandler();
    	nh.handleNavigation(context, null, Constantes.GlobalConstant_Page.GEST_MARCHE_ACCUEIL_PAGE + "?faces-redirect=true");
    }

}
