package utils

val anon: Class<*> = object {}.javaClass

fun String.fixLineSeparators(): String = this.replace("\r\n", "\n")

fun getResource(name: String) = anon.getResource(name)

fun getResourceAsString(name: String) = getResource(name).readText().fixLineSeparators().trim()
fun getResourceAsLines(name: String) = getResourceAsString(name).lines()
fun getResourceAsInts(name: String) = getResourceAsLines(name).map(String::toInt)
fun getResourceAsLongs(name: String) = getResourceAsLines(name).map(String::toLong)


class NoSolutionFoundException : Exception {
    constructor() : super()
    constructor(message: String) : super(message)
    constructor(message: String, cause: Throwable) : super(message, cause)
    constructor(cause: Throwable) : super(cause)
}

val whitespace = "\\s".toRegex()