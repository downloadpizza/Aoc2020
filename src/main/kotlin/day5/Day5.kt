package day5

import utils.getResourceAsLines

val input = getResourceAsLines("/day5/input.txt")

fun main() {
    println(task1())
    println(task2())
}

fun id(s: String): Int = s.replace("[BR]".toRegex(), "1")
        .replace("[FL]".toRegex(), "0")
        .toInt(2)

fun task1(): Int = input
        .map(::id)
        .maxOrNull()!!

fun task2(): Int {
    val ids = input.map(::id)
    return (0..(127*8+7))
            .dropWhile { it !in ids }
            .dropWhile { it in ids }
            .first()
}