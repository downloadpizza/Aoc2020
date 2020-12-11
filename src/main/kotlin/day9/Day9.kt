package day9

import utils.*

fun main() {
    println(task1().also { INVALID = it})
    println(task2())
}

val input = getResourceAsLongs("/day9/input.txt")

val length = (input.size - 25)

fun getNumberAndPreamble(n: Int): Pair<Long, List<Long>> {
    val pre = input.subList(n, n + 25)
    val num = input[n + 25]
    return Pair(num, pre)
}

fun findSum(n: Long, pre: List<Long>): Boolean {
    val set = mutableSetOf<Long>()
    for (i in pre) {
        if(i in set)
            return true
        set.add(n - i)
    }
    return false
}

fun task1(): Long {
    for (i in (0 until length)) {
        val (num, pre) = getNumberAndPreamble(i)
        if(!findSum(num, pre))
            return num
    }
    throw NoSolutionFoundException()
}

var INVALID: Long = 0

fun task2(): Long {
    for (s in input.indices) {
        for (e in s until input.size) {
            val sl = input.subList(s, e)
            if(sl.sum() == INVALID)
                return (sl.min() ?: continue) + (sl.max() ?: continue)
        }
    }
    throw NoSolutionFoundException()
}