package io.github.finkmoritz.springgrouprolepermissions.auth

/**
 * Annotation for Controller methods which require authentication.
 *
 * @param requireGroupRoles Array of required roles for the requested Group
 * If you provide an Array of requireGroupRoles, then the Aspect checks them against the
 * roles in the principal and denies the request if not found. Since the roles to be checked are
 * defined per Group, the method call MUST CONTAIN the groupId (Long) !
 *
 * @param requireGroupPermissions Array of required permissions for the requested Group
 * If you provide an Array of requireGroupPermissions, then the Aspect checks them against the
 * permissions in the principal and denies the request if not found. Since the permissions to be checked are
 * defined per Group, the method call MUST CONTAIN the groupId (Long) !
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class RequireAuth(
    val requireGroupRoles: Array<String> = [],
    val requireGroupPermissions: Array<String> = [],
)
