package in.nucleusteq.plasma.feignservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import feign.Response;
import in.nucleusteq.plasma.exception.TokenServiceException;


/**
* This class is used for give the response when the token microservice is
* unable to give the token related to a particular user.
* @author abhis
*
*/
@Component
public class FallbackTokenServiceClient implements TokenService {

    /**
     * this is the logger for the message.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(FallbackTokenServiceClient.class);

    @Override
    public Boolean validateToken(String token) throws TokenServiceException {
    	LOGGER.error("Fallback method for validateToken is called");
        return false; 
    }

	@Override
	public String getUserName(String token) throws TokenServiceException {
		LOGGER.error("Fallback method for validateToken is called");
        return "Something went wrong";
	}


}