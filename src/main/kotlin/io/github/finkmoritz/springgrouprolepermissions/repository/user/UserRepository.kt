package io.github.finkmoritz.springgrouprolepermissions.repository.user

import io.github.finkmoritz.springgrouprolepermissions.entity.user.User
import org.springframework.data.jpa.repository.JpaRepository


interface UserRepository : JpaRepository<User?, Long?> {
    fun findByUsername(username: String?): User?
}
