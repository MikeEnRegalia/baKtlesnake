package com.mikeboiers.battlesnake

import com.mikeboiers.battlesnake.Direction.DOWN
import kotlin.Int.Companion.MAX_VALUE
import kotlin.math.min

fun decideMove(request: MoveRequest): Direction {
    val head = request.you.head
    val body = request.you.body
    val board = request.board
    val otherSnakes = board.snakes.filterNot { it.id == request.you.id}

    fun Position.outsideMe() = this !in body
    fun Position.onBoard() = x in 0 until board.width && y in 0 until board.height
    fun Position.notInOtherSnakes() = this !in otherSnakes.flatMap(BattleSnake::body)
    fun Position.notInHazards() = this !in board.hazards
    fun Position.notNearDangerousHeads() =
        this !in otherSnakes.filter { it.body.size >= body.size }.flatMap { it.head.adjacent() }

    fun Position.isNotSuicide() = outsideMe() && onBoard() && notInOtherSnakes() && notInHazards()

    fun Position.realDistanceTo(target: Position): Int? {
        var s = this
        val v = mutableSetOf<Position>()
        val u = mutableSetOf<Position>()
        val d = mutableMapOf(s to 0)

        while (true) {
            s.adjacent().filter { it !in v && it.isNotSuicide() }.forEach { n ->
                u += n
                val distance = d.getValue(s) + 1
                d.compute(n) { _, old -> min(distance, old ?: MAX_VALUE) }
            }
            v += s
            u -= s
            if (s == target) return d.getValue(s)
            s = u.minByOrNull { d.getValue(it) } ?: break
        }
        return null
    }

    fun Position.maxDistance(): Int? {
        var s = this
        val v = mutableSetOf<Position>()
        val u = mutableSetOf<Position>()
        val d = mutableMapOf(s to 0)

        while (true) {
            s.adjacent().filter { it !in v && it.isNotSuicide() }.forEach { n ->
                u += n
                val distance = d.getValue(s) + 1
                d.compute(n) { _, old -> min(distance, old ?: MAX_VALUE) }
            }
            v += s
            u -= s
            s = u.minByOrNull { d.getValue(it) } ?: break
        }
        return d.values.maxOrNull()
    }

    fun List<Direction>.pick() = firstOrNull { (head + it).notNearDangerousHeads() } ?: firstOrNull()

    val nonSuicidalMoves = enumValues<Direction>().filter { (head + it).isNotSuicide() }

    val hungry = request.you.health < 50 || body.size < otherSnakes.minOf { it.body.size }
    if (!hungry) {
        return nonSuicidalMoves.sortedByDescending { (head + it).maxDistance() }.pick() ?: DOWN
    }

    // prioritize food
    val bestMove = nonSuicidalMoves
        .map { it to board.food.minOfOrNull { food -> food.realDistanceTo(head + it) ?: MAX_VALUE } }
        .filter { it.second != null }
        .sortedBy { it.second!! }
        .map { it.first }
        .pick()
    if (bestMove != null) return bestMove

    return nonSuicidalMoves
        .sortedBy { direction -> board.food.minOfOrNull { it.distanceTo(head + direction) } ?: MAX_VALUE }
        .pick() ?: DOWN
}