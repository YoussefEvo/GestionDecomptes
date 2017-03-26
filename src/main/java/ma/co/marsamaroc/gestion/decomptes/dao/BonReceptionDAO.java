package ma.co.marsamaroc.gestion.decomptes.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import ma.co.marsamaroc.gestion.decomptes.dao.interfaces.IBonReceptionDAO;
import ma.co.marsamaroc.gestion.decomptes.dto.BonReceptionDTO;
import ma.co.marsamaroc.gestion.decomptes.entite.BonReception;
import ma.co.marsamaroc.gestion.decomptes.utils.Constantes;

@Repository
public class BonReceptionDAO extends Dao<BonReception> implements IBonReceptionDAO{
	
	public BonReceptionDAO(){
		super(BonReception.class);
	}


	@Override
	@SuppressWarnings("unchecked")
	public List<BonReceptionDTO> findBonReceptionsByidsDecompte(List<Integer> idsDecompte) throws Exception {
		StringBuilder req = new StringBuilder("select sum(qte) as qte ,BR.prixUnitaire as prixUnitaire , ")
				.append("designiationBonReception as designiationBonReception ,unite as unite, tva as tva ")
				.append("from BonReception BR ")
				.append("where BR.attachement.decompte.idDecompte in (:p_idsDecompte) ")
				.append("and coalesce(indSupp,:p_notSupprime) = :p_notSupprime ")
				.append("group by BR.designiationBonReception, BR.prixUnitaire , BR.unite, BR.tva");
				
		return entityManager
				.unwrap(Session.class)
				.createQuery(req.toString())
				.setParameterList("p_idsDecompte", idsDecompte)
				.setParameter("p_notSupprime",Constantes.GlobalConstant.NOT_SUPPRIME)
				.setResultTransformer(Transformers.aliasToBean(BonReceptionDTO.class)).list();
	}

}
