package practice

import practice.board.*
import practice.board.Number
import practice.piece.King

data class GameMaster(val board: Board,
                      var fromSquare: Square? = null,
                      var toSquare: Square? = null,
                      var whiteToMove: Boolean = true,
                      var whiteKingInCheck: Boolean = false,
                      var blackKingInCheck: Boolean = false,
                      var blackKingLegalMoves: Int = 0) {

    fun equalLetterNumber(letter: Letter, number: Number): Boolean {
        return Square.letterNumberEqual(toSquare!!.letter, letter, toSquare!!.number, number)
    }

    fun movement() {
        val pieceColorToMove = if (whiteToMove) PieceColor.WHITE else PieceColor.BLACK
        if (fromSquare!!.piece == Piece.KING && fromSquare!!.pieceColor == pieceColorToMove) {
            val squares = King.kingMoves(fromSquare!!, board)
            squares.forEach {
                if (equalLetterNumber(it.letter, it.number)) {
                    toSquare!!.piece = fromSquare!!.piece
                    toSquare!!.pieceColor = pieceColorToMove
                    fromSquare!!.piece = Piece.NONE
                    fromSquare!!.pieceColor = PieceColor.NONE
                    whiteToMove = !whiteToMove
                    return@forEach
                }
            }
        }
    }
}