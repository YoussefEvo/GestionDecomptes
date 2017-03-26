package ma.co.marsamaroc.gestion.decomptes.services;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.comparators.ComparatorChain;
import org.apache.commons.collections.comparators.NullComparator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import ma.co.marsamaroc.gestion.decomptes.dao.interfaces.IAttachementDAO;
import ma.co.marsamaroc.gestion.decomptes.dto.AttachementDTO;
import ma.co.marsamaroc.gestion.decomptes.dto.BonReceptionDTO;
import ma.co.marsamaroc.gestion.decomptes.dto.BordereauPrixDTO;
import ma.co.marsamaroc.gestion.decomptes.dto.EditionAttachementDTO;
import ma.co.marsamaroc.gestion.decomptes.dto.MarcheDTO;
import ma.co.marsamaroc.gestion.decomptes.dto.ParamWsApiproDTO;
import ma.co.marsamaroc.gestion.decomptes.edition.excel.AttachementExcel;
import ma.co.marsamaroc.gestion.decomptes.entite.Attachement;
import ma.co.marsamaroc.gestion.decomptes.exceptions.DecomptesResponseErrorHandler;
import ma.co.marsamaroc.gestion.decomptes.services.interfaces.IAttachementService;
import ma.co.marsamaroc.gestion.decomptes.utils.Constantes;
import ma.co.marsamaroc.gestion.decomptes.utils.Utilitaires;

@Service
@Transactional("transactionManager")
public class AttachementService implements IAttachementService {

	@Autowired
	private IAttachementDAO attachementDAO;
	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private AttachementExcel attachementExcel;

	@Override
	public List<AttachementDTO> findAttachementsByidDecompte(Integer idDecompte) throws Exception {
		return attachementDAO.findAttachementsByidDecompte(idDecompte);
	}
	
	@Override
	public List<AttachementDTO> findAttachementsNonDecompteByidMarche(Integer idMarche) throws Exception {
		return attachementDAO.findAttachementsNonDecompteByidMarche(idMarche);
	}
	
	
	@Override
	@SuppressWarnings("unchecked")
	public List<MarcheDTO> wsGetListMarche(String codeSite) {
		List<MarcheDTO> listMarches = new ArrayList<>();
		ParameterizedTypeReference<List<MarcheDTO>> typeRef = new ParameterizedTypeReference<List<MarcheDTO>>() {};
		restTemplate.setErrorHandler(new DecomptesResponseErrorHandler());
		String url = messageSource.getMessage(Constantes.WebService.URL_WS_APIPRO, null, null) ;
		if(StringUtils.isNotEmpty(url)){
			HttpHeaders requestHeaders = new HttpHeaders();
			requestHeaders.setContentType(new MediaType("application", "json", Charset.forName("utf-8")));
			requestHeaders.set("Accept", "application/json");
			HttpEntity<String> request = new HttpEntity<String>(codeSite, requestHeaders);

			ResponseEntity<List<MarcheDTO>> response = restTemplate.exchange(url.trim() + "listMarche/", HttpMethod.POST,
					request, typeRef);
			if (HttpStatus.OK.equals(response.getStatusCode())) {
				listMarches = response.getBody();
				if (CollectionUtils.isNotEmpty(listMarches)) {
					ComparatorChain comparatorChain = new ComparatorChain();
			        comparatorChain.addComparator(new NullComparator(new BeanComparator("dateDemarage")));
			        Collections.sort(listMarches, comparatorChain);
			        Collections.reverse(listMarches);
				}
			}
		}
		return listMarches;
	}
	
	public List<BordereauPrixDTO> wsGetBordereauPrixByNumMarche(String numMarche) {
		List<BordereauPrixDTO> listBordereauPrix = new ArrayList<>();
		ParameterizedTypeReference<List<BordereauPrixDTO>> typeRef = new ParameterizedTypeReference<List<BordereauPrixDTO>>() {};
		restTemplate.setErrorHandler(new DecomptesResponseErrorHandler());
		String url = messageSource.getMessage(Constantes.WebService.URL_WS_APIPRO, null, null) ;
		if(StringUtils.isNotEmpty(url)){
			HttpHeaders requestHeaders = new HttpHeaders();
			requestHeaders.setContentType(new MediaType("application", "json", Charset.forName("utf-8")));
			requestHeaders.set("Accept", "application/json");
			HttpEntity<String> request = new HttpEntity<String>(numMarche, requestHeaders);
			ResponseEntity<List<BordereauPrixDTO>> response = restTemplate.exchange(url.trim() + "bordereauPrixMarche/", HttpMethod.POST,
					request, typeRef);
			if (HttpStatus.OK.equals(response.getStatusCode())) {
				listBordereauPrix = response.getBody();
			}
		}
		return listBordereauPrix;
	}
	
	@Override
	public void verificationMarche(Integer idSite, MarcheDTO marcheApipro) throws Exception {
		MarcheDTO marcheDTO = attachementDAO.findMarcheByNum(marcheApipro.getNumMarche());
		if (null == marcheDTO) {
			marcheApipro.setIdMarche(attachementDAO.createMarche(idSite, marcheApipro));
			List<BordereauPrixDTO> listBordereauPrix =  wsGetBordereauPrixByNumMarche(marcheApipro.getNumMarche());
			attachementDAO.createBordereauPrix(listBordereauPrix, marcheApipro.getIdMarche());
		}else{
			marcheApipro.setFlagCloture(marcheDTO.getFlagCloture());
			marcheApipro.setIdMarche(marcheDTO.getIdMarche());
		}
	}

	@Override
	public Set<Attachement> findAttachementsByIdsAttachement(Set<Integer> idsAttachement) throws Exception {
		return attachementDAO.findAttachementsByIdsAttachement(idsAttachement);
	}

	@Override
	public void affectedDecompteToAttachements(Set<Integer> idsAttachement,Integer idDecompte) throws Exception {
		 attachementDAO.affectedDecompteToAttachements(idsAttachement,idDecompte);
	}

	
	@Override
	public List<AttachementDTO> findAttachementByIdMarche(Integer idMarche) throws Exception {
		List<AttachementDTO> listAtt = attachementDAO.findAttachementByIdMarche(idMarche);
		if(CollectionUtils.isNotEmpty(listAtt)){
	        AttachementDTO attachement = listAtt.get(0);
	        if(!StringUtils.isNotBlank(attachement.getNumDecompte())){
	        	attachement.setFlagSupprime(Constantes.GlobalConstant.OUI_O);
	        }
		}
		
		return listAtt;
	}
	
	@Override
	public void deleteAttachementById(Integer idAttachement) throws Exception{
		attachementDAO.deleteAttachementById(idAttachement);
		attachementDAO.deleteBnRecepByIdAtt(idAttachement);
	}

	@Override
	public List<BonReceptionDTO> wsGetListBonReceps(Integer idMarche, String numMarche, Date dateDebut, Date dateFin) throws Exception {
		List<BonReceptionDTO> listBonReception = new ArrayList<>();
		Set<String> listBnRecpIgnore = listBnRecpIgnore(idMarche);
		ParameterizedTypeReference<List<BonReceptionDTO>> typeRef = new ParameterizedTypeReference<List<BonReceptionDTO>>() {};
		restTemplate.setErrorHandler(new DecomptesResponseErrorHandler());
		ParamWsApiproDTO paramWsApipro = new ParamWsApiproDTO();
		String url = messageSource.getMessage(Constantes.WebService.URL_WS_APIPRO, null, null) ;
		if(StringUtils.isNotEmpty(url)){
			paramWsApipro.setCodeMarche(numMarche);
			paramWsApipro.setDateDebut(dateDebut);
			paramWsApipro.setDateFin(dateFin);
			paramWsApipro.setListBnRecpIgnore(listBnRecpIgnore);
			HttpHeaders requestHeaders = new HttpHeaders();
			requestHeaders.setContentType(new MediaType("application", "json", Charset.forName("utf-8")));
			requestHeaders.set("Accept", "application/json");
			HttpEntity<ParamWsApiproDTO> request = new HttpEntity<ParamWsApiproDTO>(paramWsApipro, requestHeaders);
			ResponseEntity<List<BonReceptionDTO>> response = restTemplate.exchange(url.trim() + "listBonReceptionMarche/", HttpMethod.POST,request, typeRef);
			if (HttpStatus.OK.equals(response.getStatusCode())) {
				listBonReception = response.getBody();
			}
		}
		return listBonReception;
	}
	
	public Set<String> listBnRecpIgnore(Integer idMarche) throws Exception{
		Set<String> setBnRecpIgnore =  new HashSet<>();
		List<BonReceptionDTO> listBnRecpIgnore = attachementDAO.listBnRecepIgnoreByIdMarch(idMarche);
		if (CollectionUtils.isNotEmpty(listBnRecpIgnore)) {
			for (BonReceptionDTO bonReception : listBnRecpIgnore) {
				StringBuilder key = new StringBuilder();
            	if (StringUtils.isNotBlank(bonReception.getNumBonReception())) {
            		key.append(bonReception.getNumBonReception()).append(Constantes.GlobalConstant.SHARP_SEPARATOR);
				}else{
					key.append(Constantes.GlobalConstant.SHARP_SEPARATOR);
				}
            	if (null != bonReception.getDateReception() ) {
            		key.append(bonReception.getDateReceptionStr()).append(Constantes.GlobalConstant.SHARP_SEPARATOR);
				}else{
					key.append(Constantes.GlobalConstant.SHARP_SEPARATOR);
				}
            	if (StringUtils.isNotBlank(bonReception.getDesigniationBonReception())) {
            		key.append(bonReception.getDesigniationBonReception()).append(Constantes.GlobalConstant.SHARP_SEPARATOR);
				}else{
					key.append(Constantes.GlobalConstant.SHARP_SEPARATOR);
				}
            	setBnRecpIgnore.add(key.toString());
			}
		}
		return setBnRecpIgnore;
	}

	@Override
	public String generateSequenceMarche(Integer idMarche, String typeMarche) throws Exception {
		String lastSequence = null;
		AttachementDTO att = attachementDAO.lastSequenceMarche(idMarche);
		if (null != att) {
			lastSequence = att.getNumAttachement();
			if (StringUtils.isNotBlank(lastSequence)) {
				if(Constantes.GlobalConstant.MARCHE_CADRE.equalsIgnoreCase(typeMarche)){
					Integer annee =  Utilitaires.getInteger(lastSequence.substring(lastSequence.length()-4, lastSequence.length()));
					Integer ordre = Utilitaires.getInteger(lastSequence.substring(0, lastSequence.length()-5));
					if (Constantes.GlobalConstant.OUI_O.equalsIgnoreCase(att.getFlagDernier())) {
						annee = annee + 1;
						lastSequence = "01" +  Constantes.GlobalConstant.SLASH_SEPARATOR  + annee.toString();
					}else{
						ordre = ordre + 1;
						lastSequence = ordre > 9 ? ordre.toString() : "0" + ordre.toString();
						lastSequence = 	lastSequence +  Constantes.GlobalConstant.SLASH_SEPARATOR  + annee.toString();
					}
				}else{
					if (Constantes.GlobalConstant.OUI_O.equalsIgnoreCase(att.getFlagDernier())) {
						throw new Exception("marche ferme, vous pouver pas créer un attachement");
					}else{
						Integer ordre = Utilitaires.getInteger(lastSequence) + 1;
						lastSequence = ordre.toString();
					}
				}
			}else{
				throw new Exception("Attachement sans numéro");
			}
		}else{
			if(!Constantes.GlobalConstant.MARCHE_CADRE.equalsIgnoreCase(typeMarche)){
				lastSequence = Constantes.GlobalConstant.CONST_1_STR;
			}
		}
		return lastSequence;
	}
	
	public String generateSequenceMarcheFirstTime(List<BonReceptionDTO> listBnReceps){
		List<Date> listDates = new ArrayList<>();
		for (BonReceptionDTO bonReceptionDTO : listBnReceps) {
			listDates.add(bonReceptionDTO.getDateReception());
		}
		Date LastDate = Collections.max(listDates);
		int year = Integer.valueOf(String.format(Constantes.GlobalConstant.DATE_YEAR_PATTERN,LastDate));
		return "01" + Constantes.GlobalConstant.SLASH_SEPARATOR + year;
	}
	
	public Double calcMontant(List<BonReceptionDTO> listBnReceps){
		Double montant = new Double(0);
		for (BonReceptionDTO bonReceptionDTO : listBnReceps) {
			montant = montant + (bonReceptionDTO.getMontant());
		}
		return montant;
	}

	@Override
	public String createAttachement(MarcheDTO marche, AttachementDTO attachement, List<BonReceptionDTO> listBnReceps)
			throws Exception {
		if (StringUtils.isBlank(attachement.getNumAttachement())) {
			attachement.setNumAttachement(generateSequenceMarcheFirstTime(listBnReceps));
		}else{
			attachement.setNumAttachement(generateSequenceMarche(marche.getIdMarche(), marche.getTypeMarche()));
		}
		attachement.setMontant(calcMontant(listBnReceps));
		// Create attachement
		Integer idAttachement = attachementDAO.createAttachementMarche(marche.getIdMarche(), attachement);
		// Create bons de reception
		attachementDAO.createBonsReception(idAttachement, listBnReceps);
		return attachement.getNumAttachement();
	}
	

	@Override
	public String exportExcel(AttachementDTO attachementDTO, MarcheDTO marcheDTO, String codeSite, String codeIso, String type, String url) throws Exception {
		// get Liste des bons de réception
		EditionAttachementDTO editionAttachementDTO = getListBnReceptionAEditer(attachementDTO, marcheDTO.getIdMarche());
		// Construire le nom du fichier
		long ctime = System.currentTimeMillis();
		StringBuilder fileName = new StringBuilder();
		if (Constantes.GlobalConstant.CONST_V.equalsIgnoreCase(type)) {
			fileName.append(Constantes.GlobalConstant.ATTACHEMENT_VALORISER);
		} else {
			fileName.append(Constantes.GlobalConstant.ATTACHEMENT_NON_VALORISER);
		}
		fileName.append("_").append(marcheDTO.getNumMarche().replace("/", "-"))
				.append("_").append(attachementDTO.getNumAttachement().replace("/", "-"))
				.append("_").append(Utilitaires.dateToString(new Date(), Constantes.GlobalConstant.DATE_PATTERN_WITH_DASH))
				.append("_").append(ctime)
				.append(Constantes.GlobalConstant.XLSX_EXTENSION);
		url = url + fileName;
		// set les valeurs
		editionAttachementDTO.setMarcheDTO(marcheDTO);
		editionAttachementDTO.setAttachementDTO(attachementDTO);
		editionAttachementDTO.setSiteCode(codeSite);
		editionAttachementDTO.setCodeIso(codeIso);
		editionAttachementDTO.setTypeEdition(type);
		// générer le fichier excel
		attachementExcel.exportExcel(editionAttachementDTO, type, url);
		return url;
	}

	@Override
	public String exportPdf(AttachementDTO attachementDTO, MarcheDTO marcheDTO, String codeSite, String type, String url) throws Exception {
		return null;
	}

	public EditionAttachementDTO getListBnReceptionAEditer(AttachementDTO attachementDTO, Integer idMarche) throws Exception {
		EditionAttachementDTO editionAttachementDTO = null;
		Integer premierIdAttachement =  attachementDAO.getIdAttEtDernier(attachementDTO.getIdAttachement(), idMarche);
		List<BonReceptionDTO> listBonReception = attachementDAO.getListBonReceptionByIdAttachement(attachementDTO.getIdAttachement(), idMarche);
		List<BonReceptionDTO> listSumBonReception = attachementDAO.getSumListBonReception(attachementDTO.getIdAttachement(), premierIdAttachement, idMarche);
		String firstKey = null;
		String secondKey = null;
		for (BonReceptionDTO bonReception : listBonReception) {
			firstKey = bonReception.getDesigniationBonReception() + bonReception.getUnite() + bonReception.getPrixUnitaireStr();
			for (BonReceptionDTO bonSumReception : listSumBonReception) {
				secondKey = bonSumReception.getDesigniationBonReception() + bonSumReception.getUnite() + bonSumReception.getPrixUnitaireStr();
				if (firstKey.equalsIgnoreCase(secondKey)) {
					bonSumReception.setQteAttachement(bonReception.getQte());
					break;
				}
			}
		}
		if (CollectionUtils.isNotEmpty(listSumBonReception)) {
			editionAttachementDTO = new EditionAttachementDTO ();
			editionAttachementDTO.setAttachementDTO(attachementDTO);
			editionAttachementDTO.setListBonReception(listSumBonReception);
		}
		return editionAttachementDTO;
	}

	@Override
	public List<BonReceptionDTO> findAllBonsRecepByIdAttachement(Integer idAttachement) throws Exception {
		return attachementDAO.findAllBonsRecepByIdAttachement(idAttachement);
	}
	
}
