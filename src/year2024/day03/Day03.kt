package year2024.day03

import println
import readInput
import java.util.regex.Matcher
import java.util.regex.Pattern

fun main() {
    fun part1(input: List<String>): Int {
        var score = 0

        for (data in input) {
            val regex = "mul\\((\\d{1,3}),(\\d{1,3})\\)"
            val pattern: Pattern = Pattern.compile(regex, Pattern.MULTILINE)
            val matcher: Matcher = pattern.matcher(data)

            while (matcher.find()) {
                val x = matcher.group(1).toInt()
                val y = matcher.group(2).toInt()

                score += x*y
            }
        }

        return score
    }

    fun part2(input: List<String>): Int {
        var score = 0

        var isDo = true
        for (data in input) {
            val regex = "(?>mul\\((\\d{1,3}),(\\d{1,3})\\))|(?>do(?>n't)?)"
            val pattern: Pattern = Pattern.compile(regex, Pattern.MULTILINE)
            val matcher: Matcher = pattern.matcher(data)

            while (matcher.find()) {
                when (matcher.group(0)) {
                    "do" -> isDo = true
                    "don't" -> isDo = false
                    else -> {
                        if (isDo) {
                            val x = matcher.group(1).toInt()
                            val y = matcher.group(2).toInt()
                            score += x*y
                        }
                    }
                }

            }
        }

        return score
    }

    val testInput = readInput("03", 2024, testData = true)
    val input = readInput("03", 2024)

    part1(input).println()
    part2(input).println()

    check(part1(testInput) == 161)
    check(part2(testInput) == 48)
    check(part1(input) == 173785482)
    check(part2(input) == 83158140)

}
