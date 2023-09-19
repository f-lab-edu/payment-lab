package org.collaborators.paymentslab.payment.presentation

import org.collaborator.paymentlab.common.V1_TOSS_PAYMENTS
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping(V1_TOSS_PAYMENTS)
class PaymentController {
    @GetMapping
    fun purchasePage(model: Model): String {
        return "purchase"
    }
}