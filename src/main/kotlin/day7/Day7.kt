package day7

import utils.getResourceAsLines

fun main() {
    println(task1())
    println(task2())
}

val bagRegex = "bag(s?)".toRegex()

val input = getResourceAsLines("/day7/input.txt")

typealias Rules = Map<String, Int>

fun parseRule(s: String): Rules {
    val s = s.replace(".", "")
    return if(s == "no other bags") {
        emptyMap()
    } else {
        val s = s.replace(bagRegex, "")
        println(s)
        s.split(", ").map(String::trim).map {
            val (n, bag) = it.split(" ", limit=2)
            bag to n.toInt()
        }.toMap()
    }
}

val map = input.map {
    val (bag, rule) = it.split("bags contain").map(String::trim)
    bag to parseRule(rule)
}.toMap()

const val GOLD_BAG = "shiny gold"

fun checkGolden(rules: Rules): Boolean {
    return if(rules.containsKey(GOLD_BAG)) {
        true
    } else {
        rules
            .any { map[it.key]?.let(::checkGolden) ?: false }
    }
}

fun task1(): Int {
    var n = 0
    for(rules in map.values) {
        if(checkGolden(rules))
            n += 1
    }
    return n
}

fun countContained(rules: Rules): Int = rules.map { (bag, count) ->
    val rules = map[bag] ?: emptyMap()
    count * (countContained(rules) + 1)
}.sum()

fun task2(): Int = countContained(map[GOLD_BAG]!!)