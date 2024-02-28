package ca.mcgill.ecse428.CourseChamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import ca.mcgill.ecse428.CourseChamp.model.Admin;
import ca.mcgill.ecse428.CourseChamp.repository.AdminRepository;
import ca.mcgill.ecse428.CourseChamp.service.AdminService;

@SpringBootApplication
public class CourseChampApplication {

	//Initialization of the parkingLot if it is the first time it is used
	@Bean
    CommandLineRunner initDatabase(@Autowired AdminService adminService,@Autowired AdminRepository adminRepository) {
        return args -> {
			if (adminRepository.count() == 0){
				adminService.createAdminAccount(new Admin("courseChampAdmin@mail.com", "OGAdmin", "Password1!"));
			}
        };
    }
	
	public static void main(String[] args) {
		SpringApplication.run(CourseChampApplication.class, args);
	}

}
