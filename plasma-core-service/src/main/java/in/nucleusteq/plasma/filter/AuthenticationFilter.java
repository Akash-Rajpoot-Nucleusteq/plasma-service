package in.nucleusteq.plasma.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import in.nucleusteq.plasma.exception.TokenServiceException;
import in.nucleusteq.plasma.feignservice.TokenService;
import in.nucleusteq.plasma.service.CustomUserDetailsService;
import java.io.IOException;

@Component
public class AuthenticationFilter extends OncePerRequestFilter {
	@Autowired
	private TokenService tokenService;
	@Autowired
	private CustomUserDetailsService customUserDetails;
	private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationFilter.class);
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String accessToken = extractTokenFromRequest(request);

		if (accessToken == null || accessToken.isEmpty()) {
			sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "Access denied: Unauthorized Access");
			return;
		}
		try {
			String username = getUsernameFromToken(accessToken);
			System.out.println("name :" + username);
			if (username == null) {
				sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "Access denied: invalid token");
				return;
			}
			if (!validateTokenFromTokenService(accessToken)) {
				sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "Access denied: invalid token");
				return;
			}
			UserDetails userDetails = customUserDetails.loadUserByUsername(username);

			UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null,
					userDetails.getAuthorities());
			authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			SecurityContextHolder.getContext().setAuthentication(authToken);
			filterChain.doFilter(request, response);

		} catch (TokenServiceException e) {
			sendErrorResponse(response, HttpServletResponse.SC_FORBIDDEN, "Access denied: Invalid token");
		}
	}
	private boolean validateTokenFromTokenService(String authToken) throws TokenServiceException {
		if (authToken == null || authToken.isBlank()) {
			return false;
		}
		LOGGER.info("Validating dynamic token");
		Boolean status = tokenService.validateToken(authToken);
		LOGGER.info("Response from token service: {}", status);
		return status == Boolean.TRUE;
	}

	private String extractTokenFromRequest(HttpServletRequest request) {
		String authHeader = request.getHeader("Authorization");
		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			return authHeader.substring(7);
		}
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if ("accessToken".equals(cookie.getName())) {
					return cookie.getValue();
				}
			}
		}
		return null;
	}
	private String getUsernameFromToken(String accessToken) throws TokenServiceException {
		return tokenService.getUserName(accessToken);
	}
	private void sendErrorResponse(HttpServletResponse response, int status, String message) throws IOException {
		response.setStatus(status);
		response.setContentType("application/json");
		response.getWriter().write("{\"status\": " + status + ", \"error\": \"" + message + "\"}");
	}
}
