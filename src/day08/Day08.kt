package day08

import println
import readInput

typealias Antennas = Map<Char, List<Pair<Int, Int>>>

fun main() {
    fun getAntennas(input: List<String>): Antennas {
        val antennas: MutableMap<Char, List<Pair<Int, Int>>> = mutableMapOf()
        input.forEachIndexed { y, line ->
            line.forEachIndexed { x, char ->
                if (char != '.') {
                    val positions = antennas.getOrDefault(char, listOf()).toMutableList()
                    positions.add(x to y)
                    antennas[char] = positions
                }
            }
        }

        return antennas
    }

    fun Antennas.getPairsWithDelta(callback: (Pair<Int, Int>, Pair<Int, Int>, Pair<Int, Int>) -> Unit) {
        this.entries.forEach { (_, positions) ->
            for ((i1, position1) in positions.subList(0, positions.lastIndex).withIndex()) {
                for (position2 in positions.subList(i1 + 1, positions.size)) {
                    val delta = position2.first - position1.first to position2.second - position1.second
                    callback(position1, position2, delta)
                }
            }
        }
    }

    fun part1(input: List<String>): Int {
        val antennas = getAntennas(input)

        val xMax = input.first().lastIndex
        val yMax = input.lastIndex

        val antinodes = mutableSetOf<Pair<Int, Int>>()
        antennas.getPairsWithDelta { (x1, y1), (x2, y2), (dx, dy) ->
            val antinode1 = x1 - dx to y1 - dy
            val antinode2 = x2 + dx to y2 + dy

            if (antinode1.first in 0..xMax && antinode1.second in 0..yMax) {
                antinodes.add(antinode1)
            }
            if (antinode2.first in 0..xMax && antinode2.second in 0..yMax) {
                antinodes.add(antinode2)
            }
        }

        return antinodes.size
    }

    fun part2(input: List<String>): Int {
        val antennas = getAntennas(input)

        val xMax = input.first().lastIndex
        val yMax = input.lastIndex

        val antinodes = mutableSetOf<Pair<Int, Int>>()
        antennas.getPairsWithDelta { (x1, y1), _, (dx, dy) ->
            var (x, y) = x1 to y1
            while (x in 0..xMax && y in 0..yMax) {
                antinodes.add(x to y)
                x += dx
                y += dy
            }

            x = x1
            y = y1

            while (x in 0..xMax && y in 0..yMax) {
                antinodes.add(x to y)
                x -= dx
                y -= dy
            }
        }

        return antinodes.size
    }

    val testInput = readInput("08", testData = true)
    val input = readInput("08")

    part1(input).println()
    part2(input).println()

    check(part1(testInput) == 14)
    check(part2(testInput) == 34)
    check(part1(input) == 341)
    check(part2(input) == 1134)
}
