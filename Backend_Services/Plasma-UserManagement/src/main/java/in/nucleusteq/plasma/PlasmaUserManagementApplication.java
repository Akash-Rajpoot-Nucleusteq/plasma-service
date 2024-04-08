package in.nucleusteq.plasma;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class PlasmaUserManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(PlasmaUserManagementApplication.class, args);
	}

}
