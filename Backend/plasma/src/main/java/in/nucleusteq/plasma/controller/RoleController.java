package in.nucleusteq.plasma.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import in.nucleusteq.plasma.dto.in.role.RoleInDTO;
import in.nucleusteq.plasma.dto.out.role.RoleOutDTO;
import in.nucleusteq.plasma.entity.Role;
import in.nucleusteq.plasma.service.RoleService;

@RestController
@RequestMapping("/plasma/role")
public class RoleController {

//    private static final Logger logger = LoggerFactory.getLogger(RoleController.class);

	@Autowired
	private RoleService roleService;

	@PostMapping("/add")
//	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<String> addRole(@RequestBody RoleInDTO roleInDTO) {
//		logger.info("Creating role in the database");
		String responseDto = roleService.addRole(roleInDTO);
		return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
	}

	@GetMapping("/all")
	public ResponseEntity<List<RoleOutDTO>> getAllRoles() {
		List<RoleOutDTO> roles = roleService.getAllRoles();
		return new ResponseEntity<>(roles, HttpStatus.OK);
	}

	@GetMapping("/{roleId}")
	public ResponseEntity<RoleOutDTO> getRoleById(@PathVariable("roleId") Long roleId) {
		RoleOutDTO roles = roleService.getRoleById(roleId);
		return new ResponseEntity<>(roles, HttpStatus.OK);
	}

	@PostMapping("/{userId}/assignRoles")
//	@PreAuthorize("hasAuthority('ADMIN','MANAGER')")
	public ResponseEntity<String> assignRolesToUser(@PathVariable("userId") String userId,
			@RequestBody Set<Role> roles) {
		String response = roleService.assignRolesToUser(userId, roles);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("/{userId}/removeRoles")
 
	// @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('MANAGER')")
	public ResponseEntity<String> removeRolesFromUser(@PathVariable("userId") String userId,
			@RequestBody Set<Role> roles) {
		System.out.println("Reached in romove Role ontroller.");
		String response = roleService.removeRolesFromUser(userId, roles);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("/{roleId}/permissions/{permissionKey}")
	public ResponseEntity<String> addPermissionOnRole(
			@PathVariable("roleId") Long roleId, @PathVariable("permissionKey") String permissionKey) {
	    roleService.addPermissionOnRole(roleId, permissionKey);
	    return ResponseEntity.status(HttpStatus.CREATED).body("Permission added to role with ID: " + roleId);
	}

	@DeleteMapping("/{roleId}/permissions/{permissionKey}")
	public ResponseEntity<String> removePermissionOnRole(
			@PathVariable("roleId") Long roleId, @PathVariable("permissionKey") String permissionKey) {
	    roleService.removePermissionOnRole(roleId, permissionKey);
	    return ResponseEntity.status(HttpStatus.OK).body("Permission removed from role with ID: " + roleId);
	}

}
