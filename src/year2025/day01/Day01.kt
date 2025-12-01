package year2025.day01

import println
import readInput
import kotlin.math.abs
import kotlin.math.sign

fun main() {
    fun part1(input: List<String>): Int {
        var score = 0

        var dial = 50
        input.forEach { rotation ->
            val (direction, distance) = (rotation.take(1).first() to rotation.substring(1).toInt())
            dial = when (direction) {
                'L' -> dial - distance
                'R' -> dial + distance
                else -> throw IllegalArgumentException()
            } % 100

            if (dial == 0) {
                score++
            }
        }

        return score
    }

    fun part2(input: List<String>): Int {
        var score = 0

        var dial = 50
        for (rotation in input) {
            val (direction, distance) = (rotation.take(1).first() to rotation.substring(1).toInt())
            val newDial = when (direction) {
                'L' -> dial - distance
                'R' -> dial + distance
                else -> throw IllegalArgumentException()
            }

            score += abs(newDial / 100)
            if (newDial.sign * dial.sign == -1 || newDial == 0) {
                score++
            }
            dial = newDial % 100
        }

        return score
    }

    val testInput = readInput("01", 2025, testData = true)
    val input = readInput("01", 2025)

    part1(input).println()
    part2(input).println()

    check(part1(testInput) == 3)
    check(part2(testInput) == 6)
    check(part1(input) == 1158)
    check(part2(input) == 6860)
}
