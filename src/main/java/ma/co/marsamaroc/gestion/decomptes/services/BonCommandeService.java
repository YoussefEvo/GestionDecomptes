package ma.co.marsamaroc.gestion.decomptes.services;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

import ma.co.marsamaroc.gestion.decomptes.dao.interfaces.IAttachementBonCmdDAO;
import ma.co.marsamaroc.gestion.decomptes.dto.AttachementDTO;
import ma.co.marsamaroc.gestion.decomptes.dto.BonCommandeDTO;
import ma.co.marsamaroc.gestion.decomptes.exceptions.DecomptesResponseErrorHandler;
import ma.co.marsamaroc.gestion.decomptes.services.interfaces.IBonCommandeService;
import ma.co.marsamaroc.gestion.decomptes.utils.Constantes;

@Service
@Transactional("transactionManager")
public class BonCommandeService implements IBonCommandeService {

	@Autowired
	private IAttachementBonCmdDAO attachementBonCmdDAO;
	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private MessageSource messageSource;

	@Override
	@SuppressWarnings("unchecked")
	public List<BonCommandeDTO> wsGetListBonCommandeDTO(String codeSite) {
		List<BonCommandeDTO> listBonCommandeDTO = new ArrayList<>();
		ParameterizedTypeReference<List<BonCommandeDTO>> typeRef = new ParameterizedTypeReference<List<BonCommandeDTO>>() {};
		restTemplate.setErrorHandler(new DecomptesResponseErrorHandler());
		String url = messageSource.getMessage(Constantes.WebService.URL_WS_APIPRO, null, null) ;
		if(StringUtils.isNotEmpty(url)){
			HttpHeaders requestHeaders = new HttpHeaders();
			requestHeaders.setContentType(new MediaType("application", "json", Charset.forName("utf-8")));
			requestHeaders.set("Accept", "application/json");
			HttpEntity<String> request = new HttpEntity<String>(codeSite, requestHeaders);

			ResponseEntity<List<BonCommandeDTO>> response = restTemplate.exchange(url.trim() + "listBonCmd/", HttpMethod.POST,
					request, typeRef);
			if (HttpStatus.OK.equals(response.getStatusCode())) {
				listBonCommandeDTO = response.getBody();
				if (CollectionUtils.isNotEmpty(listBonCommandeDTO)) {
					ComparatorChain comparatorChain = new ComparatorChain();
			        comparatorChain.addComparator(new NullComparator(new BeanComparator("dateCommande")));
			        Collections.sort(listBonCommandeDTO, comparatorChain);
			        Collections.reverse(listBonCommandeDTO);
				}
			}
		}
		return listBonCommandeDTO;
	}

	@Override
	public void verificationMarche(Integer idSite, BonCommandeDTO bonCommandeApipro) throws Exception {
		BonCommandeDTO bonCommandeDTO = attachementBonCmdDAO.findBonCmdByNum(bonCommandeApipro.getNumBonCommande());
		if (null == bonCommandeDTO) {
			bonCommandeApipro.setIdBonCommande(attachementBonCmdDAO.createBonCmd(idSite, bonCommandeApipro));
		}else{
			bonCommandeApipro.setIdBonCommande(bonCommandeApipro.getIdBonCommande());
		}
	}

	@Override
	public List<AttachementDTO> findAttachementByIdCommande(Integer idBonCommande) throws Exception {
		List<AttachementDTO> listAtt = attachementBonCmdDAO.findAttachementByIdCommande(idBonCommande);
		if(CollectionUtils.isNotEmpty(listAtt)){
	        AttachementDTO attachement = listAtt.get(0);
	        if(!StringUtils.isNotBlank(attachement.getNumDecompte())){
	        	attachement.setFlagSupprime(Constantes.GlobalConstant.OUI_O);
	        }
		}
		return listAtt;
	}
	
}
