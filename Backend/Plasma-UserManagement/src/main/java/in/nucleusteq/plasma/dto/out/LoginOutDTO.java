package in.nucleusteq.plasma.dto.out;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginOutDTO {
	/**
	 * The user's email address.
	 */
	private String email;
	private String role;
	private String message;
	private String token;
	private String refershToken;
}
