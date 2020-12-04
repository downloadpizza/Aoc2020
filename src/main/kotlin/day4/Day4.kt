package day4

import utils.getResourceAsString

val input = getResourceAsString("/day4/input.txt")

data class UncheckedPassport(
        val birthYear: String,
        val issueYear: String,
        val expirationYear: String,
        val height: String,
        val hairColor: String,
        val eyeColor: String,
        val passportId: String,
        val countryId: String?
)

data class Passport(
        val birthYear: Int,
        val issueYear: Int,
        val expirationYear: Int,
        val height: String,
        val hairColor: String,
        val eyeColor: String,
        val passportId: String,
        val countryId: String?
)

val whitespace = "[ \n]".toRegex()

fun String.toUncheckedPassword(): UncheckedPassport? {
    val fields = whitespace.split(this).map { it.split(":")[0] to it.split(":")[1] }.toMap()

    val birthYear = fields["byr"] ?: return null
    val issueYear = fields["iyr"] ?: return null
    val expirationYear = fields["eyr"] ?: return null
    val height = fields["hgt"] ?: return null
    val hairColor = fields["hcl"] ?: return null
    val eyeColor = fields["ecl"] ?: return null
    val passportId = fields["pid"] ?: return null
    val countryId = fields["cid"]

    return UncheckedPassport(
            birthYear, issueYear, expirationYear, height, hairColor, eyeColor, passportId, countryId
    )
}

fun String.toPassport(): Passport? {
    val fields = whitespace.split(this).map { it.split(":")[0] to it.split(":")[1] }.toMap()

    val birthYear = fields["byr"]?.checkBirthYear() ?: return null
    val issueYear = fields["iyr"]?.checkIssueYear() ?: return null
    val expirationYear = fields["eyr"]?.checkExpirationYear() ?: return null
    val height = fields["hgt"]?.checkHeight() ?: return null
    val hairColor = fields["hcl"]?.checkHairColor() ?: return null
    val eyeColor = fields["ecl"]?.checkEyeColor() ?: return null
    val passportId = fields["pid"]?.checkPassportId() ?: return null
    val countryId = fields["cid"]

    return Passport(
            birthYear, issueYear, expirationYear, height, hairColor, eyeColor, passportId, countryId
    )
}

fun String.checkBirthYear(): Int? {
    val num = this.toIntOrNull() ?: return null
    if(num !in 1920..2002)
        return null
    return num
}

fun String.checkIssueYear(): Int? {
    val num = this.toIntOrNull() ?: return null
    if(num !in 2010..2020)
        return null
    return num
}

fun String.checkExpirationYear(): Int? {
    val num = this.toIntOrNull() ?: return null
    if(num !in 2020..2030)
        return null
    return num
}

fun String.checkHeight(): String? {
    val (num, unit) = this.trim().let { Pair(it.substring(0, it.length-2), it.substring(it.length-2)) }
    val parsedNum = num.toIntOrNull() ?: return null
    val valid = when(unit) {
        "cm" -> parsedNum in 150..193
        "in" -> parsedNum in 59..76
        else -> false
    }
    return if(valid) this else null
}

val colorRegex = "#[a-f0-9]{6}".toRegex()

fun String.checkHairColor(): String? = if(colorRegex.matches(this.trim())) this else null

val eyeColors = listOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth")

fun String.checkEyeColor(): String? = if(this.trim() in eyeColors) this else null

val pidRegex = "[0-9]{9}".toRegex()

fun String.checkPassportId(): String? = if(pidRegex.matches(this.trim())) this else null

fun main() {
    println(task1())
    println(task2())
}

fun task1(): Int = input.split("\n\n")
        .map(String::toUncheckedPassword)
        .count { it != null }

fun task2(): Int = input.split("\n\n")
        .map(String::toPassport)
        .count { it != null }