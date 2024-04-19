package in.nucleusteq.plasma.feignservice;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import feign.Param;
import feign.RequestLine;
import in.nucleusteq.plasma.exception.TokenServiceException;

@FeignClient(name = "tokenClient", url = "http://localhost:9099", fallback = FallbackTokenServiceClient.class, configuration = TokenClientConfiguration.class)
@Component
public interface TokenService {

	@RequestLine("GET /plasma/token/username?token={token}")
	String getUserName(@Param("token") String token) throws TokenServiceException;
	
	@RequestLine("GET /plasma/token/validate-token?token={token}")
	Boolean validateToken(@Param("token") String token) throws TokenServiceException;

}