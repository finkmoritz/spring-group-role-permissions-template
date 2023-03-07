package io.github.finkmoritz.springgrouprolepermissions.entity.permission

import jakarta.persistence.*
import lombok.AllArgsConstructor
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter

@Entity
@Table(name = "permission", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
class Permission(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "permission_seq")
    @SequenceGenerator(name = "permission_seq", sequenceName = "permission_seq")
    var id: Long?,

    @Getter
    @Setter
    var name: String
) {
    override fun equals(other: Any?): Boolean {
        if (other !is Permission) {
            return false
        }
        return this.name == other.name
    }
}
