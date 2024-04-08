package in.nucleusteq.plasma.dto.out.user;

import java.util.Date;

import in.nucleusteq.plasma.enums.BloodGroup;
import in.nucleusteq.plasma.enums.Gender;
import in.nucleusteq.plasma.enums.OnBoardingStatus;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PersonalDetailOutDTO {

	private int id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String phoneNumber;
    private String personalEmailId;
    private Date dateOfBirth;
    private Gender gender;
    private BloodGroup bloodGroup;
    private OnBoardingStatus onBoardingStatus;
}
