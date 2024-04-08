package in.nucleusteq.plasma.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
import in.nucleusteq.plasma.service.JwtService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;

import java.io.IOException;

@Component
public class AuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private TokenService tokenService;

	@Autowired
	private JwtService jwtService;

	@Autowired
	private CustomUserDetailsService customUserDetails;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String token = extractTokenFromRequest(request);

		if (token == null || token.isEmpty()) {
			sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "Access denied: Unauthorized Access");
			return;
		}

		try {
			String username = jwtService.getUsername(token);
			String name = tokenService.getUserName(token);
			System.out.println("name :"+name);
			if (username == null) {
				sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "Access denied: invalid token");
				return;
			}

			UserDetails userDetails = customUserDetails.loadUserByUsername(username);
			tokenService.validateToken(token);

			UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null,
					userDetails.getAuthorities());
			authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			SecurityContextHolder.getContext().setAuthentication(authToken);
			filterChain.doFilter(request, response);

		} catch (ExpiredJwtException e) {
			sendErrorResponse(response, HttpServletResponse.SC_FORBIDDEN, "Access denied: expired token");
		} catch (MalformedJwtException e) {
			sendErrorResponse(response, HttpServletResponse.SC_FORBIDDEN, "Access denied: Invalid Token");
		} catch (SignatureException e) {
			sendErrorResponse(response, HttpServletResponse.SC_FORBIDDEN, "Access denied: Invalid token");
		} catch (TokenServiceException e) {
			sendErrorResponse(response, HttpServletResponse.SC_FORBIDDEN, "Access denied: Invalid token");
		}
	}

	private String extractTokenFromRequest(HttpServletRequest request) {
		String authHeader = request.getHeader("Authorization");
		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			return authHeader.substring(7);
		}

		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if ("SessionCookie".equals(cookie.getName())) {
					return cookie.getValue();
				}
			}
		}

		return null;
	}

	private void sendErrorResponse(HttpServletResponse response, int status, String message) throws IOException {
		response.setStatus(status);
		response.setContentType("application/json");
		response.getWriter().write("{\"status\": " + status + ", \"error\": \"" + message + "\"}");
	}
}
