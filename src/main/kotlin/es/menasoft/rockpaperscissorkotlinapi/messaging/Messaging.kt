package es.menasoft.rockpaperscissorkotlinapi.messaging

import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.kafka.clients.producer.ProducerConfig.*
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory
import org.springframework.stereotype.Component

interface Messaging {
    fun send(routing: String, message: Any)
}

@Component
@ConditionalOnMissingBean(value = [Messaging::class], ignored = [KafkaMessaging::class])
class KafkaMessaging(val kafkaTemplate: KafkaTemplate<String, String>, val objectMapper: ObjectMapper) : Messaging {
    override fun send(routing: String, message: Any) {
        kafkaTemplate.send(routing, objectMapper.writeValueAsString(message))
    }
}

@Configuration
@ConditionalOnMissingBean(value = [Messaging::class], ignored = [KafkaMessaging::class])
class KafkaConfig {
    @Bean
    fun producerFactory(): ProducerFactory<String, String> =
        DefaultKafkaProducerFactory(
            mapOf(
                BOOTSTRAP_SERVERS_CONFIG to "localhost:9092",
                KEY_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java,
                VALUE_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java
            )
        )

    @Bean
    fun kafkaTemplate(): KafkaTemplate<String, String> = KafkaTemplate(producerFactory())

}