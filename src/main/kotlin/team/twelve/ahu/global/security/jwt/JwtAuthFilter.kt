package team.twelve.ahu.global.security.jwt

import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import team.twelve.ahu.global.security.service.OAuth2UserService
import java.io.IOException

class JwtAuthFilter(
    private val jwtTokenProvider: JwtTokenProvider,
    private val oAuth2UserService: OAuth2UserService
) : OncePerRequestFilter() {

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            val token = extractToken(request)

            if (!token.isNullOrBlank() && jwtTokenProvider.validateToken(token)) {
                authenticateUser(token)
            }
        } catch (e: Exception) {
            SecurityContextHolder.clearContext()
            logger.debug("JWT authentication failed: ${e.message}")
        }

        filterChain.doFilter(request, response)
    }

    private fun authenticateUser(token: String) {
        try {
            val userId = jwtTokenProvider.extractUserId(token)
            val user = oAuth2UserService.findById(userId)
            
            if (user != null) {
                val authentication = UsernamePasswordAuthenticationToken(
                    user, null, emptyList()
                )
                SecurityContextHolder.getContext().authentication = authentication
            }
        } catch (e: Exception) {
            logger.debug("User authentication failed: ${e.message}")
            SecurityContextHolder.clearContext()
        }
    }

    private fun extractToken(request: HttpServletRequest): String? {
        return try {
            val bearerToken = request.getHeader("Authorization")
            if (bearerToken?.startsWith("Bearer ") == true) {
                bearerToken.substring(7)
            } else null
        } catch (e: Exception) {
            null
        }
    }
}