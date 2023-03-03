package io.github.finkmoritz.springgrouprolepermissions.controller.user

import io.github.finkmoritz.springgrouprolepermissions.auth.MyUserPrincipal
import io.github.finkmoritz.springgrouprolepermissions.entity.user.User
import io.github.finkmoritz.springgrouprolepermissions.repository.user.UserRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.util.*

@RestController
@Component
@RequestMapping("/api/user")
class UserController(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
) {
    @GetMapping
    fun get(
        @RequestParam id: Long,
    ): ResponseEntity<User> {
        val user = userRepository.findById(id)
        return if (user.isPresent) ResponseEntity.ok(user.get()) else ResponseEntity.notFound().build()
    }

    @PostMapping("/signUp")
    fun signUp(
        @RequestBody user: User,
    ): ResponseEntity<Any> {
        if (userRepository.findByUsername(user.username) != null) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already exists")
        }
        var newUser = user.copyWith(
            password = passwordEncoder.encode(user.password)
        )
        newUser = userRepository.save(newUser)
        return ResponseEntity.ok(newUser)
    }

    @PutMapping
    fun update(
        @RequestBody user: User,
        @AuthenticationPrincipal principal: MyUserPrincipal,
    ): ResponseEntity<User> {
        if (user.id != principal.user.id) {
            throw ResponseStatusException(HttpStatus.FORBIDDEN, "Cannot update other user")
        }
        val updatedUser = userRepository.save(user)
        return ResponseEntity.ok(updatedUser)
    }

    @DeleteMapping("/{userId}")
    fun delete(
        @PathVariable userId: Long,
        @AuthenticationPrincipal principal: MyUserPrincipal,
    ): ResponseEntity<Void> {
        if (userId != principal.user.id) {
            throw ResponseStatusException(HttpStatus.FORBIDDEN, "Cannot delete other user")
        }
        userRepository.deleteById(userId)
        return ResponseEntity.ok().build()
    }
}
