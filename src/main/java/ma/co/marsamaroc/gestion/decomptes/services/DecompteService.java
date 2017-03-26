package ma.co.marsamaroc.gestion.decomptes.services;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ma.co.marsamaroc.gestion.decomptes.dao.Dao;
import ma.co.marsamaroc.gestion.decomptes.dao.interfaces.IAttachementDAO;
import ma.co.marsamaroc.gestion.decomptes.dao.interfaces.IDecompteDAO;
import ma.co.marsamaroc.gestion.decomptes.dto.AttachementDTO;
import ma.co.marsamaroc.gestion.decomptes.dto.DecompteDTO;
import ma.co.marsamaroc.gestion.decomptes.entite.Decompte;
import ma.co.marsamaroc.gestion.decomptes.entite.Marche;
import ma.co.marsamaroc.gestion.decomptes.services.interfaces.IDecompteService;
import ma.co.marsamaroc.gestion.decomptes.utils.Constantes;
import ma.co.marsamaroc.gestion.decomptes.utils.Utilitaires;

@Service
@Transactional("transactionManager")
public class DecompteService extends Dao<Decompte> implements IDecompteService{
	
	public DecompteService(){
		super(Decompte.class);
	}
	
	@Autowired
	private IDecompteDAO decompteDAO;
	@Autowired
	private IAttachementDAO attachementDAO;

	public IDecompteDAO getDecompteDAO() {
		return decompteDAO;
	}

	@Override
	public List<DecompteDTO> findDecomptesByidMarche(Integer idMarche) throws Exception {
		return decompteDAO.findDecomptesByidMarche(idMarche);
	}

	@Override
	public DecompteDTO findDecompteDTOByid(Integer idDecompte) throws Exception {
		return decompteDAO.findDecompteDTOByid(idDecompte);
	}

	@Override
	public void createDecompte(DecompteDTO decompteDTO,List<AttachementDTO> listSelectedAttachements) throws Exception {
		Set<Integer> idsAttachements = new HashSet<>();
		Decompte newDecompte = new Decompte();
		Marche marche = new Marche();
		Double montantTtc = 0.0;
		marche.setIdMarche(decompteDTO.getIdMarche());
		newDecompte.setMarche(marche);
		newDecompte.setNumDecompte(decompteDTO.getNumDecompte());
		newDecompte.setDateEtablissement(decompteDTO.getDateEtablissement());
		newDecompte.setRetenuGarantie(decompteDTO.getRetenuGarantie());
		newDecompte.setPenaliteRetard(decompteDTO.getPenaliteRetard());
		newDecompte.setRetenuAvance(decompteDTO.getRetenuAvance());
		newDecompte.setRevisionPrix(decompteDTO.getRevisionPrix());
		newDecompte.setAutresRetenues(decompteDTO.getAutresRetenues());
		newDecompte.setRetenueSource(decompteDTO.getRetenueSource());
		newDecompte.setFlagDernier(decompteDTO.getFlagDernier());
		newDecompte.setFlagDernier(decompteDTO.getFlagDernier());
		newDecompte.setDatCreat(new Date());
		newDecompte.setIndSupp(Constantes.GlobalConstant.NOT_SUPPRIME);
		newDecompte.setUserCreat(Utilitaires.getCurrentUser());
		
		//FlagDernier
		if(decompteDTO.getDdPartiel()){
			if(decompteDTO.getDdGlobal()){
				newDecompte.setFlagDernier(Constantes.GlobalConstant.DECOMPTE_DEFINITIF_GLOBAL);
			}else{
				newDecompte.setFlagDernier(Constantes.GlobalConstant.DECOMPTE_DEFINITIF_PARTIEL);
			}
		}else{
			newDecompte.setFlagDernier(Constantes.GlobalConstant.DECOMPTE_DEFINITIF_PROVISOIRE);
		}
		
		for (AttachementDTO attachementDTO : listSelectedAttachements) {
			idsAttachements.add(attachementDTO.getIdAttachement());
			if(null != attachementDTO.getMontant()){
				montantTtc+=attachementDTO.getMontant();
			}	
		}
		montantTtc-=decompteDTO.getRetenuGarantie();
		montantTtc-=decompteDTO.getPenaliteRetard();
		montantTtc-=decompteDTO.getRetenuAvance();
		montantTtc-=decompteDTO.getRevisionPrix();
		montantTtc-=decompteDTO.getAutresRetenues();
		montantTtc-=decompteDTO.getRetenueSource();
		newDecompte.setMontantTtc(montantTtc);
		
		Integer idDecompte  = entityManager.merge(newDecompte).getIdDecompte();
		attachementDAO.affectedDecompteToAttachements(idsAttachements,idDecompte);
		
	}

	@Override
	public Decompte findDecompteByid(Integer idDecompte) throws Exception {		
		return decompteDAO.rechercherParKP(idDecompte);
	}

	@Override
	public void deleteDecompte(DecompteDTO decompteDTO) throws Exception {
		decompteDAO.deleteDecompte(decompteDTO.getIdDecompte());
		attachementDAO.dissociateAttachementByIdDecompte(decompteDTO.getIdDecompte());
	}

}
