package day3

val input = Forest(utils.getResourceAsLines("/day3/input.txt").map {
    it.trim().map { f ->
        when(f) {
            '.' -> Field.Empty
            '#' -> Field.Tree
            else -> error("Invalid")
        }
    }
})

enum class Field {
    Empty, Tree
}

data class Forest(
    val fields: List<List<Field>>
) {
    fun getOrNull(x: Int, y: Int) = fields.getOrNull(y)?.get(x % fields[0].size)
}

fun main() {
    println(task1())
    println(task2())
}

fun goSloped(slopeX: Int, slopeY: Int): Int {
    var posX = 0
    var posY = 0

    var count = 0

    while (true) {
        val field = input.getOrNull(posX, posY) ?: break

        if (field == Field.Tree)
            count++

        posX += slopeX
        posY += slopeY
    }
    return count
}

fun task1(): Int = goSloped(3, 1)

fun task2() = listOf(
        Pair(1, 1),
        Pair(3, 1),
        Pair(5, 1),
        Pair(7, 1),
        Pair(1, 2)
).map { (slopeX, slopeY) -> goSloped(slopeX, slopeY) }
        .reduce { acc, i -> acc * i }