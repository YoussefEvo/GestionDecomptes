package ma.co.marsamaroc.gestion.decomptes.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ma.co.marsamaroc.gestion.decomptes.dao.interfaces.IBonReceptionDAO;
import ma.co.marsamaroc.gestion.decomptes.dto.BonReceptionDTO;
import ma.co.marsamaroc.gestion.decomptes.services.interfaces.IBonReceptionService;

@Service
@Transactional("transactionManager")
public class BonReceptionService implements IBonReceptionService{

	@Autowired
	IBonReceptionDAO bonReceptionDAO;

	@Override
	public Map<String, BonReceptionDTO> findBonReceptionsByidsDecompte(List<Integer> idsDecompte) throws Exception {
		Map<String, BonReceptionDTO> mapBR = new HashMap<>();
		List<BonReceptionDTO> listBR = bonReceptionDAO.findBonReceptionsByidsDecompte(idsDecompte);
		if(CollectionUtils.isNotEmpty(listBR)){
			for (BonReceptionDTO bonReceptionDTO : listBR) {
				mapBR.put(bonReceptionDTO.getDesigniationBonReception(), bonReceptionDTO);
			} 
		}
		return mapBR;
	}

}
