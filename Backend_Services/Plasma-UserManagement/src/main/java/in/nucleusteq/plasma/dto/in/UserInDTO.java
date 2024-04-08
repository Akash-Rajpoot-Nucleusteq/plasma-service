package in.nucleusteq.plasma.dto.in;

import java.util.Date;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class UserInDTO {
    private String employeeId;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private Date startDate;
    private String phoneNumber;
    private String city;
    private String state;
    private int peresonalDetailId;
    private Set<RoleInDTO> roles;

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

	public int getPeresonalDetailId() {
		return peresonalDetailId;
	}

	public void setPeresonalDetailId(int peresonalDetailId) {
		this.peresonalDetailId = peresonalDetailId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<RoleInDTO> getRoles() {
		return roles;
	}

	public void setRoles(Set<RoleInDTO> roles) {
		this.roles = roles;
	}

	
}
