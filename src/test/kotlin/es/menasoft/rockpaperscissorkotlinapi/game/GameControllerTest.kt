package es.menasoft.rockpaperscissorkotlinapi.game

import es.menasoft.rockpaperscissorkotlinapi.game.BasicHand.ROCK
import es.menasoft.rockpaperscissorkotlinapi.game.BasicHand.SCISSOR
import es.menasoft.rockpaperscissorkotlinapi.game.Result.WIN
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito
import org.mockito.kotlin.any
import org.mockito.kotlin.eq
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.time.LocalDateTime.now

@ExtendWith(SpringExtension::class)
@WebMvcTest(GameController::class)
internal class GameControllerTest {

    @MockBean
    lateinit var gameService: GameService

    @Autowired
    lateinit var mockMvc: MockMvc

    @Test
    fun `should play a round player`() {

        Mockito.`when`(gameService.play(any())).thenReturn(PlayerResultedRound("player@player.com", ROCK, WIN, SCISSOR, now()))

        mockMvc.perform(
            MockMvcRequestBuilders.post("/round")
                .content("{\"id\":\"player@player.com\", \"hand\":\"ROCK\", \"level\":\"EASY\"}")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk)

        Mockito.verify(gameService, Mockito.times(1)).play(any())

    }

    @Test
    fun `should retrieve round played by a player`() {
        Mockito.`when`(gameService.list(eq("player@player.com"))).thenReturn(listOf(PlayerResultedRound("player@player.com", ROCK, WIN, SCISSOR, now())))

        mockMvc.perform(
            MockMvcRequestBuilders.get("/round/player@player.com")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk)

        Mockito.verify(gameService, Mockito.times(1)).list(eq("player@player.com"))
    }
}