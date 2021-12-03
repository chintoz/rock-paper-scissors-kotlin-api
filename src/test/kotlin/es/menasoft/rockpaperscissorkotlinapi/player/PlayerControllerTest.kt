package es.menasoft.rockpaperscissorkotlinapi.player

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.*
import org.mockito.kotlin.any
import org.mockito.kotlin.eq
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@ExtendWith(SpringExtension::class)
@WebMvcTest
internal class PlayerControllerTest {

    @MockBean
    lateinit var playerRepository: PlayerRepository

    @Autowired
    lateinit var mockMvc: MockMvc

    @Test
    fun `should create a player`() {

        `when`(playerRepository.save(any())).thenReturn(Player("player@player.com", "Gary"))

        mockMvc.perform(
            post("/player")
                .content("{\"id\":\"player@player.com\", \"name\":\"Gary\"}")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk)

        verify(playerRepository, times(1)).save(any())
    }

    @Test
    fun `should retrieve a list of players`() {
        `when`(playerRepository.findAll()).thenReturn(
            listOf(
                Player("player@player.com", "Gary"),
                Player("lplayer@player.com", "Lina")
            )
        )

        mockMvc.perform(
            get("/player")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk)

        verify(playerRepository, times(1)).findAll()
    }

    @Test
    fun `should retrieve a player by id`() {
        `when`(playerRepository.findById(eq("player@player.com"))).thenReturn(
            Player("player@player.com", "Gary")
        )

        mockMvc.perform(
            get("/player/player@player.com")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk)

        verify(playerRepository, times(1)).findById(eq("player@player.com"))
    }

    @Test
    fun `should delete a player by id`() {

        mockMvc.perform(
            delete("/player/player@player.com")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNoContent)

        verify(playerRepository, times(1)).delete(eq("player@player.com"))
    }
}