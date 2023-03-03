package io.github.finkmoritz.springgrouprolepermissions.entity.permission

import lombok.Getter
import lombok.Setter
import jakarta.persistence.*

@Embeddable
class EmbeddedPermission(
    @Getter
    @Setter
    @Column(name = "permission_id")
    var permissionId: Long,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "permission_id", insertable = false, updatable = false)
    var permission: Permission? = null,
)
