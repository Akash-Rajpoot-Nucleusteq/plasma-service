package in.nucleusteq.plasma.dao;

import in.nucleusteq.plasma.domain.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Integer> {
    Optional <RefreshToken> findByRefreshToken(String token);
}
