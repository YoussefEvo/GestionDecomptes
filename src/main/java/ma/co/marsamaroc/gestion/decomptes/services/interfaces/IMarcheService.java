package ma.co.marsamaroc.gestion.decomptes.services.interfaces;

import java.util.List;

import ma.co.marsamaroc.gestion.decomptes.dto.MarcheDTO;
import ma.co.marsamaroc.gestion.decomptes.entite.Marche;

public interface IMarcheService {
	
	/**
	 * DESCRIPTION : Trouver Marché par idMarche
	 * DATE DERNIERE MODIF : 07 Fév. 2017
	 * PROJET : Gestion Décompte
	 * AUTEUR : LABAL
	 *
	 * @param idMarche
	 * @return Marche
	 */
	Marche findMarcheById(Integer idMarche) throws Exception;	
	
	/**
	 * DESCRIPTION : Trouver Marché par idSite
	 * DATE DERNIERE MODIF : 01 Fév. 2017
	 * PROJET : Gestion Décompte
	 * AUTEUR : LABAL
	 *
	 * @param idSite
	 * @return List<MarcheDTO>
	 */
	List<MarcheDTO> findMarches(Integer idSite) throws Exception;
	
	/**
	 * DESCRIPTION : cloturer Marché
	 * DATE DERNIERE MODIF : 13 Fév. 2017
	 * PROJET : Gestion Décompte
	 * AUTEUR : LABAL
	 *
	 * @param idMarche
	 */
	 void clotureMarche(Integer idMarche) throws Exception;
	 
		/**
		 * DESCRIPTION : liberer Marché
		 * DATE DERNIERE MODIF : 13 Fév. 2017
		 * PROJET : Gestion Décompte
		 * AUTEUR : LABAL
		 *
		 * @param idMarche
		 */
	 void libereMarche(Integer idMarche) throws Exception;

	
}
