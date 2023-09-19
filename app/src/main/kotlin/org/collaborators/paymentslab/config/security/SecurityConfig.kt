package org.collaborators.paymentslab.config.security

import org.collaborator.paymentlab.common.V1_API_AUTH
import org.collaborator.paymentlab.common.V1_API_TOSS_PAYMENTS
import org.collaborator.paymentlab.common.V1_TOSS_PAYMENTS
import org.springframework.boot.autoconfigure.security.servlet.PathRequest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.context.annotation.Profile
import org.springframework.http.HttpMethod.GET
import org.springframework.http.HttpMethod.POST
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.security.web.access.ExceptionTranslationFilter
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Import(value = [
    DefaultAuthenticationEntryPoint::class,
    DefaultAccessDeniedHandler::class,
    JwtAuthenticationFilter::class,
    JwtExceptionFilter::class,
    BCryptPasswordEncoder::class
])
@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val jwtExceptionFilter: JwtExceptionFilter,
    private val authenticationEntryPoint: AuthenticationEntryPoint,
    private val accessDeniedHandler: AccessDeniedHandler,
    private val jwtAuthenticationFilter: JwtAuthenticationFilter
) {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {

        http
            .csrf().disable()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .exceptionHandling()
            .authenticationEntryPoint(authenticationEntryPoint)
            .accessDeniedHandler(accessDeniedHandler)
            .and()
            .authorizeHttpRequests()
            .requestMatchers(GET, "/", "/index.html").permitAll()
            .requestMatchers(GET, V1_TOSS_PAYMENTS).permitAll()
            .requestMatchers(POST,"$V1_API_AUTH/register").permitAll()
            .requestMatchers(POST,"$V1_API_AUTH/login").permitAll()
            .requestMatchers(GET, "$V1_API_AUTH/confirm").permitAll()
            .anyRequest().authenticated()
            .and()
            .formLogin().disable()
            http.addFilterBefore(CustomCorsFilter(), UsernamePasswordAuthenticationFilter::class.java)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
                .addFilterBefore(jwtExceptionFilter, JwtAuthenticationFilter::class.java)
        return http.build()
    }
    @Bean
    fun exceptionTranslationFilter(): ExceptionTranslationFilter {
        val filter = ExceptionTranslationFilter(authenticationEntryPoint)
        filter.setAccessDeniedHandler(accessDeniedHandler)
        return filter
    }
    @Bean
    @Profile("test")
    fun webSecurityCustomizer(): WebSecurityCustomizer {
        return WebSecurityCustomizer { web ->
            web.ignoring()
                .requestMatchers(PathRequest.toH2Console())
        }

    }
}