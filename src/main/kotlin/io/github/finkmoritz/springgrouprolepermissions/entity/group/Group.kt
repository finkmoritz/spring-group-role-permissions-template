package io.github.finkmoritz.springgrouprolepermissions.entity.group

import jakarta.persistence.*
import lombok.AllArgsConstructor
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter

@Entity
@Table(name = "group", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
class Group(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?,

    @Getter
    @Setter
    var name: String,
)
