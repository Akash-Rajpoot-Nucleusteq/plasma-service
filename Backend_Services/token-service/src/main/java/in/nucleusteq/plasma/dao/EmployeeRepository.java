package in.nucleusteq.plasma.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import in.nucleusteq.plasma.domain.Employee;

/**
 * EmployeeRepository.
 */
public interface EmployeeRepository extends JpaRepository<Employee, String>{
   
    Optional<Employee> findByUserId(String employeeId);
    /**
     * getByEmail.
     */
    Employee getByEmail(String userName);

}
