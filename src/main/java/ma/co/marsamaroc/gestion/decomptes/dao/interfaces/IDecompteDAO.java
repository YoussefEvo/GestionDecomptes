package ma.co.marsamaroc.gestion.decomptes.dao.interfaces;

import java.util.List;

import ma.co.marsamaroc.gestion.decomptes.dto.DecompteDTO;
import ma.co.marsamaroc.gestion.decomptes.entite.Decompte;

public interface IDecompteDAO extends IDao<Decompte>{
	
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
	 * DESCRIPTION : Trouver DécompteDTO par idDecompte
	 * DATE DERNIERE MODIF : 02 Fév. 2017
	 * PROJET : Gestion Décompte
	 * AUTEUR : LABAL
	 * 
	 * @param idDecompte
	 * @return DecompteDTO
	 */
	DecompteDTO findDecompteDTOByid(Integer idDecompte) throws Exception;

	/**
	 * DESCRIPTION : Suppriper un Décompte
	 * DATE DERNIERE MODIF : 08 Fév. 2017
	 * PROJET : Gestion Décompte
	 * AUTEUR : LABAL
	 * @param idDecompte
	 * @throws Exception 
	 */
	
	void deleteDecompte(Integer idDecompte) throws Exception;
}
