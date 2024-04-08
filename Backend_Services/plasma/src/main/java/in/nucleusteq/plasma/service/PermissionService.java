package in.nucleusteq.plasma.service;

import in.nucleusteq.plasma.dto.in.PermissionInDTO;
import in.nucleusteq.plasma.entity.Permission;

public interface PermissionService {

    Iterable<Permission> getPermissionList();

    Permission getPermissionByKey(String key);

    String createPermission(PermissionInDTO permissionDTO);

    String updatePermission(long permissionId,PermissionInDTO permissionDTO);

    void deletePermissionByKey(String permissionKey);

}

