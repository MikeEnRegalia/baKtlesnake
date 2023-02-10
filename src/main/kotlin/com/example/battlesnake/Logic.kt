package com.example.battlesnake

// This is the heart of your snake
// It defines what to do on your next move
// You get the current game state passed as a parameter, you only have to return a direction to move into
fun decideMove(request: MoveRequest): Direction {
    // Find all "safe" moves to do
    // (if you do a move that is not in this list, you will lose)
    val safeMoves = enumValues<Direction>().filter { direction ->
        // Find the next intended position
        val head = request.you.head
        val newPosition = head + direction

        // Step 0: Don't let your Battlesnake move back on its own neck
        val neck = request.you.body[1]
        newPosition != neck

        // TODO: Step 1 - Don't hit walls.
        // Use information in the request to prevent your Battlesnake from moving beyond the boundaries of the board.
        // val boardWidth = request.board.width
        // val boardHeight = request.board.height

        // TODO: Step 2 - Don't hit yourself.
        // Use information in the request to prevent your Battlesnake from colliding with itself.
        // val myBody = request.you.body

        // TODO: Step 3 - Don't collide with others.
        // Use information in the request to prevent your Battlesnake from colliding with others.

        // TODO: Step 4 - Find food.
        // Use information in the request to seek out and find food.
    }

    // Finally, choose a move from the available safe moves.
    // TODO: Step 5 - Select a move to make based on strategy, rather than random.

    // Note: we use randomOrNull, so we don't get an exception when we are out of options
    // Rather, we move down, which will most likely kill us, but at least we do something
    return safeMoves.randomOrNull() ?: Direction.DOWN
}