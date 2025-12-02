package year2025.day02

import println
import readInput

fun main() {
    fun part1(input: List<String>): Long {
        var score = 0L

        val ranges = input[0].split(',')
        for (range in ranges) {
            val (a, b) = range.split("-")
            for (n in a.toLong()..b.toLong()) {
                if (!isValidPart1(n.toString())) {
                    score += n
                }
            }
        }

        return score
    }

    fun part2(input: List<String>): Long {
        var score = 0L

        val ranges = input[0].split(',')
        for (range in ranges) {
            val (a, b) = range.split("-")
            for (n in a.toLong()..b.toLong()) {
                if (!isValidPart2(n.toString())) {
                    score += n
                }
            }
        }

        return score
    }

    val testInput = readInput("02", 2025, testData = true)
    val input = readInput("02", 2025)

    part1(input).println()
    part2(input).println()

    check(part1(testInput) == 1227775554L)
    check(part2(testInput) == 4174379265)
    check(part1(input) == 15873079081)
    check(part2(input) == 22617871034)
}

fun isValidPart1(n: String): Boolean {
    if (n.length % 2 != 0) return true

    return n.take(n.length/2) != n.substring(n.length/2)
}

fun isValidPart2(n: String): Boolean {
    for (size in 1..(n.length/2)) {
        if (n.length % size != 0) continue

        if (n.chunked(size).toSet().size == 1) return false
    }
    return true
}
