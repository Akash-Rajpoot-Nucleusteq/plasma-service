package in.nucleusteq.plasma.feignservice;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import in.nucleusteq.plasma.dto.in.UserInDTO;
import in.nucleusteq.plasma.handler.CustomErrorDecoder;

@ComponentScan
@FeignClient(name = "PLASMA-SERVICE",url="http://localhost:8081", configuration = CustomErrorDecoder.class)
public interface UserService {

    @GetMapping("/plasma/employee/{employeeId}")
    UserInDTO getEmployeeById(@PathVariable("employeeId") String employeeId);

    @GetMapping("/plasma/employee/personaldetail/{userId}")
    UserInDTO getUserPersonalDetailById(@PathVariable("userId") int userId);

    @GetMapping("/plasma/employee/email/{email}")
    UserInDTO getUserPersonalDetailByEmail(@PathVariable("email") String email);
}
