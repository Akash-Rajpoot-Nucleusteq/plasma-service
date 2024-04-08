package in.nucleusteq.plasma.service;

import in.nucleusteq.plasma.dao.EmployeeRepository;
import in.nucleusteq.plasma.dao.RefreshTokenRepository;
import in.nucleusteq.plasma.domain.Employee;
import in.nucleusteq.plasma.domain.RefreshToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class RefreshTokenService {
    public long refershTokenValidity =  2*60*1000;
    @Autowired
    private RefreshTokenRepository refreshTokenRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    public RefreshToken createRefreshToken(String userName){
        Employee employee = employeeRepository.getByEmail(userName);
        RefreshToken refreshToken = employee.getRefreshToken();

        if(refreshToken == null){
            refreshToken = RefreshToken.builder()
                    .refreshToken(UUID.randomUUID().toString())
                    .expiry(Instant.now().plusMillis(refershTokenValidity))
                    .employee(employeeRepository.getByEmail(userName))
                    .build();
        }else{
            refreshToken.setExpiry(Instant.now().plusMillis(refershTokenValidity));
        }
        employee.setRefreshToken(refreshToken);
        refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }
    public RefreshToken verfiyRefershToken(String refreshToken){
        RefreshToken token = refreshTokenRepository.findByRefreshToken(refreshToken).orElseThrow(()
                -> new RuntimeException("Given Token Does not exist in the database"));
        if(token.getExpiry().compareTo(Instant.now()) < 0){
            refreshTokenRepository.delete(token);
            throw new RuntimeException("Referesh Token Expired");
        }
        return token;
    }
}
