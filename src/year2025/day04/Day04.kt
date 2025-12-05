package year2025.day04

import println
import readInput

fun main() {
    fun part1(input: List<String>): Int {
        val inputList = input.map { it.toList() }
        var score = 0

        inputList.subList(1, inputList.lastIndex).forEachIndexed { indexRow, row ->
            row.subList(1, row.lastIndex).forEachIndexed { indexColumn, item ->
                if (item == '@' && inputList.canAccess(indexColumn + 1, indexRow + 1)) {
                    score++
                }
            }
        }

        return score
    }

    fun part2(input: List<String>): Int {
        val editedInout = input.map { it.toMutableList() }
        var score = 0

        do {
            val rollsToRemove = mutableListOf<Pair<Int,Int>>()
            editedInout.subList(1, editedInout.lastIndex).forEachIndexed { indexRow, row ->
                row.subList(1, row.lastIndex).forEachIndexed { indexColumn, item ->
                    if (item == '@' && editedInout.canAccess(indexColumn + 1, indexRow + 1)) {
                        rollsToRemove.add((indexColumn + 1) to (indexRow + 1))
                    }
                }
            }

            rollsToRemove.forEach { (column, row) ->
                editedInout[row][column] = '.'
            }

            score += rollsToRemove.size
        } while (rollsToRemove.isNotEmpty())

        return score
    }

    val testInput = readInput("04", 2025, testData = true)
    val input = readInput("04", 2025)

    part1(input).println()
    part2(input).println()

    check(part1(testInput) == 13)
    check(part2(testInput) == 43)
    check(part1(input) == 1486)
    check(part2(input) == 9024)
}

fun List<List<Char>>.canAccess(column: Int, row: Int): Boolean {
    return mutableListOf<Char>().also {
        it.addAll(this[row-1].subList(column - 1, column + 2).toList())
        it.addAll(this[row+1].subList(column - 1, column + 2).toList())
        it.add(this[row][column-1])
        it.add(this[row][column+1])
    }.count { it == '@' } < 4
}
