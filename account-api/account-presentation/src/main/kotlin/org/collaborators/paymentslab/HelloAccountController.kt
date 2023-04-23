package org.collaborators.paymentslab

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HelloAccountController {
    @GetMapping("hello-account")
    fun hello(): ResponseEntity<String> {
        return ResponseEntity.ok("hello from account")
    }
}