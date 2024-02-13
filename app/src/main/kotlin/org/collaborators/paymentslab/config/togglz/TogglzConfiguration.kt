package org.collaborators.paymentslab.config.togglz



import org.collaborator.paymentlab.common.PaymentFeature
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.togglz.core.context.StaticFeatureManagerProvider
import org.togglz.core.manager.FeatureManager
import org.togglz.core.manager.FeatureManagerBuilder
import org.togglz.core.repository.StateRepository
import org.togglz.core.repository.mem.InMemoryStateRepository
import org.togglz.core.spi.FeatureProvider
import org.togglz.core.user.SimpleFeatureUser
import org.togglz.core.user.UserProvider
import org.togglz.kotlin.EnumClassFeatureProvider
import org.togglz.kotlin.FeatureManagerSupport

@Configuration
class TogglzConfiguration {

    @Value("\${togglz.features.TOSS_PAYMENTS_FEATURE.enabled}")
    private lateinit var isPaymentFeatureActive: String

    private val log = LoggerFactory.getLogger(this::class.java)

    @Bean
    fun featureProvider() = EnumClassFeatureProvider(PaymentFeature::class.java)

    @Bean
    fun stateRepository() = InMemoryStateRepository()

    @Bean
    fun userProvider() = UserProvider { SimpleFeatureUser("admin", true) }

    @Bean
    @Primary
    fun featureManager(stateRepository: StateRepository,
                         userProvider: UserProvider,
                         featureProvider: FeatureProvider
    ): FeatureManager {
        val featureManager = FeatureManagerBuilder()
            .featureProvider(featureProvider)
            .stateRepository(stateRepository)
            .userProvider(userProvider)
            .build()
        StaticFeatureManagerProvider.setFeatureManager(featureManager)
        if (isPaymentFeatureActive == "false") {
            log.info("payment disabled")
            FeatureManagerSupport.disableAllFeatures(FeatureManagerSupport.createFeatureManagerForTest(PaymentFeature::class))
        }
        return featureManager
    }
}