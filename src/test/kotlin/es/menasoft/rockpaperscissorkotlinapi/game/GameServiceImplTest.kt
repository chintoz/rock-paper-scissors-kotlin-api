package es.menasoft.rockpaperscissorkotlinapi.game

import es.menasoft.rockpaperscissorkotlinapi.game.BasicHand.ROCK
import es.menasoft.rockpaperscissorkotlinapi.game.BasicHand.SCISSOR
import es.menasoft.rockpaperscissorkotlinapi.game.Level.EASY
import es.menasoft.rockpaperscissorkotlinapi.game.Result.WIN
import es.menasoft.rockpaperscissorkotlinapi.player.Player
import es.menasoft.rockpaperscissorkotlinapi.player.PlayerRepository
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.eq
import java.time.LocalDateTime

@ExtendWith(MockitoExtension::class)
internal class GameServiceImplTest {

    private lateinit var gameService: GameService

    @Mock
    lateinit var playerRepository: PlayerRepository

    @Mock
    lateinit var gameRepository: GameRepository

    @BeforeEach
    fun setup() {
        gameService = GameServiceImpl(gameRepository, playerRepository)
    }

    @Test
    fun `should play a game`() {
        val round = PlayerResultedRound("player@player.com", ROCK, WIN, SCISSOR, LocalDateTime.now())
        `when`(playerRepository.findById(eq("player@player.com"))).thenReturn(
            Player("player@player.com", "Gary", "Player")
        )

        `when`(gameRepository.save(any())).thenReturn(round)

        val resultedRound = gameService.play(PlayerRound("player@player.com", ROCK, EASY))

        assertEquals(round, resultedRound)
    }

    @Test
    fun `should retrieve list of games of a player`() {
        `when`(playerRepository.findById(eq("player@player.com"))).thenReturn(
            Player("player@player.com", "Gary", "Player")
        )
        `when`(gameRepository.findByPlayer(eq("player@player.com"))).thenReturn(
            listOf(PlayerResultedRound("player@player.com", ROCK, WIN, SCISSOR, LocalDateTime.now()))
        )
        val games = gameService.list("player@player.com")
        assertEquals(1, games.size)
    }

    @Test
    fun `should fail listing games for a non existing a player`() {
        assertThrows<IllegalArgumentException> { gameService.list("player@player.com") }
    }

}