package org.collaborators.paymentslab.config.togglz

import org.collaborators.paymentslab.payment.infrastructure.togglz.PaymentFeature
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.togglz.core.context.StaticFeatureManagerProvider
import org.togglz.core.manager.FeatureManager
import org.togglz.core.manager.FeatureManagerBuilder
import org.togglz.core.repository.StateRepository
import org.togglz.core.repository.mem.InMemoryStateRepository
import org.togglz.core.spi.FeatureProvider
import org.togglz.core.user.NoOpUserProvider
import org.togglz.core.user.SimpleFeatureUser
import org.togglz.core.user.UserProvider
import org.togglz.kotlin.EnumClassFeatureProvider

@Configuration
class TogglzConfiguration {
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
        return featureManager
    }
}