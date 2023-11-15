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

		if (!isProfileInBoundary(env))
			throw IllegalStateException("profile은 'local', 'test', 'dev', 'prod' 중 하나로 지정 해야합니다.")

		if (isProfileProduction(env) && isLoggingLevelDebug(rootLoggingLevel, applicationLoggingLevel))
			throw IllegalStateException("production 환경에서는 로그 출력 레벨을 'debug'로 설정해선 안됩니다.")
	}

	private fun isLoggingLevelDebug(loggingLevel: String, applicationLoggingLevel: String) =
		loggingLevel.equals("DEBUG", ignoreCase = true)
				|| applicationLoggingLevel.equals("DEBUG", ignoreCase = true)

	private fun isProfileInBoundary(env: ConfigurableEnvironment)
		= isProfileLocal(env) || isProfileTest(env) || isProfileDev(env) || isProfileProduction(env)

	private fun isProfileProduction(env: ConfigurableEnvironment) = "prod" in env.activeProfiles

	private fun isProfileDev(env: ConfigurableEnvironment) = "dev" in env.activeProfiles

	private fun isProfileTest(env: ConfigurableEnvironment) = "test" in env.activeProfiles
	private fun isProfileLocal(env: ConfigurableEnvironment) = "local" in env.activeProfiles

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
