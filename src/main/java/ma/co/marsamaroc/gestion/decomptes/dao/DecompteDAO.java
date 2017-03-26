package ma.co.marsamaroc.gestion.decomptes.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import ma.co.marsamaroc.gestion.decomptes.dao.interfaces.IDecompteDAO;
import ma.co.marsamaroc.gestion.decomptes.dto.DecompteDTO;
import ma.co.marsamaroc.gestion.decomptes.entite.Decompte;
import ma.co.marsamaroc.gestion.decomptes.utils.Constantes;
import ma.co.marsamaroc.gestion.decomptes.utils.Utilitaires;

@Repository
public class DecompteDAO extends Dao<Decompte> implements IDecompteDAO {

	public DecompteDAO() {
		super(Decompte.class);
	}


	@Override
	@SuppressWarnings("unchecked")
	public List<DecompteDTO> findDecomptesByidMarche(Integer idMarche) throws Exception {
		StringBuilder req = new StringBuilder("select idDecompte as idDecompte, numDecompte as numDecompte, ")
				.append("dateEtablissement as dateEtablissement, montantTtc as montantTtc, ")
				.append("retenuGarantie as retenuGarantie, penaliteRetard as penaliteRetard, ")
				.append("retenuAvance as retenuAvance, revisionPrix as revisionPrix, ")
				.append("autresRetenues as autresRetenues, retenueSource as retenueSource, ")
				.append("flagDernier as flagDernier,dec.marche.idMarche as idMarche ")
				.append("from Decompte dec ")
				.append("where dec.marche.idMarche = :p_idMarche ")
				.append("and coalesce(indSupp,:p_notSupprime) = :p_notSupprime ")
				.append("order by idDecompte");
			return entityManager
					.unwrap(Session.class)
					.createQuery(req.toString())
					.setParameter("p_idMarche", idMarche)
					.setParameter("p_notSupprime",Constantes.GlobalConstant.NOT_SUPPRIME)
					.setResultTransformer(Transformers.aliasToBean(DecompteDTO.class)).list();
	}

	
	@Override
	public DecompteDTO findDecompteDTOByid(Integer idDecompte) throws Exception {
		StringBuilder req = new StringBuilder("select idDecompte as idDecompte, numDecompte as numDecompte, ")
				.append("dateEtablissement as dateEtablissement, montantTtc as montantTtc, ")
				.append("retenuGarantie as retenuGarantie, penaliteRetard as penaliteRetard, ")
				.append("retenuAvance as retenuAvance, revisionPrix as revisionPrix, ")
				.append("autresRetenues as autresRetenues, retenueSource as retenueSource, ")
				.append("flagDernier as flagDernier ")
				.append("from Decompte ")
				.append("where idDecompte = :p_idDecompte ")
				.append("and coalesce(indSupp,:p_notSupprime) = :p_notSupprime");
			return (DecompteDTO) entityManager
					.unwrap(Session.class)
					.createQuery(req.toString())
					.setParameter("p_idDecompte", idDecompte)
					.setParameter("p_notSupprime",Constantes.GlobalConstant.NOT_SUPPRIME)
					.setResultTransformer(Transformers.aliasToBean(DecompteDTO.class)).uniqueResult() ;
	}


	@Override
	public void deleteDecompte(Integer idDecompte) throws Exception {
		StringBuilder req = new StringBuilder()
				.append("UPDATE Decompte SET indSupp = :p_supprime , datModif= :p_datModif , userModif =:p_userModif ")
				.append("where idDecompte = :p_idDecompte");
		
		entityManager.createQuery(req.toString())
		.setParameter("p_supprime", Constantes.GlobalConstant.SUPPRIME)
		.setParameter("p_idDecompte", idDecompte)
		.setParameter("p_datModif", new Date())
		.setParameter("p_userModif", Utilitaires.getCurrentUser())
		.executeUpdate();
	}


}
