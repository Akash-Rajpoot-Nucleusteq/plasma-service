package in.nucleusteq.plasma.dto.in;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginInDTO {
	
	
	
//  @NotBlank(message = "Email is required.")
  private String email;

  /**
   * The user's password.
   */
//  @NotBlank(message = "Password is required.")
  private String password;

}
