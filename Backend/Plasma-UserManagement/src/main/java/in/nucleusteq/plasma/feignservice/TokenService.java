package in.nucleusteq.plasma.feignservice;

import in.nucleusteq.plasma.domain.JwtResponce;
import in.nucleusteq.plasma.domain.RefreshToken;
import in.nucleusteq.plasma.domain.RefreshTokenRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import feign.Param;
import feign.RequestLine;
import in.nucleusteq.plasma.exception.TokenServiceException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "tokenClient", url = "http://localhost:9099", fallback = FallbackTokenServiceClient.class, configuration = TokenClientConfiguration.class)
@Component
public interface TokenService {
	@RequestLine("POST /plasma/token/generate-token?username={username}")
	String getToken(@Param("username") String username) throws TokenServiceException;
	
	@RequestLine("GET /plasma/token/expiration-time?token={token}")
	Integer getExpireTime(@Param("token") String token) throws TokenServiceException;

	@RequestLine("POST /plasma/refresh-token/create?userName={userName}")
	RefreshToken createRefreshToken(@Param("userName") String userName);

}