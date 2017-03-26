package ma.co.marsamaroc.gestion.decomptes.dao;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import ma.co.marsamaroc.gestion.decomptes.dao.interfaces.IAttachementBonCmdDAO;
import ma.co.marsamaroc.gestion.decomptes.dto.AttachementDTO;
import ma.co.marsamaroc.gestion.decomptes.dto.BonCommandeDTO;
import ma.co.marsamaroc.gestion.decomptes.entite.Attachement;
import ma.co.marsamaroc.gestion.decomptes.entite.BonCommande;
import ma.co.marsamaroc.gestion.decomptes.entite.Site;
import ma.co.marsamaroc.gestion.decomptes.utils.Constantes;
import ma.co.marsamaroc.gestion.decomptes.utils.Utilitaires;

@Repository
public class AttachementBonCmdDAO extends Dao<Attachement> implements IAttachementBonCmdDAO  {

	public AttachementBonCmdDAO(){
		super(Attachement.class);
	}

	@Override
	public BonCommandeDTO findBonCmdByNum(String numBonCommande) throws Exception {
		StringBuilder req = new StringBuilder("select  idBonCommande as idBonCommande")
				.append(" from BonCommande")
				.append(" where numBonCommande = :p_numBonCommande")
				.append(" and coalesce(indSupp,:p_notSupprime) = :p_notSupprime");
			return (BonCommandeDTO) entityManager
					.unwrap(Session.class)
					.createQuery(req.toString())
					.setParameter("p_numBonCommande", numBonCommande)
					.setParameter("p_notSupprime",Constantes.GlobalConstant.NOT_SUPPRIME)
					.setResultTransformer(Transformers.aliasToBean(BonCommandeDTO.class)).setMaxResults(1).uniqueResult();
	}

	@Override
	public Integer createBonCmd(Integer idSite, BonCommandeDTO bonCommandeDTO) throws Exception {
		BonCommande bonCmd = new BonCommande();
		Site site = new Site();
		site.setIdSite(idSite);
		bonCmd.setSite(site);
		bonCmd.setNumBonCommande(bonCmd.getNumBonCommande());
		bonCmd.setCodFournisseur(bonCommandeDTO.getCodFournisseur());
		bonCmd.setLibFournisseur(bonCommandeDTO.getLibFournisseur());
		bonCmd.setLibBonCommande(StringUtils.isNotBlank(bonCommandeDTO.getLibBonCommande()) ? bonCommandeDTO.getLibBonCommande() : null );
		bonCmd.setDevise(bonCommandeDTO.getDevise());
		bonCmd.setMontant(bonCommandeDTO.getMontant());
		bonCmd.setDateCommande(bonCommandeDTO.getDateCommande());
		bonCmd.setIndSupp(Constantes.GlobalConstant.NOT_SUPPRIME);
		bonCmd.setDatCreat(new Date());
		bonCmd.setUserCreat(Utilitaires.getCurrentUser());
		entityManager.persist(bonCmd);
		return bonCmd.getIdBonCommande();
	}

	
	@Override
	@SuppressWarnings("unchecked")
	public List<AttachementDTO> findAttachementByIdCommande(Integer idBonCommande) throws Exception {
		StringBuilder req = new StringBuilder("select att.idAttachement as idAttachement, att.numAttachement as numAttachement, ")
				.append("att.dateEtablissement as dateEtablissement, att.montant as montant, ")
				.append("att.dateDebut as dateDebut, att.dateFin as dateFin, ")
				.append("coalesce(att.flagDernier,:p_flagDernier) as flagDernier, ")
				.append("decpt.numDecompte as numDecompte ")
				.append("from Attachement att left join att.decompte decpt ")
				.append("where att.bonCommande.idBonCommande = :p_idBonCommande ")
				.append("and att.marche.idMarche is null ")
				.append("and coalesce(att.indSupp,:p_notSupprime) = :p_notSupprime ")
				.append("and coalesce(att.bonCommande.indSupp,:p_notSupprime) = :p_notSupprime order by att.idAttachement desc");
			return entityManager
					.unwrap(Session.class)
					.createQuery(req.toString())
					.setParameter("p_idBonCommande", idBonCommande)
					.setParameter("p_flagDernier", Constantes.GlobalConstant.NON_N)
					.setParameter("p_notSupprime",Constantes.GlobalConstant.NOT_SUPPRIME)
					.setResultTransformer(Transformers.aliasToBean(AttachementDTO.class)).list();
	}
	
}
