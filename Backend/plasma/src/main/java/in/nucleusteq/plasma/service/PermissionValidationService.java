package in.nucleusteq.plasma.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import in.nucleusteq.plasma.dao.PermissionRepository;
import in.nucleusteq.plasma.dao.RoleRepository;
import in.nucleusteq.plasma.entity.Permission;
import in.nucleusteq.plasma.entity.Role;

@Service
public class PermissionValidationService {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PermissionRepository permissionRepository;

    public boolean hasPermission(String permissionName) {
        List<String> rolesAndPermissions = getRolesAndPermissionsForCurrentUser();
        return rolesAndPermissions.contains(permissionName);
    }

    public List<String> getRolesAndPermissionsForCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return Collections.emptyList();
        }
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Set<String> roles = new HashSet<>();
        Set<String> permissions = new HashSet<>();

        for (GrantedAuthority authority : authorities) {
            String roleName = authority.getAuthority();
            Optional<Role> roleOptional = roleRepository.findByName(roleName);

            if (roleOptional.isPresent()) {
                Role role = roleOptional.get();
                roles.add(roleName);

                Set<Permission> rolePermissions = role.getPermissions();
                for (Permission permission : rolePermissions) {
                    permissions.add(permission.getPermission());
                }
            }
        }

        List<String> rolesAndPermissions = new ArrayList<>();
        rolesAndPermissions.addAll(roles);
        rolesAndPermissions.addAll(permissions);
        return rolesAndPermissions;
    }
}
