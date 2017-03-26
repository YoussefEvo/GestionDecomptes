package ma.co.marsamaroc.gestion.decomptes.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Repository;

import ma.co.marsamaroc.gestion.decomptes.dao.interfaces.IAttachementDAO;
import ma.co.marsamaroc.gestion.decomptes.dto.AttachementDTO;
import ma.co.marsamaroc.gestion.decomptes.dto.BonReceptionDTO;
import ma.co.marsamaroc.gestion.decomptes.dto.BordereauPrixDTO;
import ma.co.marsamaroc.gestion.decomptes.dto.MarcheDTO;
import ma.co.marsamaroc.gestion.decomptes.entite.Attachement;
import ma.co.marsamaroc.gestion.decomptes.entite.Marche;
import ma.co.marsamaroc.gestion.decomptes.entite.Site;
import ma.co.marsamaroc.gestion.decomptes.utils.Constantes;
import ma.co.marsamaroc.gestion.decomptes.utils.Utilitaires;

@Repository
public class AttachementDAO extends Dao<Attachement> implements IAttachementDAO  {

	public AttachementDAO(){
		super(Attachement.class);
	}


	@Override
	@SuppressWarnings("unchecked")
	public List<AttachementDTO> findAttachementsByidDecompte(Integer idDecompte) throws Exception {
		StringBuilder req = new StringBuilder("select idAttachement as idAttachement, numAttachement as numAttachement, ")
				.append("dateEtablissement as dateEtablissement,montant as montant, ")
				.append("dateDebut as dateDebut, dateFin as dateFin ")
				.append("from Attachement attach ")
				.append("where attach.decompte.idDecompte = :p_idDecompte ")
				.append("and coalesce(indSupp,:p_notSupprime) = :p_notSupprime");
			return entityManager
					.unwrap(Session.class)
					.createQuery(req.toString())
					.setParameter("p_idDecompte", idDecompte)
					.setParameter("p_notSupprime",Constantes.GlobalConstant.NOT_SUPPRIME)
					.setResultTransformer(Transformers.aliasToBean(AttachementDTO.class)).list();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<AttachementDTO> findAttachementsNonDecompteByidMarche(Integer idMarche) throws Exception {
		StringBuilder req = new StringBuilder("select idAttachement as idAttachement, numAttachement as numAttachement, ")
				.append("dateEtablissement as dateEtablissement,montant as montant, ")
				.append("dateDebut as dateDebut, dateFin as dateFin ")
				.append("from Attachement attach ")
				.append("where attach.marche.idMarche = :p_idMarche ")
				.append("and coalesce(ID_DECOMPTE,:p_supprime) = :p_supprime ")
				.append("and coalesce(indSupp,:p_notSupprime) = :p_notSupprime ")
				.append("order by idAttachement");
			return entityManager
					.unwrap(Session.class)
					.createQuery(req.toString())
					.setParameter("p_idMarche", idMarche)
					.setParameter("p_supprime",Constantes.GlobalConstant.SUPPRIME)
					.setParameter("p_notSupprime",Constantes.GlobalConstant.NOT_SUPPRIME)
					.setResultTransformer(Transformers.aliasToBean(AttachementDTO.class)).list();
	}

	
	@Override
	public MarcheDTO findMarcheByNum(String numMarche) throws Exception {
		StringBuilder req = new StringBuilder("select  idMarche as idMarche, numMarche as numMarche, libFournisseur as libFournisseur")
				.append(", coalesce(flagCloture, :p_flagCloture)  as flagCloture ")
				.append(" from Marche")
				.append(" where numMarche = :p_numMarche")
				.append(" and coalesce(indSupp,:p_notSupprime) = :p_notSupprime");
			return (MarcheDTO) entityManager
					.unwrap(Session.class)
					.createQuery(req.toString())
					.setParameter("p_numMarche", numMarche)
					.setParameter("p_flagCloture", Constantes.GlobalConstant.NON_N)
					.setParameter("p_notSupprime",Constantes.GlobalConstant.NOT_SUPPRIME)
					.setResultTransformer(Transformers.aliasToBean(MarcheDTO.class)).setMaxResults(1).uniqueResult();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public Set<Attachement> findAttachementsByIdsAttachement(Set<Integer> idsAttachement) throws Exception {
		StringBuilder req = new StringBuilder("from Attachement ")
				  .append("where idAttachement in (:p_idsAttachement) ")
				  .append("and coalesce(indSupp,:p_notSupprime) = :p_notSupprime ");
		List<Attachement> list = entityManager
					.unwrap(Session.class)
					.createQuery(req.toString())
					.setParameterList("p_idsAttachement", idsAttachement)
					.setParameter("p_notSupprime",Constantes.GlobalConstant.NOT_SUPPRIME)
					.list();
		
		return new HashSet<Attachement>(list);
	}


	@Override
	public void affectedDecompteToAttachements(Set<Integer> idsAttachement,Integer idDecompte) throws Exception {
		StringBuilder req = new StringBuilder()
				  .append("update Attachement attach ")
				  .append("SET attach.decompte.idDecompte = :p_idDecompte ")
				  .append("where idAttachement in (:p_idsAttachement) ")
				  .append("and coalesce(indSupp,:p_notSupprime) = :p_notSupprime ");
		 entityManager
					.unwrap(Session.class)
					.createQuery(req.toString())
					.setParameter("p_idDecompte", idDecompte)
					.setParameterList("p_idsAttachement", idsAttachement)
					.setParameter("p_notSupprime",Constantes.GlobalConstant.NOT_SUPPRIME)
					.executeUpdate();
		
	}
	
	@Override
	public Integer createMarche(Integer idSite, MarcheDTO marcheDTO) throws Exception {
		Marche marche = new Marche();
		Site site = new Site();
		site.setIdSite(idSite);
		marche.setSite(site);
		marche.setNumMarche(marcheDTO.getNumMarche());
		marche.setCodFournisseur(marcheDTO.getCodFournisseur());
		marche.setLibFournisseur(marcheDTO.getLibFournisseur());
		marche.setLibMarche(marcheDTO.getLibMarche());
		marche.setDescription(marcheDTO.getBudget());
		marche.setDevise(marcheDTO.getDevise());
		marche.setMontant(marcheDTO.getMontant());
		marche.setDateDemarage(marcheDTO.getDateDemarage());
		marche.setDateFin(marcheDTO.getDateFin());
		marche.setTypeMarche(marcheDTO.getTypeMarche());
		marche.setIndSupp(Constantes.GlobalConstant.NOT_SUPPRIME);
		marche.setDatCreat(new Date());
		marche.setUserCreat(Utilitaires.getCurrentUser());
		entityManager.persist(marche);
		return marche.getIdMarche();
	}
	
	


	@Override
	public void createBordereauPrix(final List<BordereauPrixDTO> listBordereauPrix, final Integer idMarche) throws Exception {
		
		String sql = "INSERT INTO BORDEREAU_PRIX "
				+ "(ID_MARCHE, DESIGNIATION, UNITE, QTE, PRIX_UNITAIRE, IND_SUPP, USER_CREAT, DAT_CREAT) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		
		jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				BordereauPrixDTO bordereauPrix = listBordereauPrix.get(i);
				Date currentDate = new Date();
				java.sql.Date dateCmd = new java.sql.Date(currentDate.getTime());
				ps.setInt(1, idMarche);
				ps.setString(2, bordereauPrix.getDesigniation());
				ps.setString(3, bordereauPrix.getUnite());
				ps.setDouble(4, bordereauPrix.getQte());
				ps.setDouble(5, bordereauPrix.getPrixUnitaire());
				ps.setInt(6, Constantes.GlobalConstant.NOT_SUPPRIME);
				ps.setString(7, Utilitaires.getCurrentUser());
				ps.setDate(8, dateCmd);
			}

			@Override
			public int getBatchSize() {
				return listBordereauPrix.size();
			}
			
		});
		
	}


	@Override
	public void dissociateAttachementByIdDecompte(Integer idDecompte) throws Exception {
		StringBuilder req = new StringBuilder()
				.append("UPDATE Attachement attach ")
				.append("SET attach.decompte.idDecompte = NULL , datModif= :p_datModif , userModif =:p_userModif ")
				.append("where attach.decompte.idDecompte = :p_idDecompte ")
				.append("and coalesce(indSupp,:p_notSupprime) = :p_notSupprime ");	
		entityManager.createQuery(req.toString())
		.setParameter("p_notSupprime",Constantes.GlobalConstant.NOT_SUPPRIME)
		.setParameter("p_idDecompte", idDecompte)
		.setParameter("p_datModif", new Date())
		.setParameter("p_userModif", Utilitaires.getCurrentUser())
		.executeUpdate();
		
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<AttachementDTO> findAttachementByIdMarche(Integer idMarche) throws Exception {
		StringBuilder req = new StringBuilder("select att.idAttachement as idAttachement, att.numAttachement as numAttachement, ")
				.append("att.dateEtablissement as dateEtablissement, att.montant as montant, ")
				.append("att.dateDebut as dateDebut, att.dateFin as dateFin, ")
				.append("coalesce(att.flagDernier,:p_flagDernier) as flagDernier, ")
				.append("decpt.numDecompte as numDecompte ")
			  //.append(", coalesce(att.marche.flagCloture, :p_flagCloture)  as flagCloture ")
				.append("from Attachement att left join att.decompte decpt ")
				.append("where att.marche.idMarche = :p_idMarche ")
				.append("and coalesce(att.indSupp,:p_notSupprime) = :p_notSupprime ")
				.append("and coalesce(att.marche.indSupp,:p_notSupprime) = :p_notSupprime order by att.idAttachement desc");
			return entityManager
					.unwrap(Session.class)
					.createQuery(req.toString())
					.setParameter("p_idMarche", idMarche)
					.setParameter("p_flagDernier", Constantes.GlobalConstant.NON_N)
				  //.setParameter("p_flagCloture", Constantes.GlobalConstant.NON_N)
					.setParameter("p_notSupprime",Constantes.GlobalConstant.NOT_SUPPRIME)
					.setResultTransformer(Transformers.aliasToBean(AttachementDTO.class)).list();
	}
	
	@Override
	public void deleteAttachementById(Integer idAttachement) throws Exception{
		StringBuilder req = new StringBuilder()
				.append("UPDATE Attachement ")
				.append("SET indSupp = :p_supprime , datModif= :p_datModif , userModif =:p_userModif ")
				.append("where idAttachement = :p_idAttachement ");
		entityManager.createQuery(req.toString())
			.setParameter("p_supprime", Constantes.GlobalConstant.SUPPRIME)
			.setParameter("p_datModif", new Date())
			.setParameter("p_userModif", Utilitaires.getCurrentUser())
			.setParameter("p_idAttachement", idAttachement)
			.executeUpdate();
	}
	
	@Override
	public void deleteBnRecepByIdAtt(Integer idAttachement) throws Exception{
		StringBuilder req = new StringBuilder()
				.append("UPDATE BonReception ")
				.append("SET indSupp = :p_supprime , datModif= :p_datModif , userModif =:p_userModif ")
				.append("where attachement.idAttachement = :p_idAttachement ");
		entityManager.createQuery(req.toString())
			.setParameter("p_supprime", Constantes.GlobalConstant.SUPPRIME)
			.setParameter("p_datModif", new Date())
			.setParameter("p_userModif", Utilitaires.getCurrentUser())
			.setParameter("p_idAttachement", idAttachement)
			.executeUpdate();
	}

	
	@Override
	@SuppressWarnings("unchecked")
	public List<BonReceptionDTO> listBnRecepIgnoreByIdMarch(Integer idMarche) throws Exception {
		StringBuilder req = new StringBuilder("select bonRecep.numBonReception as numBonReception")
				.append(" , bonRecep.designiationBonReception as designiationBonReception, bonRecep.dateReception as dateReception")
				.append(" from Marche marche inner join marche.attachements attachement inner join attachement.bonReceptions bonRecep ")
				.append("where marche.idMarche = :p_idMarche ")
				.append("and coalesce(marche.indSupp,:p_notSupprime) = :p_notSupprime ")
				.append("and coalesce(attachement.indSupp,:p_notSupprime) = :p_notSupprime ")
				.append("and coalesce(bonRecep.indSupp,:p_notSupprime) = :p_notSupprime ");
		return entityManager
					.unwrap(Session.class)
					.createQuery(req.toString())
					.setParameter("p_idMarche", idMarche)
					.setParameter("p_notSupprime",Constantes.GlobalConstant.NOT_SUPPRIME)
					.setResultTransformer(Transformers.aliasToBean(BonReceptionDTO.class)).list();
		
	}


	@Override
	public AttachementDTO lastSequenceMarche(Integer idMarche) throws Exception {
		StringBuilder req = new StringBuilder("select attachement.numAttachement  as numAttachement, attachement.flagDernier as flagDernier")
				.append(" from Marche marche inner join marche.attachements attachement")
				.append(" where marche.idMarche = :p_idMarche ")
				.append(" and coalesce(marche.indSupp,:p_notSupprime) = :p_notSupprime ")
				.append(" and coalesce(attachement.indSupp,:p_notSupprime) = :p_notSupprime ")
				.append(" order by attachement.idAttachement desc ");
		return (AttachementDTO) entityManager
					.unwrap(Session.class)
					.createQuery(req.toString())
					.setParameter("p_idMarche", idMarche)
					.setParameter("p_notSupprime",Constantes.GlobalConstant.NOT_SUPPRIME)
					.setResultTransformer(Transformers.aliasToBean(AttachementDTO.class))
					.setMaxResults(1).uniqueResult();
	}


	@Override
	public Integer createAttachementMarche(Integer idMarche, AttachementDTO attachementDTO) throws Exception {
		Attachement attachement = new Attachement();
		Marche marche = new Marche();
		marche.setIdMarche(idMarche);
		attachement.setMarche(marche);
		attachement.setBonCommande(null);
		attachement.setNumAttachement(attachementDTO.getNumAttachement());
		attachement.setDateEtablissement(attachementDTO.getDateEtablissement());
		attachement.setDateDebut(attachementDTO.getDateDebut());
		attachement.setDateFin(attachementDTO.getDateFin());
		attachement.setFlagDernier( attachementDTO.getIsDernier() ? Constantes.GlobalConstant.OUI_O : Constantes.GlobalConstant.NON_N);
		attachement.setMontant(attachementDTO.getMontant());
		attachement.setIndSupp(Constantes.GlobalConstant.NOT_SUPPRIME);
		attachement.setDatCreat(new Date());
		attachement.setUserCreat(Utilitaires.getCurrentUser());
		entityManager.persist(attachement);
		return attachement.getIdAttachement();
	}


	@Override
	public void createBonsReception(final Integer idAttachement, final List<BonReceptionDTO> listBonReception) throws Exception {

		String sql = "INSERT INTO BON_RECEPTION "
				+ "(ID_ATTACHEMENT, DESIGNIATION_BON_RECEPTION, DATE_RECEPTION, "
				+ "DEVISE, UNITE, QTE, PRIX_UNITAIRE, MONTANT, TVA, MO, OT, "
				+ "NUM_BON_COMMANDE, NUM_MARCHE, NUM_BON_RECEPTION, IND_SUPP, USER_CREAT, DAT_CREAT) "
				+ "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		
		jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				BonReceptionDTO bonReceptionDTO = listBonReception.get(i);
				Date date = new Date();
				java.sql.Date currentDate = new java.sql.Date(date.getTime());
				
				//
				ps.setInt(1, idAttachement);
				ps.setString(2, bonReceptionDTO.getDesigniationBonReception());
				java.sql.Date dateReception = new java.sql.Date(bonReceptionDTO.getDateReception().getTime());
				ps.setDate(3, dateReception);
				//
				ps.setString(4, bonReceptionDTO.getDevise());
				ps.setString(5, bonReceptionDTO.getUnite());
				ps.setDouble(6, bonReceptionDTO.getQte());
				ps.setDouble(7, bonReceptionDTO.getPrixUnitaire());
				ps.setDouble(8, bonReceptionDTO.getMontant());
				ps.setDouble(9, bonReceptionDTO.getTva());
				ps.setString(10, bonReceptionDTO.getMo());
				ps.setString(11, bonReceptionDTO.getOt());
				//
				ps.setString(12, bonReceptionDTO.getNumBonCommande());
				ps.setString(13, bonReceptionDTO.getNumMarche());
				ps.setString(14, bonReceptionDTO.getNumBonReception());
				ps.setInt(15, Constantes.GlobalConstant.NOT_SUPPRIME);
				ps.setString(16, Utilitaires.getCurrentUser());
				ps.setDate(17, currentDate);
			}

			@Override
			public int getBatchSize() {
				return listBonReception.size();
			}
			
		});
	}


	@Override
	public Integer getIdAttEtDernier(Integer idAttachement, Integer idMarche) throws Exception {
		StringBuilder req = new StringBuilder("select attachement.idAttachement from Marche marche inner join marche.attachements attachement")
				.append(" where marche.idMarche = :p_idMarche ")
				.append(" and attachement.idAttachement < :p_idAttachement")
				.append(" and coalesce(marche.indSupp,:p_notSupprime) = :p_notSupprime ")
				.append(" and coalesce(attachement.indSupp,:p_notSupprime) = :p_notSupprime ")
				.append(" order by attachement.flagDernier desc, attachement.idAttachement desc ");
		Integer idAtt =  (Integer) entityManager
				.unwrap(Session.class)
				.createQuery(req.toString())
				.setParameter("p_idMarche", idMarche)
				.setParameter("p_idAttachement", idAttachement)
				.setParameter("p_notSupprime",Constantes.GlobalConstant.NOT_SUPPRIME)
				.setMaxResults(1).uniqueResult();
		return idAtt;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<BonReceptionDTO> getListBonReceptionByIdAttachement(Integer idAttachementSelectionne, Integer idMarche){
		StringBuilder req = new StringBuilder("select bonRecep.designiationBonReception as designiationBonReception")
				.append(" , bonRecep.unite as unite, bonRecep.prixUnitaire as prixUnitaire")
				.append(" , sum(bonRecep.qte) as qte, sum(bonRecep.montant) as montant")
				.append(" from Marche marche inner join marche.attachements attachement inner join attachement.bonReceptions bonRecep ")
				.append(" where marche.idMarche = :p_idMarche ")
				.append(" and attachement.idAttachement = :p_idAttachementSelectionne")
				.append(" and coalesce(marche.indSupp,:p_notSupprime) = :p_notSupprime ")
				.append(" and coalesce(attachement.indSupp,:p_notSupprime) = :p_notSupprime ")
				.append(" and coalesce(bonRecep.indSupp,:p_notSupprime) = :p_notSupprime ")
				.append(" group by bonRecep.designiationBonReception,  bonRecep.unite, bonRecep.prixUnitaire");
		return entityManager
					.unwrap(Session.class)
					.createQuery(req.toString())
					.setParameter("p_idMarche", idMarche)
					.setParameter("p_idAttachementSelectionne", idAttachementSelectionne)
					.setParameter("p_notSupprime",Constantes.GlobalConstant.NOT_SUPPRIME)
					.setResultTransformer(Transformers.aliasToBean(BonReceptionDTO.class)).list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<BonReceptionDTO> getSumListBonReception(Integer idAttachementSelectionne, Integer premierIdAttachement, Integer idMarche){
		StringBuilder req = new StringBuilder("select bonRecep.designiationBonReception as designiationBonReception")
				.append(" , bonRecep.unite as unite, bonRecep.prixUnitaire as prixUnitaire")
				.append(" , sum(bonRecep.qte) as qte, sum(bonRecep.montant) as montant")
				.append(" from Marche marche inner join marche.attachements attachement inner join attachement.bonReceptions bonRecep ")
				.append(" where marche.idMarche = :p_idMarche ")
				.append(" and attachement.idAttachement <= :p_idAttachementSelectionne and attachement.idAttachement > :p_premierIdAttachement")
				.append(" and coalesce(marche.indSupp,:p_notSupprime) = :p_notSupprime ")
				.append(" and coalesce(attachement.indSupp,:p_notSupprime) = :p_notSupprime ")
				.append(" and coalesce(bonRecep.indSupp,:p_notSupprime) = :p_notSupprime ")
				.append(" group by bonRecep.designiationBonReception,  bonRecep.unite, bonRecep.prixUnitaire");
		return entityManager
					.unwrap(Session.class)
					.createQuery(req.toString())
					.setParameter("p_idMarche", idMarche)
					.setParameter("p_idAttachementSelectionne", idAttachementSelectionne)
					.setParameter("p_premierIdAttachement", null != premierIdAttachement ? premierIdAttachement : 0)
					.setParameter("p_notSupprime",Constantes.GlobalConstant.NOT_SUPPRIME)
					.setResultTransformer(Transformers.aliasToBean(BonReceptionDTO.class)).list();
	}

	
	@Override
	@SuppressWarnings("unchecked")
	public List<BonReceptionDTO> findAllBonsRecepByIdAttachement(Integer idAttachement) throws Exception {
		StringBuilder req = new StringBuilder("select designiationBonReception as designiationBonReception")
				.append(" , unite as unite, prixUnitaire as prixUnitaire")
				.append(" , qte as qte, montant as montant, tva as tva, ot as ot, mo as mo")
				.append(" , numBonReception as numBonReception, dateReception as dateReception")
				.append(" from BonReception ")
				.append(" where attachement.idAttachement = :p_idAttachement")
				.append(" and coalesce(attachement.indSupp,:p_notSupprime) = :p_notSupprime ")
				.append(" and coalesce(indSupp,:p_notSupprime) = :p_notSupprime ")
				.append(" order by designiationBonReception");
		return entityManager
					.unwrap(Session.class)
					.createQuery(req.toString())
					.setParameter("p_idAttachement", idAttachement)
					.setParameter("p_notSupprime",Constantes.GlobalConstant.NOT_SUPPRIME)
					.setResultTransformer(Transformers.aliasToBean(BonReceptionDTO.class)).list();
	}
	
}
