package year2025.day05

import println
import readInput

fun main() {
    fun part1(input: List<String>): Int {
        var score = 0
        val (ranges, ingredients) = splitInput(input)

        ingredients.forEach { ingredient ->
            if (ranges.any { ingredient in it.first..it.second }) {
                score++
            }
        }

        return score
    }

    fun part2(input: List<String>): Long {
        val ranges = splitInput(input).first.sortedBy { it.first }.toMutableList()
        val rangesNoOverlap = mutableListOf<Pair<Long, Long>>()

        var currentRange: Pair<Long, Long> = ranges.first()
        ranges.subList(1, ranges.size).forEach { range ->
            if (range.first in currentRange.first..currentRange.second) {
                if (range.second in currentRange.first..currentRange.second) {
                    return@forEach
                } else {
                    currentRange = currentRange.copy(second = range.second)
                }
            } else {
                rangesNoOverlap.add(currentRange)
                currentRange = range
            }
        }
        rangesNoOverlap.add(currentRange)

        return rangesNoOverlap.sumOf { it.second - it.first + 1 }
    }

    val testInput = readInput("05", 2025, testData = true)
    val input = readInput("05", 2025)

    part1(input).println()
    part2(input).println()

    check(part1(testInput) == 3)
    check(part2(testInput) == 14L)
    check(part1(input) == 744)
    check(part2(input) == 347468726696961)
}

fun splitInput(input: List<String>): Pair<List<Pair<Long, Long>>, List<Long>> =
    input.indexOf("").let { indexOfBlank ->
        input.take(indexOfBlank).map {
                inputRange -> inputRange.split("-").let { it.first().toLong() to it.last().toLong() }
        } to
                input.subList(indexOfBlank + 1, input.size).map { it.toLong() }
    }
