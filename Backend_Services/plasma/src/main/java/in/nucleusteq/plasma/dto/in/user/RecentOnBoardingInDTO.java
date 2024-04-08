package in.nucleusteq.plasma.dto.in.user;

import java.util.Date;
import java.util.List;
import java.util.Set;

import in.nucleusteq.plasma.entity.Role;
import in.nucleusteq.plasma.enums.BloodGroup;
import in.nucleusteq.plasma.enums.Currency;
import in.nucleusteq.plasma.enums.EmployementStatus;
import in.nucleusteq.plasma.enums.EmploymentNature;
import in.nucleusteq.plasma.enums.Gender;
import in.nucleusteq.plasma.enums.OnshoreOrOffshore;

import in.nucleusteq.plasma.enums.VisaStatus;
import in.nucleusteq.plasma.enums.WorkMode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RecentOnBoardingInDTO {
    private String firstName;
    private String middleName;
    private String lastName;
    private String phoneNumber;
    private String personalEmailId;
    private String country;
    private String state;
    private String city;
    private String addressLine1;
    private String addressLine2;
    private int zipCode;
    private Date dateOfBirth;
    private Gender gender;
    private BloodGroup bloodGroup;
    private String workLocation;
    private OnshoreOrOffshore onshoreOrOffshore;
    private VisaStatus visaStatus;
    private String citizenship;
    private EmploymentNature employmentNature;
    private String employmentCompany;
    private int contractingRate;
    private Currency contractingRateCurrency;
    private String contractingCompany;
    private WorkMode workMode;
    private Date employmentStartDate;
    private Set<Role> roles;
    private String designation;
    private EmployementStatus employementStatus;
    private List<String> skill;
    private String recruiter_id;
}
