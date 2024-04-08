package in.nucleusteq.plasma.dao;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import in.nucleusteq.plasma.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
}