package ma.co.marsamaroc.gestion.decomptes.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ma.co.marsamaroc.gestion.decomptes.dao.interfaces.IBordereauPrixDAO;
import ma.co.marsamaroc.gestion.decomptes.dto.BordereauPrixDTO;
import ma.co.marsamaroc.gestion.decomptes.services.interfaces.IBordereauPrixService;

@Service
@Transactional("transactionManager")
public class BordereauPrixSerive implements IBordereauPrixService{
	
	@Autowired
	IBordereauPrixDAO bordereauPrixDAO;

	@Override
	public List<BordereauPrixDTO> findBordereauPrixByDesigniations(List<String> designiations) throws Exception {
		return bordereauPrixDAO.findBordereauPrixByDesigniations(designiations);
	}

}
