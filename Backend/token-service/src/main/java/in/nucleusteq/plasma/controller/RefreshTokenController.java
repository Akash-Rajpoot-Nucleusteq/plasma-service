package in.nucleusteq.plasma.controller;

import in.nucleusteq.plasma.domain.Employee;
import in.nucleusteq.plasma.domain.JwtResponce;
import in.nucleusteq.plasma.domain.RefreshToken;
import in.nucleusteq.plasma.domain.RefreshTokenRequest;
import in.nucleusteq.plasma.service.JwtService;
import in.nucleusteq.plasma.service.RefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("plasma/refresh-token")
public class RefreshTokenController {
    @Autowired
    private RefreshTokenService refreshTokenService;
    @Autowired
    private JwtService jwtService;

    @PostMapping("/create")
    public RefreshToken createRefreshToken(@RequestParam String userName) {
        return refreshTokenService.createRefreshToken(userName);
    }
    @GetMapping("/verify")
    public RefreshToken verifyRefreshToken(@RequestParam String refreshToken) {
        return refreshTokenService.verfiyRefershToken(refreshToken);
    }
    @PostMapping("/refresh")
    public JwtResponce refreshJwtToken(@RequestBody RefreshTokenRequest request){
         RefreshToken refreshToken = refreshTokenService
                 .verfiyRefershToken(request.getRefreshToken());

        Employee employee = refreshToken.getEmployee();
        String token = jwtService.generateToken(employee.getEmail());
        return JwtResponce.builder()
                .JwtToken(token)
                .refreshToken(refreshToken.getRefreshToken())
                .userName(employee.getEmail())
                .build();
    }
}