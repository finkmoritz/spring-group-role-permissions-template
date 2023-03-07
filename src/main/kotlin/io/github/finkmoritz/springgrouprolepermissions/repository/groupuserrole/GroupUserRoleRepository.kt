package io.github.finkmoritz.springgrouprolepermissions.repository.groupuserrole

import io.github.finkmoritz.springgrouprolepermissions.entity.groupuserrole.GroupUserRole
import org.springframework.data.jpa.repository.JpaRepository

interface GroupUserRoleRepository : JpaRepository<GroupUserRole, Long> {
    fun findByEmbeddedGroupGroupIdAndEmbeddedUserUserIdAndEmbeddedRoleRoleId(
        groupId: Long,
        userId: Long,
        roleId: Long,
    ): GroupUserRole?

    fun findAllByEmbeddedGroupGroupId(
        groupId: Long,
    ): List<GroupUserRole>

    fun findAllByEmbeddedGroupGroupIdAndEmbeddedUserUserId(
        groupId: Long,
        userId: Long,
    ): List<GroupUserRole>

    fun findAllByEmbeddedGroupGroupIdAndEmbeddedRoleRoleId(
        groupId: Long,
        roleId: Long,
    ): List<GroupUserRole>
}
