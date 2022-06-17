package es.urjc.realfood.restaurants.infrastructure.api.security.filters

import es.urjc.realfood.restaurants.infrastructure.api.security.TOKEN_BEARER_PREFIX
import io.jsonwebtoken.Jwts
import org.springframework.http.HttpHeaders.AUTHORIZATION
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import java.util.*
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JWTAuthorizationFilter(
    authManager: AuthenticationManager,
    private val tokenSecret: String
) : BasicAuthenticationFilter(authManager) {

    override fun doFilterInternal(
        req: HttpServletRequest,
        res: HttpServletResponse,
        chain: FilterChain
    ) {
        val header = req.getHeader(AUTHORIZATION)
        if (header == null || !header.startsWith(TOKEN_BEARER_PREFIX)) {
            chain.doFilter(req, res)
            return
        }
        SecurityContextHolder.getContext().authentication = getAuthentication(req)
        chain.doFilter(req, res)
    }

    private fun getAuthentication(request: HttpServletRequest): UsernamePasswordAuthenticationToken? {
        val token = request.getHeader(AUTHORIZATION)
        if (token != null) {
            val user: String = Jwts.parser()
                .setSigningKey(Base64.getEncoder().encodeToString(tokenSecret.encodeToByteArray()).toString())
                .parseClaimsJws(token.replace(TOKEN_BEARER_PREFIX, ""))
                .body
                .subject
            return UsernamePasswordAuthenticationToken(user, null, ArrayList())
        }
        return null
    }
}