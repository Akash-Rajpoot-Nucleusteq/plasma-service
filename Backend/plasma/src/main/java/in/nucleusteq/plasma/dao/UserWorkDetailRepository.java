package in.nucleusteq.plasma.dao;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import in.nucleusteq.plasma.entity.UserWorkDetail;

public interface UserWorkDetailRepository
        extends
            JpaRepository<UserWorkDetail, Integer> {
    @Query("select u.id from UserWorkDetail u where u.employmentStartDate between ?1 and ?2")
    List<Integer> findByEmploymentStartDateBetween(Date previousMonth,
            Date currentMonth);
}
