package es.menasoft.rockpaperscissorkotlinapi.messaging

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.stereotype.Component

interface Messaging {
    fun send(routing: String, message: Any)
}

@Component
@ConditionalOnMissingBean(value = [Messaging::class], ignored = [KafkaMessaging::class])
class KafkaMessaging:Messaging {
    override fun send(routing: String, message: Any) {
        println("Publish message $message to topic $routing")
        // TODO: Implement kafka send message
    }
}