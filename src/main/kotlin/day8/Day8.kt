package day8

import utils.NoSolutionFoundException
import utils.getResourceAsLines

val input = getResourceAsLines("/day8/input.txt").map(::parseInstruction).toMutableList()

fun main() {
    println(task1())
    println(task2())
}

enum class InstructionType {
    NOP,
    ACC,
    JMP
}

data class Instruction(val type: InstructionType, val arg: Int)

fun parseInstruction(s: String): Instruction {
    val (type, arg) = s.split(" ")
    return Instruction(
            InstructionType.valueOf(type.toUpperCase()),
            arg.trim().toInt()
    )
}

fun task1(): Int {
    val (value, terminated) = run()
    assert(!terminated) { "program should not terminate" }
    return value
}

fun task2(): Int {
    var lastChanged = -1
    
    for (i in input.indices) {
        if(tryFlip(i)) {
            if(lastChanged != -i)
                assert(tryFlip(lastChanged))
            lastChanged = i
            val (value, terminated) = run()
            if(terminated)
                return value
        }
    }

    throw NoSolutionFoundException()
}

fun tryFlip(n: Int): Boolean = when(input[n].type) {
    InstructionType.NOP -> {
        input[n] = Instruction(InstructionType.JMP, input[n].arg)
        true
    }
    InstructionType.JMP -> {
        input[n] = Instruction(InstructionType.NOP, input[n].arg)
        true
    }
    InstructionType.ACC -> false
}

data class ProgramResult(val value: Int, val terminated: Boolean)

fun run(): ProgramResult {
    var ip = 0
    var acc = 0
    val visited = mutableListOf<Int>()

    while(true) {
        if(visited.contains(ip))
            return ProgramResult(acc, false)
        visited.add(ip)

        if(ip == input.size)
            return ProgramResult(acc, true)
        ip %= input.size
        val instr = input[ip  % input.size]
        when(instr.type) {
            InstructionType.NOP -> {}
            InstructionType.JMP -> {
                ip += instr.arg
                continue
            }
            InstructionType.ACC -> {
                acc += instr.arg
            }
        }
        ip += 1
    }
}