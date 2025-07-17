package team.twelve.ahu.global.security.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import team.twelve.ahu.global.security.handler.CustomAuthenticationEntryPoint
import team.twelve.ahu.global.security.handler.OAuthFailureHandler
import team.twelve.ahu.global.security.handler.OAuthSuccessHandler
import team.twelve.ahu.global.security.jwt.JwtAuthFilter
import team.twelve.ahu.global.security.jwt.JwtTokenProvider
import team.twelve.ahu.global.security.service.OAuth2UserService

@Configuration
class SecurityConfig(
    private val jwtTokenProvider: JwtTokenProvider,
    private val oAuth2UserService: OAuth2UserService,
    private val oAuthSuccessHandler: OAuthSuccessHandler,
    private val oAuthFailureHandler: OAuthFailureHandler,
    private val customAuthenticationEntryPoint: CustomAuthenticationEntryPoint
) {
    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .cors { }
            .csrf { it.disable() }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .authorizeHttpRequests {
                it.requestMatchers(
                    "/oauth2/**",
                    "/login/**",
                    "/api/auth/signup/info",
                    "/api/oauth2/test/**",
                    "/swagger-ui/**",
                    "/v3/api-docs/**"
                ).permitAll()
                    .anyRequest().authenticated()
            }
            .oauth2Login {
                it.successHandler(oAuthSuccessHandler)
                    .failureHandler(oAuthFailureHandler)
            }
            .exceptionHandling {
                it.authenticationEntryPoint(customAuthenticationEntryPoint)
            }

        http.addFilterBefore(
            JwtAuthFilter(jwtTokenProvider, oAuth2UserService),
            UsernamePasswordAuthenticationFilter::class.java
        )

        return http.build()
    }
}
