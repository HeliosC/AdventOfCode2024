package day06

import println
import readInput

enum class Direction(val dx: Int, val dy: Int) {
    UP(0, -1),
    RIGHT(1 ,0),
    DOWN(0, 1),
    LEFT(-1, 0)
}

fun main() {
    fun getPositions(input: List<List<Char>>): MutableSet<Pair<Int, Int>> {
        val positions = mutableSetOf<Pair<Int, Int>>()

        val xMax = input.first().lastIndex
        val yMax = input.lastIndex

        var direction = Direction.UP
        var (x, y) = input.indexOfFirst { it.contains('^') }.let { input[it].indexOf('^') to it }
        //println("$x $y")

        do {
            while (x in 0..xMax && y in 0..yMax && input[y][x] != '#') {
                positions.add(x to y)
                //println(x to y)

                x += direction.dx
                y += direction.dy
            }

            if (x !in 0..xMax || y !in 0..yMax) {
                break
            } else {
                x -= direction.dx
                y -= direction.dy

                direction = Direction.entries.toTypedArray()[(Direction.entries.indexOf(direction) + 1) % 4]

                x += direction.dx
                y += direction.dy
            }
        } while (x in 0..xMax && y in 0..yMax)

        return positions
    }

    fun doesItExit(input: List<List<Char>>): Boolean {
        //for (i in input)
        //    println(i.joinToString())

        val positions = mutableSetOf<Pair<Pair<Int, Int>, Direction>>()

        val xMax = input.first().lastIndex
        val yMax = input.lastIndex

        var direction = Direction.UP
        var (x, y) = input.indexOfFirst { it.contains('^') }.let { input[it].indexOf('^') to it }
        //println("$x $y")


        do {
            while (x in 0..xMax && y in 0..yMax && input[y][x] != '#') {
                if (positions.contains((x to y) to direction)) {
                    return false
                } else {
                    positions.add((x to y) to direction)
                    //println(x to y)

                    x += direction.dx
                    y += direction.dy
                }
            }

            if (x !in 0..xMax || y !in 0..yMax) {
                return true
            } else {
                x -= direction.dx
                y -= direction.dy

                direction = Direction.entries.toTypedArray()[(Direction.entries.indexOf(direction) + 1) % 4]

                x += direction.dx
                y += direction.dy
            }
        } while (x in 0..xMax && y in 0..yMax)

        return true
    }

    fun part1(input: List<String>): Int {
        val inputTab = input.map { it.toList() }
        val positions = getPositions(inputTab)

        return positions.size
    }

    fun part2(input: List<String>): Int {
        var score = 0
        var (x0, y0) = input.indexOfFirst { it.contains('^') }.let { input[it].indexOf('^') to it }

        val inputTab = input.map { it.toMutableList() }
        val positions = getPositions(inputTab)

        for((x, y) in positions) {
            if (x to y == x0 to y0)
                continue

            val newInput = input.map { it.toMutableList() }
            newInput[y][x] = '#'
            if (doesItExit(newInput)) {

            } else {
                //println(x to y)
                score++
            }
        }

        return score
    }

    val testInput = readInput("06", testData = true)
    val input = readInput("06")

    part1(input).println()
    part2(input).println()

    check(part1(testInput) == 41)
    check(part2(testInput) == 6)
    check(part1(input) == 5534)
    check(part2(input) == 2262)
}
