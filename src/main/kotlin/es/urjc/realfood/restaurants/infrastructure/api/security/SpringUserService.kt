package es.urjc.realfood.restaurants.api.security

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component

@Component
class SpringUserService : UserDetailsService {

    override fun loadUserByUsername(p0: String?): UserDetails {
        TODO("Not yet implemented")
    }
}