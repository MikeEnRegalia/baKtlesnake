package com.example.battlesnake

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// This file contains utility for calculating positions and directions
// You can add more logic to this file, if you want.

@Serializable
// Enumeration of all valid directions you can go in, in battlesnake
enum class Direction {
    @SerialName("up") UP,
    @SerialName("down") DOWN,
    @SerialName("left") LEFT,
    @SerialName("right") RIGHT
}

// Converts a direction to a position
val Direction.position get() = when (this) {
    Direction.UP -> Position(0, 1)
    Direction.LEFT -> Position(-1, 0)
    Direction.RIGHT -> Position(1, 0)
    Direction.DOWN -> Position(0, -1)
}

// Data structure that holds an x and y value
@Serializable
data class Position(val x: Int, val y: Int)

// Utility for working with positions
// (basic math)
// You can add more extensions here if necessary
operator fun Position.plus(other: Position) = Position(x + other.x, y + other.y)
operator fun Position.minus(other: Position) = Position(x - other.x, y - other.y)
operator fun Position.plus(other: Direction) = plus(other.position)

// Calculates all adjacent positions to one given position
fun Position.adjacent() = listOf(
    this + Direction.UP,
    this + Direction.DOWN,
    this + Direction.LEFT,
    this + Direction.RIGHT
)