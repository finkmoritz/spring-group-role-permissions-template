package io.github.finkmoritz.springgrouprolepermissions.entity.group

import jakarta.persistence.*
import lombok.AllArgsConstructor
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter

@Entity
@Table(name = "\"group\"", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
class Group(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "group_seq")
    @SequenceGenerator(name = "group_seq", sequenceName = "group_seq")
    var id: Long?,

    @Getter
    @Setter
    var name: String,
)
