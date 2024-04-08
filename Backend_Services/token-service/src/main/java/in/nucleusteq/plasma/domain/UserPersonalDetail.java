package in.nucleusteq.plasma.domain;

import java.util.Date;
import java.util.List;

import in.nucleusteq.plasma.enums.BloodGroup;
import in.nucleusteq.plasma.enums.Gender;
import in.nucleusteq.plasma.enums.OnBoardingStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "user_personal_detail")
public class UserPersonalDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "middle_name")
    private String middleName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "personal_email_id")
    private String personalEmailId;
    @Column(name = "date_of_birth")
    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;
    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;
    @Enumerated(EnumType.STRING)
    @Column(name = "blood_group")
    private BloodGroup bloodGroup;
    @Enumerated(EnumType.STRING)
    @Column(name = "onBoardingStatus")
    private OnBoardingStatus onBoardingStatus;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_address_id")
    private UserAddress userAddress;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_work_id")
    private UserWorkDetail userWorkDetail;
    @OneToOne(mappedBy = "userPersonalDetail")
    private Employee user;
    @OneToMany(mappedBy = "userPersonalDetail", cascade = CascadeType.ALL)
    private List<Document> documents;
	public UserPersonalDetail(String firstName, String middleName, String lastName, String phoneNumber,
			String personalEmailId, Date dateOfBirth, Gender gender, BloodGroup bloodGroup,
			OnBoardingStatus onBoardingStatus, UserAddress userAddress, UserWorkDetail userWorkDetail) {
		super();
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		this.personalEmailId = personalEmailId;
		this.dateOfBirth = dateOfBirth;
		this.gender = gender;
		this.bloodGroup = bloodGroup;
		this.onBoardingStatus = onBoardingStatus;
		this.userAddress = userAddress;
		this.userWorkDetail = userWorkDetail;
	}
    
    
}
