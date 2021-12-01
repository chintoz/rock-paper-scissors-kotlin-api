package es.menasoft.rockpaperscissorkotlinapi.player

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.stereotype.Component
import java.util.concurrent.ConcurrentHashMap
import javax.validation.constraints.Email

class Player(@Email val id: String, val name: String, val surname: String? = null)

interface PlayerRepository {
    fun save(player: Player): Player
    fun findAll(): Collection<Player>
    fun findById(id: String): Player?
}

@Component
@ConditionalOnMissingBean(value = [PlayerRepository::class], ignored = [PlayerRepositoryImpl::class])
class PlayerRepositoryImpl : PlayerRepository {

    var data: MutableMap<String, Player> = ConcurrentHashMap()

    override fun save(player: Player): Player {
        data[player.id] = player
        return player
    }

    override fun findAll(): Collection<Player> = data.values
    override fun findById(id: String): Player? = data[id]
}
