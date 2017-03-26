package ma.co.marsamaroc.gestion.decomptes.dao.interfaces;

import java.util.List;
import java.util.Set;

import ma.co.marsamaroc.gestion.decomptes.dto.AttachementDTO;
import ma.co.marsamaroc.gestion.decomptes.dto.BonReceptionDTO;
import ma.co.marsamaroc.gestion.decomptes.dto.BordereauPrixDTO;
import ma.co.marsamaroc.gestion.decomptes.dto.MarcheDTO;
import ma.co.marsamaroc.gestion.decomptes.entite.Attachement;



public interface IAttachementDAO extends IDao<Attachement>{
	
	/**
	 * DESCRIPTION : Trouver Attachements par idsAttachement
	 * DATE DERNIERE MODIF : 07 Fév. 2017
	 * PROJET : Gestion Décompte
	 * AUTEUR : LABAL
	 * 
	 * @param List<Integer> idsAttachement
	 * @return List<Attachement>
	 */
	Set<Attachement> findAttachementsByIdsAttachement(Set<Integer> idsAttachement) throws Exception;
	
	/**
	 * DESCRIPTION : Trouver Attachements par idDecompte
	 * DATE DERNIERE MODIF : 02 Fév. 2017
	 * PROJET : Gestion Décompte
	 * AUTEUR : LABAL
	 * 
	 * @param idDecompte
	 * @return List<AttachementDTO>
	 */
	List<AttachementDTO> findAttachementsByidDecompte(Integer idDecompte) throws Exception;
	
	/**
	 * DESCRIPTION : Trouver Attachements Non Decompte par idMarche
	 * DATE DERNIERE MODIF : 06 Fév. 2017
	 * PROJET : Gestion Décompte
	 * AUTEUR : LABAL
	 * 
	 * @param idMarche
	 * @return List<AttachementDTO>
	 */
	List<AttachementDTO> findAttachementsNonDecompteByidMarche(Integer idMarche) throws Exception;
	
	/**
	 * DESCRIPTION : Trouver Attachements par idDecompte
	 * DATE DERNIERE MODIF : 07 Fév. 2017
	 * PROJET : Gestion Décompte
	 * AUTEUR : ZGUIOUAR
	 * 
	 * @param numMarche
	 * @return 
	 * @throws Exception
	 */
	MarcheDTO findMarcheByNum(String numMarche) throws Exception;
	
	/**
	 * DESCRIPTION : Association décompte Attachements 
	 * DATE DERNIERE MODIF : 08 Fév. 2017
	 * PROJET : Gestion Décompte
	 * AUTEUR : LABAL
	 * @param decompte
	 * @throws Exception 
	 */
	
	void affectedDecompteToAttachements(Set<Integer> idsAttachement,Integer idDecompte) throws Exception;
	
	/**
	 * DESCRIPTION : Trouver Attachements par idDecompte
	 * DATE DERNIERE MODIF : 07 Fév. 2017
	 * PROJET : Gestion Décompte
	 * AUTEUR : ZGUIOUAR
	 * 
	 * @param idSite
	 * @param marcheDTO
	 * @return 
	 * @throws Exception
	 */
	Integer createMarche(Integer idSite, MarcheDTO marcheDTO) throws Exception;
	
	/**
	 * DESCRIPTION : Trouver Attachements par idDecompte
	 * DATE DERNIERE MODIF : 07 Fév. 2017
	 * PROJET : Gestion Décompte
	 * AUTEUR : ZGUIOUAR
	 * 
	 * @param listBordereauPrix
	 * @return 
	 * @throws Exception
	 */
	void createBordereauPrix(final List<BordereauPrixDTO> listBordereauPrix, final Integer idMarche) throws Exception;
	
	/**
	 * DESCRIPTION : Suppriper Attachement par IdDecompte
	 * DATE DERNIERE MODIF : 08 Fév. 2017
	 * PROJET : Gestion Décompte
	 * AUTEUR : LABAL
	 * @param IdDecompte
	 * @throws Exception 
	 */
	
	void dissociateAttachementByIdDecompte(Integer idDecompte) throws Exception;
	
	/**
	 * DESCRIPTION : Récupérer les attachement par idmarche 
	 * DATE DERNIERE MODIF : 08 Fév. 2017
	 * PROJET : Gestion Décompte
	 * AUTEUR : ZGUIOUAR
	 * 
	 * @param idMarche
	 * @return
	 * @throws Exception
	 */
	List<AttachementDTO> findAttachementByIdMarche(Integer idMarche) throws Exception;
	
	/**
	 * DESCRIPTION : Methode permet de supprimer une attachement par id 
	 * DATE DERNIERE MODIF : 08 Fév. 2017
	 * PROJET : Gestion Décompte
	 * AUTEUR : ZGUIOUAR
	 * 
	 * @param idAttachement
	 * @throws Exception
	 */
	void deleteAttachementById(Integer idAttachement) throws Exception;
	
	/**
	 * DESCRIPTION : Methode permet de supprimer les bons de reception d'une attachement
	 * DATE DERNIERE MODIF : 08 Fév. 2017
	 * PROJET : Gestion Décompte
	 * AUTEUR : ZGUIOUAR
	 * 
	 * @param idAttachement
	 * @throws Exception
	 */
	void deleteBnRecepByIdAtt(Integer idAttachement) throws Exception;
	
	/**
	 * DESCRIPTION : Methode permet de  récupérer une liste des clés à ignore
	 * DATE DERNIERE MODIF : 08 Fév. 2017
	 * PROJET : Gestion Décompte
	 * AUTEUR : ZGUIOUAR
	 * 
	 * @param idMarche
	 * @return
	 * @throws Exception
	 */
	List<BonReceptionDTO> listBnRecepIgnoreByIdMarch(Integer idMarche) throws Exception;
	
	/**
	 * DESCRIPTION : Methode qui permet de récupérer  la dernière séquence d'un attachement pour un marché selectionné 
	 * 				 idMarche
	 * DATE DERNIERE MODIF : 10 fév. 2017
	 * PROJET : Gestion Décompte
	 * AUTEUR : ZGUIOUAR
	 * 
	 * @param idMarche
	 * @return
	 * @throws Exception
	 */
	AttachementDTO lastSequenceMarche(Integer idMarche) throws Exception;
	
	/**
	 * DESCRIPTION : Methode qui permet de creer un attachement
	 * 				 idMarche, attachementDTO
	 * DATE DERNIERE MODIF : 10 fév. 2017
	 * PROJET : Gestion Décompte
	 * AUTEUR : ZGUIOUAR
	 * 
	 * @param idMarche
	 * @param attachementDTO
	 * @return
	 * @throws Exception
	 */
	Integer createAttachementMarche(Integer idMarche, AttachementDTO attachementDTO) throws Exception;
	
	/**
	 * DESCRIPTION : Methode qui permet de creer les bons de reception
	 * 				 idMarche, attachementDTO
	 * DATE DERNIERE MODIF : 10 fév. 2017
	 * PROJET : Gestion Décompte
	 * AUTEUR : ZGUIOUAR
	 * 
	 * @param idAttachement
	 * @param listBonReception
	 * @throws Exception
	 */
	void createBonsReception(Integer idAttachement, List<BonReceptionDTO> listBonReception) throws Exception;
	
	/**
	 * DESCRIPTION : Methode qui permet de récupérer l'id de dernier attachement
	 * 				 attachementDTO, idMarche
	 * DATE DERNIERE MODIF : 10 fév. 2017
	 * PROJET : Gestion Décompte
	 * AUTEUR : ZGUIOUAR
	 * 
	 * @param attachementDTO
	 * @param attachementDTO
	 * @return
	 * @throws Exception
	 */
	Integer getIdAttEtDernier(Integer idAttachement, Integer idMarche) throws Exception;
	
	/**
	 * DESCRIPTION : Methode qui permet de récupérer la liste des bons de reception
	 * 				 idAttachementSelectionne, idMarche
	 * DATE DERNIERE MODIF : 10 fév. 2017
	 * PROJET : Gestion Décompte
	 * AUTEUR : ZGUIOUAR
	 * 
	 * @param idAttachementSelectionne
	 * @param premierIdAttachement
	 * @param idMarche
	 * @return
	 * @throws Exception
	 */
	List<BonReceptionDTO> getListBonReceptionByIdAttachement(Integer idAttachementSelectionne, Integer idMarche);
	
	/**
	 * DESCRIPTION : Methode qui permet de faire la somme des bons de reception
	 * 				 idAttachementSelectionne, premierIdAttachement, idMarche
	 * DATE DERNIERE MODIF : 10 fév. 2017
	 * PROJET : Gestion Décompte
	 * AUTEUR : ZGUIOUAR
	 * 
	 * @param idAttachementSelectionne
	 * @param premierIdAttachement
	 * @param idMarche
	 * @return
	 * @throws Exception
	 */
	List<BonReceptionDTO> getSumListBonReception(Integer idAttachementSelectionne, Integer premierIdAttachement, Integer idMarche);
	
	/**
	 * DESCRIPTION : Récupérer des bons de réception par id attachement
	 * DATE DERNIERE MODIF : 28 fév. 2017
	 * PROJET : Gestion Décompte
	 * AUTEUR : ZGUIOUAR
	 * 
	 * @param idAttachement
	 * @return
	 * @throws Exception
	 */
	List<BonReceptionDTO> findAllBonsRecepByIdAttachement(Integer idAttachement) throws Exception;
	
	
}
