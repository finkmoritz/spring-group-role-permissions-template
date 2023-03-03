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
    companion object {
        const val ID_READ_GROUP: Long = 0
        const val ID_UPDATE_GROUP: Long = 1
        const val ID_DELETE_GROUP: Long = 2
    }

    override fun equals(other: Any?): Boolean {
        if (other !is Permission) {
            return false
        }
        return this.name == other.name
    }
}
