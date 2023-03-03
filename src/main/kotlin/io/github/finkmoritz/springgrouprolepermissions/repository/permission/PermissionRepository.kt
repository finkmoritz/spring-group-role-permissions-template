package io.github.finkmoritz.springgrouprolepermissions.repository.permission

import io.github.finkmoritz.springgrouprolepermissions.entity.permission.Permission
import org.springframework.data.jpa.repository.JpaRepository

interface PermissionRepository : JpaRepository<Permission, Long>
