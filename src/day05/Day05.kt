package day05

import println
import readInput

data class DataDay05(
    val rules: List<Pair<Int, Int>>,
    val updates: List<List<Int>>
)

fun main() {
    fun getData(input: List<String>): DataDay05 {
        val rules: MutableList<Pair<Int, Int>> = mutableListOf()
        val updates: MutableList<List<Int>> = mutableListOf()

        val iterator = input.iterator()
        var line = iterator.next()
        while (line.isNotEmpty()) {
            rules.add(line.split('|').map { it.toInt() }.let { it.first() to it.last() })
            line = iterator.next()
        }

        while (iterator.hasNext()) {
            line = iterator.next()
            updates.add(line.split(",").map { it.toInt() })
        }

        return DataDay05(rules, updates)
    }

    fun part1(input: List<String>): Int {
        var score = 0
        val (rules, updates) = getData(input)

        for (update in updates) {
            var isCorrect = true
            for ((iPage, page) in update.withIndex()) {
                for (rule in rules) {
                    if (rule.first == page && iPage > 0) {
                        if(update.subList(0, iPage).any { it == rule.second }) {
                            isCorrect = false
                            break
                        }
                    }
                }

                if (!isCorrect)
                    break
            }

            if (isCorrect)
                score += update[update.size/2]
        }

        return score
    }

    fun part2(input: List<String>): Int {
        var score = 0
        val (rules, updates) = getData(input)

        for (update in updates) {
            val updateCorrected = update.subList(0, update.size).toMutableList()

            var isCorrect = true
            for ((iPage, page) in update.withIndex()) {

                val rulesNotRespected = mutableListOf<Pair<Int, Int>>()

                for (rule in rules) {
                    if (rule.first == page && iPage > 0) {
                        val index = updateCorrected.subList(0, iPage).indexOfFirst { it == rule.second }
                        if(index != -1) {
                            rulesNotRespected.add(index to rule.second)
                            isCorrect = false
                        }
                    }
                }
                val pagesToMove = rulesNotRespected.sortedBy { it.first }.map { it.second }
                updateCorrected.addAll(iPage + 1, pagesToMove)
                pagesToMove.forEach {
                    updateCorrected.remove(it)
                }
            }

            if (!isCorrect)
                score += updateCorrected[update.size/2]
        }

        return score
    }

    val testInput = readInput("05", testData = true)
    val input = readInput("05")

    part1(input).println()
    part2(input).println()

    check(part1(testInput) == 143)
    check(part2(testInput) == 123)
    check(part1(input) == 4766)
    check(part2(input) == 6257)
}
