package ma.co.marsamaroc.gestion.decomptes.dao.interfaces;

import java.util.List;

import ma.co.marsamaroc.gestion.decomptes.dto.BonReceptionDTO;
import ma.co.marsamaroc.gestion.decomptes.entite.BonReception;

public interface IBonReceptionDAO extends IDao<BonReception>{
	
	
	/**
	 * DESCRIPTION : Trouver Bons Reception par idsDecompte
	 * DATE DERNIERE MODIF : 16 Fév. 2017
	 * PROJET : Gestion Décompte
	 * AUTEUR : LABAL
	 * 
	 * @param List<Integer> idsDecompte
	 * @return List<BonReceptionDTO>
	 */
	List<BonReceptionDTO> findBonReceptionsByidsDecompte(List<Integer> idsDecompte) throws Exception;

}
