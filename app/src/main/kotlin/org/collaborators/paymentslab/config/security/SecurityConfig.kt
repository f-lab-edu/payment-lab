package org.collaborators.paymentslab.config.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.context.annotation.Profile
import org.springframework.http.HttpMethod
import org.springframework.http.HttpMethod.GET
import org.springframework.http.HttpMethod.POST
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.authentication.logout.LogoutFilter

@Import(value = [
    DefaultAuthenticationEntryPoint::class,
    DefaultAccessDeniedHandler::class,
    FilterChainExceptionHelper::class,
    BCryptPasswordEncoder::class
])
@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val filterChainExceptionHelper: FilterChainExceptionHelper,
    private val authenticationEntryPoint: AuthenticationEntryPoint,
    private val accessDeniedHandler: AccessDeniedHandler
) {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {

        http.csrf().disable()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .exceptionHandling()
            .authenticationEntryPoint(authenticationEntryPoint)
            .accessDeniedHandler(accessDeniedHandler)
            .and()
            .authorizeHttpRequests()
            .requestMatchers(POST, "/api/v1/auth/register").permitAll()
            .requestMatchers(GET, "/api/v1/auth/confirm").permitAll()
            .anyRequest().authenticated()
            .and()
            .addFilterBefore(filterChainExceptionHelper, LogoutFilter::class.java)
            .addFilterBefore(CustomCorsFilter(), UsernamePasswordAuthenticationFilter::class.java)
            // TODO jwt 필터 추가
        return http.build()
    }


}