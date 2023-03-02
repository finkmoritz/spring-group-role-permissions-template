package io.github.finkmoritz.springgrouprolepermissions.configuration

import org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain


@Configuration
@EnableWebSecurity
class WebSecurityConfig {

    @Bean
    @Throws(Exception::class)
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        return http
            .headers().frameOptions().sameOrigin() //TODO remove for PROD
            .and()
            .authorizeHttpRequests { requests ->
                requests
                    .requestMatchers(toH2Console()).permitAll() //TODO remove for PROD
                    .requestMatchers(HttpMethod.POST, "/api/user").permitAll()
                    .anyRequest().authenticated()
                    .and()
                    .httpBasic()
                    .and().csrf().ignoringRequestMatchers(toH2Console()) //TODO remove for PROD
            }
            .build()
    }
}
