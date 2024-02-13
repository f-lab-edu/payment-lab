package org.collaborator.paymentlab.common

import org.togglz.core.annotation.EnabledByDefault
import org.togglz.core.annotation.Label
import org.togglz.core.context.FeatureContext

enum class PaymentFeature {
    @EnabledByDefault
    @Label("toggle tossPayments")
    TOSS_PAYMENTS_FEATURE;

    fun isActive(): Boolean {
        return FeatureContext.getFeatureManager().isActive { name }
    }
}