package ma.co.marsamaroc.gestion.decomptes.services.interfaces;

import java.util.List;
import java.util.Map;

import ma.co.marsamaroc.gestion.decomptes.dto.BonReceptionDTO;

public interface IBonReceptionService {
	
	/**
	 * DESCRIPTION : Trouver Bons Reception par idsDecompte
	 * DATE DERNIERE MODIF : 16 Fév. 2017
	 * PROJET : Gestion Décompte
	 * AUTEUR : LABAL
	 * 
	 * @param List<Integer> idsDecompte
	 * @return Map<String,BonReceptionDTO>
	 */
	Map<String,BonReceptionDTO> findBonReceptionsByidsDecompte(List<Integer> idsDecompte) throws Exception;
}
