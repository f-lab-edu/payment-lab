package org.collaborators.paymentslab

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PaymentsLabApplication

fun main(args: Array<String>) {
	runApplication<PaymentsLabApplication>(*args)
}
