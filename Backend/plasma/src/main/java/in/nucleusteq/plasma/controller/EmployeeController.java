package in.nucleusteq.plasma.controller;

import in.nucleusteq.plasma.dto.in.user.RecentOnBoardingInDTO;
import in.nucleusteq.plasma.dto.in.user.RejectOnBoardInDTO;
import in.nucleusteq.plasma.dto.in.user.UpdatedDetailOfOnBoardInDTO;
import in.nucleusteq.plasma.dto.out.user.DetailOfRecentOnBoardingOutDTO;
import in.nucleusteq.plasma.dto.out.user.PendingOnBoardOutDTO;
import in.nucleusteq.plasma.dto.out.user.PersonalDetailOutDTO;
import in.nucleusteq.plasma.dto.out.user.RecentOnBoardingListOutDTO;
import in.nucleusteq.plasma.entity.UserPersonalDetail;
import in.nucleusteq.plasma.entity.UserWorkDetail;
import in.nucleusteq.plasma.payload.SuccessResponse;
import in.nucleusteq.plasma.service.EmployeeService;
import in.nucleusteq.plasma.service.PermissionDefinitions.HasAcceptOnboardingPermission;
import in.nucleusteq.plasma.service.PermissionDefinitions.HasOnboardingPermission;
import in.nucleusteq.plasma.service.PermissionDefinitions.HasRejectOnboardingPermission;
import in.nucleusteq.plasma.service.PermissionDefinitions.HasShowPendingOnboardingPermission;
import in.nucleusteq.plasma.service.UserWorkDetailService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/plasma/employee")
@CrossOrigin
public class EmployeeController {
	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private UserWorkDetailService userWorkDetailService;

	public EmployeeController(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	@PostMapping("/onboard/recent")
	@HasOnboardingPermission
	public ResponseEntity<SuccessResponse> processRecentOnboard(@RequestBody RecentOnBoardingInDTO recentOnboardInDTO) {
		SuccessResponse successResponse = employeeService.processRecentOnboard(recentOnboardInDTO);
		return ResponseEntity.ok(successResponse);
	}

	@GetMapping("/pending-onboard")
	@HasShowPendingOnboardingPermission
	public List<PendingOnBoardOutDTO> getPendingOnBoardUsers() {
		return employeeService.getPendingOnBoardUsers();
	}

	@GetMapping("/onboard/{id}")
	@HasShowPendingOnboardingPermission
	public ResponseEntity<DetailOfRecentOnBoardingOutDTO> getOnboardingDetails(@PathVariable("id") int id) {
		DetailOfRecentOnBoardingOutDTO onboardingDetails = employeeService.onboardForm(id);
		return new ResponseEntity<>(onboardingDetails, HttpStatus.OK);
	}

	@PostMapping("/reject")
	@HasRejectOnboardingPermission
	public ResponseEntity<SuccessResponse> rejectOnboard(@RequestBody RejectOnBoardInDTO rejectOnBoard) {
		SuccessResponse successResponse = employeeService.rejectOnboard(rejectOnBoard);
		return ResponseEntity.ok(successResponse);
	}

	@PostMapping("/update-onboard-details")
	@HasAcceptOnboardingPermission
	public ResponseEntity<SuccessResponse> updateOnBoardDetails(@RequestBody UpdatedDetailOfOnBoardInDTO uBoardInDTO) {
		SuccessResponse successResponse = employeeService.updateDetailOfOnBoard(uBoardInDTO);
		return ResponseEntity.ok(successResponse);
	}

	@GetMapping("/recent-joiners")
	public ResponseEntity<List<RecentOnBoardingListOutDTO>> getRecentJoiners() {
		List<RecentOnBoardingListOutDTO> recentJoiners = employeeService.getListOfRecentOnboarding();
		return new ResponseEntity<>(recentJoiners, HttpStatus.OK);
	}

	@GetMapping("/all-employees")
	public ResponseEntity<List<RecentOnBoardingListOutDTO>> getAllEmployees() {
		List<RecentOnBoardingListOutDTO> allEmployees = employeeService.getAllEmployees();
		return new ResponseEntity<>(allEmployees, HttpStatus.OK);
	}
	

	@GetMapping("/{employeeId}")
    public RecentOnBoardingListOutDTO getEmployeeById(@PathVariable("employeeId") String employeeId) {
        RecentOnBoardingListOutDTO employeeDto = employeeService.getEmployeeById(employeeId);
        return employeeDto;
    }
	@GetMapping("/personaldetail/{userId}")
    public PersonalDetailOutDTO getUserPersonalDetailById(@PathVariable("userId") int userId) {
		PersonalDetailOutDTO userPersonalDetail = employeeService.getUserPersonalDetailById(userId);
        return userPersonalDetail;
    }
	
	@GetMapping("/email/{email}")
    public RecentOnBoardingListOutDTO getEmployeeByEmail(@PathVariable("email") String email) {
        RecentOnBoardingListOutDTO employeeDto = employeeService.getEmployeeByEmail(email);
        return employeeDto;
    }
	
	@PostMapping("/create-work-detail")
	public ResponseEntity<UserWorkDetail> createUserWorkDetail(@RequestBody UserWorkDetail userWorkDetail) {
		UserWorkDetail savedUserWorkDetail = userWorkDetailService.saveUserWorkDetail(userWorkDetail);
		return new ResponseEntity<>(savedUserWorkDetail, HttpStatus.CREATED);
	}

}
