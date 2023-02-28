package io.github.finkmoritz.springgrouprolepermissions

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
open class SpringGroupRolePermissionsApplication

fun main(args: Array<String>) {
    runApplication<SpringGroupRolePermissionsApplication>(*args)
}
