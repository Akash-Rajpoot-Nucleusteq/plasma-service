package in.nucleusteq.plasma.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import in.nucleusteq.plasma.entity.DocumentAttribute;

public interface DocumentAttributeRepository extends JpaRepository<DocumentAttribute, Integer> {
}
