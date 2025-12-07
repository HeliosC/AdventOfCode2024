package year2025.day07

import println
import readInput

fun main() {
    fun part1(input: List<String>): Int {
        var score = 0

        val beams: MutableSet<Int> = mutableSetOf(input.first().indexOf('S'))

        input.subList(1, input.size).forEach { line ->
            beams.toSet().forEach { beam ->
                if (line[beam] == '^') {
                    score++
                    beams.remove(beam)
                    if (beam > 0) {
                        beams.add(beam - 1)
                    }
                    if (beam < line.lastIndex) {
                        beams.add(beam + 1)
                    }
                }
            }
        }

        return score
    }

    fun part2(input: List<String>): Long {
        /** >Index, Timelines> */
        val beams: MutableMap<Int, Long> = mutableMapOf(input.first().indexOf('S') to 1)

        input.subList(1, input.size).forEach { line ->
            beams.toMap().forEach { (beam, timelines) ->
                if (line[beam] == '^') {
                    beams.remove(beam)
                    if (beam > 0) {
                        beams[beam - 1] = (beams[beam - 1] ?: 0) + timelines
                    }
                    if (beam < line.lastIndex) {
                        beams[beam + 1] = (beams[beam + 1] ?: 0) + timelines
                    }
                }
            }
        }

        return beams.values.sum()
    }

    val testInput = readInput("07", 2025, testData = true)
    val input = readInput("07", 2025)

    part1(input).println()
    part2(input).println()

    check(part1(testInput) == 21)
    check(part2(testInput) == 40L)
    check(part1(input) == 1570)
    check(part2(input) == 15118009521693)
}
