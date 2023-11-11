package org.collaborators.paymentslab

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.ApplicationListener
import org.springframework.core.env.ConfigurableEnvironment

@SpringBootApplication
class PaymentsLabApplication: ApplicationListener<ApplicationReadyEvent> {
	override fun onApplicationEvent(event: ApplicationReadyEvent) {
		val env = event.applicationContext.environment
		val rootLoggingLevel = env.getProperty("logging.level.root", String::class.java, "NOT_SET")
		val applicationLoggingLevel = env
			.getProperty("logging.level.org.collaborators.paymentslab.*", String::class.java, "NOT_SET")

		if (isProfileProduction(env) && isLoggingLevelDebug(rootLoggingLevel, applicationLoggingLevel)) {
			throw IllegalStateException("production 환경에서는 로그 출력 레벨을 'debug'로 설정해선 안됩니다.")
		}
	}

	private fun isLoggingLevelDebug(loggingLevel: String, applicationLoggingLevel: String) =
		loggingLevel.equals("DEBUG", ignoreCase = true)
				|| applicationLoggingLevel.equals("DEBUG", ignoreCase = true)

	private fun isProfileProduction(env: ConfigurableEnvironment) = "prod" in env.activeProfiles

	companion object {
		@JvmStatic
		fun main(args: Array<String>) {
			SpringApplication.run(PaymentsLabApplication::class.java, *args)
		}
	}
}

fun main(args: Array<String>) {
	PaymentsLabApplication.main(args)
}
