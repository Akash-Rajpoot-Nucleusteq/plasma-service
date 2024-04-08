package in.nucleusteq.plasma.dto.out;

import in.nucleusteq.plasma.entity.Permission;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class PermissionOutDTO {

	

	    private Long id;
	    private String permission;
	    private boolean enabled;
	    private String note;

	    public PermissionOutDTO(Permission permission) {
	        this.id = permission.getId();
	        this.permission = permission.getPermission();
	        this.enabled = permission.isEnabled();
	        this.note = permission.getNote();
	    }

	}