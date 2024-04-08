package in.nucleusteq.plasma.service.impl;

import in.nucleusteq.plasma.dao.FinancialDetailRepository;
import in.nucleusteq.plasma.dao.RoleRepository;
import in.nucleusteq.plasma.dao.SkillRepository;
import in.nucleusteq.plasma.dao.UserAddressRepository;
import in.nucleusteq.plasma.dao.UserPersonalDetailRepository;
import in.nucleusteq.plasma.dao.UserSkillRepository;
import in.nucleusteq.plasma.dao.DesignationRepository;
import in.nucleusteq.plasma.dao.DocumentRepository;
import in.nucleusteq.plasma.dao.EmployeeRepository;
import in.nucleusteq.plasma.dao.UserWorkDetailRepository;
import in.nucleusteq.plasma.dto.common.SkillDTO;
import in.nucleusteq.plasma.dto.in.user.RecentOnBoardingInDTO;
import in.nucleusteq.plasma.dto.in.user.RecentOnBoardingWithDocumentInDto;
import in.nucleusteq.plasma.dto.in.user.RejectOnBoardInDTO;
import in.nucleusteq.plasma.dto.in.user.UpdatedDetailOfOnBoardInDTO;
import in.nucleusteq.plasma.dto.out.user.DetailOfRecentOnBoardingOutDTO;
import in.nucleusteq.plasma.dto.out.user.PendingOnBoardOutDTO;
import in.nucleusteq.plasma.dto.out.user.PersonalDetailOutDTO;
import in.nucleusteq.plasma.dto.out.user.RecentOnBoardingListOutDTO;
import in.nucleusteq.plasma.entity.FinancialDetail;
import in.nucleusteq.plasma.entity.Role;
import in.nucleusteq.plasma.entity.Skills;
import in.nucleusteq.plasma.entity.Designation;
import in.nucleusteq.plasma.entity.Employee;
import in.nucleusteq.plasma.entity.UserAddress;
import in.nucleusteq.plasma.entity.UserPersonalDetail;
import in.nucleusteq.plasma.entity.UserSkill;
import in.nucleusteq.plasma.entity.UserWorkDetail;
import in.nucleusteq.plasma.enums.OnBoardingStatus;
import in.nucleusteq.plasma.enums.OnshoreOrOffshore;
import in.nucleusteq.plasma.exception.NotFoundException;
import in.nucleusteq.plasma.exception.ResourceNotFoundException;
import in.nucleusteq.plasma.payload.SuccessResponse;
import in.nucleusteq.plasma.payload.UserIdEmailGeneration;
import in.nucleusteq.plasma.service.EmployeeService;
import in.nucleusteq.plasma.utility.FileStorageProperties;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


import java.util.Optional;
import java.util.Set;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmployeeServiceImplementation implements EmployeeService {
    @Autowired
    private UserPersonalDetailRepository userPersonalDetailRepository;
    @Autowired
    private UserWorkDetailRepository userWorkDetailRepository;
    @Autowired
    private UserAddressRepository userAddressRepository;
    @Autowired
    private EmployeeRepository userRepository;
    @Autowired
    private FinancialDetailRepository financialDetailRepository;
    @Autowired
    private SkillRepository skillRepository;
    @Autowired
    private UserSkillRepository userSkillRepository;
    @Autowired
    private DesignationRepository designationRepository;
    @Autowired
    private DocumentRepository documentRepository;
    private Path fileStorageLocation;
    @Autowired
    private FileStorageProperties fileStorageProperties;
    @Autowired
    private RoleRepository roleRepository;

    public EmployeeServiceImplementation(
            FileStorageProperties fileStorageProperties) {
        this.fileStorageLocation = Paths
                .get(fileStorageProperties.getUploadDir()).toAbsolutePath()
                .normalize();
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new RuntimeException(
                    "Could not create the directory where the uploaded files will be stored.");
        }
    }
    @Override
    @Transactional
    public SuccessResponse processRecentOnboard(
            RecentOnBoardingInDTO recentOnboardInDTO) {
        UserAddress userAddress = new UserAddress(
                recentOnboardInDTO.getAddressLine1(),
                recentOnboardInDTO.getAddressLine2(),
                recentOnboardInDTO.getCity(), recentOnboardInDTO.getState(),
                recentOnboardInDTO.getCountry(),
                recentOnboardInDTO.getZipCode());
        userAddressRepository.save(userAddress);
        Designation designation = designationRepository
                .findByDesignationName(recentOnboardInDTO.getDesignation())
                .orElseThrow(() -> new NotFoundException(
                        "Designation not found for name: "
                                + recentOnboardInDTO.getDesignation()));
        UserWorkDetail userWorkDetail = new UserWorkDetail();
        userWorkDetail.setCitizenship(recentOnboardInDTO.getCitizenship());
        userWorkDetail.setContractingCompany(
                recentOnboardInDTO.getContractingCompany());
        userWorkDetail
                .setContractingRate(recentOnboardInDTO.getContractingRate());
        userWorkDetail.setContractingRateCurrency(
                recentOnboardInDTO.getContractingRateCurrency());
        userWorkDetail.setDesignation(designation);
        userWorkDetail.setEmploymentCompany(
                recentOnboardInDTO.getEmploymentCompany());
        userWorkDetail
                .setEmploymentNature(recentOnboardInDTO.getEmploymentNature());
        userWorkDetail.setEmploymentStartDate(
                recentOnboardInDTO.getEmploymentStartDate());
        userWorkDetail
                .setEmploymentStatus(recentOnboardInDTO.getEmployementStatus());
        userWorkDetail.setOnshoreOrOffshore(
                recentOnboardInDTO.getOnshoreOrOffshore());
        //userWorkDetail.setRole(recentOnboardInDTO.getRole());
        Set<Role> validatedRoles = validateAndRetrieveRoles(recentOnboardInDTO.getRoles());
        userWorkDetail.setRoles(validatedRoles);
        userWorkDetail.setVisaStatus(recentOnboardInDTO.getVisaStatus());
        userWorkDetail.setWorkLocation(recentOnboardInDTO.getWorkLocation());
        userWorkDetail.setWorkMode(recentOnboardInDTO.getWorkMode());
        Employee recruiter = userRepository
                .findById(recentOnboardInDTO.getRecruiter_id())
                .orElseThrow(() -> new NotFoundException(
                        "Recruiter not found for ID: "
                                + recentOnboardInDTO.getRecruiter_id()));
        userWorkDetail.setRecruiter(recruiter);
        userWorkDetailRepository.save(userWorkDetail);
        List<Skills> skills = new ArrayList<>();
        for (String skillName : recentOnboardInDTO.getSkill()) {
            Skills skill = skillRepository.findBySkillName(skillName)
                    .orElseGet(() -> {
                        Skills newSkill = new Skills(skillName);
                        return skillRepository.save(newSkill);
                    });
            skills.add(skill);
        }
        for (Skills skill : skills) {
            UserSkill employeeSkill = new UserSkill(userWorkDetail, skill);
            userSkillRepository.save(employeeSkill);
        }
        UserPersonalDetail userPersonalDetail = new UserPersonalDetail(
                recentOnboardInDTO.getFirstName(),
                recentOnboardInDTO.getMiddleName(),
                recentOnboardInDTO.getLastName(),
                recentOnboardInDTO.getPhoneNumber(),
                recentOnboardInDTO.getPersonalEmailId(),
                recentOnboardInDTO.getDateOfBirth(),
                recentOnboardInDTO.getGender(),
                recentOnboardInDTO.getBloodGroup(), OnBoardingStatus.PENDING,
                userAddress, userWorkDetail);
        userPersonalDetailRepository.save(userPersonalDetail);
        return new SuccessResponse("new onborading successfuly", 200);
    }

    @Override
    public List<PendingOnBoardOutDTO> getPendingOnBoardUsers() {
        List<UserPersonalDetail> pendingUsers = userPersonalDetailRepository
                .findByOnBoardingStatus(OnBoardingStatus.PENDING);
        return pendingUsers.stream().map(this::mapToPendingOnBoardOutDTO)
                .toList();
    }


    @Override
    public DetailOfRecentOnBoardingOutDTO onboardForm(int id) {
        UserPersonalDetail userPersonalDetail = userPersonalDetailRepository
                .findById(id).orElseThrow(() -> new NotFoundException(
                        "User not found for ID: " + id));
        DetailOfRecentOnBoardingOutDTO onboardInDTO = new DetailOfRecentOnBoardingOutDTO();
        UserAddress userAddress = userPersonalDetail.getUserAddress();
        UserWorkDetail userWorkDetail = userPersonalDetail.getUserWorkDetail();
        onboardInDTO.setAddressLine1(userAddress.getAddress1());
        onboardInDTO.setAddressLine2(userAddress.getAddress2());
        onboardInDTO.setBloodGroup(userPersonalDetail.getBloodGroup());
        onboardInDTO.setCitizenship(userWorkDetail.getCitizenship());
        onboardInDTO.setCity(userAddress.getCity());
        onboardInDTO
                .setContractingCompany(userWorkDetail.getContractingCompany());
        onboardInDTO.setContractingRate(userWorkDetail.getContractingRate());
        onboardInDTO.setContractingRateCurrency(
                userWorkDetail.getContractingRateCurrency());
        onboardInDTO.setCountry(userAddress.getCountry());
        onboardInDTO.setDateOfBirth(userPersonalDetail.getDateOfBirth());
        onboardInDTO.setDesignation(
                userWorkDetail.getDesignation().getDesignationName());
        onboardInDTO.setEmployementStatus(userWorkDetail.getEmploymentStatus());
        onboardInDTO
                .setEmploymentCompany(userWorkDetail.getEmploymentCompany());
        onboardInDTO.setEmploymentNature(userWorkDetail.getEmploymentNature());
        onboardInDTO.setEmploymentStartDate(
                userWorkDetail.getEmploymentStartDate());
        onboardInDTO.setFirstName(userPersonalDetail.getFirstName());
        onboardInDTO.setGender(userPersonalDetail.getGender());
        onboardInDTO.setId(userPersonalDetail.getId());
        onboardInDTO.setLastName(userPersonalDetail.getLastName());
        onboardInDTO.setMiddleName(userPersonalDetail.getMiddleName());
        onboardInDTO
                .setOnshoreOrOffshore(userWorkDetail.getOnshoreOrOffshore());
        onboardInDTO
                .setPersonalEmailId(userPersonalDetail.getPersonalEmailId());
        onboardInDTO.setPhoneNumber(userPersonalDetail.getPhoneNumber());
        Set<Role> validatedRoles = validateAndRetrieveRoles(userWorkDetail.getRoles());
		onboardInDTO.setRoles(validatedRoles);
        onboardInDTO.setState(userAddress.getState());
        onboardInDTO.setVisaStatus(userWorkDetail.getVisaStatus());
        onboardInDTO.setWorkLocation(userWorkDetail.getWorkLocation());
        onboardInDTO.setWorkMode(userWorkDetail.getWorkMode());
        onboardInDTO.setZipCode(userAddress.getZipCode());
        List<SkillDTO> skillDTOs = userSkillRepository
                .findByUserWorkDetail(userWorkDetail).stream()
                .map(userSkill -> new SkillDTO(
                        userSkill.getSkill().getSkillName()))
                .collect(Collectors.toList());
        onboardInDTO.setSkills(skillDTOs);
        return onboardInDTO;
    }

    @Override
    @Transactional
    public SuccessResponse updateDetailOfOnBoard(
            UpdatedDetailOfOnBoardInDTO uBoardInDTO) {
        UserPersonalDetail userPersonalDetail = userPersonalDetailRepository
                .findById(uBoardInDTO.getId())
                .orElseThrow(() -> new NotFoundException(
                        "User not found for ID: " + uBoardInDTO.getId()));
        String newUserId = UserIdEmailGeneration
                .generateUserId(userRepository.findLastUserId());
        String newEmail = UserIdEmailGeneration.generateEmail(
                userPersonalDetail.getFirstName(),
                userPersonalDetail.getLastName());
        String newPassword = UserIdEmailGeneration.generatePassword();
        FinancialDetail financialDetail = new FinancialDetail();
        financialDetail.setCtc(uBoardInDTO.getCtc());
        financialDetail.setBonus(uBoardInDTO.getBonus());
        financialDetail.setVisaExpense(uBoardInDTO.getVisaExpense());
        financialDetail.setOtherExpense(uBoardInDTO.getOtherExpense());
        financialDetail.setCtcCurrency(uBoardInDTO.getCtcCurrency());
        financialDetail.setBonusCurrency(uBoardInDTO.getBonusCurrency());
        financialDetail
                .setVisaExpenseCurrency(uBoardInDTO.getVisaExpenseCurrency());
        financialDetail
                .setOtherExpenseCurrency(uBoardInDTO.getOtherExpenseCurrency());
        financialDetailRepository.save(financialDetail);
        Employee user = new Employee();
        user.setUserId(newUserId);
        user.setEmail(newEmail);
        user.setPassword(newPassword);
        user.setFinancialDetail(financialDetail);
        user.setUserPersonalDetail(userPersonalDetail);
        user.setUserWorkDetail(userPersonalDetail.getUserWorkDetail());
        userRepository.save(user);
        Employee recruiterManager = userRepository
                .findById(uBoardInDTO.getRecruiterManager())
                .orElseThrow(() -> new NotFoundException(
                        "Recruiter not found for ID: "
                                + uBoardInDTO.getRecruiterManager()));
        userPersonalDetail.getUserWorkDetail()
                .setRecruiterManager(recruiterManager);
        userPersonalDetail.setOnBoardingStatus(OnBoardingStatus.APPROVED);
        userPersonalDetailRepository.save(userPersonalDetail);
        return new SuccessResponse(
                "New OnBoarding with finance and all other detail updated successfully",
                200);
    }


    @Override
    public SuccessResponse rejectOnboard(RejectOnBoardInDTO rejectOnBoard) {
        UserPersonalDetail userPersonalDetail = userPersonalDetailRepository
                .findById(rejectOnBoard.getId())
                .orElseThrow(() -> new NotFoundException(
                        "User not found for ID: " + rejectOnBoard.getId()));
        userPersonalDetail
                .setOnBoardingStatus(rejectOnBoard.getOnBoardingStatus());
        userPersonalDetailRepository.save(userPersonalDetail);
        
        return new SuccessResponse("On-board status rejected successfully", 200);
    }

    @Override
    public List<RecentOnBoardingListOutDTO> getListOfRecentOnboarding() {
        LocalDate currentDate = LocalDate.now();
        YearMonth currentYearMonth = YearMonth.from(currentDate);
        YearMonth previousYearMonth = currentYearMonth.minusMonths(1);
        LocalDate firstDayOfPreviousMonth = LocalDate.of(
                previousYearMonth.getYear(), previousYearMonth.getMonth(), 1);
        List<Integer> recentUserWorkIds = userWorkDetailRepository
                .findByEmploymentStartDateBetween(
                        java.sql.Date.valueOf(firstDayOfPreviousMonth),
                        java.sql.Date.valueOf(currentDate));
        List<Employee> recentEmployees = userRepository
                .findByUserWorkDetailIdIn(recentUserWorkIds);
        List<RecentOnBoardingListOutDTO> recentOnBoardingList = recentEmployees
                .stream().map(this::mapToRecentOnBoardingListDTO)
                .collect(Collectors.toList());
        return recentOnBoardingList;
    }

    @Override
    public List<RecentOnBoardingListOutDTO> getAllEmployees() {
        List<Employee> allEmployees = userRepository.findAll();
        List<RecentOnBoardingListOutDTO> allEmployeeBoardingListOutDTOs = new ArrayList<>();
        for (Employee employee : allEmployees) {
            UserPersonalDetail userPersonalDetail = employee
                    .getUserPersonalDetail();
            UserAddress userAddress = userPersonalDetail.getUserAddress();
            RecentOnBoardingListOutDTO dto = new RecentOnBoardingListOutDTO();
            dto.setEmployeeId(employee.getUserId());
            dto.setFirstName(userPersonalDetail.getFirstName());
            dto.setLastName(userPersonalDetail.getLastName());
            dto.setStartDate(userPersonalDetail.getUserWorkDetail()
                    .getEmploymentStartDate());
            dto.setPhoneNumber(userPersonalDetail.getPhoneNumber());
            dto.setCity(userAddress.getCity());
            dto.setState(userAddress.getState());
            allEmployeeBoardingListOutDTOs.add(dto);
        }
        return allEmployeeBoardingListOutDTOs;
    }


    @Override
    public RecentOnBoardingListOutDTO getEmployeeById(String employeeId) {
    	System.out.println("Reached in getEmployeeby id.");
        Optional<Employee> optionalEmployee = userRepository.findById(employeeId);
        if (optionalEmployee.isPresent()) {
            Employee employee = optionalEmployee.get();
            UserPersonalDetail userPersonalDetail = employee.getUserPersonalDetail();
            UserAddress userAddress = userPersonalDetail.getUserAddress();
            RecentOnBoardingListOutDTO dto = new RecentOnBoardingListOutDTO();
            dto.setEmployeeId(employee.getUserId());
            dto.setFirstName(userPersonalDetail.getFirstName());
            dto.setLastName(userPersonalDetail.getLastName());
            dto.setStartDate(userPersonalDetail.getUserWorkDetail().getEmploymentStartDate());
            dto.setPhoneNumber(userPersonalDetail.getPhoneNumber());
            dto.setCity(userAddress.getCity());
            dto.setState(userAddress.getState());
            dto.setPeresonalDetailId(userPersonalDetail.getId());
            return dto;
        } else {
            throw new ResourceNotFoundException("Employee not found with ID: " + employeeId);
        }
    }
    @Override
    public PersonalDetailOutDTO getUserPersonalDetailById(int userId) {
    	UserPersonalDetail userPersonalDetail =userPersonalDetailRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee personal detail not found with ID: " + userId));
    	 
    	PersonalDetailOutDTO personalDTO = new PersonalDetailOutDTO();
         personalDTO.setId(userPersonalDetail.getId());
         personalDTO.setFirstName(userPersonalDetail.getFirstName());
         personalDTO.setMiddleName(userPersonalDetail.getMiddleName());
         personalDTO.setLastName(userPersonalDetail.getLastName());
         personalDTO.setPhoneNumber(userPersonalDetail.getPhoneNumber());
         personalDTO.setPersonalEmailId(userPersonalDetail.getPersonalEmailId());
         personalDTO.setDateOfBirth(userPersonalDetail.getDateOfBirth());
         personalDTO.setGender(userPersonalDetail.getGender());
         personalDTO.setBloodGroup(userPersonalDetail.getBloodGroup());
         personalDTO.setOnBoardingStatus(userPersonalDetail.getOnBoardingStatus());

         return personalDTO;
    }
    
    @Override
    public RecentOnBoardingListOutDTO getEmployeeByEmail(String email) {
        Employee employee = userRepository.getByEmail(email);

        if (employee != null) {
            UserPersonalDetail userPersonalDetail = employee.getUserPersonalDetail();
            UserAddress userAddress = userPersonalDetail.getUserAddress();

            RecentOnBoardingListOutDTO dto = new RecentOnBoardingListOutDTO();
            dto.setEmployeeId(employee.getUserId());
            dto.setEmail(employee.getEmail());
            dto.setPassword(employee.getPassword());
            Set<Role> roles = employee.getUserWorkDetail().getRoles();
            dto.setRoles(roles);
            dto.setFirstName(userPersonalDetail.getFirstName());
            dto.setLastName(userPersonalDetail.getLastName());
            dto.setStartDate(userPersonalDetail.getUserWorkDetail().getEmploymentStartDate());
            dto.setPhoneNumber(userPersonalDetail.getPhoneNumber());
            dto.setCity(userAddress.getCity());
            dto.setState(userAddress.getState());

            return dto;
        } else {
            throw new ResourceNotFoundException("Employee not found with email: " + email);
        }
    }

    
    private PendingOnBoardOutDTO mapToPendingOnBoardOutDTO(
            UserPersonalDetail user) {
        PendingOnBoardOutDTO dto = new PendingOnBoardOutDTO();
        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setStartDate(
                user.getUserWorkDetail().getEmploymentStartDate().toString());
        dto.setCity(user.getUserAddress().getCity());
        dto.setState(user.getUserAddress().getState());
        return dto;
    }

    private RecentOnBoardingListOutDTO mapToRecentOnBoardingListDTO(
            Employee employee) {
        UserPersonalDetail userPersonalDetail = employee
                .getUserPersonalDetail();
        UserAddress userAddress = userPersonalDetail.getUserAddress();
        RecentOnBoardingListOutDTO dto = new RecentOnBoardingListOutDTO();
        dto.setEmployeeId(employee.getUserId());
        dto.setFirstName(userPersonalDetail.getFirstName());
        dto.setLastName(userPersonalDetail.getLastName());
        dto.setStartDate(userPersonalDetail.getUserWorkDetail()
                .getEmploymentStartDate());
        dto.setPhoneNumber(userPersonalDetail.getPhoneNumber());
        dto.setCity(userAddress.getCity());
        dto.setState(userAddress.getState());
        return dto;
    }

    @Override
    @Transactional
    public void processRecentOnboardWithDocument(
            RecentOnBoardingWithDocumentInDto recentOnboardInDTO) {
        UserAddress userAddress = new UserAddress(
                recentOnboardInDTO.getAddressLine1(),
                recentOnboardInDTO.getAddressLine2(),
                recentOnboardInDTO.getCity(), recentOnboardInDTO.getState(),
                recentOnboardInDTO.getCountry(),
                recentOnboardInDTO.getZipCode());
        userAddressRepository.save(userAddress);
        Designation designation = designationRepository
                .findByDesignationName(recentOnboardInDTO.getDesignation())
                .orElseThrow(() -> new NotFoundException(
                        "Designation not found for name: "
                                + recentOnboardInDTO.getDesignation()));
        UserWorkDetail userWorkDetail = new UserWorkDetail();
        userWorkDetail.setCitizenship(recentOnboardInDTO.getCitizenship());
        userWorkDetail.setContractingCompany(
                recentOnboardInDTO.getContractingCompany());
        userWorkDetail
                .setContractingRate(recentOnboardInDTO.getContractingRate());
        userWorkDetail.setContractingRateCurrency(
                recentOnboardInDTO.getContractingRateCurrency());
        userWorkDetail.setDesignation(designation);
        userWorkDetail.setEmploymentCompany(
                recentOnboardInDTO.getEmploymentCompany());
        userWorkDetail
                .setEmploymentNature(recentOnboardInDTO.getEmploymentNature());
        userWorkDetail.setEmploymentStartDate(
                recentOnboardInDTO.getEmploymentStartDate());
        userWorkDetail
                .setEmploymentStatus(recentOnboardInDTO.getEmployementStatus());
        userWorkDetail.setOnshoreOrOffshore(
                recentOnboardInDTO.getOnshoreOrOffshore());
        Set<Role> validatedRoles = validateAndRetrieveRoles(recentOnboardInDTO.getRoles());
        userWorkDetail.setRoles(validatedRoles);
        userWorkDetail.setVisaStatus(recentOnboardInDTO.getVisaStatus());
        userWorkDetail.setWorkLocation(recentOnboardInDTO.getWorkLocation());
        userWorkDetail.setWorkMode(recentOnboardInDTO.getWorkMode());
        Employee recruiter = userRepository
                .findById(recentOnboardInDTO.getRecruiter_id())
                .orElseThrow(() -> new NotFoundException(
                        "Recruiter not found for ID: "
                                + recentOnboardInDTO.getRecruiter_id()));
        userWorkDetail.setRecruiter(recruiter);
        userWorkDetailRepository.save(userWorkDetail);
        List<Skills> skills = new ArrayList<>();
        for (String skillName : recentOnboardInDTO.getSkill()) {
            Skills skill = skillRepository.findBySkillName(skillName)
                    .orElseGet(() -> {
                        Skills newSkill = new Skills(skillName);
                        return skillRepository.save(newSkill);
                    });
            skills.add(skill);
        }
        for (Skills skill : skills) {
            UserSkill employeeSkill = new UserSkill(userWorkDetail, skill);
            userSkillRepository.save(employeeSkill);
        }
        UserPersonalDetail userPersonalDetail = new UserPersonalDetail(
                recentOnboardInDTO.getFirstName(),
                recentOnboardInDTO.getMiddleName(),
                recentOnboardInDTO.getLastName(),
                recentOnboardInDTO.getPhoneNumber(),
                recentOnboardInDTO.getPersonalEmailId(),
                recentOnboardInDTO.getDateOfBirth(),
                recentOnboardInDTO.getGender(),
                recentOnboardInDTO.getBloodGroup(), OnBoardingStatus.PENDING,
                userAddress, userWorkDetail);
        userPersonalDetailRepository.save(userPersonalDetail);
        saveDocuments(userPersonalDetail, recentOnboardInDTO);
    }

    private void saveDocuments(UserPersonalDetail userPersonalDetail,
            RecentOnBoardingWithDocumentInDto recentOnboardInDTO) {
        if (recentOnboardInDTO
                .getOnshoreOrOffshore() == OnshoreOrOffshore.OFFSHORE) {
            saveOffshoreDocumentDetails(userPersonalDetail, recentOnboardInDTO);
        } else if (recentOnboardInDTO
                .getOnshoreOrOffshore() == OnshoreOrOffshore.ONSHORE) {
            saveOnshoreDocumentDetails(userPersonalDetail, recentOnboardInDTO);
        }
    }


    private void saveOffshoreDocumentDetails(
            UserPersonalDetail userPersonalDetail,
            RecentOnBoardingWithDocumentInDto recentOnboardInDTO) {
    }

    private void saveOnshoreDocumentDetails(
            UserPersonalDetail userPersonalDetail,
            RecentOnBoardingWithDocumentInDto recentOnboardInDTO) {
    }

	//@Override
	public DetailOfRecentOnBoardingOutDTO onboardForm1(int id) {
		UserPersonalDetail userPersonalDetail = userPersonalDetailRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("User not found for ID: " + id));

		DetailOfRecentOnBoardingOutDTO onboardInDTO = new DetailOfRecentOnBoardingOutDTO();
		UserAddress userAddress = userPersonalDetail.getUserAddress();
		UserWorkDetail userWorkDetail = userPersonalDetail.getUserWorkDetail();

		onboardInDTO.setAddressLine1(userAddress.getAddress1());
		onboardInDTO.setAddressLine2(userAddress.getAddress2());
		onboardInDTO.setBloodGroup(userPersonalDetail.getBloodGroup());
		onboardInDTO.setCitizenship(userWorkDetail.getCitizenship());
		onboardInDTO.setCity(userAddress.getCity());
		onboardInDTO.setContractingCompany(userWorkDetail.getContractingCompany());
		onboardInDTO.setContractingRate(userWorkDetail.getContractingRate());
		onboardInDTO.setContractingRateCurrency(userWorkDetail.getContractingRateCurrency());
		onboardInDTO.setCountry(userAddress.getCountry());
		onboardInDTO.setDateOfBirth(userPersonalDetail.getDateOfBirth());
		onboardInDTO.setDesignation(userWorkDetail.getDesignation().getDesignationName()); // Convert Designation to
																							// String
		onboardInDTO.setEmployementStatus(userWorkDetail.getEmploymentStatus());
		onboardInDTO.setEmploymentCompany(userWorkDetail.getEmploymentCompany());
		onboardInDTO.setEmploymentNature(userWorkDetail.getEmploymentNature());
		onboardInDTO.setEmploymentStartDate(userWorkDetail.getEmploymentStartDate());
		onboardInDTO.setFirstName(userPersonalDetail.getFirstName());
		onboardInDTO.setGender(userPersonalDetail.getGender());
		onboardInDTO.setId(userPersonalDetail.getId());
		onboardInDTO.setLastName(userPersonalDetail.getLastName());
		onboardInDTO.setMiddleName(userPersonalDetail.getMiddleName());
		onboardInDTO.setOnshoreOrOffshore(userWorkDetail.getOnshoreOrOffshore());
		onboardInDTO.setPersonalEmailId(userPersonalDetail.getPersonalEmailId());
		onboardInDTO.setPhoneNumber(userPersonalDetail.getPhoneNumber());
		// onboardInDTO.setRole(userWorkDetail.getRole());
		Set<Role> validatedRoles = validateAndRetrieveRoles(userWorkDetail.getRoles());
		onboardInDTO.setRoles(validatedRoles);
		onboardInDTO.setState(userAddress.getState());
		onboardInDTO.setVisaStatus(userWorkDetail.getVisaStatus());
		onboardInDTO.setWorkLocation(userWorkDetail.getWorkLocation());
		onboardInDTO.setWorkMode(userWorkDetail.getWorkMode());
		onboardInDTO.setZipCode(userAddress.getZipCode());

		List<SkillDTO> skillDTOs = userSkillRepository.findByUserWorkDetail(userWorkDetail)
				.stream()
				.map(userSkill -> new SkillDTO(userSkill.getSkill().getSkillName()))
				.collect(Collectors.toList());

		onboardInDTO.setSkills(skillDTOs);

		return onboardInDTO;
	}
	
	private Set<Role> validateAndRetrieveRoles(Set<Role> roles) {
		Set<Role> validatedRoles = new HashSet<>();
		for (Role role : roles) {
			try {
				Role existingRole = roleRepository.findById(role.getRole_id())
						.orElseThrow(() -> new RuntimeException("Role does not exist with id: " + role.getRole_id()));
				if (!existingRole.getName().equals(role.getName())) {
					throw new RuntimeException("Role name mismatch for id: " + role.getRole_id());
				}
				validatedRoles.add(existingRole);
			} catch (Exception e) {
				throw new RuntimeException("Error validating role existence for id: " + role.getRole_id(), e);
			}
		}
		return validatedRoles;
	}

	

	
}
