package year2024.day02

import println
import readInput
import kotlin.math.abs

fun main() {
    fun getReportErrorIndex(report: List<Int>): Int {
        val isAscending = report[2] - report.first() > 0
        var isSafe = true
        var errorIndex = 0
        for (index in 1..report.lastIndex) {
            val diff = report[index] - report[index -1]
            if ((diff > 0) == isAscending && abs(diff) in 1..3) {
                continue
            } else {
                isSafe = false
                errorIndex = index
                break
            }
        }

        return if (isSafe)
            -1
        else
            errorIndex
    }

    fun part1(input: List<String>): Int {
        val data = input.map { report -> report.split(' ').map { it.toInt() } }

        var score = 0
        data.forEach { report ->
            if (getReportErrorIndex(report) == -1)
                score += 1
        }

        return score
    }

    fun part2(input: List<String>): Int {
        val data = input.map { report -> report.split(' ').map { it.toInt() } }

        var score = 0
        data.forEach { report ->
            val errorIndex = getReportErrorIndex(report)
            val isSafe = if(errorIndex == -1) {
                true
            } else {
                val reportFix1 = report.toMutableList().also { it.removeAt(errorIndex) }
                if (getReportErrorIndex(reportFix1) == -1) {
                    true
                } else {
                    val reportFix2 = report.toMutableList().also { it.removeAt(errorIndex - 1) }
                    if (getReportErrorIndex(reportFix2) == -1) {
                        true
                    } else if(errorIndex == 2) {
                        val reportFix3 = report.toMutableList().also { it.removeAt(0) }
                        (getReportErrorIndex(reportFix3) == -1)
                    } else {
                        false
                    }
                }
            }

            if (isSafe)
                score += 1
        }

        return score
    }

    val testInput = readInput("02", 2024, testData = true)
    val input = readInput("02", 2024)

    part1(input).println()
    part2(input).println()

    check(part1(testInput) == 2)
    check(part2(testInput) == 4)
}
