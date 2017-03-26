package ma.co.marsamaroc.gestion.decomptes.dao.interfaces;

import java.util.List;

import ma.co.marsamaroc.gestion.decomptes.dto.ProfilDTO;
import ma.co.marsamaroc.gestion.decomptes.entite.Profil;



public interface IProfilDAO extends IDao<Profil>{
	
	
	/**
	 * DESCRIPTION : RÉCUPÉRER LA LISTE DES PROFILES
	 * DATE DERNIERE MODIF : 04 Jan. 2017 
	 * PROJET : Gestion Des Tarifs
	 * AUTEUR : LABAL
	 * 
	 * @return List<FamilleDTO>
	 * @throws Exception
	 */
	List<ProfilDTO> findAllProfils() throws Exception;

}
