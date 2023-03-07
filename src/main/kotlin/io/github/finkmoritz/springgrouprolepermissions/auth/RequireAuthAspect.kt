package io.github.finkmoritz.springgrouprolepermissions.auth

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.reflect.MethodSignature
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.server.ResponseStatusException
import java.util.*


@Aspect
@Component
class RequireAuthAspect() {

    companion object {
        val log: Logger = LoggerFactory.getLogger(this::class.java)
    }

    @Around("@annotation(io.github.finkmoritz.springgrouprolepermissions.auth.RequireAuth)")
    fun around(jp: ProceedingJoinPoint): ResponseEntity<Any> {
        try {
            val modifiedArgs = jp.args

            val principal = SecurityContextHolder.getContext().authentication.principal as MyUserPrincipal
            val groupUserRoles = principal.user.groupUserRoles

            val annotation = getAnnotation(jp)
            val requiredGroupRoles = annotation.requireGroupRoles.toList()
            val requiredGroupPermissions = annotation.requireGroupPermissions.toList()

            if (requiredGroupRoles.isNotEmpty() || requiredGroupPermissions.isNotEmpty()) {
                val requestedGroupId = getArgument(jp, "groupId") as Long

                val relevantGroupUserRoles =
                    groupUserRoles.filter { gur -> gur.embeddedGroup.groupId == requestedGroupId }
                val groupRoles = relevantGroupUserRoles.map { gur -> gur.embeddedRole.role }
                val groupPermissions = groupRoles.flatMap { gr -> gr?.permissions ?: setOf() }

                val groupRoleNames = groupRoles.map { it?.name ?: "" }.toSet()
                if (!groupRoleNames.containsAll(requiredGroupRoles)) {
                    val missingRoles = requiredGroupRoles.minus(groupRoleNames).joinToString(", ")
                    throw ResponseStatusException(HttpStatus.FORBIDDEN, "Missing role(s): $missingRoles")
                }

                val groupPermissionNames = groupPermissions.map { it.name }.toSet()
                if (!groupPermissionNames.containsAll(requiredGroupPermissions)) {
                    val missingPermissions =
                        requiredGroupPermissions.minus(groupPermissionNames.toSet()).joinToString(", ")
                    throw ResponseStatusException(HttpStatus.FORBIDDEN, "Missing permission(s): $missingPermissions")
                }
            }

            for (i in jp.args.indices) {
                if (jp.args[i] is MyUserPrincipal) {
                    modifiedArgs[i] = principal
                }
            }

            return jp.proceed(modifiedArgs) as ResponseEntity<Any>

        } catch (e: ResponseStatusException) {
            throw e
        } catch (e: Exception) {
            log.error("Unknown error during authentication!", e)
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unbekannter Fehler")
        }
    }

    private fun getAnnotation(jp: ProceedingJoinPoint): RequireAuth {
        val signature = jp.signature as MethodSignature
        val method = jp.target.javaClass.getMethod(signature.method.name, *signature.method.parameterTypes)
        return method.getAnnotation(RequireAuth::class.java)
    }

    private fun getArgumentIndex(jp: ProceedingJoinPoint, name: String): Int {
        val signature = jp.signature as MethodSignature
        val parameters = signature.parameterNames
        return parameters.indexOf(name)
    }

    private fun getArgument(jp: ProceedingJoinPoint, name: String): Any? {
        val args = jp.args
        val parameterIndex = getArgumentIndex(jp, name)

        if (parameterIndex != -1 && parameterIndex < args.size) {
            return args[parameterIndex]
        }
        return null
    }
}
