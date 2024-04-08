package in.nucleusteq.plasma.dto.out;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginOutDTO {
	
	/**
	 * The user's email address.
	 */
	private String email;
	private String role;
	private String message;

//	    private String token;

}
