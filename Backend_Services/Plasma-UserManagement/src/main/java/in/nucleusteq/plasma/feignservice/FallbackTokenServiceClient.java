package in.nucleusteq.plasma.feignservice;

import in.nucleusteq.plasma.domain.JwtResponce;
import in.nucleusteq.plasma.domain.RefreshToken;
import in.nucleusteq.plasma.domain.RefreshTokenRequest;
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
	public String getToken(String username) throws TokenServiceException {
		// TODO Auto-generated method stub
	    LOGGER.info("Unable to connect to service");
        throw new TokenServiceException("FEIGN_EXCEPTION");
		
	}

	@Override
	public Integer getExpireTime(String token) throws TokenServiceException {
	    LOGGER.info("Unable to connect to service");
        throw new TokenServiceException("FEIGN_EXCEPTION");
	}

	@Override
	public RefreshToken createRefreshToken(String userName) {
		return null;
	}

//	@Override
//	public RefreshToken verifyRefreshToken(String refreshToken) {
//		return null;
//	}
//
//	@Override
//	public JwtResponce refreshJwtToken(RefreshTokenRequest request) {
//		return null;
//	}


}