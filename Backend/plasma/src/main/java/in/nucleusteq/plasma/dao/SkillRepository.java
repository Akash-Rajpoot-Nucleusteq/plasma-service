package in.nucleusteq.plasma.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import in.nucleusteq.plasma.entity.Skills;

public interface SkillRepository extends JpaRepository<Skills, Integer> {
	Optional<Skills> findBySkillName(String skillName);
}
