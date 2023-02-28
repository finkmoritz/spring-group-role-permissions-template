package io.github.finkmoritz.springgrouprolepermissions.entity.role

import lombok.Getter
import lombok.Setter
import jakarta.persistence.*

@Embeddable
class EmbeddedRole(
    @Getter
    @Setter
    @Column(name = "role_id")
    var roleId: Long,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", insertable = false, updatable = false)
    var role: Role? = null,
)
