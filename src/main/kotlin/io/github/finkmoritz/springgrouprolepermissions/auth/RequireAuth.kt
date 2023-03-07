package io.github.finkmoritz.springgrouprolepermissions.auth

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class RequireAuth(
    val requireGroupRoles: Array<String> = [],
    val requireGroupPermissions: Array<String> = [],
)
