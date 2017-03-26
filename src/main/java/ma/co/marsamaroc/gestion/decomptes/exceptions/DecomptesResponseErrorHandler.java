package ma.co.marsamaroc.gestion.decomptes.exceptions;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestClientException;

import ma.co.marsamaroc.gestion.decomptes.utils.Constantes;
import ma.co.marsamaroc.gestion.decomptes.utils.Utilitaires;


public class DecomptesResponseErrorHandler implements ResponseErrorHandler{
    @Override
	public void handleError(ClientHttpResponse response) throws IOException {
    	
    	 HttpStatus statusCode = response.getStatusCode();
    	    switch (statusCode.series()) {
    	        case CLIENT_ERROR:
    	            throw new HttpClientErrorException(statusCode, response.getStatusText()) ;
    	        case SERVER_ERROR:
    	        	HttpHeaders header = response.getHeaders();
    	        	StringBuilder msgError = new StringBuilder();
    	        	if (null != header && header.containsKey(Constantes.WebService.ERROR_RESPONSE_MESSAGE)) {
    	        		msgError.append(Constantes.WebService.ERROR_RESPONSE_STATUS_CODE_STR)
    	        				.append(header.get(Constantes.WebService.ERROR_RESPONSE_STATUS_CODE))
    	        				.append(Constantes.WebService.ERROR_RESPONSE_MESSAGE_STR)
    	        				.append(header.get(Constantes.WebService.ERROR_RESPONSE_MESSAGE))
    	        				.append(Constantes.WebService.ERROR_RESPONSE_METHODE_STR)
    	        				.append(header.get(Constantes.WebService.ERROR_RESPONSE_METHODE));
    	        		throw new HttpClientErrorException(statusCode, msgError.toString());
					}else{
						throw new HttpClientErrorException(statusCode, response.getStatusText()) ;
					}
    	        default:
    	            throw new RestClientException("Unknown status code [" + statusCode + "]");
    	    }
    	
	}

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return Utilitaires.isError(response.getStatusCode());
    }

}
