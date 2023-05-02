package org.collaborator.paymentlab.common

import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.concurrent.ThreadLocalRandom

object KeyGenerator {
    private const val ZONE_ID = "Asia/Seoul"
    private const val DATE_FORMAT_PATTERN = "yyyyMMddHHmmssSSS"
    private const val ORIGIN: Long = 1000000
    private const val BOUND: Long = 9999999

    fun generate(prefix: String): String? {
        return prefix + generate()
    }

    private fun generate(): String {
        val bounded = ThreadLocalRandom.current()
            .nextLong(ORIGIN, BOUND)
        val now = ZonedDateTime.now(ZoneId.of(ZONE_ID))
        val formatter = DateTimeFormatter.ofPattern(DATE_FORMAT_PATTERN)
        return formatter.format(now) + bounded
    }
}