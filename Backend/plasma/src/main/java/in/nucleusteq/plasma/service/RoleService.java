package in.nucleusteq.plasma.service;

import java.util.List;
import java.util.Set;

import in.nucleusteq.plasma.dto.in.role.RoleInDTO;
import in.nucleusteq.plasma.dto.out.role.RoleOutDTO;
import in.nucleusteq.plasma.entity.Role;



public interface RoleService {
	String addRole(RoleInDTO roleRequestDto);

	List<RoleOutDTO> getAllRoles();

	RoleOutDTO getRoleById(Long roleId);

	String assignRolesToUser(String userId, Set<Role> roles);

	String removeRolesFromUser(String userId, Set<Role> roles);
	
	String addPermissionOnRole(Long roleId, String permissionKey);
	
	String removePermissionOnRole(Long roleId, String permissionKey);

	RoleOutDTO getRoleByName(String roleName);
}