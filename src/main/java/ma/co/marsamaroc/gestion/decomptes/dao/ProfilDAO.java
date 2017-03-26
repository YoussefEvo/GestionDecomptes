package ma.co.marsamaroc.gestion.decomptes.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import ma.co.marsamaroc.gestion.decomptes.dao.interfaces.IProfilDAO;
import ma.co.marsamaroc.gestion.decomptes.dto.ProfilDTO;
import ma.co.marsamaroc.gestion.decomptes.entite.Profil;

@Repository
public class ProfilDAO extends Dao<Profil> implements IProfilDAO {
	
	

	
	public ProfilDAO() {
		super(Profil.class);

	}

	
	@Override
	@SuppressWarnings("unchecked")
	public List<ProfilDTO> findAllProfils() throws Exception {
		
		StringBuilder req = new StringBuilder()
							.append("select p.idProfil as idProfil, ")
							.append("p.codProfil as codProfil, ")
							.append("p.libProfil as libProfil ")
							.append("from Profil p");
		
		return entityManager.unwrap(Session.class)
							.createQuery(req.toString())
							.setResultTransformer(Transformers.aliasToBean(ProfilDTO.class)).list();
	}

}
