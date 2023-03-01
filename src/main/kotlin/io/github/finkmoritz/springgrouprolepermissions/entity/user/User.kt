package io.github.finkmoritz.springgrouprolepermissions.entity.user

import io.github.finkmoritz.springgrouprolepermissions.entity.group.role.GroupUserRole
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?,

    @Getter
    @Setter
    @Column(unique = true)
    var username: String,

    @Getter
    @Setter
    var password: String,

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "embeddedUser.user", orphanRemoval = true)
    @Fetch(FetchMode.JOIN)
    var groupUserRoles: Set<GroupUserRole> = setOf(),
)
