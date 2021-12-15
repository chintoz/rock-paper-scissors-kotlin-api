package es.menasoft.rockpaperscissorkotlinapi.aop

import es.menasoft.rockpaperscissorkotlinapi.game.PlayerResultedRound
import es.menasoft.rockpaperscissorkotlinapi.messaging.Messaging
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.AfterReturning
import org.aspectj.lang.annotation.Aspect
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean
import org.springframework.stereotype.Component

@Aspect
@Component
@ConditionalOnBean(value = [Messaging::class])
// TODO: Condition bean creation to an attribute to control send metrics through messaging engine
class Statistics(val messaging: Messaging) {

    @AfterReturning(
        "within(es.menasoft.rockpaperscissorkotlinapi.game.GameService+) & execution(* *.play(...)) ",
        returning = "playerRound"
    )
    fun sendStatisticsInfo(joinPoint: JoinPoint, playerRound: PlayerResultedRound): PlayerResultedRound =
        playerRound.apply { messaging.send("statistics", playerRound) }

}