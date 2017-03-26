package ma.co.marsamaroc.gestion.decomptes.services.interfaces;

import java.util.List;

import ma.co.marsamaroc.gestion.decomptes.dto.BordereauPrixDTO;

public interface IBordereauPrixService {
	
	/**
	 * DESCRIPTION : Trouver BordereauPrix par Designiations
	 * DATE DERNIERE MODIF : 16 Fév. 2017
	 * PROJET : Gestion Décompte
	 * AUTEUR : LABAL
	 * 
	 * @param List<String> designiations
	 * @return List<BordereauPrixDTO>
	 */
	List<BordereauPrixDTO> findBordereauPrixByDesigniations(List<String> designiations) throws Exception;

}
