package com.mikeboiers.battlesnake

import com.mikeboiers.battlesnake.Direction.DOWN

// This is the heart of your snake
// It defines what to do on your next move
// You get the current game state passed as a parameter, you only have to return a direction to move into
fun decideMove(request: MoveRequest): Direction {
    val head = request.you.head
    val body = request.you.body
    val board = request.board

    // Find all "safe" moves to do
    // (if you do a move that is not in this list, you will lose)
    val safeMoves = enumValues<Direction>()
        .filter { head + it !in body }
        .filter { (head + it).x in 0 until board.width }
        .filter { (head + it).y in 0 until board.height }
        .filter { (head + it) !in board.snakes.flatMap(BattleSnake::body) }
        .filter { (head + it) !in board.hazards }
        .sortedBy { direction -> board.food.minOfOrNull { it.distanceTo(head + direction) } }


    return safeMoves.firstOrNull() ?: DOWN
}