package in.nucleusteq.plasma.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.nucleusteq.plasma.dao.PermissionRepository;
import in.nucleusteq.plasma.dto.in.PermissionInDTO;
import in.nucleusteq.plasma.entity.Permission;
import in.nucleusteq.plasma.exception.ResourceNotFoundException;
import in.nucleusteq.plasma.service.PermissionService;

@Service
public class PermissionServiceImplementation implements PermissionService {

	@Autowired
	PermissionRepository permissionRepository;

	@Override
	public Iterable<Permission> getPermissionList() {
		return permissionRepository.findAll();
	}

	@Override
	public Permission getPermissionByKey(String key) {

		Optional<Permission> userOpt = permissionRepository.findByPermission(key);

		if (userOpt.isPresent()) {
			return userOpt.get();
		}
		throw new ResourceNotFoundException("Permission Not Found with name : " + key);
	}

	@Override
	public String createPermission(PermissionInDTO permissionDTO) {

		Optional<Permission> permissionOpt = permissionRepository.findByPermission(permissionDTO.getPermission());
		if (permissionOpt.isPresent()) {
			throw new ResourceNotFoundException(
					"Permission already existing with the same name :" + permissionDTO.getPermission());
		}

		Permission permission = new Permission();
		permission.setPermission(permissionDTO.getPermission());
		permission.setNote(permissionDTO.getNote());

		permissionRepository.save(permission);

		return "Created permission with name :" + permissionDTO.getPermission();
	}

	@Override
	public String updatePermission(long permissionId, PermissionInDTO permissionDTO) {

		Optional<Permission> permissionOpt = permissionRepository.findById(permissionId);
		if (!permissionOpt.isPresent()) {
			throw new ResourceNotFoundException(
					String.format("The permission with the id = %s has not been found", permissionId));
		}

		Permission permission = permissionOpt.get();
		String permissionKey = permissionDTO.getPermission();

		Optional<Permission> permissionByKeyOpt = permissionRepository.findByPermission(permissionKey);
		if (permissionByKeyOpt.isPresent()) {
			Permission permission1 = permissionByKeyOpt.get();
			if (!permission1.getId().equals(permission.getId())) {
				throw new ResourceNotFoundException(String
						.format("Exists already a permission with the key %s." + " Use another key", permissionKey));
			}
		}
		permission.setPermission(permissionDTO.getPermission());
		permission.setNote(permissionDTO.getNote());

		permissionRepository.save(permission);

		return "Permission with id = %s has been updated." + permission.getId();
	}

	@Override
	public void deletePermissionByKey(String permissionKey) {

		Optional<Permission> permissionOpt = permissionRepository.findByPermission(permissionKey);
		if (!permissionOpt.isPresent()) {
			throw new ResourceNotFoundException(String.format("Permission %s not found", permissionKey));
		}

		Permission permission = permissionOpt.get();

		Long rowsFound = permissionRepository.countPermissionUsage(permission.getId());
		if (rowsFound > 0) {
			throw new ResourceNotFoundException(String
					.format("The permission with key %s is in used (%s configuration rows)", permissionKey, rowsFound));
		}

		permissionRepository.delete(permission);
	}

}
