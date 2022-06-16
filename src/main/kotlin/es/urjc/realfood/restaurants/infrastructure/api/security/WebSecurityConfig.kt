package es.urjc.realfood.restaurants.infrastructure.api.security

import es.urjc.realfood.restaurants.api.security.filters.JWTAuthenticationFilter
import es.urjc.realfood.restaurants.api.security.filters.JWTAuthorizationFilter
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource


@Configuration
@EnableWebSecurity
class WebSecurityConfig(
    @Value("\${jwt.secret}") private val tokenSecret: String,
    private val userService: SpringUserService
) : WebSecurityConfigurerAdapter() {

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", CorsConfiguration().applyPermitDefaultValues())
        return source
    }

    override fun configure(http: HttpSecurity) {
        http
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
            .cors().and().csrf().disable()
            .authorizeRequests()
            .antMatchers("/swagger-ui/**").permitAll()
            .antMatchers("/actuator/health").permitAll()
            .antMatchers("/**").permitAll()
            .antMatchers("/api/orders/**").permitAll()
            .antMatchers("/api/restaurants/**").permitAll()
            .anyRequest().authenticated().and()
            .addFilter(JWTAuthenticationFilter(authenticationManager(), tokenSecret))
            .addFilter(JWTAuthorizationFilter(authenticationManager(), tokenSecret))
    }

    @Bean
    fun bCryptPasswordEncoder() = BCryptPasswordEncoder()

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder())
    }
}