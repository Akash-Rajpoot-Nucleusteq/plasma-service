package in.nucleusteq.plasma.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import in.nucleusteq.plasma.feignservice.RefreshTokenSevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import in.nucleusteq.plasma.dao.EmployeeRepository;
import in.nucleusteq.plasma.domain.Employee;
import in.nucleusteq.plasma.domain.RefreshToken;
import in.nucleusteq.plasma.domain.Role;
import in.nucleusteq.plasma.dto.in.LoginInDTO;
import in.nucleusteq.plasma.dto.out.LoginOutDTO;
import in.nucleusteq.plasma.exception.ResourceNotFoundException;
import in.nucleusteq.plasma.exception.TokenServiceException;
import in.nucleusteq.plasma.exception.UnauthorizedAccessException;
import in.nucleusteq.plasma.feignservice.TokenService;
import in.nucleusteq.plasma.service.AuthenticationService;
import in.nucleusteq.plasma.utility.CookieUtil;
import jakarta.servlet.http.HttpServletResponse;


@Service
public class AuthenticationServiceImplementation implements AuthenticationService {

	@Autowired
	private EmployeeRepository userService;
	@Autowired
	private TokenService tokenService;
	@Autowired
	private RefreshTokenSevice refreshTokenSevice;
	private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	@Override
	public LoginOutDTO loginUser(LoginInDTO inputUserDto, HttpServletResponse httpServletResponse) throws TokenServiceException {

		Employee foundRegistration = userService.getByEmail(inputUserDto.getEmail());

		if (foundRegistration == null) {
			throw new ResourceNotFoundException("User not found");
		}

		String password = inputUserDto.getPassword();
		String encodedPassword = foundRegistration.getPassword();

		boolean isRightPassword = passwordEncoder.matches(password, encodedPassword);

		if (isRightPassword) {
			String authToken = tokenService.getToken(foundRegistration.getEmail());
			RefreshToken refreshTokenDTO = refreshTokenSevice.createRefreshToken(foundRegistration.getEmail());

			String refreshToken = String.valueOf(refreshTokenDTO);
			Integer expirationTimeInSeconds = tokenService.getExpireTime(authToken);
			CookieUtil.create(httpServletResponse, "accessToken", authToken, false, expirationTimeInSeconds,
			"localhost");
			CookieUtil.create(httpServletResponse, "refreshToken",refreshTokenDTO.getRefreshToken() , false, expirationTimeInSeconds,
					"localhost");

			LoginOutDTO response = LoginOutDTO.builder()
					.message("Login SuccessFully")
					.email(foundRegistration.getEmail())
					.role(getHighestWeightRole(foundRegistration.getUserWorkDetail().getRoles()))
					.token(authToken)
					.refershToken(refreshToken)
					.build();
			return response;
		} else {
			throw new UnauthorizedAccessException("Incorrect userName or password");
		}
	}

	public final String getHighestWeightRole(Set<Role> set) {
		String highestWeightRole = null;

		Map<String, Integer> roleWeights = new HashMap<>();
		roleWeights.put("Super_Admin", 4);
		roleWeights.put("ADMIN", 3);
		roleWeights.put("MANAGER", 2);
		roleWeights.put("EMPLOYEE", 1);

		int maxWeight = Integer.MIN_VALUE;

		for (Role role : set) {
			String roleName = role.getName();
			if (roleWeights.containsKey(roleName)) {
				int weight = roleWeights.get(roleName);
				if (weight > maxWeight) {
					maxWeight = weight;
					highestWeightRole = roleName;
				}
			}
		}

		return highestWeightRole;
	}

}
