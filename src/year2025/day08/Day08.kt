package year2025.day08

import println
import readInput
import java.util.SortedSet
import kotlin.math.sqrt

data class Vector(val x: Int, val y: Int, val z: Int)

fun main() {
    fun part1(input: List<String>, junctions: Int): Int {
        val boxes = input.map { box ->
            val (x, y, z) = box.split(',') .map { it.toInt() }
            Vector(x, y, z)
        }

        val distances: SortedSet<Pair<Set<Vector>, Double>> = sortedSetOf(
            compareBy { it.second }
        )

        boxes.dropLast(1).forEachIndexed { box1Index, box1 ->
            boxes.subList(box1Index + 1, boxes.size).forEach { box2 ->
                distances.add(setOf(box1, box2) to distance(box1, box2))
            }
        }

        val shortenDistances: List<Pair<Set<Vector>, Double>> = distances.take(junctions)
        val circuits: MutableList<Set<Vector>> = shortenDistances.asSequence()
            .map { it.first }.flatten().toSet().map { setOf(it) }.toMutableList()

        shortenDistances.forEach { (boxes, _) ->
            val (box1, box2) = boxes.toList()
            val box1Circuit = circuits.indexOfFirst { box1 in it }
            val box2Circuit = circuits.indexOfFirst { box2 in it }

            if (box1Circuit != box2Circuit) {
                circuits[box1Circuit] = circuits[box1Circuit] + circuits[box2Circuit]
                circuits.removeAt(box2Circuit)
            }
        }

        return circuits.map { it.count() }.sortedDescending().take(3).reduce { acc, i -> acc * i }
    }

    fun part2(input: List<String>): Long {
        val boxes = input.map { box ->
            val (x, y, z) = box.split(',') .map { it.toInt() }
            Vector(x, y, z)
        }

        val distances: SortedSet<Pair<Set<Vector>, Double>> = sortedSetOf(
            compareBy { it.second }
        )

        boxes.dropLast(1).forEachIndexed { box1Index, box1 ->
            boxes.subList(box1Index + 1, boxes.size).forEach { box2 ->
                distances.add(setOf(box1, box2) to distance(box1, box2))
            }
        }

        val shortenDistances: List<Pair<Set<Vector>, Double>> = distances.toList()
        val circuits: MutableList<Set<Vector>> = shortenDistances.asSequence()
            .map { it.first }.flatten().toSet().map { setOf(it) }.toMutableList()

        shortenDistances.forEach { (boxes, _) ->
            val (box1, box2) = boxes.toList()
            val box1Circuit = circuits.indexOfFirst { box1 in it }
            val box2Circuit = circuits.indexOfFirst { box2 in it }

            if (box1Circuit != box2Circuit) {
                circuits[box1Circuit] = circuits[box1Circuit] + circuits[box2Circuit]
                circuits.removeAt(box2Circuit)
            }

            if (circuits.size == 1) {
                return box1.x.toLong() * box2.x
            }
        }

        return 0
    }

    val testInput = readInput("08", 2025, testData = true)
    val input = readInput("08", 2025)

    part1(input, junctions = 1000).println()
    part2(input).println()

    check(part1(testInput, junctions = 10) == 40)
    check(part2(testInput) == 25272L)
    check(part1(input, junctions = 1000) == 127551)
    check(part2(input) == 2347225200)
}

fun distance(v1: Vector, v2: Vector): Double =
    sqrt((v2.x - v1.x)*(v2.x - v1.x).toDouble() + (v2.y - v1.y)*(v2.y - v1.y).toDouble() + (v2.z - v1.z)*(v2.z - v1.z).toDouble())
