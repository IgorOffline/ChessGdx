package practice.board.ui

import java.lang.IllegalArgumentException

class LetterNumber {
    companion object {
        fun getLetter(letter: Int): String {
            return when (letter) {
                0 -> "A"
                1 -> "B"
                2 -> "C"
                3 -> "D"
                4 -> "E"
                5 -> "F"
                6 -> "G"
                7 -> "H"
                else -> throw IllegalArgumentException("Unknown letter")
            }
        }
        fun getNumber(number: Int): String {
            return when (number) {
                0 -> "1"
                1 -> "2"
                2 -> "3"
                3 -> "4"
                4 -> "5"
                5 -> "6"
                6 -> "7"
                7 -> "8"
                else -> throw IllegalArgumentException("Unknown number")
            }
        }
    }
}