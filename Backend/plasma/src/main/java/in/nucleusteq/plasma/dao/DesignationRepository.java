package in.nucleusteq.plasma.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import in.nucleusteq.plasma.entity.Designation;

public interface DesignationRepository extends JpaRepository<Designation, Integer> {
/**
 * findByDesignationName.
 */
	Optional<Designation> findByDesignationName(String designationName);
}
