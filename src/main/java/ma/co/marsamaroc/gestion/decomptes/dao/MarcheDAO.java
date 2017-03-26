package ma.co.marsamaroc.gestion.decomptes.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import ma.co.marsamaroc.gestion.decomptes.dao.interfaces.IMarcheDAO;
import ma.co.marsamaroc.gestion.decomptes.dto.MarcheDTO;
import ma.co.marsamaroc.gestion.decomptes.entite.Marche;
import ma.co.marsamaroc.gestion.decomptes.utils.Constantes;
import ma.co.marsamaroc.gestion.decomptes.utils.Utilitaires;

@Repository
public class MarcheDAO extends Dao<Marche> implements IMarcheDAO{
	
	
	public MarcheDAO() {
		super(Marche.class);
	}


	@Override
	@SuppressWarnings("unchecked")
	public List<MarcheDTO> findMarches(Integer idSite) {
		StringBuilder req = new StringBuilder("select idMarche as idMarche, numMarche as numMarche , ")
				.append("libMarche as libMarche, libFournisseur as libFournisseur , ")
				.append("dateDemarage as dateDemarage , dateFin as dateFin , ")
				.append("typeMarche as typeMarche, flagCloture as flagCloture , ")
				.append("montant as montant ,dateDemarage as dateDemarage , ")
				.append("dateFin as dateFin , devise as devise , budget as budget ")
				.append("from Marche mar ")
				.append("where mar.site.idSite = :p_idSite ")
				.append("and coalesce(indSupp,:p_notSupprime) = :p_notSupprime");
			return entityManager
					.unwrap(Session.class)
					.createQuery(req.toString())
					.setParameter("p_idSite", idSite)
					.setParameter("p_notSupprime",Constantes.GlobalConstant.NOT_SUPPRIME)
					.setResultTransformer(Transformers.aliasToBean(MarcheDTO.class)).list();
	}


	@Override
	public void clotureMarche(Integer idMarche) throws Exception {
		StringBuilder req = new StringBuilder()
				.append("UPDATE Marche SET flagCloture = :p_oui_o , datModif= :p_datModif , userModif =:p_userModif ")
				.append("where idMarche = :p_idMarche");
		
		entityManager.createQuery(req.toString())
		.setParameter("p_oui_o", Constantes.GlobalConstant.OUI_O)
		.setParameter("p_idMarche", idMarche)
		.setParameter("p_datModif", new Date())
		.setParameter("p_userModif", Utilitaires.getCurrentUser())
		.executeUpdate();
	}


	@Override
	public void libereMarche(Integer idMarche) throws Exception {
		StringBuilder req = new StringBuilder()
				.append("UPDATE Marche SET flagCloture = :p_non_n , datModif= :p_datModif , userModif =:p_userModif ")
				.append("where idMarche = :p_idMarche");
		
		entityManager.createQuery(req.toString())
		.setParameter("p_non_n", Constantes.GlobalConstant.NON_N)
		.setParameter("p_idMarche", idMarche)
		.setParameter("p_datModif", new Date())
		.setParameter("p_userModif", Utilitaires.getCurrentUser())
		.executeUpdate();
		
	}

}
