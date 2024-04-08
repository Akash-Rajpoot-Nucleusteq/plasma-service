package in.nucleusteq.plasma.service;



import in.nucleusteq.plasma.dto.in.LoginInDTO;
import in.nucleusteq.plasma.dto.out.LoginOutDTO;
import in.nucleusteq.plasma.exception.TokenServiceException;
import jakarta.servlet.http.HttpServletResponse;


public interface AuthenticationService {
/**
 * LoginUser.
 * @param inputUserDto
 * @param httpServletResponse
 * @return loginOutDTO
 * @throws TokenServiceException 
 */
    LoginOutDTO loginUser(LoginInDTO inputUserDto, HttpServletResponse httpServletResponse) throws TokenServiceException;
}
