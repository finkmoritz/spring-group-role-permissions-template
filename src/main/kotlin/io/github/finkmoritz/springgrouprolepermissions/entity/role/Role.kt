package io.github.finkmoritz.springgrouprolepermissions.entity.role

import io.github.finkmoritz.springgrouprolepermissions.entity.permission.Permission
import lombok.AllArgsConstructor
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter
import jakarta.persistence.*

@Entity
@Table(name = "role", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
class Role(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_seq")
    @SequenceGenerator(name = "role_seq", sequenceName = "role_seq")
    var id: Long?,

    @Getter
    @Setter
    var name: String,

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "role_permission",
        joinColumns = [JoinColumn(name = "role_id")],
        inverseJoinColumns = [JoinColumn(name = "permission_id")]
    )
    var permissions: Set<Permission>
) {
    override fun equals(other: Any?): Boolean {
        if (other !is Role) {
            return false
        }
        return this.name == other.name
    }
}
