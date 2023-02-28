package io.github.finkmoritz.springgrouprolepermissions.entity.user

import lombok.Getter
import lombok.Setter
import jakarta.persistence.*

@Embeddable
class EmbeddedUser(
    @Getter
    @Setter
    @Column(name = "user_id")
    var userId: Long,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    var user: User? = null,
)
