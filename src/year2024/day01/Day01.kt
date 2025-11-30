package year2024.day01

import println
import readInput
import kotlin.math.abs

fun main() {
    fun part1(input: List<String>): Int {
        val list1 = mutableListOf<Int>()
        val list2 = mutableListOf<Int>()
        input.forEach { line ->
            val (e1, e2) = line.split("   ")
            list1.add(e1.toInt())
            list2.add(e2.toInt())
        }

        list1.sort()
        list2.sort()

        return list1.mapIndexed { index, e1 ->
            abs(e1 - list2[index])
        }.sum()
    }

    fun part2(input: List<String>): Int {
        val map1 = mutableMapOf<Int, Int>()
        val map2 = mutableMapOf<Int, Int>()

        input.forEach { line ->
            val (e1, e2) = line.split("   ").map { it.toInt() }
            map1[e1] = map1[e1]?.plus(1) ?: 1
            map2[e2] = map2[e2]?.plus(1) ?: 1
        }

        var score = 0
        map1.forEach { (key, value) ->
            score += map2.getOrDefault(key, 0) * key * value
        }

        return score
    }

    val testInput = readInput("01", 2024, testData = true)
    val input = readInput("01", 2024)

    part1(input).println()
    part2(input).println()

    check(part1(testInput) == 11)
    check(part2(testInput) == 31)
}
