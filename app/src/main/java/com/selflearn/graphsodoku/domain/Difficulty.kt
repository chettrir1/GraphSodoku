package com.selflearn.graphsodoku.domain

//enum class is useful for creating restricted set of values.
enum class Difficulty(val modifier: Double) {
    EASY(0.50),
    MEDIUM(0.44),
    HARD(0.38)
}