package io.github.finkmoritz.springgrouprolepermissions.repository.group.role

import io.github.finkmoritz.springgrouprolepermissions.entity.group.role.GroupUserRole
import org.springframework.data.jpa.repository.JpaRepository

interface GroupUserRoleRepository : JpaRepository<GroupUserRole, Long>
