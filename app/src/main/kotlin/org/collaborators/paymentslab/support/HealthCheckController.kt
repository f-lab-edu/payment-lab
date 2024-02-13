package org.collaborators.paymentslab.support

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HealthCheckController {
    @GetMapping("health")
    fun healthCheck(): ResponseEntity<Void> {
        return ResponseEntity.ok().build()
    }
}