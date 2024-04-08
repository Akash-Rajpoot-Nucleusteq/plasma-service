package in.nucleusteq.plasma.service;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.security.access.prepost.PreAuthorize;

public class PermissionDefinitions {


	@Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @PreAuthorize("@permissionValidationService.hasPermission('ONBOARDING_PERMISSION')")
    public @interface HasOnboardingPermission {
    }
	
	@Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @PreAuthorize("@permissionValidationService.hasPermission('REJECT_ONBOARDING_PERMISSION')")
    public @interface HasRejectOnboardingPermission {
    }

	@Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @PreAuthorize("@permissionValidationService.hasPermission('ACCEPT_ONBOARDING_PERMISSION')")
    public @interface HasAcceptOnboardingPermission {
    }

	@Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @PreAuthorize("@permissionValidationService.hasPermission('SHOW_PENDING_ONBOARDING_PERMISSION')")
    public @interface HasShowPendingOnboardingPermission {
    }
}
