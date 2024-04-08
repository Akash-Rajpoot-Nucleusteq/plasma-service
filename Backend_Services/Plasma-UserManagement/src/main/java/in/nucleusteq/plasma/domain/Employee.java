package in.nucleusteq.plasma.domain;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

//import jakarta.persistence.CascadeType;
//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.FetchType;
//import jakarta.persistence.Id;
//import jakarta.persistence.JoinColumn;
//import jakarta.persistence.OneToMany;
//import jakarta.persistence.OneToOne;
//import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_table")
public class Employee {

	@Id
	@Column(name = "user_id", unique = true)
	private String userId;
	@Column(name = "email", unique = true)
	private String email;
	@Column(name = "password")
	private String password;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_personal_id")
	private UserPersonalDetail userPersonalDetail;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_work_id")
	private UserWorkDetail userWorkDetail;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "finance_id")
	private FinancialDetail financialDetail;
	@OneToMany(mappedBy = "recruiter", cascade = CascadeType.ALL)
	private List<UserWorkDetail> recruited;
	@OneToMany(mappedBy = "recruiterManager", cascade = CascadeType.ALL)
	private List<UserWorkDetail> recruiterManaged;
	@OneToMany(mappedBy = "employeeId", fetch = FetchType.LAZY)
	private List<AssetAllocation> assetsAllocation;


}
