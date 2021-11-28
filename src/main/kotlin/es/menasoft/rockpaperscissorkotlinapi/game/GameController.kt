package es.menasoft.rockpaperscissorkotlinapi.game

import lombok.RequiredArgsConstructor
import lombok.extern.slf4j.Slf4j
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping(value = ["/round"], produces = [MediaType.APPLICATION_JSON_VALUE])
@RequiredArgsConstructor
@Slf4j
@RestController
class GameController {

    @GetMapping(value = ["", "/"])
    fun blog(): String {
        return "{\"name\":\"Jacinto\"}"
    }

}
