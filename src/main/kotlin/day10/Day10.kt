package day10

import utils.*

val input = getResourceAsLongs("/day10/input.txt").sorted()
const val wall = 0L
val own = input.maxOrNull()!! + 3L

fun main() {
    println(input)
    println(task1())
    println(task2())
}

fun task1(): Long {
    var last = wall
    var ones = 0L
    var threes = 0L
    input.forEach {
        when(it - last) {
            1L -> ++ones
            3L -> ++threes
        }
        last = it
    }

    when(own - last) {
        1L -> ++ones
        3L -> ++ threes
    }

    return ones*threes
}

fun task2(): Long {
    return getPermutations(wall)
}

val solutions = mutableMapOf<Long, Long>()

fun getPermutations(j: Long): Long {
    val res = (1..3).map {
        if((it+j) in input)
            solutions[it+j] ?: getPermutations(it+j).also { r -> solutions[it+j] = r }
        else
            0
    }.sum()

    if(own-j <= 3)
        return res + 1
    return res
}