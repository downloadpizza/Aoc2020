package day6

import utils.whitespace
import utils.*

fun main() {
    println(task1())
    println(task2())
}

val input = getResourceAsString("/day6/input.txt").split("\n\n")

fun task1(): Int = input.map {
    whitespace.replace(it, "").toList().distinct().size
}.sum()

fun task2(): Int = input.map {
    it.split("\n")
            .map(String::toList)
            .reduce { acc, s -> acc.intersect(s).toList() }
            .size
}.sum()