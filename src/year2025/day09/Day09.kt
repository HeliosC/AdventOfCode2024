package year2025.day09

import println
import readInput
import java.awt.BasicStroke
import java.awt.Color
import java.awt.Graphics
import java.awt.Graphics2D
import javax.swing.JFrame
import javax.swing.JPanel
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

data class VerticalBorder(
    val x: Int,
    val yMin: Int,
    val yMax: Int
)

data class HorizontalBorder(
    val y: Int,
    val xMin: Int,
    val xMax: Int
)

fun main() {
    fun part1(input: List<String>): Long {
        val redTiles: List<Pair<Long, Long>> = input.map { line -> line.split(',').let { it.first().toLong() to it.last().toLong() } }

        var maxArea = 0L
        redTiles.dropLast(1).forEachIndexed { index, tile1 ->
            redTiles.subList(index + 1, redTiles.size).forEach { tile2 ->
                val area = (abs(tile2.first - tile1.first) + 1) * (abs(tile2.second - tile1.second) + 1)
                if (area > maxArea) {
                    maxArea = area
                }
            }
        }

        return maxArea
    }

    fun part2(input: List<String>): Long {
        val redTiles: List<Pair<Int, Int>> = input.map { line -> line.split(',').let { it.first().toInt() to it.last().toInt() } }

        val verticalBorders = mutableSetOf<VerticalBorder>()
        val horizontalBorders = mutableSetOf<HorizontalBorder>()
        redTiles.forEachIndexed { index, tile1 ->
            val tile2 = redTiles.getOrElse(index + 1) { redTiles.first() }
            if (tile1.first == tile2.first) {
                verticalBorders.add(VerticalBorder(
                    x = tile1.first,
                    yMin = min(tile1.second, tile2.second),
                    yMax = max(tile1.second, tile2.second)
                ))
            } else if (tile1.second == tile2.second) {
                horizontalBorders.add(HorizontalBorder(
                    y = tile1.second,
                    xMin = min(tile1.first, tile2.first),
                    xMax = max(tile1.first, tile2.first)
                ))
            }
        }

        var maxArea = 0L
        val maxCorners: MutableList<Pair<Pair<Int, Int>, Pair<Int, Int>>> = mutableListOf() //only for the image
        redTiles.dropLast(1).forEachIndexed { index, tile1 ->
            redTiles.subList(index + 1, redTiles.size).forEach { tile2 ->
                if (tile1.first == tile2.first || tile1.second == tile2.second) {
                    return@forEach
                }

                val xMin = min(tile1.first, tile2.first) + 1
                val xMax = max(tile1.first, tile2.first) - 1
                val yMin = min(tile1.second, tile2.second) + 1
                val yMax = max(tile1.second, tile2.second) - 1

                if (
                    isInBorders(xMin, yMin, verticalBorders) &&
                    isInBorders(xMax, yMax, verticalBorders) &&

                    verticalBorders.none {
                        it.x in xMin..xMax && (yMin in it.yMin..it.yMax || yMax in it.yMin..it.yMax)
                    } &&
                    horizontalBorders.none {
                        it.y in yMin..yMax && (xMin in it.xMin..it.xMax || xMax in it.xMin..it.xMax)
                    }
                ) {
                    val area = (xMax - xMin + 3).toLong() * (yMax - yMin + 3).toLong()
                    if (area > maxArea) {
                        maxArea = area
                        maxCorners.add((xMin - 1 to yMin - 1) to (xMax + 1 to yMax + 1))
                    }
                }
            }
        }

        //drawImage(verticalBorders, horizontalBorders, maxCorners)

        return maxArea
    }

    val testInput = readInput("09", 2025, testData = true)
    val input = readInput("09", 2025)

    part1(input).println()
    part2(input).println()

    check(part1(testInput) == 50L)
    check(part2(testInput) == 24L)
    check(part1(input) == 4755429952)
    check(part2(input) == 1429596008L)
}

fun isInBorders(x: Int, y: Int, borders: Set<VerticalBorder>): Boolean {
    val bordersOfXY = borders.filter { border ->
        border.x <= x && y in border.yMin..border.yMax
    }

    return bordersOfXY.groupBy { border ->
        when (y) {
            border.yMin -> 1
            border.yMax -> -1
            in border.yMin..border.yMax -> 0
            else -> null
        }
    }.all {
        it.value.size % 2 == 1
    }
}

fun drawImage(
    verticalBorders: MutableSet<VerticalBorder>,
    horizontalBorders: MutableSet<HorizontalBorder>,
    maxCorners: List<Pair<Pair<Int, Int>, Pair<Int, Int>>>
) {
    val frame = JFrame("Day 09")
    frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
    frame.setSize(500, 500)
    frame.add(DrawPanel(verticalBorders, horizontalBorders, maxCorners))
    frame.isVisible = true
}

class DrawPanel(
    private val verticalBorders: MutableSet<VerticalBorder>,
    private val horizontalBorders: MutableSet<HorizontalBorder>,
    private val maxCorners: List<Pair<Pair<Int, Int>, Pair<Int, Int>>>
) : JPanel() {
    override fun paintComponent(gr: Graphics) {
        super.paintComponent(gr)

        val g = gr as Graphics2D

        g.color = Color.RED
        g.stroke = BasicStroke(2f)

        val ratio = height.toDouble() / 100_000

        for (border in verticalBorders) {
            g.drawLine((border.x*ratio).toInt(), (border.yMin*ratio).toInt(), (border.x*ratio).toInt(), (border.yMax*ratio).toInt())
        }

        for (border in horizontalBorders) {
            g.drawLine((border.xMin*ratio).toInt(), (border.y*ratio).toInt(), (border.xMax*ratio).toInt(), (border.y*ratio).toInt())
        }

        g.color = Color.BLUE
        g.stroke = BasicStroke(1f)


        for (maxCorner in maxCorners) {
            g.drawRect(
                (maxCorner.first.first*ratio).toInt(),
                (maxCorner.first.second*ratio).toInt(),
                ((maxCorner.second.first - maxCorner.first.first)*ratio).toInt(),
                ((maxCorner.second.second - maxCorner.first.second)*ratio).toInt()
            )
        }
    }
}
