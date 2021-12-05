package es.menasoft.rockpaperscissorkotlinapi.game

import es.menasoft.rockpaperscissorkotlinapi.game.Result.*
import es.menasoft.rockpaperscissorkotlinapi.player.PlayerRepository
import es.menasoft.rockpaperscissorkotlinapi.player.PlayerRepositoryImpl
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import java.util.function.Predicate


interface GameService {
    fun play(round: PlayerRound): PlayerResultedRound
    fun list(id: String): Collection<PlayerResultedRound>
}

@Service
class GameServiceImpl(val gameRepository: GameRepository, val playerRepository: PlayerRepository) : GameService {

    override fun play(round: PlayerRound): PlayerResultedRound {
        playerRepository.findById(round.id) ?: throw IllegalArgumentException("Player Not Found")
        // TODO: Choose strategy
        val opponentHand = RandomStrategy.playHand();
        return gameRepository.save(
            PlayerResultedRound(
                round.id,
                round.hand,
                opponentHand.let { round.hand.evaluate(it) },
                opponentHand,
                LocalDateTime.now()
            )
        )
    }

    override fun list(id: String): Collection<PlayerResultedRound> = (playerRepository.findById(id)
        ?: throw IllegalArgumentException("Player Not Found")).let { gameRepository.findByPlayer(id) }

}

interface GameRepository {
    fun save(round: PlayerResultedRound): PlayerResultedRound
    fun findByPlayer(id: String): Collection<PlayerResultedRound>
}

@Component
@ConditionalOnMissingBean(value = [PlayerRepository::class], ignored = [PlayerRepositoryImpl::class])
class GameRepositoryImpl : GameRepository {
    var data: MutableMap<String, MutableList<PlayerResultedRound>> = ConcurrentHashMap()
    override fun save(round: PlayerResultedRound): PlayerResultedRound =
        round.apply { data.getOrPut(round.id) { Vector() }.add(round) }

    override fun findByPlayer(id: String): Collection<PlayerResultedRound> = data.getOrDefault(id, emptyList())
}

enum class BasicHand(private val isWinner: Predicate<BasicHand>) {
    ROCK(Predicate<BasicHand> { t -> t == SCISSOR }),
    PAPER(Predicate<BasicHand> { t -> t == ROCK }),
    SCISSOR(Predicate<BasicHand> { t -> t == PAPER });

    fun evaluate(opponentHand: BasicHand): Result =
        if (this.isWinner.test(opponentHand)) WIN else if (opponentHand.isWinner.test(this)) LOSE else TIED
}

enum class Result {
    WIN, LOSE, TIED;
}

open class PlayerRound(val id: String, val hand: BasicHand)

class PlayerResultedRound(
    id: String,
    hand: BasicHand,
    val result: Result,
    val opponentHand: BasicHand,
    val time: LocalDateTime
) :
    PlayerRound(id, hand)