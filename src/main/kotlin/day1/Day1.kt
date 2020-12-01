package day1

import utils.*

fun main() {
    print(task2())
}

val input = getResourceAsInts("/day1/input.txt")

fun task1(): Int {
    val map = mutableMapOf<Int, Int>()

    for(n in input) {
        val c = map[n]
        if(c != null)
            return c * n
        map[2020-n] = n
    }

    throw NoSolutionFoundException()
}

fun task2(): Int {
    val map = mutableMapOf<Int, Int>()

    for(n in input) {
        map[2020-n] = n
    }

    val resMap = mutableMapOf<Int, Pair<Int, Int>>()

    for(n in input) {
        val c = resMap[n]
        if(c != null) {
            return n * c.first * c.second
        }

        for((k,v) in map)
            resMap[k-n] = Pair(v, n)
    }

    throw NoSolutionFoundException()
}