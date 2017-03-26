package ma.co.marsamaroc.gestion.decomptes.dao.interfaces;

import java.util.List;
import java.util.Set;

import ma.co.marsamaroc.gestion.decomptes.dto.AutorisationDTO;
import ma.co.marsamaroc.gestion.decomptes.entite.Utilisateur;


/**
 * 
 * @author ZGUIOUAR
 *
 */
public interface IUtilisateurDAO extends IDao<Utilisateur>{
	
	
	/**
	 * DESCRIPTION : Trouver l'utilisateur par nom 
	 * DATE DERNIERE MODIF : 27 Jan. 2017
	 * PROJET : Gestion Decompte
	 * AUTEUR : ZGUIOUAR
	 *
	 * @param userName
	 * @return
	 * @throws Exception
	 */
	Utilisateur findUserByUserName(String userName) throws Exception;
	
	/**
	 * DESCRIPTION : Trouver tous les sites affectés à l'utilisateur choisi
	 * DATE DERNIERE MODIF : 27 Jan. 2017
	 * PROJET : Gestion Decompte
	 * AUTEUR : ZGUIOUAR
	 * 
	 * @param userName
	 * @return
	 * @throws Exception
	 */
	List<AutorisationDTO> findAllSiteAffectedUser(String userName) throws Exception;
	
	/**
	 * DESCRIPTION : Trouver tous les rôles affectés au profil choisi
	 * DATE DERNIERE MODIF : 27 Jan. 2017
	 * PROJET : Gestion Decompte
	 * AUTEUR : ZGUIOUAR
	 * 
	 * @param idProfil
	 * @return
	 * @throws Exception
	 */
	Set<String> findAllRoleCodeSiteByIdProfil(Integer idProfil) throws Exception;
}
