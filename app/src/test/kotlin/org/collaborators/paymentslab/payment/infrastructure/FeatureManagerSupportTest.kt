package org.collaborators.paymentslab.payment.infrastructure

import io.kotlintest.shouldBe
import org.collaborators.paymentslab.payment.infrastructure.togglz.PaymentFeature
import org.junit.jupiter.api.Test
import org.togglz.kotlin.FeatureManagerSupport
import org.togglz.kotlin.FeatureManagerSupport.createFeatureManagerForTest

class FeatureManagerSupportTest {
    @Test
    internal fun `should change toggle state after enable`() {
        FeatureManagerSupport.disableAllFeatures(createFeatureManagerForTest(PaymentFeature::class))

        PaymentFeature.TOSS_PAYMENTS_FEATURE.isActive() shouldBe false

        FeatureManagerSupport.enable { PaymentFeature.TOSS_PAYMENTS_FEATURE.name }
        PaymentFeature.TOSS_PAYMENTS_FEATURE.isActive() shouldBe true

        FeatureManagerSupport.disable { PaymentFeature.TOSS_PAYMENTS_FEATURE.name }
        PaymentFeature.TOSS_PAYMENTS_FEATURE.isActive() shouldBe false
    }

    @Test
    internal fun `what happens when toggle state disable after disabled`() {
        FeatureManagerSupport.disableAllFeatures(createFeatureManagerForTest(PaymentFeature::class))

        PaymentFeature.TOSS_PAYMENTS_FEATURE.isActive() shouldBe false
        FeatureManagerSupport.disable { PaymentFeature.TOSS_PAYMENTS_FEATURE.name }
        PaymentFeature.TOSS_PAYMENTS_FEATURE.isActive() shouldBe false
    }
}