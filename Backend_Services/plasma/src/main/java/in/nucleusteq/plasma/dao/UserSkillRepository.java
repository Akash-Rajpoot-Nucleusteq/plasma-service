package in.nucleusteq.plasma.dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import in.nucleusteq.plasma.entity.UserSkill;
import in.nucleusteq.plasma.entity.UserWorkDetail;

public interface UserSkillRepository extends JpaRepository<UserSkill, Integer> {

	List<UserSkill> findByUserWorkDetail(UserWorkDetail userWorkDetail);
}
