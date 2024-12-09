package day07

import println
import readInput

enum class Operator {
    ADD,
    MULTIPLY,
    CONCATENATE
}

fun main() {
    fun getData(input: List<String>): List<Pair<Long, List<Int>>> {
        val data = mutableListOf<Pair<Long, List<Int>>>()
        for (equation in input) {
            val (result, numbers) = equation.split(": ")
            data.add(result.toLong() to numbers.split(" ").map { it.toInt() })
        }

        return data
    }

    fun operate(x: Long, y: Long, operator: Operator): Long {
       return when(operator) {
            Operator.ADD -> x + y
            Operator.MULTIPLY -> x * y
            Operator.CONCATENATE -> (x.toString() + y.toString()).toLong()
        }
    }

    fun canCalculate(x: Long, y: Long, operator: Operator, nextNumbers: List<Int>, resultExpected: Long, operatorsAvailable: List<Operator>): Boolean {
        val result = operate(x, y, operator)

        return if (result > resultExpected) {
            false
        } else if (nextNumbers.isEmpty()) {
            result == resultExpected
        } else {
            operatorsAvailable.any { nextOperator ->
                canCalculate(result, nextNumbers.first().toLong(), nextOperator, nextNumbers.subList(1, nextNumbers.size), resultExpected, operatorsAvailable)
            }
        }
    }

    fun part1(input: List<String>): Long {
        var score: Long = 0
        val data = getData(input)

        for ((result, numbers) in data) {
            val operatorsAvailable = listOf(Operator.ADD, Operator.MULTIPLY)

            val x = numbers.first().toLong()
            if (canCalculate(0, x, Operator.ADD, numbers.subList(1, numbers.size), result, operatorsAvailable)) {
                score += result
            }
        }

        return score
    }

    fun part2(input: List<String>): Long {
        var score: Long = 0
        val data = getData(input)

        for ((result, numbers) in data) {
            val operatorsAvailable = Operator.entries

            val x = numbers.first().toLong()
            if (canCalculate(0, x, Operator.ADD, numbers.subList(1, numbers.size), result, operatorsAvailable)) {
                score += result
            }
        }

        return score
    }

    val testInput = readInput("07", testData = true)
    val input = readInput("07")

    part1(input).println()
    part2(input).println()

    check(part1(testInput) == 3749.toLong())
    check(part2(testInput) == 11387.toLong())
    check(part1(input) == 12839601725877)
    check(part2(input) == 149956401519484)
}
