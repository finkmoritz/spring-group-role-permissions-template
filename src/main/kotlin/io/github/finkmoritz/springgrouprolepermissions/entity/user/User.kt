package io.github.finkmoritz.springgrouprolepermissions.entity.user

import io.github.finkmoritz.springgrouprolepermissions.entity.groupuserrole.GroupUserRole
import lombok.AllArgsConstructor
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter
import org.hibernate.annotations.Fetch
import org.hibernate.annotations.FetchMode
import jakarta.persistence.*

@Entity
@Table(name = "\"user\"", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(name = "user_seq", sequenceName = "user_seq")
    var id: Long? = null,

    @Getter
    @Setter
    var username: String,

    @Getter
    @Setter
    var password: String,

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "embeddedUser.user", orphanRemoval = true)
    @Fetch(FetchMode.JOIN)
    var groupUserRoles: Set<GroupUserRole> = setOf(),
) {
    fun copyWith(
        id: Long? = this.id,
        username: String = this.username,
        password: String = this.password,
    ) : User {
        return User(
            id,
            username,
            password,
        )
    }
}
