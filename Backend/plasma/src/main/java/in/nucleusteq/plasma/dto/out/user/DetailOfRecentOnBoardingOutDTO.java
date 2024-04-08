package in.nucleusteq.plasma.dto.out.user;

import java.util.Date;
import java.util.List;
import java.util.Set;

import in.nucleusteq.plasma.dto.common.SkillDTO;
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
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class DetailOfRecentOnBoardingOutDTO {
    private int id;
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
    private List<SkillDTO> skills;

    public void setSkills(List<SkillDTO> skills) {
        this.skills = skills;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPersonalEmailId() {
        return personalEmailId;
    }

    public void setPersonalEmailId(String personalEmailId) {
        this.personalEmailId = personalEmailId;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public int getZipCode() {
        return zipCode;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public BloodGroup getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(BloodGroup bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getWorkLocation() {
        return workLocation;
    }

    public void setWorkLocation(String workLocation) {
        this.workLocation = workLocation;
    }

    public OnshoreOrOffshore getOnshoreOrOffshore() {
        return onshoreOrOffshore;
    }

    public void setOnshoreOrOffshore(OnshoreOrOffshore onshoreOrOffshore) {
        this.onshoreOrOffshore = onshoreOrOffshore;
    }

    public VisaStatus getVisaStatus() {
        return visaStatus;
    }

    public void setVisaStatus(VisaStatus visaStatus) {
        this.visaStatus = visaStatus;
    }

    public String getCitizenship() {
        return citizenship;
    }

    public void setCitizenship(String citizenship) {
        this.citizenship = citizenship;
    }

    public EmploymentNature getEmploymentNature() {
        return employmentNature;
    }

    public void setEmploymentNature(EmploymentNature employmentNature) {
        this.employmentNature = employmentNature;
    }

    public String getEmploymentCompany() {
        return employmentCompany;
    }

    public void setEmploymentCompany(String employmentCompany) {
        this.employmentCompany = employmentCompany;
    }

    public int getContractingRate() {
        return contractingRate;
    }

    public void setContractingRate(int contractingRate) {
        this.contractingRate = contractingRate;
    }

    public Currency getContractingRateCurrency() {
        return contractingRateCurrency;
    }

    public void setContractingRateCurrency(Currency contractingRateCurrency) {
        this.contractingRateCurrency = contractingRateCurrency;
    }

    public String getContractingCompany() {
        return contractingCompany;
    }

    public void setContractingCompany(String contractingCompany) {
        this.contractingCompany = contractingCompany;
    }

    public WorkMode getWorkMode() {
        return workMode;
    }

    public void setWorkMode(WorkMode workMode) {
        this.workMode = workMode;
    }

    public Date getEmploymentStartDate() {
        return employmentStartDate;
    }

    public void setEmploymentStartDate(Date employmentStartDate) {
        this.employmentStartDate = employmentStartDate;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public EmployementStatus getEmployementStatus() {
        return employementStatus;
    }

    public void setEmployementStatus(EmployementStatus employementStatus) {
        this.employementStatus = employementStatus;
    }

    public List<SkillDTO> getSkills() {
        return skills;
    }
}
