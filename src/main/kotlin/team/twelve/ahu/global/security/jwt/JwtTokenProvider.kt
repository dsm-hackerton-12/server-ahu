package team.twelve.ahu.global.security.jwt

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import team.twelve.ahu.domain.user.entity.User
import java.util.*
import javax.crypto.SecretKey

@Component
class JwtTokenProvider(
    @Value("\${jwt.secret}") secret: String,
    @Value("\${jwt.expiration}") private val expirationMs: Long
) {
    private val secretKey: SecretKey = Keys.hmacShaKeyFor(secret.toByteArray())

    fun generateToken(user: User): String{
        val now = Date()
        val expiry = Date(now.time + expirationMs)

        return Jwts.builder()
            .setSubject(user.id.toString())
            .claim("email", user.email)
            .setIssuedAt(now)
            .setExpiration(expiry)
            .signWith(secretKey, SignatureAlgorithm.HS256)
            .compact()
    }

    fun validateToken(token: String): Boolean = try {
        Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token)
        true
    } catch (e : Exception) {
        false
    }

    fun extractUserId(token: String) : UUID {
        val claims = Jwts.parserBuilder().setSigningKey(secretKey).build()
            .parseClaimsJws(token)
            .body
        return UUID.fromString(claims.subject)
    }
}