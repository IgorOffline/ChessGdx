package practice.board

import practice.Messages
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
                else -> throw IllegalArgumentException(Messages.UNKNOWN_LETTER)
            }
        }
        fun getLetterEnum(letter: Int): Letter {
            return when (letter) {
                -1 -> Letter.L
                0 -> Letter.A
                1 -> Letter.B
                2 -> Letter.C
                3 -> Letter.D
                4 -> Letter.E
                5 -> Letter.F
                6 -> Letter.G
                7 -> Letter.H
                8 -> Letter.R
                else -> throw IllegalArgumentException(Messages.UNKNOWN_LETTER)
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
                else -> throw IllegalArgumentException(Messages.UNKNOWN_NUMBER)
            }
        }
        fun getNumberEnum(number: Int): Number2 {
            return when (number) {
                -1 -> Number2.NMinus1
                0 -> Number2.N1
                1 -> Number2.N2
                2 -> Number2.N3
                3 -> Number2.N4
                4 -> Number2.N5
                5 -> Number2.N6
                6 -> Number2.N7
                7 -> Number2.N8
                8 -> Number2.N99
                else -> throw IllegalArgumentException(Messages.UNKNOWN_NUMBER)
            }
        }

        fun getNumberEnumReverse(number: Int): Number2 {
            return when (number) {
                -1 -> Number2.N99
                0 -> Number2.N8
                1 -> Number2.N7
                2 -> Number2.N6
                3 -> Number2.N5
                4 -> Number2.N4
                5 -> Number2.N3
                6 -> Number2.N2
                7 -> Number2.N1
                8 -> Number2.NMinus1
                else -> throw IllegalArgumentException(Messages.UNKNOWN_NUMBER)
            }
        }

        fun isEnumLegal(letter: Letter): Boolean {
            return letter.index in 0..7
        }

        fun isEnumLegal(number: Number2): Boolean {
            return number.index in 0..7
        }
    }
}