package team.twelve.ahu.global.security.config

import jakarta.servlet.http.HttpServletResponse
import org.springframework.boot.autoconfigure.graphql.GraphQlProperties.Http
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import team.twelve.ahu.domain.auth.service.OauthUserService
import team.twelve.ahu.global.security.jwt.JwtAuthFilter

@Configuration
class SecurityConfig(
    private val oAuthUserService: OauthUserService,
    private val jwtAuthFilter: JwtAuthFilter
) {
    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { it.disable() }
            .formLogin { it.disable() }
            .authorizeHttpRequests {
                it.requestMatchers("/", "/login**", "/oauth2/**", "/error").permitAll()
                    .anyRequest().authenticated()
            }
            .oauth2Login {
                it.userInfoEndpoint {endpoint ->
                    endpoint.userService(oAuthUserService)
                }.successHandler { _, response, authentication ->
                    val user = authentication.principal as OAuth2User
                    val token = user.getAttribute<String>("accessToken")

                    response.contentType = "application/json"
                    response.characterEncoding = "UTF-8"
                    response.writer.write("""{ "accessToken": "$token" }""")
                }
            }
            .exceptionHandling {
                it.authenticationEntryPoint { _, response, _ ->
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized")
                }
            }
        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter::class.java)

        return http.build()
    }
}