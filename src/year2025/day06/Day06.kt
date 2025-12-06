package year2025.day06

import println
import readInput

fun main() {
    fun part1(input: List<String>): Long {
        val operators: List<Char> = input.last().split(Regex("\\s+")).map { it.first() }
        val problems: List<List<Long>> = input.take(input.size - 1).map { problem ->  problem.trim().split(Regex("\\s+")).map { it.toLong() } }

        var results: List<Long> = problems.first()

        problems.subList(1, problems.size).forEach { problem ->
            results = results.mapIndexed { index, result ->
                when (operators[index]) {
                    '+' -> result + problem[index]
                    '*' -> result * problem[index]
                    else -> throw IllegalArgumentException()
                }
            }
        }

        return results.sum()
    }

    fun part2(input: List<String>): Long {
        var score: Long = 0

        val operatorsWithLength: List<Pair<Char, Int>> = Regex("(\\S)(\\s+)").findAll(input.last()).map { it.groups }.map { it[1]!!.value.first() to it[2]!!.value.length }.toList()
        val lines: List<List<String>> = input.take(input.size - 1).map { line ->
            var startIndex = 0
            mutableListOf<String>().also { numbers ->
                for (length in operatorsWithLength.map { it.second }) {
                    numbers.add(line.substring(startIndex, (startIndex + length).coerceAtMost(line.length)) +
                            //because of trim at the end of the line
                            if(startIndex + length > line.length) " ".repeat(startIndex + length - line.length) else ""
                    )
                    startIndex += length + 1
                }
            }
        }

        operatorsWithLength.forEachIndexed { problem, operatorWithLength ->
            val (operator: Char, maxLength: Int) = operatorWithLength
            val numbers: List<String> = lines.map { it[problem] }

            var result: Long = when (operator) {
                '+' -> 0
                '*' -> 1
                else -> throw IllegalArgumentException()
            }

            for(digit in 0 until maxLength) {
                val operand = numbers.map { it[digit] }.toCharArray().concatToString().trim().toLong()
                when (operator) {
                    '+' -> result += operand
                    '*' -> result *= operand
                    else -> throw IllegalArgumentException()
                }
            }

            score += result
        }

        return score
    }

    val testInput = readInput("06", 2025, testData = true)
    val input = readInput("06", 2025)

    part1(input).println()
    part2(input).println()

    check(part1(testInput) == 4277556L)
    check(part2(testInput) == 3263827L)
    check(part1(input) == 4412382293768)
    check(part2(input) == 7858808482092)
}
