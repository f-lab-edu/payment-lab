package org.collaborators.paymentslab.payment.infrastructure.kafka

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import java.util.concurrent.TimeUnit

class Hello: EnableKafkaKotlinTests() {
    @Test
    fun `test listener`() {
        this.template.send("kotlinTestTopic1", "foo")
        Assertions.assertThat(this.config.latch1.await(10, TimeUnit.SECONDS)).isTrue()
        Assertions.assertThat(this.config.received).isEqualTo("foo")
    }

    @Test
    fun `test checkedEx`() {
        this.template.send("kotlinTestTopic2", "fail")
        Assertions.assertThat(this.config.latch2.await(10, TimeUnit.SECONDS)).isTrue()
        Assertions.assertThat(this.config.error).isTrue()
    }

    @Test
    fun `test batch listener`() {
        this.template.send("kotlinBatchTestTopic1", "foo")
        Assertions.assertThat(this.config.batchLatch1.await(10, TimeUnit.SECONDS)).isTrue()
        Assertions.assertThat(this.config.batchReceived).isEqualTo("foo")
    }

    @Test
    fun `test batch checkedEx`() {
        this.template.send("kotlinBatchTestTopic2", "fail")
        Assertions.assertThat(this.config.batchLatch2.await(10, TimeUnit.SECONDS)).isTrue()
        Assertions.assertThat(this.config.batchError).isTrue()
    }
}