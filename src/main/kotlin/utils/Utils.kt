package utils

val anon: Class<*> = object {}.javaClass

fun getResource(name: String) = anon.getResource(name)

fun getResourceAsLines(name: String) = getResourceAsString(name).lines()
fun getResourceAsString(name: String) = getResource(name).readText().trim()

fun getResourceAsInts(name: String) = getResourceAsLines(name).map(String::toInt)


class NoSolutionFoundException : Exception {
    constructor() : super()
    constructor(message: String) : super(message)
    constructor(message: String, cause: Throwable) : super(message, cause)
    constructor(cause: Throwable) : super(cause)
}