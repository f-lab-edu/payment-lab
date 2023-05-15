package org.collaborators.paymentslab.config.security

import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter

class CustomCorsFilter : CorsFilter(configurationSource()) {

    companion object {
        private fun configurationSource(): UrlBasedCorsConfigurationSource {
            val config = CorsConfiguration()
            config.allowCredentials = true
            config.addAllowedOrigin("*")
            config.addAllowedHeader("*")
            config.maxAge = 36000L
            config.allowedMethods = listOf("GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS")
            val source = UrlBasedCorsConfigurationSource()
            source.registerCorsConfiguration("/api/v1/**", config)
            return source
        }
    }
}