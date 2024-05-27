package com.selflearn.graphsudoku.domain

import java.io.Serializable
import java.util.LinkedList
import kotlin.collections.LinkedHashMap


data class SudokuPuzzle(
    val boundary: Int,
    val difficulty: Difficulty,
    val graph: LinkedHashMap<Int, LinkedList<SudokuNode>>,
//    = buildNewSudoku(boundary, difficulty).graph,
    val elapsedTime: Long = 0L
) : Serializable {
    fun getValue(): LinkedHashMap<Int, LinkedList<SudokuNode>> = graph
}
