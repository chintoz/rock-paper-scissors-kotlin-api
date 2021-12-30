package es.menasoft.rockpaperscissorkotlinapi.aop

import es.menasoft.rockpaperscissorkotlinapi.game.BasicHand
import es.menasoft.rockpaperscissorkotlinapi.game.GameService
import es.menasoft.rockpaperscissorkotlinapi.game.Level
import es.menasoft.rockpaperscissorkotlinapi.game.PlayerRound
import es.menasoft.rockpaperscissorkotlinapi.player.Player
import es.menasoft.rockpaperscissorkotlinapi.player.PlayerRepository
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.any
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean

@SpringBootTest
internal class StatisticsTest {

    @Autowired
    lateinit var gameService: GameService

    @MockBean
    lateinit var rabbitTemplate: RabbitTemplate

    @MockBean
    lateinit var playerRepository: PlayerRepository

    @Test
    fun contextLoads() {

        Mockito.`when`(playerRepository.findById(any())).thenReturn(Player("player@player.com", "Gary"))

        gameService.play(PlayerRound("player@player.com", BasicHand.PAPER, Level.EASY))

        Mockito.verify(rabbitTemplate, Mockito.times(1)).convertAndSend(any() as String, any() as String, any() as String)
    }
}