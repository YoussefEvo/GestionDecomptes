package ma.co.marsamaroc.gestion.decomptes.services.interfaces;

import java.util.List;

import ma.co.marsamaroc.gestion.decomptes.dto.AttachementDTO;
import ma.co.marsamaroc.gestion.decomptes.dto.BonCommandeDTO;

public interface IBonCommandeService {
	
	/**
	 * DESCRIPTION : Methode qui permet de récupérer la liste des bons de commande 
	 * DATE DERNIERE MODIF : 28 fév. 2017
	 * PROJET : APIPROWS
	 * AUTEUR : ZGUIOUAR
	 * 
	 * @param codeSite
	 * @return
	 * @throws Exception
	 */
	List<BonCommandeDTO> wsGetListBonCommandeDTO(String codeSite) throws Exception;
	
	/**
	 * DESCRIPTION : vérifie si le bon de commande déja existe dans la base decompte.
	 * DATE DERNIERE MODIF : 07 Fév. 2017
	 * PROJET : Gestion Décompte
	 * AUTEUR : ZGUIOUAR
	 * 
	 * @param idSite
	 * @return
	 * @throws Exception
	 */
	void verificationMarche(Integer idSite, BonCommandeDTO bonCommandeDTO) throws Exception;
	
	/**
	 * DESCRIPTION : Récupérer les attachement par id bon de commande 
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
 