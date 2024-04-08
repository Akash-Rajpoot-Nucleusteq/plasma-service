package in.nucleusteq.plasma.feignservice;

import feign.Param;
import feign.RequestLine;
import in.nucleusteq.plasma.domain.JwtResponce;
import in.nucleusteq.plasma.domain.RefreshToken;
import in.nucleusteq.plasma.domain.RefreshTokenRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "refreshTokenService", url = "http://localhost:9099")
@Component
public interface RefreshTokenSevice {
    @RequestLine("POST /plasma/refresh-token/create?userName={userName}")
    RefreshToken createRefreshToken(@Param("userName") String userName);
}
