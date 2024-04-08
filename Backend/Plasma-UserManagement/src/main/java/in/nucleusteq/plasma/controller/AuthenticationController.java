package in.nucleusteq.plasma.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.nucleusteq.plasma.dto.in.LoginInDTO;
import in.nucleusteq.plasma.dto.out.LoginOutDTO;
import in.nucleusteq.plasma.exception.TokenServiceException;
import in.nucleusteq.plasma.service.AuthenticationService;
import in.nucleusteq.plasma.utility.CookieUtil;
import jakarta.servlet.http.HttpServletResponse;


@RestController
@RequestMapping("/plasma/auth")
public class AuthenticationController {
	
	@Autowired
	private AuthenticationService authenticationService;
	
	@PostMapping("/login")
    public ResponseEntity<LoginOutDTO> logineUser(@RequestBody LoginInDTO loginRequestDto, HttpServletResponse httpServletResponse) throws TokenServiceException {
    	System.out.println("user Email is :"+loginRequestDto.getEmail());
    	LoginOutDTO response = authenticationService.loginUser(loginRequestDto,httpServletResponse);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @GetMapping("/logout")
    public ResponseEntity< String> logout(HttpServletResponse httpServletResponse) {
    	    CookieUtil.clear(httpServletResponse, "SessionCookie");
    	    String message = "Logout successfully .";
    	    return new ResponseEntity<>(message, HttpStatus.OK);
    }

}
