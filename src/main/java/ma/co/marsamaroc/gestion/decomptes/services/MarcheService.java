package ma.co.marsamaroc.gestion.decomptes.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ma.co.marsamaroc.gestion.decomptes.dao.interfaces.IMarcheDAO;
import ma.co.marsamaroc.gestion.decomptes.dto.MarcheDTO;
import ma.co.marsamaroc.gestion.decomptes.entite.Marche;
import ma.co.marsamaroc.gestion.decomptes.services.interfaces.IMarcheService;

@Service
@Transactional("transactionManager")
public class MarcheService implements IMarcheService{
	
	
	@Autowired
	private IMarcheDAO marcheDAO;

	public IMarcheDAO getMarcheDAO() {
		return marcheDAO;
	}




	@Override
	public List<MarcheDTO> findMarches(Integer idSite) throws Exception{
		return marcheDAO.findMarches(idSite);
	}




	@Override
	public Marche findMarcheById(Integer idMarche) throws Exception {
		return marcheDAO.rechercherParKP(idMarche);
	}




	@Override
	public void clotureMarche(Integer idMarche) throws Exception {
		marcheDAO.clotureMarche(idMarche);
		
	}




	@Override
	public void libereMarche(Integer idMarche) throws Exception {
		marcheDAO.libereMarche(idMarche);
		
	}
	
	
	
	
	
	
}
