package ma.co.marsamaroc.gestion.decomptes.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import ma.co.marsamaroc.gestion.decomptes.dao.interfaces.IBordereauPrixDAO;
import ma.co.marsamaroc.gestion.decomptes.dto.BordereauPrixDTO;
import ma.co.marsamaroc.gestion.decomptes.entite.BordereauPrix;
import ma.co.marsamaroc.gestion.decomptes.utils.Constantes;

@Repository
public class BordereauPrixDAO extends Dao<BordereauPrix> implements IBordereauPrixDAO{
	
	public BordereauPrixDAO(){
		super(BordereauPrix.class);
	}


	@Override
	@SuppressWarnings("unchecked")
	public List<BordereauPrixDTO> findBordereauPrixByDesigniations(List<String> designiations) throws Exception {
		StringBuilder req = new StringBuilder("select designiation as designiation , ")
				.append("qte as qte , unite as unite , ")
				.append("prixUnitaire as prixUnitaire ")
				.append("from BordereauPrix BP ")
				.append("where BP.designiation in(:p_designiations) ")
				.append("and coalesce(indSupp,:p_notSupprime) = :p_notSupprime ")
				.append("order by designiation");
			return entityManager
					.unwrap(Session.class)
					.createQuery(req.toString())
					.setParameterList("p_designiations", designiations)
					.setParameter("p_notSupprime",Constantes.GlobalConstant.NOT_SUPPRIME)
					.setResultTransformer(Transformers.aliasToBean(BordereauPrixDTO.class)).list();
	}

}
