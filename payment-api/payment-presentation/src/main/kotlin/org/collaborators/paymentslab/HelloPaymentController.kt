package org.collaborators.paymentslab

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HelloPaymentController {
    @GetMapping("hello-payment")
    fun hello(): String {
        return "hello payment"
    }
}