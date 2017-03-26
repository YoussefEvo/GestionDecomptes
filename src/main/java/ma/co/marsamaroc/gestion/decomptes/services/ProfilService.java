package ma.co.marsamaroc.gestion.decomptes.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ma.co.marsamaroc.gestion.decomptes.dao.interfaces.IProfilDAO;
import ma.co.marsamaroc.gestion.decomptes.dto.ProfilDTO;
import ma.co.marsamaroc.gestion.decomptes.entite.Profil;
import ma.co.marsamaroc.gestion.decomptes.services.interfaces.IProfilService;



@Service
@Transactional("transactionManager")
public class ProfilService implements IProfilService{
	
	
	@Autowired
	private IProfilDAO profilDAO;
	
	public IProfilDAO getProfilDAO() {
		return profilDAO;
	}






	@Override
	public List<ProfilDTO> findAllProfils() throws Exception {
		return profilDAO.findAllProfils();
	}






	@Override
	public Profil findProfilById(Integer id) {
		return profilDAO.rechercherParKP(id);
	}
	
	

}
