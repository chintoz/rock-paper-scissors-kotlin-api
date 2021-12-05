package es.menasoft.rockpaperscissorkotlinapi.game

import lombok.RequiredArgsConstructor
import lombok.extern.slf4j.Slf4j
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RequestMapping(
    value = ["/round"],
    produces = [MediaType.APPLICATION_JSON_VALUE],
    consumes = [MediaType.APPLICATION_JSON_VALUE]
)
@RequiredArgsConstructor
@Slf4j
@RestController
class GameController(val gameService: GameService) {

    @PostMapping
    fun play(@RequestBody playerRound: PlayerRound): PlayerRound = gameService.play(playerRound)

    @GetMapping("/{id}")
    fun getRounds(@PathVariable("id") id: String): Collection<PlayerResultedRound> = gameService.list(id)

}
