package ma.co.marsamaroc.gestion.decomptes.spring.security;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import ma.co.marsamaroc.gestion.decomptes.dto.PortDTO;
import ma.co.marsamaroc.gestion.decomptes.dto.SiteDTO;
import ma.co.marsamaroc.gestion.decomptes.dto.TerminalDTO;
import ma.co.marsamaroc.gestion.decomptes.managed.beans.UtilisateurController;
import ma.co.marsamaroc.gestion.decomptes.services.interfaces.IUtilisateurService;
import ma.co.marsamaroc.gestion.decomptes.utils.Constantes;

//@Service("authenticationSuccessHandler")
public class AuthenticationSuccessHandler
		implements org.springframework.security.web.authentication.AuthenticationSuccessHandler {

	private final static Logger LOGGER = Logger.getLogger(AuthenticationSuccessHandler.class);

	@Autowired
	private IUtilisateurService utilisateurService;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse res, Authentication auth_)
			throws IOException, ServletException {
		try {
			Authentication authp = SecurityContextHolder.getContext().getAuthentication();
			// Liste des autorités 
			List<PortDTO> listProts = utilisateurService.findAllSiteAffectedUser(authp.getName());
			if (CollectionUtils.isNotEmpty(listProts)) {
				List<TerminalDTO> listTerminal = listProts.get(0).getListTerminal();
				List<SiteDTO> listSite = listTerminal.get(0).getListSite();
				Integer idPort = listProts.get(0).getIdPort();
				String codePort = listProts.get(0).getCodPort();
				Integer idTerminal = listTerminal.get(0).getIdTerminal();
				Integer idSite = listSite.get(0).getIdSite();
				String codeSite = listSite.get(0).getCodSite();
				Integer idProfil = listSite.get(0).getIdProfil();
				Set<String> roles = utilisateurService.findAllRoleCodeSiteByIdProfil(idProfil);
				if (CollectionUtils.isNotEmpty(roles)) {
					Set<GrantedAuthority> updatedAuthorities = new HashSet<GrantedAuthority>();
					for (String role : roles) {
						updatedAuthorities.add(new GrantedAuthorityImpl(role));
					}
					Authentication newAuth = new UsernamePasswordAuthenticationToken(authp.getPrincipal(), authp.getCredentials(),updatedAuthorities);
					SecurityContextHolder.getContext().setAuthentication(newAuth);
				}else{
					LOGGER.info("L'utilisateur n'a pas d'autorité reconnue. - User : " + authp.getName() + " - Site : " + codeSite);
				}
				
				req.getSession().setAttribute(Constantes.GlobalConstant.LIST_PORTS, listProts);
				req.getSession().setAttribute(Constantes.GlobalConstant.LIST_TERMINAUX, listTerminal);
				req.getSession().setAttribute(Constantes.GlobalConstant.LIST_SITES, listSite);
				req.getSession().setAttribute(Constantes.GlobalConstant.ID_PORT, idPort);
				req.getSession().setAttribute(Constantes.GlobalConstant.ID_TERMINAL, idTerminal);
				req.getSession().setAttribute(Constantes.GlobalConstant.ID_SITE, idSite);
				req.getSession().setAttribute(Constantes.GlobalConstant.CODE_SITE, codeSite);
				req.getSession().setAttribute(Constantes.GlobalConstant.USER_NAME, authp.getName());
				req.getSession().setAttribute(Constantes.GlobalConstant.LIB_PORT, codePort);
				
				
			}
			res.sendRedirect(req.getContextPath() + "/accueil.xhtml");
		} catch (Exception e) {
			res.sendRedirect(req.getContextPath() + "/login.xhtml?state=failure");
			LOGGER.error(e);
		}
	}
}
