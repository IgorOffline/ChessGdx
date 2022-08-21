package practice

import practice.board.Letter
import practice.board.Number
import practice.board.Square

data class GameMaster(var fromSquare: Square? = null,
                      var toSquare: Square? = null,
                      var whiteToMove: Boolean = true,
                      var whiteKingInCheck: Boolean = false,
                      var blackKingInCheck: Boolean = false) {

    fun equalLetterNumber(letter: Letter, number: Number): Boolean {
        return Square.letterNumberEqual(toSquare!!.letter, letter, toSquare!!.number, number)
    }
}