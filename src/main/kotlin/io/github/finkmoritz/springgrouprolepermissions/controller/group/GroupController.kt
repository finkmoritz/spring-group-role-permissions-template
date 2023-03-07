package io.github.finkmoritz.springgrouprolepermissions.controller.group

import io.github.finkmoritz.springgrouprolepermissions.auth.MyUserPrincipal
import io.github.finkmoritz.springgrouprolepermissions.auth.RequireAuth
import io.github.finkmoritz.springgrouprolepermissions.entity.group.Group
import io.github.finkmoritz.springgrouprolepermissions.entity.group.role.GroupUserRole
import io.github.finkmoritz.springgrouprolepermissions.entity.role.Role
import io.github.finkmoritz.springgrouprolepermissions.repository.group.GroupRepository
import io.github.finkmoritz.springgrouprolepermissions.repository.group.role.GroupUserRoleRepository
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@Component
@RequestMapping("/api/group")
class GroupController(
    private val groupRepository: GroupRepository,
    private val groupUserRoleRepository: GroupUserRoleRepository,
) {
    @RequireAuth(requireGroupPermissions = ["READ_GROUP"])
    @GetMapping("/{groupId}")
    fun get(
        @PathVariable groupId: Long,
        @AuthenticationPrincipal principal: MyUserPrincipal,
    ): ResponseEntity<Group> {
        val group = groupRepository.findById(groupId)
        return if (group.isPresent) ResponseEntity.ok(group.get()) else ResponseEntity.notFound().build()
    }

    @PostMapping
    fun create(
        @RequestBody group : Group,
        @AuthenticationPrincipal principal: MyUserPrincipal,
    ): ResponseEntity<Group> {
        val newGroup = groupRepository.save(group)
        groupUserRoleRepository.save(GroupUserRole(newGroup.id!!, principal.user.id!!, 0)) // ADMIN
        groupUserRoleRepository.save(GroupUserRole(newGroup.id!!, principal.user.id!!, 1)) // MEMBER
        return ResponseEntity.ok(newGroup)
    }

    @RequireAuth(requireGroupPermissions = ["UPDATE_GROUP"])
    @PutMapping("/{groupId}")
    fun update(
        @PathVariable groupId: Long,
        @RequestBody group : Group,
        @AuthenticationPrincipal principal: MyUserPrincipal,
    ): ResponseEntity<Group> {
        val updatedGroup = groupRepository.save(group)
        return ResponseEntity.ok(updatedGroup)
    }

    @RequireAuth(requireGroupPermissions = ["DELETE_GROUP"])
    @DeleteMapping("/{groupId}")
    fun delete(
        @PathVariable groupId: Long,
        @AuthenticationPrincipal principal: MyUserPrincipal,
    ): ResponseEntity<Void> {
        groupRepository.deleteById(groupId)
        return ResponseEntity.ok().build()
    }
}
