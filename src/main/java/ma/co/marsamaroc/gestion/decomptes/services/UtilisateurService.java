package ma.co.marsamaroc.gestion.decomptes.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ma.co.marsamaroc.gestion.decomptes.dao.interfaces.IUtilisateurDAO;
import ma.co.marsamaroc.gestion.decomptes.dto.AutorisationDTO;
import ma.co.marsamaroc.gestion.decomptes.dto.PortDTO;
import ma.co.marsamaroc.gestion.decomptes.dto.SiteDTO;
import ma.co.marsamaroc.gestion.decomptes.dto.TerminalDTO;
import ma.co.marsamaroc.gestion.decomptes.entite.Utilisateur;
import ma.co.marsamaroc.gestion.decomptes.services.interfaces.IUtilisateurService;



@Service
@Transactional("transactionManager")
public class UtilisateurService implements IUtilisateurService{

	@Autowired
	private IUtilisateurDAO userDAO;
	
	public IUtilisateurDAO getUserDAO() {
		return userDAO;
	}
	public void setUserDAO(IUtilisateurDAO userDAO) {
		this.userDAO = userDAO;
	}


	@Override
	public Utilisateur findUserByUserName(String userName) throws Exception {
		return userDAO.findUserByUserName(userName);
	}
	
	@Override
	public List<PortDTO>  findAllSiteAffectedUser(String userName) throws Exception {
		List<AutorisationDTO> listSiteDTO = userDAO.findAllSiteAffectedUser(userName);
		List<PortDTO> listProts = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(listSiteDTO)) {
			Map<Integer, PortDTO> mapPort = new HashMap<>();
			Map<Integer, TerminalDTO> mapTerminal = new HashMap<>();
			Map<Integer, SiteDTO> mapSite = new HashMap<>();
			for (AutorisationDTO autorisation : listSiteDTO) {
				// Port
				if (!mapPort.containsKey(autorisation.getIdPort())) {
					PortDTO portDTO = new PortDTO();
					portDTO.setIdPort(autorisation.getIdPort());
					portDTO.setCodPort(autorisation.getCodPort());
					portDTO.setLibPort(autorisation.getLibPort());
					mapPort.put(autorisation.getIdPort(), portDTO);
				}
				// Terminal & Profile
				if (!mapTerminal.containsKey(autorisation.getIdTerminal())) {
					TerminalDTO terminalDTO = new TerminalDTO();
					terminalDTO.setIdTerminal(autorisation.getIdTerminal());
					terminalDTO.setCodTerminal(autorisation.getCodTerminal());
					terminalDTO.setLibTerminal(autorisation.getLibTerminal());
					mapTerminal.put(autorisation.getIdTerminal(), terminalDTO);
					mapPort.get(autorisation.getIdPort()).setListTerminal(new ArrayList<>(mapTerminal.values()));
				}
				// Site
				if (!mapSite.containsKey(autorisation.getIdSite())) {
					SiteDTO siteDTO = new SiteDTO();
					siteDTO.setIdSite(autorisation.getIdSite());
					siteDTO.setCodSite(autorisation.getCodSite());
					siteDTO.setLibSite(autorisation.getLibSite());
					siteDTO.setIdProfil(autorisation.getIdProfil());
					mapSite.put(autorisation.getIdSite(), siteDTO);
					mapTerminal.get(autorisation.getIdTerminal()).setListSite(new ArrayList<>(mapSite.values()));
				}
			}
			listProts = new ArrayList<>(mapPort.values());
			
		}
		return listProts;
	}
	
	@Override
	public Set<String> findAllRoleCodeSiteByIdProfil(Integer idProfil) throws Exception {
		return userDAO.findAllRoleCodeSiteByIdProfil(idProfil);
	}

	

}
