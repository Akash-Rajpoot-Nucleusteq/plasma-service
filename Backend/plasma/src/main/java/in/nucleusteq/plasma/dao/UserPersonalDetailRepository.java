package in.nucleusteq.plasma.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import in.nucleusteq.plasma.entity.UserPersonalDetail;
import in.nucleusteq.plasma.enums.OnBoardingStatus;

public interface UserPersonalDetailRepository
        extends
            JpaRepository<UserPersonalDetail, Integer> {
    

    List<UserPersonalDetail> findByOnBoardingStatus(OnBoardingStatus pending);
}
