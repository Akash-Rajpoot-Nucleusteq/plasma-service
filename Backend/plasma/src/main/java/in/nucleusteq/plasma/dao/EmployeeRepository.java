package in.nucleusteq.plasma.dao;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import in.nucleusteq.plasma.entity.Employee;
/**
 * EmployeeRepository.
 */
public interface EmployeeRepository extends JpaRepository<Employee, String> {
    @Query("SELECT MAX(u.userId) FROM Employee u")
    String findLastUserId();
    
    List<Employee> findByUserWorkDetailIdIn(List<Integer> userWorkIds);
    /**
     * findByUserId.
     */
    Optional<Employee> findByUserId(String employeeId);
    /**
     * getByEmail.
     */
    Employee getByEmail(String userName);

}
