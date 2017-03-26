package ma.co.marsamaroc.gestion.decomptes.services.interfaces;

import java.util.Date;
import java.util.List;
import java.util.Set;

import ma.co.marsamaroc.gestion.decomptes.dto.AttachementDTO;
import ma.co.marsamaroc.gestion.decomptes.dto.BonReceptionDTO;
import ma.co.marsamaroc.gestion.decomptes.dto.MarcheDTO;
import ma.co.marsamaroc.gestion.decomptes.entite.Attachement;

public interface IAttachementService {
	
	/**
	 * DESCRIPTION : Methode qui permet de récupérer la liste des marchés 
	 * DATE DERNIERE MODIF : 31 janv. 2017
	 * PROJET : GESTION DECOMPTE
	 * AUTEUR : ZGUIOUAR
	 * 
	 * @param codeSite
	 * @return
	 * @throws Exception
	 */
	List<MarcheDTO> wsGetListMarche(String codeSite) throws Exception;
	
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
	 * AUTEUR : LABAL
	 * 
	 * @param List<Integer> idsAttachement
	 * @return List<Attachement>
	 */
	Set<Attachement> findAttachementsByIdsAttachement(Set<Integer> idsAttachement) throws Exception;
	
	

	/**
	 * DESCRIPTION : vérifie si le marche déja existe dans la base decompte.
	 * DATE DERNIERE MODIF : 07 Fév. 2017
	 * PROJET : Gestion Décompte
	 * AUTEUR : ZGUIOUAR
	 * 
	 * @param idSite
	 * @return
	 * @throws Exception
	 */
	void verificationMarche(Integer idSite, MarcheDTO marcheApipro) throws Exception;
	
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
	 * DESCRIPTION : Association décompte Attachements 
	 * DATE DERNIERE MODIF : 08 Fév. 2017
	 * PROJET : Gestion Décompte
	 * AUTEUR : LABAL
	 * @param decompte
	 * @throws Exception 
	 */
	
	void affectedDecompteToAttachements(Set<Integer> idsAttachement,Integer idDecompte) throws Exception;
	
	/**
	 * DESCRIPTION : Methode qui permet de récupérer la liste des bons de réception par 
	 * 				 id et numéro du marché plus date debut et date fin	 
	 * DATE DERNIERE MODIF : 10 fév. 2017
	 * PROJET : Gestion Décompte
	 * AUTEUR : ZGUIOUAR
	 * 
	 * @param idMarche
	 * @param numMarche
	 * @param dateDebut
	 * @param dateFin
	 * @return
	 * @throws Exception
	 */
	List<BonReceptionDTO> wsGetListBonReceps(Integer idMarche, String numMarche, Date dateDebut, Date dateFin) throws Exception;
	
	/**
	 * DESCRIPTION : Methode qui permet de générer une séquence d'attachement pour un marché selectionné 
	 * 				 id et type du marché 
	 * DATE DERNIERE MODIF : 10 fév. 2017
	 * PROJET : Gestion Décompte
	 * AUTEUR : ZGUIOUAR
	 * 
	 * @param idMarche
	 * @param typeMarche
	 * @return
	 */
	String generateSequenceMarche(Integer idMarche, String typeMarche) throws Exception;
	
	/**
	 * DESCRIPTION : Methode qui permet de créer un nouveau attachement
	 * 				 idMarche
	 * DATE DERNIERE MODIF : 10 fév. 2017
	 * PROJET : Gestion Décompte
	 * AUTEUR : ZGUIOUAR
	 * 
	 * @param marche
	 * @param attachement
	 * @param listBnReceps
	 * @return
	 * @throws Exception
	 */
	String createAttachement(MarcheDTO marche, AttachementDTO attachement, List<BonReceptionDTO> listBnReceps) throws Exception;
	
	/**
	 * DESCRIPTION : EXPORT EXCEL
	 * DATE DERNIERE MODIF : 10 fév. 2017
	 * PROJET : Gestion Décompte
	 * AUTEUR : ZGUIOUAR
	 * 
	 * @param attachementDTO
	 * @param marcheDTO
	 * @param codeSite
	 * @param type
	 * @param url
	 * @return
	 * @throws Exception
	 */
	String exportExcel(AttachementDTO attachementDTO,MarcheDTO marcheDTO, String codeSite, String codeIso, String type, String url) throws Exception;
	
	/**
	 * DESCRIPTION : EXPORT PDF
	 * DATE DERNIERE MODIF : 10 fév. 2017
	 * PROJET : Gestion Décompte
	 * AUTEUR : ZGUIOUAR
	 * 
	 * @param attachementDTO
	 * @param marcheDTO
	 * @param codeSite
	 * @param type
	 * @param url
	 * @return
	 * @throws Exception
	 */
	String exportPdf(AttachementDTO attachementDTO,MarcheDTO marcheDTO, String codeSite, String type, String url) throws Exception;
	
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
 