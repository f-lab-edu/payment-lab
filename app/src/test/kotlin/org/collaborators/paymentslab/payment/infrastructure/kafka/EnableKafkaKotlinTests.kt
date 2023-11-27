package org.collaborators.paymentslab.payment.infrastructure.kafka

import org.apache.kafka.clients.consumer.Consumer
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.clients.consumer.ConsumerRecords
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory
import org.springframework.kafka.listener.*
import org.springframework.kafka.test.EmbeddedKafkaBroker
import org.springframework.kafka.test.context.EmbeddedKafka
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig
import java.lang.Exception
import java.util.concurrent.CountDownLatch

@SpringJUnitConfig
@DirtiesContext
@EmbeddedKafka(topics = ["kotlinTestTopic1", "kotlinBatchTestTopic1", "kotlinTestTopic2", "kotlinBatchTestTopic2"])
abstract class EnableKafkaKotlinTests {

    @Autowired
    protected lateinit var config: Config

    @Autowired
    protected lateinit var template: KafkaTemplate<String, String>

    @Configuration
    @EnableKafka
    class Config {

        @Volatile
        lateinit var received: String

        @Volatile
        lateinit var batchReceived: String

        @Volatile
        var error: Boolean = false

        @Volatile
        var batchError: Boolean = false

        val latch1 = CountDownLatch(1)

        val latch2 = CountDownLatch(1)

        val batchLatch1 = CountDownLatch(1)

        val batchLatch2 = CountDownLatch(1)

        @Value("\${" + EmbeddedKafkaBroker.SPRING_EMBEDDED_KAFKA_BROKERS + "}")
        private lateinit var brokerAddresses: String

        @Bean
        fun kafkaProducerFactory(): ProducerFactory<String, String> {
            val configs = HashMap<String, Any>()
            configs[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] = this.brokerAddresses
            configs[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java
            configs[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java
            return DefaultKafkaProducerFactory(configs)
        }

        @Bean
        fun kafkaConsumerFactory(): ConsumerFactory<String, String> {
            val configs = HashMap<String, Any>()
            configs[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = this.brokerAddresses
            configs[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java
            configs[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java
            configs[ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG] = false
            configs[ConsumerConfig.AUTO_OFFSET_RESET_CONFIG] = "earliest"
            return DefaultKafkaConsumerFactory(configs)
        }

        @Bean
        fun kafkaTemplate(): KafkaTemplate<String, String> {
            return KafkaTemplate(kafkaProducerFactory())
        }

        val errorHandler = object: CommonErrorHandler {
            override fun handleOne(
                thrownException: Exception,
                record: ConsumerRecord<*, *>,
                consumer: Consumer<*, *>,
                container: MessageListenerContainer
            ): Boolean {
                error = true
                latch2.countDown()
                return true
            }

            override fun handleBatch(
                thrownException: Exception,
                recs: ConsumerRecords<*, *>,
                consumer: Consumer<*, *>,
                container: MessageListenerContainer,
                invokeListener: Runnable
            ) {
                if (!recs.isEmpty) {
                    batchError = true;
                    batchLatch2.countDown()
                }
            }
        }

        @Bean
        fun kafkaListenerContainerFactory(): ConcurrentKafkaListenerContainerFactory<String, String> {
            val factory: ConcurrentKafkaListenerContainerFactory<String, String>
                    = ConcurrentKafkaListenerContainerFactory()
            factory.consumerFactory = kafkaConsumerFactory()
            factory.setCommonErrorHandler(errorHandler)
            return factory
        }

        @Bean
        fun kafkaBatchListenerContainerFactory(): ConcurrentKafkaListenerContainerFactory<String, String> {
            val factory: ConcurrentKafkaListenerContainerFactory<String, String>
                    = ConcurrentKafkaListenerContainerFactory()
            factory.isBatchListener = true
            factory.consumerFactory = kafkaConsumerFactory()
            factory.setCommonErrorHandler(errorHandler)
            return factory
        }

        @KafkaListener(id = "kotlin", topics = ["kotlinTestTopic1"], containerFactory = "kafkaListenerContainerFactory")
        fun listen(value: String) {
            this.received = value
            this.latch1.countDown()
        }

        @KafkaListener(id = "kotlin-batch", topics = ["kotlinBatchTestTopic1"], containerFactory = "kafkaBatchListenerContainerFactory")
        fun batchListen(values: List<ConsumerRecord<String, String>>) {
            this.batchReceived = values.first().value()
            this.batchLatch1.countDown()
        }

        @Bean
        fun checkedEx(kafkaListenerContainerFactory : ConcurrentKafkaListenerContainerFactory<String, String>) :
                ConcurrentMessageListenerContainer<String, String> {

            val container = kafkaListenerContainerFactory.createContainer("kotlinTestTopic2")
            container.containerProperties.setGroupId("checkedEx")
            container.containerProperties.messageListener = MessageListener<String, String> {
                if (it.value() == "fail") {
                    throw Exception("checked")
                }
            }
            return container;
        }

        @Bean
        fun batchCheckedEx(kafkaBatchListenerContainerFactory :
                           ConcurrentKafkaListenerContainerFactory<String, String>) :
                ConcurrentMessageListenerContainer<String, String> {

            val container = kafkaBatchListenerContainerFactory.createContainer("kotlinBatchTestTopic2")
            container.containerProperties.setGroupId("batchCheckedEx")
            container.containerProperties.messageListener = BatchMessageListener<String, String> {
                if (it.first().value() == "fail") {
                    throw Exception("checked")
                }
            }
            return container;
        }

    }

}