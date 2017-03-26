package ma.co.marsamaroc.gestion.decomptes.dao.interfaces;

import java.util.List;

import ma.co.marsamaroc.gestion.decomptes.dto.MarcheDTO;
import ma.co.marsamaroc.gestion.decomptes.entite.Marche;

public interface IMarcheDAO extends IDao<Marche> {
	
	/**
	 * DESCRIPTION : Trouver Marché par id Site 
	 * DATE DERNIERE MODIF : 01 Fév. 2017
	 * PROJET : Gestion Décompte
	 * AUTEUR : LABAL
	 *
	 * @param idSite
	 * @return List<MarcheDTO>
	 */
	List<MarcheDTO> findMarches(Integer idSite);
	
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
