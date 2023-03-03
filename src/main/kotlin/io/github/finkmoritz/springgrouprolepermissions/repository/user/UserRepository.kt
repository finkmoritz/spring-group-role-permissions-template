package io.github.finkmoritz.springgrouprolepermissions.repository.user

import io.github.finkmoritz.springgrouprolepermissions.entity.user.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query


interface UserRepository : JpaRepository<User, Long> {
    fun findByUsername(username: String): User?

    @Query(
        "select u " +
                "from User u " +
                "left join fetch u.groupUserRoles gur " +
                "left join fetch gur.embeddedGroup.group " +
                "left join fetch gur.embeddedRole.role role " +
                "left join fetch role.permissions " +
                "where u.username = :username "
    )
    fun findUserWithGroupRolesAndPermissions(username: String): User?
}
