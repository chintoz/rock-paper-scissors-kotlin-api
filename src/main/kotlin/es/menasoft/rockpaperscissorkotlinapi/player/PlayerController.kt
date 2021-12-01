package es.menasoft.rockpaperscissorkotlinapi.player

import lombok.extern.slf4j.Slf4j
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RequestMapping(value = ["/player"], produces = [MediaType.APPLICATION_JSON_VALUE])
@Slf4j
@RestController
class PlayerController(val playerRepository: PlayerRepository) {

    @PostMapping
    fun createPlayer(@RequestBody player: Player): Player = playerRepository.save(player)

    @GetMapping
    fun getPlayers(): Collection<Player> = playerRepository.findAll()

    @GetMapping("/{id}")
    fun getPlayer(@PathVariable("id") id: String): Player? = playerRepository.findById(id)

}
