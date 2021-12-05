package es.menasoft.rockpaperscissorkotlinapi.game

import kotlin.random.Random

interface GameStrategy {
    fun playHand() : BasicHand
}

object FixedStrategy: GameStrategy {
    override fun playHand(): BasicHand = BasicHand.ROCK
}

object RandomStrategy: GameStrategy {
    private val random: Random = Random(System.currentTimeMillis())
    override fun playHand(): BasicHand = BasicHand.values()[random.nextInt(BasicHand.values().size)]
}
