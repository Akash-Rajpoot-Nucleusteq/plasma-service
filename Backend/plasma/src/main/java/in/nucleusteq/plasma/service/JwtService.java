package in.nucleusteq.plasma.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;
import com.auth0.jwt.algorithms.Algorithm;

import in.nucleusteq.plasma.dao.EmployeeRepository;
import in.nucleusteq.plasma.entity.Employee;
import in.nucleusteq.plasma.entity.Role;
import in.nucleusteq.plasma.exception.UnauthorizedAccessException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;

@Service
public class JwtService {

	@Autowired
	private EmployeeRepository employeeRepository;

	public static final String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";

	public Boolean validateToken(String token, UserDetails userDetails) {

		try {
			Jwts.parser().verifyWith(getSignKey()).build().parseSignedClaims(token);
			final String username = getUsername(token);
			return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
		} catch (ExpiredJwtException e) {
			throw new UnauthorizedAccessException("Token expired token");

		} catch (MalformedJwtException e) {
			throw new UnauthorizedAccessException("Some changed has done in token !! Invalid Token");
		} catch (SignatureException e) {
			throw new UnauthorizedAccessException("Access denied: Invalid token");
		}
	}

	public String getUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	private Claims extractAllClaims(String token) {
		return Jwts.parser().verifyWith(getSignKey()).build().parseSignedClaims(token).getPayload();
	}

	private Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	public String generateToken(String userName) {
		Map<String, Object> claims = new HashMap<>();
		Employee user = employeeRepository.getByEmail(userName);

		Set<String> roleNames = user.getUserWorkDetail().getRoles().stream().map(Role::getName)
				.collect(Collectors.toSet());
		claims.put("roles", roleNames);

		return createToken(claims, userName);
	}

	private String createToken(Map<String, Object> claims, String userName) {

		@SuppressWarnings("unchecked")
		Set<String> roles = (Set<String>) claims.get("roles");

		return JWT.create().withSubject(userName).withArrayClaim("roles", roles.toArray(new String[0]))
				.withIssuer(userName).withIssuedAt(new Date())
				.withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 1000))
				.sign(Algorithm.HMAC256(getSignKey().getEncoded()));
	}

	public int getExpirationTimeInSeconds(String token) {

		Claims claims = Jwts.parser().verifyWith(getSignKey()).build().parseSignedClaims(token).getPayload();
		Date expirationDate = claims.getExpiration();
		return (int) (expirationDate.getTime() / 1000);
	}

	private SecretKey getSignKey() {
		byte[] keyBytes = Decoders.BASE64.decode(SECRET);
		return Keys.hmacShaKeyFor(keyBytes);
	}

}