package io.github.finkmoritz.springgrouprolepermissions.entity.role.permission

import io.github.finkmoritz.springgrouprolepermissions.entity.role.EmbeddedRole
import lombok.AllArgsConstructor
import lombok.NoArgsConstructor
import jakarta.persistence.*

@Entity
@Table(name = "\"role_permission\"", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
class RolePermission(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?,

    @Embedded
    var embeddedRole: EmbeddedRole,

    @Embedded
    var embeddedPermission: EmbeddedPermission,
)
