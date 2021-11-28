package es.menasoft.rockpaperscissorkotlinapi.player

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class PlayerRepositoryImplTest {

    private lateinit var playerRepository: PlayerRepository

    @BeforeEach
    internal fun setUp() {
        playerRepository = PlayerRepositoryImpl()
    }

    @Test
    fun `should save a player`() {
        val player = playerRepository.save(Player(id="player@player.com"))
        assertNotNull(player)
        assertEquals(1, playerRepository.findAll().size)
    }

    @Test
    fun `should list all players`() {
        playerRepository.save(Player(id="player@player.com"))
        assertEquals(1, playerRepository.findAll().size)
    }

    @Test
    fun `should find a player by identifier`() {
        playerRepository.save(Player(id="player@player.com"))
        assertNotNull(playerRepository.findById("player@player.com"))
    }
}