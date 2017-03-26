package ma.co.marsamaroc.gestion.decomptes.dao.interfaces;

import java.util.List;

import ma.co.marsamaroc.gestion.decomptes.dto.AttachementDTO;
import ma.co.marsamaroc.gestion.decomptes.dto.BonCommandeDTO;
import ma.co.marsamaroc.gestion.decomptes.dto.MarcheDTO;
import ma.co.marsamaroc.gestion.decomptes.entite.Attachement;



public interface IAttachementBonCmdDAO extends IDao<Attachement>{
	
	/**
	 * DESCRIPTION : vérifie si le numero de commande est déja existe dans la base local
	 * DATE DERNIERE MODIF : 07 Fév. 2017
	 * PROJET : Gestion Décompte
	 * AUTEUR : ZGUIOUAR
	 * 
	 * @param numMarche
	 * @return 
	 * @throws Exception
	 */
	BonCommandeDTO findBonCmdByNum(String numBonCommande) throws Exception;
	
	
	/**
	 * DESCRIPTION : Creation de bon de commande
	 * DATE DERNIERE MODIF : 07 Fév. 2017
	 * PROJET : Gestion Décompte
	 * AUTEUR : ZGUIOUAR
	 * 
	 * @param idSite
	 * @param marcheDTO
	 * @return 
	 * @throws Exception
	 */
	Integer createBonCmd(Integer idSite, BonCommandeDTO bonCommandeDTO) throws Exception;
	
	/**
	 * DESCRIPTION : Récupérer les attachement par id bon commande 
	 * DATE DERNIERE MODIF : 08 Fév. 2017
	 * PROJET : Gestion Décompte
	 * AUTEUR : ZGUIOUAR
	 * 
	 * @param idMarche
	 * @return
	 * @throws Exception
	 */
	List<AttachementDTO> findAttachementByIdCommande(Integer idBonCommande) throws Exception;
	
	
}
