package in.nucleusteq.plasma.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import in.nucleusteq.plasma.enums.Currency;
import in.nucleusteq.plasma.enums.EmployementStatus;
import in.nucleusteq.plasma.enums.EmploymentNature;
import in.nucleusteq.plasma.enums.OnshoreOrOffshore;
//import in.nucleusteq.plasma.enums.Role;
import in.nucleusteq.plasma.enums.VisaStatus;
import in.nucleusteq.plasma.enums.WorkMode;
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
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "user_work_detail")
public class UserWorkDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Enumerated(EnumType.STRING)
    @Column(name = "visa_status")
    private VisaStatus visaStatus;
    @Column(name = "citizenship")
    private String citizenship;
    @Enumerated(EnumType.STRING)
    @Column(name = "employment_nature")
    private EmploymentNature employmentNature;
    @Enumerated(EnumType.STRING)
    @Column(name = "work_mode")
    private WorkMode workMode;
    @Column(name = "employment_start_date")
    @Temporal(TemporalType.DATE)
    private Date employmentStartDate;
//<<<<<<< HEAD
//    @Enumerated(EnumType.STRING)
//    @Column(name = "role")
//    private Role role;
//=======

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_work_details_roles",
            joinColumns = @JoinColumn(name = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
            )
    private Set<Role> roles = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "designation_id")
    private Designation designation;
    @Enumerated(EnumType.STRING)
    @Column(name = "employment_status")
    private EmployementStatus employmentStatus;
    @Column(name = "work_location")
    private String workLocation;
    @Enumerated(EnumType.STRING)
    @Column(name = "onshore_or_offshore")
    private OnshoreOrOffshore onshoreOrOffshore;
    @Column(name = "employment_Company")
    private String employmentCompany;
    @Column(name = "contracting_rate")
    private int contractingRate;
    @Enumerated(EnumType.STRING)
    @Column(name = "contracting_rate_currency")
    private Currency contractingRateCurrency;
    @Column(name = "contracting_Company")
    private String contractingCompany;
    @ManyToOne
    @JoinColumn(name = "recruiter")
    private Employee recruiter;
    @ManyToOne
    @JoinColumn(name = "recruiter_manager")
    private Employee recruiterManager;
    @OneToOne(mappedBy = "userWorkDetail")
    private UserPersonalDetail userPersonalDetail;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userWorkDetail", fetch = FetchType.LAZY)
    private List<UserSkill> skills;
    @OneToOne(mappedBy = "userWorkDetail")
    private Employee user;

   
}
