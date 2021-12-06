package es.menasoft.rockpaperscissorkotlinapi.game

import es.menasoft.rockpaperscissorkotlinapi.game.BasicHand.ROCK
import es.menasoft.rockpaperscissorkotlinapi.game.BasicHand.SCISSOR
import es.menasoft.rockpaperscissorkotlinapi.game.Result.WIN
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

internal class GameRepositoryImplTest {

    private lateinit var gameRepository: GameRepository

    @BeforeEach
    internal fun setUp() {
        gameRepository = GameRepositoryImpl()
    }

    @Test
    fun `should save a game`() {
        val game = gameRepository.save(PlayerResultedRound("player@player.com", ROCK, WIN, SCISSOR, LocalDateTime.now()))
        assertNotNull(game)
        assertEquals(1, gameRepository.findByPlayer("player@player.com").size)
    }

    @Test
    fun `should find player empty list for a player without games`() {
        val games = gameRepository.findByPlayer("player@player.com")
        assertEquals(0, games.size)
    }

    @Test
    fun `should find player list for a player with games`() {
        val playerRound = PlayerResultedRound("player@player.com", ROCK, WIN, SCISSOR, LocalDateTime.now()).let { gameRepository.save(it) }
        val games = gameRepository.findByPlayer("player@player.com")
        assertEquals(1, games.size)
        assertEquals(true, games.contains(playerRound))
    }


}