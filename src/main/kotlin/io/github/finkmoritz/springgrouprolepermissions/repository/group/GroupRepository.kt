package io.github.finkmoritz.springgrouprolepermissions.repository.group

import io.github.finkmoritz.springgrouprolepermissions.entity.group.Group
import org.springframework.data.jpa.repository.JpaRepository

interface GroupRepository : JpaRepository<Group, Long>
