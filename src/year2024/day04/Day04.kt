package year2024.day04

import println
import readInput

fun main() {
    fun part1(input: List<String>): Int {
        var score = 0

        val data = input.map { it.toList() }
        val yMax = data.lastIndex
        val xMax = data.first().lastIndex

        data.forEachIndexed { y, chars ->
            chars.forEachIndexed { x, char ->
                if (char == 'X' || char == 'S') {
                    val cases = mutableListOf<String>()
                    if(x <= xMax - 3) {
                        cases.add(chars.subList(x, x + 4).joinToString(""))
                    }

                    if(y <= yMax - 3) {
                        cases.add(arrayOf(data[y][x], data[y + 1][x], data[y + 2][x], data[y + 3][x]).joinToString(""))
                    }

                    if(x <= xMax - 3 && y <= yMax - 3) {
                        cases.add(arrayOf(data[y][x], data[y + 1][x + 1], data[y + 2][x + 2], data[y + 3][x + 3]).joinToString(""))
                    }

                    if(x >= 3 && y <= yMax - 3) {
                        cases.add(arrayOf(data[y][x], data[y + 1][x - 1], data[y + 2][x - 2], data[y + 3][x - 3]).joinToString(""))
                    }


                    for(case in cases) {
                        if(case == "XMAS" || case == "SAMX") {
                            score += 1
                        }
                    }
                }
            }
        }

        return score
    }

    fun part2(input: List<String>): Int {
        var score = 0

        val data = input.map { it.toList() }
        val yMax = data.lastIndex
        val xMax = data.first().lastIndex

        data.forEachIndexed { y, chars ->
            chars.forEachIndexed { x, char ->
                if (char == 'A'
                    && x in 1 until xMax
                    && y in 1 until yMax
                    && arrayOf(data[y-1][x-1], data[y+1][x+1]).joinToString("") in arrayOf("SM", "MS")
                    && arrayOf(data[y-1][x+1], data[y+1][x-1]).joinToString("") in arrayOf("SM", "MS")
                    ) {
                    score += 1
                }
            }
        }

        return score
    }

    val testInput = readInput("04", 2024, testData = true)
    val input = readInput("04", 2024)

    part1(input).println()
    part2(input).println()

    check(part1(testInput) == 18)
    check(part2(testInput) == 9)
    check(part1(input) == 2507)
    check(part2(input) == 1969)
}
