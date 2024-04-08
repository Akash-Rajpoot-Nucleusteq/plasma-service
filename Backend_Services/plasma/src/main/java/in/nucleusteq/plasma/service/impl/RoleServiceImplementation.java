package in.nucleusteq.plasma.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.nucleusteq.plasma.constants.RoleConstant;
import in.nucleusteq.plasma.dao.EmployeeRepository;
import in.nucleusteq.plasma.dao.PermissionRepository;
import in.nucleusteq.plasma.dao.RoleRepository;
import in.nucleusteq.plasma.dao.UserWorkDetailRepository;
import in.nucleusteq.plasma.dto.in.role.RoleInDTO;
import in.nucleusteq.plasma.dto.out.role.RoleOutDTO;
import in.nucleusteq.plasma.entity.Employee;
import in.nucleusteq.plasma.entity.Permission;
import in.nucleusteq.plasma.entity.Role;
import in.nucleusteq.plasma.entity.UserWorkDetail;
import in.nucleusteq.plasma.exception.AlreadyExistsException;
import in.nucleusteq.plasma.exception.ResourceNotFoundException;
import in.nucleusteq.plasma.service.RoleService;
import jakarta.transaction.Transactional;

@Service
public class RoleServiceImplementation implements RoleService {

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PermissionRepository permissionRepository;

	@Autowired
	UserWorkDetailRepository workDetailRepository;

	@Autowired
	EmployeeRepository employeeRepository;

	@Override
	public String addRole(RoleInDTO roleInDTO) {

		if (roleRepository.findByName(roleInDTO.getName()).isPresent()) {
			String errMsg = String.format("The role %s already exists", roleInDTO.getName());
			throw new AlreadyExistsException(errMsg);
		}
		Role newRole = mapRoleRequestDtoToEntity(roleInDTO);
		roleRepository.save(newRole);
		return RoleConstant.ROLE_CREATED + newRole.getName();
	}

	@Override
	public List<RoleOutDTO> getAllRoles() {
		List<Role> allRoles = roleRepository.findAll();
		return allRoles.stream().map(this::mapRoleEntityToResponseDto).collect(Collectors.toList());
	}

	@Override
	public RoleOutDTO getRoleById(Long roleId) {
		Role role = roleRepository.findById(roleId)
				.orElseThrow(() -> new ResourceNotFoundException(RoleConstant.ROLE_NOT_FOUND + roleId));

		return mapRoleEntityToResponseDto(role);
	}

	@Override
	public RoleOutDTO getRoleByName(String roleName) {
		Role role = roleRepository.findByName(roleName)
				.orElseThrow(() -> new ResourceNotFoundException("Role not found with name: " + roleName));

		return mapRoleEntityToResponseDto(role);
	}


	
	@Override
	public String assignRolesToUser(String userId, Set<Role> roles) {
	    Employee user = employeeRepository.findById(userId)
	            .orElseThrow(() -> new RuntimeException(RoleConstant.USER_NOT_FOUND + userId));

	    UserWorkDetail userWorkDetail = workDetailRepository.findById(user.getUserWorkDetail().getId())
	            .orElseThrow(() -> new ResourceNotFoundException(
	                    RoleConstant.USER_WORK_DETAIL_NOT_FOUND + user.getUserWorkDetail().getId()));

	    Set<Role> existingRoles = userWorkDetail.getRoles();

	    for (Role role : roles) {
	        Role existingRole = roleRepository.findByName(role.getName())
	                .orElseThrow(() -> new ResourceNotFoundException(RoleConstant.ROLE_NOT_FOUND + role.getName()));

	        if (!existingRoles.contains(existingRole)) {
	            existingRoles.add(existingRole);
	        }
	    }

	    workDetailRepository.save(userWorkDetail);

	    return RoleConstant.SUCCESSFULLY_UPDATED_ROLE + user.getUserPersonalDetail().getFirstName();
	}


	
	@Override
	public String removeRolesFromUser(String userId, Set<Role> rolesToRemove) {

	    Employee user = employeeRepository.findById(userId)
	            .orElseThrow(() -> new ResourceNotFoundException(RoleConstant.USER_NOT_FOUND + userId));

	    UserWorkDetail userWorkDetail = workDetailRepository.findById(user.getUserWorkDetail().getId())
	            .orElseThrow(() -> new ResourceNotFoundException(
	                    RoleConstant.USER_WORK_DETAIL_NOT_FOUND + user.getUserWorkDetail().getId()));

	    Set<Role> existingRoles = userWorkDetail.getRoles();
	    for (Role roleToRemove : rolesToRemove) {
	        Role existingRole = roleRepository.findByName(roleToRemove.getName())
	                .orElseThrow(() -> new ResourceNotFoundException(RoleConstant.ROLE_NOT_FOUND + roleToRemove.getName()));

	        if (existingRoles.contains(existingRole)) {
	            existingRoles.remove(existingRole);
	        }
	    }
	    workDetailRepository.save(userWorkDetail);

	    return RoleConstant.SUCCESSFULLY_UPDATED_ROLE + user.getUserPersonalDetail().getFirstName();
	}


//	@Transactional
//	public String addPermissionOnRole(Long roleId, String permissionKey) {
//
//		Optional<Role> roleOpt = roleRepository.findById(roleId);
//		if (!roleOpt.isPresent()) {
//			throw new ResourceNotFoundException(String.format("Role not found with Id = %s", roleId));
//		}
//		Role role = roleOpt.get();
//		Permission permission;
//
//		Optional<Permission> permissionOpt = permissionRepository.findByPermission(permissionKey);
//		if (permissionOpt.isPresent()) {
//			permission = permissionOpt.get();
//		} else {
//			permission = new Permission();
//			permission.setPermission(permissionKey);
//
//			permission = permissionRepository.save(permission);
//		}
//
//		if (role.getPermissions().contains(permission)) {
//			throw new ResourceNotFoundException(
//					String.format("The permission %s has been already" + " associated on the role %s",
//							permission.getPermission(), role.getRole_id()));
//		}
//
//		role.getPermissions().add(permission);
//		roleRepository.save(role);
//		return String.format("Added permission %s on role id = %s", permissionKey, roleId);
//	}
	
	@Transactional
	public String addPermissionOnRole(Long roleId, String permissionKey) {

	    Optional<Role> roleOpt = roleRepository.findById(roleId);
	    if (!roleOpt.isPresent()) {
	        throw new ResourceNotFoundException(String.format("Role not found with Id = %s", roleId));
	    }
	    Role role = roleOpt.get();

	    // Retrieve the existing permission by permissionKey
	    Optional<Permission> permissionOpt = permissionRepository.findByPermission(permissionKey);
	    if (!permissionOpt.isPresent()) {
	        throw new ResourceNotFoundException(String.format("Permission not found with key = %s", permissionKey));
	    }
	    Permission permission = permissionOpt.get();

	    // Check if the role already has the permission
	    if (role.getPermissions().contains(permission)) {
	        throw new ResourceNotFoundException(String.format("The permission %s is already associated with role id = %s",
	                permission.getPermission(), roleId));
	    }

	    // Add the existing permission to the role and save
	    role.getPermissions().add(permission);
	    roleRepository.save(role);

	    return String.format("Added permission %s to role id = %s", permissionKey, roleId);
	}


	@Transactional
	public String removePermissionOnRole(Long roleId, String permissionKey) {

		Optional<Role> roleOpt = roleRepository.findById(roleId);
		if (!roleOpt.isPresent()) {
			throw new ResourceNotFoundException(String.format("Role not found with Id = %s", roleId));
		}
		Role role = roleOpt.get();

		Optional<Permission> permissionOpt = permissionRepository.findByPermission(permissionKey);
		if (!permissionOpt.isPresent()) {
			throw new ResourceNotFoundException(
					String.format("Permission not found with Id = %s on role %s", permissionKey, roleId));
		}

		Permission permission = permissionOpt.get();

		role.getPermissions().remove(permission);
		roleRepository.save(role);

		return String.format("Removed permission %s from role id = %s", permissionKey, roleId);
	}

	private Role mapRoleRequestDtoToEntity(RoleInDTO roleInDTO) {
		Role role = new Role();
		role.setName(roleInDTO.getName());
		return role;
	}

	private RoleOutDTO mapRoleEntityToResponseDto(Role role) {
		RoleOutDTO responseDto = new RoleOutDTO();
		responseDto.setId(role.getRole_id());
		responseDto.setName(role.getName());
		return responseDto;
	}
}
