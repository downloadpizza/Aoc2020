package day11

import utils.*

typealias WaitingArea = List<List<Spot>>

typealias MutableWaitingArea = MutableList<MutableList<Spot>>

val input = getResourceAsLines("/day11/input.txt").map { line ->
    line.map {
        when (it) {
            '#' -> Spot.Occupied
            'L' -> Spot.Empty
            '.' -> Spot.Floor
            else -> error("??")
        }
    }
}
val height = input.size
val width = input[0].size

fun WaitingArea.getOrNull(x: Int, y: Int): Spot? = this.getOrNull(y)?.getOrNull(x)

val directions = listOf(
        Pair(-1, -1),   Pair(0, -1),    Pair(1, -1),
        Pair(-1, 0),                    Pair(1, 0),
        Pair(-1, 1),    Pair(0, 1),     Pair(1, 1),
)

fun WaitingArea.getAdjacent(x: Int, y: Int): List<Spot> = directions.mapNotNull { (xo, yo) ->
    this.getOrNull(x + xo, y + yo)
}

fun WaitingArea.getVisible(x: Int, y: Int): List<Spot> = directions.mapNotNull { (xo, yo) ->
    var currentX = x + xo
    var currentY = y + yo

    var current = this.getOrNull(currentX, currentY)

    while(current == Spot.Floor) {
        currentX += xo
        currentY += yo
        current = this.getOrNull(currentX, currentY)
    }

    current
}

enum class Spot {
    Empty,
    Occupied,
    Floor
}

fun main() {
    println(task1())
    println(task2())
}

fun iterate(input: WaitingArea): WaitingArea {
    val result: MutableWaitingArea = MutableList(height) { MutableList(width) { Spot.Floor } }
    for (y in 0 until height) {
        for (x in 0 until width) {
            val current = input[y][x]

            val adjacent = input.getAdjacent(x, y)

            result[y][x] = if (current == Spot.Empty && adjacent.none { it == Spot.Occupied })
                Spot.Occupied
            else if (current == Spot.Occupied && adjacent.count { it == Spot.Occupied } >= 4)
                Spot.Empty
            else
                input[y][x]
        }
    }
    return result
}

fun iterateVisible(input: WaitingArea): WaitingArea {
    val result: MutableWaitingArea = MutableList(height) { MutableList(width) { Spot.Floor } }
    for (y in 0 until height) {
        for (x in 0 until width) {
            val current = input[y][x]

            val visible = input.getVisible(x, y)

            result[y][x] = if (current == Spot.Empty && visible.none { it == Spot.Occupied })
                Spot.Occupied
            else if (current == Spot.Occupied && visible.count { it == Spot.Occupied } >= 5)
                Spot.Empty
            else
                input[y][x]
        }
    }
    return result
}

fun task1(): Int {
    var fare = input
    var newFare = iterate(fare)

    while (fare != newFare) {
        fare = newFare
        newFare = iterate(fare)

    }

    return fare.sumBy {
        it.count { it == Spot.Occupied }
    }
}

fun task2(): Int {
    var fare = input
    var newFare = iterateVisible(fare)

    while (fare != newFare) {
        fare = newFare
        newFare = iterateVisible(fare)

    }

    return fare.sumBy {
        it.count { it == Spot.Occupied }
    }
}