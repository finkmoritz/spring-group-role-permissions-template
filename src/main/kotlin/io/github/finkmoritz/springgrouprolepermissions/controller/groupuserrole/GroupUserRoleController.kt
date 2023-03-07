package io.github.finkmoritz.springgrouprolepermissions.controller.groupuserrole

import io.github.finkmoritz.springgrouprolepermissions.auth.MyUserPrincipal
import io.github.finkmoritz.springgrouprolepermissions.auth.RequireAuth
import io.github.finkmoritz.springgrouprolepermissions.entity.group.Group
import io.github.finkmoritz.springgrouprolepermissions.entity.groupuserrole.GroupUserRole
import io.github.finkmoritz.springgrouprolepermissions.repository.groupuserrole.GroupUserRoleRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.util.*

@RestController
@Component
@RequestMapping("/api/groupuserrole/group/{groupId}")
class GroupUserRoleController(
    private val groupUserRoleRepository: GroupUserRoleRepository,
) {
    @RequireAuth(requireGroupRoles = ["ADMIN"])
    @GetMapping
    fun get(
        @PathVariable groupId: Long,
        @AuthenticationPrincipal principal: MyUserPrincipal,
    ): ResponseEntity<List<GroupUserRole>> {
        val groupUserRoles = groupUserRoleRepository.findAllByEmbeddedGroupGroupId(
            groupId = groupId,
        )
        return ResponseEntity.ok(groupUserRoles)
    }

    @RequireAuth(requireGroupRoles = ["ADMIN"])
    @GetMapping("/user/{userId}")
    fun getByUserId(
        @PathVariable groupId: Long,
        @PathVariable userId: Long,
        @AuthenticationPrincipal principal: MyUserPrincipal,
    ): ResponseEntity<List<GroupUserRole>> {
        val groupUserRoles = groupUserRoleRepository.findAllByEmbeddedGroupGroupIdAndEmbeddedUserUserId(
            groupId = groupId,
            userId = userId,
        )
        return ResponseEntity.ok(groupUserRoles)
    }

    @RequireAuth(requireGroupRoles = ["ADMIN"])
    @GetMapping("/role/{roleId}")
    fun getByRoleId(
        @PathVariable groupId: Long,
        @PathVariable roleId: Long,
        @AuthenticationPrincipal principal: MyUserPrincipal,
    ): ResponseEntity<List<GroupUserRole>> {
        val groupUserRoles = groupUserRoleRepository.findAllByEmbeddedGroupGroupIdAndEmbeddedRoleRoleId(
            groupId = groupId,
            roleId = roleId,
        )
        return ResponseEntity.ok(groupUserRoles)
    }

    @RequireAuth(requireGroupRoles = ["ADMIN"])
    @PostMapping("/user/{userId}/role/{roleId}")
    fun create(
        @PathVariable groupId: Long,
        @PathVariable userId: Long,
        @PathVariable roleId: Long,
        @AuthenticationPrincipal principal: MyUserPrincipal,
    ): ResponseEntity<Group> {
        groupUserRoleRepository.save(GroupUserRole(
            groupId = groupId,
            userId = userId,
            roleId = roleId,
        ))
        return ResponseEntity.ok().build()
    }

    @RequireAuth(requireGroupRoles = ["ADMIN"])
    @DeleteMapping("/user/{userId}/role/{roleId}")
    fun delete(
        @PathVariable groupId: Long,
        @PathVariable userId: Long,
        @PathVariable roleId: Long,
        @AuthenticationPrincipal principal: MyUserPrincipal,
    ): ResponseEntity<Void> {
        val groupUserRole = groupUserRoleRepository.findByEmbeddedGroupGroupIdAndEmbeddedUserUserIdAndEmbeddedRoleRoleId(
            groupId = groupId,
            userId = userId,
            roleId = roleId,
        )
        if (groupUserRole == null) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find entry to delete")
        } else {
            groupUserRoleRepository.delete(groupUserRole)
            return ResponseEntity.ok().build()
        }
    }
}
