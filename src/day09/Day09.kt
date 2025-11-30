package day09

import println
import readInput

fun main() {
    fun part1(input: List<String>): Long {
        var score = 0L
        val disk = input.single().map { it.digitToInt() }
        val diskImage = mutableListOf<Int?>()

        var isFile = true
        var fileID = 0
        for (slot in disk) {
            for (i in 1..slot) {
                diskImage.add(
                    if (isFile) {
                        fileID
                    } else {
                        null
                    }
                )
            }

            if(!isFile) {
                fileID++
            }
            isFile = !isFile
        }

        var i = 0
        var iToMove = diskImage.lastIndex
        while (i < iToMove) { //sometimes < or <=, magic
            val slot = diskImage[i]
            if (slot != null) {
                score += i * slot
            } else {
                while (diskImage[iToMove] == null) {
                    iToMove--
                }
                score += i * diskImage[iToMove]!!
                diskImage[iToMove] = null
                iToMove--
            }
            i++
        }

        return score
    }

    fun part2(input: List<String>): Long {
        var score = 0L
        val disk = input.single().map { it.digitToInt() }
        val diskImage = mutableListOf<Int?>()

        var isFile = true
        var fileID = 0
        var iDiskImage = 0
        val files = sortedMapOf<Int, Pair<Int, Int>>({ t, t2 -> t2.compareTo(t) })
        val spaces = mutableMapOf<Int, Int>()

        for (slot in disk) {
            if (isFile) {
                if (slot != 0)
                    files[iDiskImage] = slot to fileID
            } else {
                if (slot != 0)
                    spaces[iDiskImage] = slot
                fileID++
            }
            iDiskImage += slot

            isFile = !isFile
        }

        //println(files.toList())
        //println(spaces.toList())

        //var fileToMoveId = files.maxOf { it.value.second }
        //while (fileToMoveId > 0) {
        for ((indexFile, fileData) in files.toSortedMap { t1, t2 -> t2.compareTo(t1) }) {
            //val (indexFile, fileData) = files.toList().find { it.second.second == fileToMoveId }!!
            //fileToMoveId--
            val (sizeFile, idFile) = fileData

            for ((indexSpace, sizeSpace) in spaces.toSortedMap().also {
                //println(it.toList())
            }) {
                //val (indexSpace, sizeSpace) = spaces[iSpace]
                if (indexSpace > indexFile)
                    continue

                if (sizeSpace >= sizeFile) {
                    //println("$idFile -> $indexSpace")
                    files.remove(indexFile)
                    files[indexSpace] = sizeFile to idFile
                    spaces[indexFile] = sizeFile
                    spaces.remove(indexSpace)
                    spaces[indexSpace + sizeFile] = sizeSpace - sizeFile

                    //merge spaces
                    for ((indexSpace, sizeSpace) in spaces.toSortedMap()) {
                        var currentIndexSpace = indexSpace
                        var currentSizeSpace = sizeSpace

                        var newSize = sizeSpace
                        val spacesToRemove = mutableListOf<Int>()
                        while (spaces.containsKey(currentIndexSpace + currentSizeSpace)) {
                            currentIndexSpace += currentSizeSpace
                            currentSizeSpace = spaces[currentIndexSpace]
                            spacesToRemove.add(currentIndexSpace)
                            newSize += currentSizeSpace

                        spaces[indexSpace] = newSize
                        //println("space to remove $spacesToRemove")
                        for (space in spacesToRemove)
                            spaces.remove(space)
                        }
                    }
                    break
                }
            }
        }

        for ((indexFile, dataFile) in files.toSortedMap()) {
            val (sizeFile, idFile) = dataFile
            score += idFile * (indexFile until (indexFile + sizeFile)).sum()
        }

        //println(files)
        //println(spaces.toSortedMap())
        return score
    }

    val testInput = readInput("09", testData = true)
    val input = readInput("09")

    //part1(input).println() //6201390809186 too high
    part2(input).println()

    //check(part1(testInput) == 1928L)
    check(part2(testInput) == 2858L)
    check(part1(input) == 6201130364722)
    check(part2(input) == 5027661887314L) //5027661887314 too low
}
