package day2

import utils.NoSolutionFoundException
import utils.getResourceAsLines

val input = getResourceAsLines("/day2/input.txt")

data class PasswordPolicy(val min: Int, val max: Int, val char: Char) {
    fun matches(password: String): Boolean {
        val a = password.count { it -> it == char }
        return a in min..max
    }

    fun matches2(password: String): Boolean {
        val minc = password.getOrNull(min - 1) ?: return false
        val maxc = password.getOrNull(max - 1) ?: return false


        return (minc == char) xor (maxc == char)
    }
}

fun main() {
//    println(task1())
    println(task2())
}

fun check1(line: String): Boolean {
    val (nums, char, pw) = line.split(" ")
    val (min, max) = nums.split("-").map(String::toInt)
    return PasswordPolicy(min, max, char[0]).matches(pw)
}

fun check2(line: String): Boolean {
    val (nums, chars, pw) = line.split(" ")
    val (min, max) = nums.split("-").map(String::toInt)
    val char = chars[0]
    return PasswordPolicy(min, max, char).matches2(pw.trim())
}

fun task1(): Int = input.count(::check1)
fun task2(): Int = input.count(::check2)