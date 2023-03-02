package io.github.finkmoritz.springgrouprolepermissions.auth

import io.github.finkmoritz.springgrouprolepermissions.repository.user.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service


@Service
class MyUserDetailsService(
    private val userRepository: UserRepository
) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findUserWithGroupRolesAndPermissions(username)
            ?: throw UsernameNotFoundException(username)
        return MyUserPrincipal(user)
    }
}
