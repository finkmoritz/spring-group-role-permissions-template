package io.github.finkmoritz.springgrouprolepermissions.repository.groupuserrole

import io.github.finkmoritz.springgrouprolepermissions.entity.groupuserrole.GroupUserRole
import org.springframework.data.jpa.repository.JpaRepository

interface GroupUserRoleRepository : JpaRepository<GroupUserRole, Long>
