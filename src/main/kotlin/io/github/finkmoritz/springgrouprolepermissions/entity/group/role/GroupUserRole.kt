package io.github.finkmoritz.springgrouprolepermissions.entity.group.role

import io.github.finkmoritz.springgrouprolepermissions.entity.group.EmbeddedGroup
import io.github.finkmoritz.springgrouprolepermissions.entity.role.EmbeddedRole
import io.github.finkmoritz.springgrouprolepermissions.entity.user.EmbeddedUser
import lombok.AllArgsConstructor
import lombok.NoArgsConstructor
import jakarta.persistence.*

@Entity
@Table(name = "group_user_role", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
class GroupUserRole(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?,

    @Embedded
    var embeddedGroup: EmbeddedGroup,

    @Embedded
    var embeddedUser: EmbeddedUser,

    @Embedded
    var embeddedRole: EmbeddedRole,
) {
    constructor(
        groupId: Long,
        userId: Long,
        groupRoleId: Long,
    ): this(
        null,
        EmbeddedGroup(groupId),
        EmbeddedUser(userId),
        EmbeddedRole(groupRoleId),
    )
}
