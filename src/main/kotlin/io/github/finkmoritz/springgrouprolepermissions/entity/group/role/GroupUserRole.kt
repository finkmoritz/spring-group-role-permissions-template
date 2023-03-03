package io.github.finkmoritz.springgrouprolepermissions.entity.group.role

import com.fasterxml.jackson.annotation.JsonIgnore
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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "group_user_role_seq")
    @SequenceGenerator(name = "group_user_role_seq", sequenceName = "group_user_role_seq")
    var id: Long?,

    @Embedded
    var embeddedGroup: EmbeddedGroup,

    @Embedded
    @JsonIgnore
    var embeddedUser: EmbeddedUser,

    @Embedded
    var embeddedRole: EmbeddedRole,
) {
    constructor(
        groupId: Long,
        userId: Long,
        roleId: Long,
    ): this(
        null,
        EmbeddedGroup(groupId),
        EmbeddedUser(userId),
        EmbeddedRole(roleId),
    )
}
