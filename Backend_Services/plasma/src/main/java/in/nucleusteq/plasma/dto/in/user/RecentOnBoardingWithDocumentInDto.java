package in.nucleusteq.plasma.dto.in.user;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

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
public class RecentOnBoardingWithDocumentInDto {
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

    private MultipartFile photoAttachment;
    private MultipartFile resumeAttachment;
    private String adharNumber;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date adharDob;
    private MultipartFile adharAttachment;
    private MultipartFile rentalAgreementAttachment;
    private MultipartFile educationCertificateAttachment;
    private MultipartFile photoIdAttachment;
    private String panNumber;
    private MultipartFile panAttachment;
    private MultipartFile i94TravelHistoryAttachment;
    private MultipartFile i9CopyAttachment;
    private String passportCountry;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date passportIssueDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date passportExpDate;
    private String passportNumber;
    private MultipartFile passportAttachment;
    private String workAuthorizationNumber;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date workAuthorizationValidTill;
    private MultipartFile workAuthorizationAttachment;
}
