package in.nucleusteq.plasma.service;

import java.util.List;

import in.nucleusteq.plasma.dto.in.user.RecentOnBoardingInDTO;
import in.nucleusteq.plasma.dto.in.user.RecentOnBoardingWithDocumentInDto;
import in.nucleusteq.plasma.dto.in.user.RejectOnBoardInDTO;
import in.nucleusteq.plasma.dto.in.user.UpdatedDetailOfOnBoardInDTO;
import in.nucleusteq.plasma.dto.out.user.DetailOfRecentOnBoardingOutDTO;
import in.nucleusteq.plasma.dto.out.user.PendingOnBoardOutDTO;
import in.nucleusteq.plasma.dto.out.user.PersonalDetailOutDTO;
import in.nucleusteq.plasma.dto.out.user.RecentOnBoardingListOutDTO;
import in.nucleusteq.plasma.entity.Employee;
import in.nucleusteq.plasma.entity.UserPersonalDetail;
import in.nucleusteq.plasma.payload.SuccessResponse;

public interface EmployeeService {
    public SuccessResponse processRecentOnboard(RecentOnBoardingInDTO recentOnboardInDTO);
    public void processRecentOnboardWithDocument(RecentOnBoardingWithDocumentInDto recentOnboardInDTO);
    public List<PendingOnBoardOutDTO> getPendingOnBoardUsers();
    public DetailOfRecentOnBoardingOutDTO onboardForm(int id);
    public SuccessResponse updateDetailOfOnBoard(UpdatedDetailOfOnBoardInDTO uBoardInDTO);
    public SuccessResponse rejectOnboard(RejectOnBoardInDTO rejectOnBoard);
    public List<RecentOnBoardingListOutDTO> getListOfRecentOnboarding();
    public List<RecentOnBoardingListOutDTO> getAllEmployees();
	RecentOnBoardingListOutDTO getEmployeeById(String employeeId);
	PersonalDetailOutDTO getUserPersonalDetailById(int userId);
	RecentOnBoardingListOutDTO getEmployeeByEmail(String email);


}
