package day6

import utils.*

fun main() {
    println(task1())
    println(task2())
}

val input = getResourceAsString("/day6/input.txt").split("\n\n")

fun task1(): Int = input.sumOf {
        it.split("\n")
            .map(String::toSet)
            .reduce { acc, s -> acc.union(s) }
            .size
}

fun task2(): Int = input.sumOf {
    it.split("\n")
            .map(String::toSet)
            .reduce { acc, s -> acc.intersect(s) }
            .size
}