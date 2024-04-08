package in.nucleusteq.plasma.service.impl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.nucleusteq.plasma.dao.RoleRepository;
import in.nucleusteq.plasma.dao.UserWorkDetailRepository;
import in.nucleusteq.plasma.entity.Role;
import in.nucleusteq.plasma.entity.UserWorkDetail;
import in.nucleusteq.plasma.service.UserWorkDetailService;

@Service
public class UserWorkDetailServiceImplementation implements UserWorkDetailService {



	@Autowired
    private UserWorkDetailRepository userWorkDetailRepository;

	@Autowired
	private RoleRepository roleRepository;

    @Override
    public UserWorkDetail saveUserWorkDetail(UserWorkDetail userWorkDetail) {
    	Set<Role> validatedRoles = validateAndRetrieveRoles(userWorkDetail.getRoles());
    	userWorkDetail.setRoles(validatedRoles);
        return userWorkDetailRepository.save(userWorkDetail);
    }
    
    private Set<Role> validateAndRetrieveRoles(Set<Role> roles) {
	    Set<Role> validatedRoles = new HashSet<>();

	    for (Role role : roles) {
	        try {
	            Role existingRole = roleRepository.findById(role.getRole_id())
	                    .orElseThrow(() -> new RuntimeException("Role does not exist with id: " + role.getRole_id()));

//	            if (!existingRole.getName().equals(role.getName())) {
//	                throw new RuntimeException("Role name mismatch for id: " + role.getRole_id());
//	            }

	            validatedRoles.add(existingRole);
	        } catch (Exception e) {
	            throw new RuntimeException("Error validating role existence for id: " + role.getRole_id(), e);
	        }
	    }

	    return validatedRoles;
	}
}