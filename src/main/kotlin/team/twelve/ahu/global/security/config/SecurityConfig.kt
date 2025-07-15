package team.twelve.ahu.global.security.config

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import team.twelve.ahu.domain.auth.service.UserService
import team.twelve.ahu.domain.user.entity.repository.UserRepository
import team.twelve.ahu.global.security.handler.OAuthSuccessHandler
import team.twelve.ahu.global.security.jwt.JwtAuthFilter
import team.twelve.ahu.global.security.jwt.JwtTokenProvider

@Configuration
class SecurityConfig(
    private val jwtTokenProvider: JwtTokenProvider,
    private val objectMapper: ObjectMapper,
    private val userService: UserService
) {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { it.disable() }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .authorizeHttpRequests {
                it
                    .requestMatchers(
                        "/oauth2/**",
                        "/login/**",
                        "/api/auth/signup/info",
                        "/swagger-ui/**",
                        "/v3/api-docs/**"
                    ).permitAll()
                    .anyRequest().authenticated()
            }
            .oauth2Login {
                it.successHandler(oAuthSuccessHandler())
            }

        http.addFilterBefore(jwtAuthFilter(), UsernamePasswordAuthenticationFilter::class.java)

        return http.build()
    }

    @Bean
    fun oAuthSuccessHandler(): OAuthSuccessHandler {
        return OAuthSuccessHandler(
            jwtTokenProvider = jwtTokenProvider,
            objectMapper = objectMapper,
            userService = userService
        )
    }

    @Bean
    fun jwtAuthFilter(): JwtAuthFilter {
        return JwtAuthFilter(jwtTokenProvider, userService)
    }

    @Bean
    fun authenticationManager(authConfig: AuthenticationConfiguration): AuthenticationManager {
        return authConfig.authenticationManager
    }
}
