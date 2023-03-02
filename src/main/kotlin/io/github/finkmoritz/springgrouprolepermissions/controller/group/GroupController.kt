package io.github.finkmoritz.springgrouprolepermissions.controller.group

import io.github.finkmoritz.springgrouprolepermissions.auth.MyUserPrincipal
import io.github.finkmoritz.springgrouprolepermissions.entity.group.Group
import io.github.finkmoritz.springgrouprolepermissions.repository.group.GroupRepository
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
) {
    @GetMapping("/{groupId}")
    fun get(
        @PathVariable groupId: Long,
        @AuthenticationPrincipal principal: MyUserPrincipal,
    ): ResponseEntity<Group> {
        val group = groupRepository.findById(groupId)
        return if (group.isPresent) ResponseEntity.ok(group.get()) else ResponseEntity.notFound().build()
    }

    //@RequireVereintToken
    @PostMapping
    fun create(
        @RequestBody group : Group,
        //vereintPrincipal: VereintPrincipal,
    ): ResponseEntity<Group> {
        val newGroup = groupRepository.save(group)
        return ResponseEntity.ok(newGroup)
    }

    //@RequireVereintToken(requireGroupPermissions = [Permission.PermissionValue.UPDATE_ASSOCIATION])
    @PutMapping("/{groupId}")
    fun update(
        @PathVariable groupId: Long,
        @RequestBody group : Group,
        //vereintPrincipal: VereintPrincipal,
    ): ResponseEntity<Group> {
        val updatedGroup = groupRepository.save(group)
        return ResponseEntity.ok(updatedGroup)
    }

    //@RequireVereintToken(requireGroupPermissions = [Permission.PermissionValue.DELETE_ASSOCIATION])
    @DeleteMapping("/{groupId}")
    fun delete(
        @PathVariable groupId: Long,
        //vereintPrincipal: VereintPrincipal,
    ): ResponseEntity<Void> {
        groupRepository.deleteById(groupId)
        return ResponseEntity.ok().build()
    }
}
