package org.collaborators.paymentslab.payment.infrastructure

import io.kotlintest.shouldBe
import org.collaborators.paymentslab.payment.infrastructure.togglz.PaymentFeature
import org.collaborators.paymentslab.payment.infrastructure.tosspayments.PaymentPropertiesResolver
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
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
}