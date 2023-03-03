package io.github.finkmoritz.springgrouprolepermissions.repository.role

import io.github.finkmoritz.springgrouprolepermissions.entity.role.Role
import org.springframework.data.jpa.repository.JpaRepository

interface RoleRepository : JpaRepository<Role, Long>
