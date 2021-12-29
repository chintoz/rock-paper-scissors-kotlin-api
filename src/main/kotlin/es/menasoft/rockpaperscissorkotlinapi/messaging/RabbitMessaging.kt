package es.menasoft.rockpaperscissorkotlinapi.messaging

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Component

@Component
class RabbitMessaging (val rabbitTemplate:RabbitTemplate, val objectMapper: ObjectMapper) : Messaging {
    override fun send(routing: String, message: Any) {
        rabbitTemplate.convertAndSend(routing, "", objectMapper.writeValueAsString(message))
    }
}