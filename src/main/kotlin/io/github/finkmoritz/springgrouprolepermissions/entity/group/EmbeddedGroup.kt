package io.github.finkmoritz.springgrouprolepermissions.entity.group

import lombok.Getter
import lombok.Setter
import jakarta.persistence.*

@Embeddable
class EmbeddedGroup(
    @Getter
    @Setter
    @Column(name = "group_id")
    var groupId: Long,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", insertable = false, updatable = false)
    var group: Group? = null,
)
