package day5

import utils.getResourceAsLines

val input = getResourceAsLines("/day5/input.txt").map(::id)

fun main() {
    println(task1())
    println(task2())
}

fun id(s: String): Int = s.replace("[BR]".toRegex(), "1")
        .replace("[FL]".toRegex(), "0")
        .toInt(2)

fun task1(): Int = input
        .maxOrNull()!!

fun task2(): Int {
    return (0..(127*8+7))
            .dropWhile { it !in input }
            .dropWhile { it in input }
            .first()
}