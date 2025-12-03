@file:Suppress("SpellCheckingInspection")

package year2025.day03

import println
import readInput

fun main() {
    fun part1(input: List<String>): Int {
        var score = 0

        input.forEach { bank ->
            val firstDigit = bank.substring(0, bank.length - 1).maxBy { it.code }

            var max = 0
            bank.substring(0, bank.length - 1).forEachIndexed { index, char ->
                if (char == firstDigit) {
                    val secondDigit = bank.substring(index + 1).maxBy { it.code }
                    val newMax = charArrayOf(firstDigit, secondDigit).concatToString().toInt()
                    if (newMax > max) {
                        max = newMax
                    }
                }
            }

            score += max
        }

        return score
    }

    fun part2(input: List<String>): Long {
        val joltageLength = 12
        var score = 0L

        input.forEach { bank ->
            var jotlage = ""
            var joltageLastPositionInBank: Int = -1

            for (battery in 1..joltageLength) {
                val (newJoltageDigit, newJoltagePositionInBank) = bank.getNextDigit(joltageLastPositionInBank + 1, battery, joltageLength)

                jotlage += newJoltageDigit
                joltageLastPositionInBank = newJoltagePositionInBank
            }

            score += jotlage.toLong()
        }

        return score
    }

    val testInput = readInput("03", 2025, testData = true)
    val input = readInput("03", 2025)

    part1(input).println()
    part2(input).println()

    check(part1(testInput) == 357)
    check(part2(testInput) == 3121910778619)
    check(part1(input) == 17383)
    check(part2(input) == 172601598658203)
}

fun String.getNextDigit(startPosition: Int, currentBattery: Int, joltageLength: Int): Pair<Char, Int> {
    val subBank = this.substring(startPosition, this.length - (joltageLength - currentBattery))
    val nextDigit = subBank.maxBy { it.code }

    return nextDigit to subBank.indexOf(nextDigit) + startPosition
}
