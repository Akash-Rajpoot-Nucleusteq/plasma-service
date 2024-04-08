package in.nucleusteq.plasma.controller;

import in.nucleusteq.plasma.exception.UnauthorizedAccessException;
import in.nucleusteq.plasma.service.CustomUserDetailsService;
import in.nucleusteq.plasma.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("plasma/token")
public class TokenController {

	@Autowired
	private JwtService jwtService;

	@Autowired
	private CustomUserDetailsService customUserDetails;

	@PostMapping("/generate-token")
	public ResponseEntity<String> generateToken(@RequestParam("username") String username) {
		String token = jwtService.generateToken(username);
		return ResponseEntity.ok(token);
	}
	@GetMapping("/validate-token")
	public boolean validateToken(@RequestParam("token") String token) {
	    try {
	        String username = jwtService.getUsername(token);
	        if (username == null) {
	            throw new UnauthorizedAccessException("Access denied: invalid token");
	        }
	        UserDetails userDetails = customUserDetails.loadUserByUsername(username);
	        return jwtService.validateToken(token, userDetails);
	    } catch (Exception e) {
	        return false; 
	    }
	}
	@GetMapping("/expiration-time")
	public ResponseEntity<Integer> getExpirationTime(@RequestParam("token") String token) {

		int expirationTimeInSeconds = jwtService.getExpirationTimeInSeconds(token);
		return ResponseEntity.ok(expirationTimeInSeconds);
	}
	@GetMapping("/username")
	public ResponseEntity<String> getUserName(@RequestParam("token") String token) {

		String userName = jwtService.getUsername(token);
		return ResponseEntity.ok(userName);
	}
}
