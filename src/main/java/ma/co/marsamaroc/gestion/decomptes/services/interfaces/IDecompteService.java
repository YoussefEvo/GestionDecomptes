package ma.co.marsamaroc.gestion.decomptes.services.interfaces;

import java.util.List;

import ma.co.marsamaroc.gestion.decomptes.dto.AttachementDTO;
import ma.co.marsamaroc.gestion.decomptes.dto.DecompteDTO;
import ma.co.marsamaroc.gestion.decomptes.entite.Decompte;

public interface IDecompteService {
	
	
	/**
	 * DESCRIPTION : Trouver Décomptes par idMarche
	 * DATE DERNIERE MODIF : 01 Fév. 2017
	 * PROJET : Gestion Décompte
	 * AUTEUR : LABAL
	 * 
	 * @param idMarche
	 * @return List<DecompteDTO>
	 */
	List<DecompteDTO> findDecomptesByidMarche(Integer idMarche) throws Exception;
	
	/**
	 * DESCRIPTION : Trouver Décompte par idDecompte
	 * DATE DERNIERE MODIF : 08 Fév. 2017
	 * PROJET : Gestion Décompte
	 * AUTEUR : LABAL
	 * 
	 * @param idDecompte
	 * @return Decompte
	 */
	Decompte findDecompteByid(Integer idDecompte) throws Exception;	
	
	/**
	 * DESCRIPTION : Trouver DecompteDTO par idDecompte
	 * DATE DERNIERE MODIF : 02 Fév. 2017
	 * PROJET : Gestion Décompte
	 * AUTEUR : LABAL
	 * 
	 * @param idDecompte
	 * @return DecompteDTO
	 */
	DecompteDTO findDecompteDTOByid(Integer idDecompte) throws Exception;
	
	/**
	 * DESCRIPTION : créer un Décompte
	 * DATE DERNIERE MODIF : 08 Fév. 2017
	 * PROJET : Gestion Décompte
	 * AUTEUR : LABAL
	 * @param decompteDTO
	 * @param List<AttachementDTO>
	 * @throws Exception 
	 */
	
	void createDecompte(DecompteDTO decompte,List<AttachementDTO> listSelectedAttachements) throws Exception;
	
	/**
	 * DESCRIPTION : Suppriper un Décompte
	 * DATE DERNIERE MODIF : 08 Fév. 2017
	 * PROJET : Gestion Décompte
	 * AUTEUR : LABAL
	 * @param decompteDTO
	 * @throws Exception 
	 */
	
	void deleteDecompte(DecompteDTO decompteDTO) throws Exception;

}
