package in.nucleusteq.plasma.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.nucleusteq.plasma.dto.in.PermissionInDTO;
import in.nucleusteq.plasma.dto.out.PermissionOutDTO;
import in.nucleusteq.plasma.entity.Permission;
import in.nucleusteq.plasma.service.PermissionService;

@RestController
@RequestMapping("/plasma/permissions")
public class PremissionController {

	@Autowired
	private PermissionService permissionService;

	@GetMapping("/all")
	public ResponseEntity<List<PermissionOutDTO>> getPermissionPresentationList() {
		Iterable<Permission> permissionList = permissionService.getPermissionList();
		ArrayList<PermissionOutDTO> list = new ArrayList<>();
		permissionList.forEach(e -> list.add(new PermissionOutDTO(e)));
		return ResponseEntity.ok(list);
	}

	@GetMapping("/{permissionKey}")
	public ResponseEntity<PermissionOutDTO> getPermissionByKey(@PathVariable("permissionKey") String permissionKey) {
		PermissionOutDTO permissionDTO = new PermissionOutDTO(permissionService.getPermissionByKey(permissionKey));
		return ResponseEntity.ok(permissionDTO);
	}

	@PostMapping("/create")
	public ResponseEntity<String> createPermission(@RequestBody PermissionInDTO permissionInDTO) {
		String response = permissionService.createPermission(permissionInDTO);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@PutMapping("/update")
	public ResponseEntity<String> updatePermission(@PathVariable("permissionId") long permissionId,
			@RequestBody PermissionInDTO permissionDTO) {
		String response = permissionService.updatePermission(permissionId, permissionDTO);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@DeleteMapping("/delete/{permissionKey}")
	public ResponseEntity<?> deletePermissionByKey(@PathVariable("permissionKey") String permissionKey) {
		permissionService.deletePermissionByKey(permissionKey);
		return ResponseEntity.noContent().build();
	}
}
