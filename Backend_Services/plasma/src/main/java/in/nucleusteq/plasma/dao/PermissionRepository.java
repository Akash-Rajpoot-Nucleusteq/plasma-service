package in.nucleusteq.plasma.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import in.nucleusteq.plasma.entity.Permission;

public interface PermissionRepository extends JpaRepository<Permission, Long> {

	Optional<Permission> findByPermission(String key);

	 @Query(value = "select count(*) from permissions_roles where permission_id = ?1", nativeQuery = true)
	 Long countPermissionUsage(Long permissionId);

}
