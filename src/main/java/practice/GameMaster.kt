package practice

import practice.board.*

data class GameMaster(val board: Board,
                      val legalMoves: LegalMoves,
                      var fromSquare: Square? = null,
                      var toSquare: Square? = null,
                      var whiteToMove: Boolean = true,
                      var whiteKingInCheck: Boolean = false,
                      var blackKingInCheck: Boolean = false) {

    fun movement() {
        legalMoves.legalMoves.keys.forEach { piece ->
            legalMoves.legalMoves[piece]!!.forEach { pieceLegalMove ->
                if (toSquareEquals(pieceLegalMove)) {
                    move()

                    return
                }
            }
        }
    }

    private fun toSquareEquals(square: Square): Boolean {
        return Square.letterNumberEqual(toSquare!!.letter, square.letter, toSquare!!.number, square.number)
    }

    private fun move() {
        toSquare!!.piece = fromSquare!!.piece
        toSquare!!.pieceColor = fromSquare!!.pieceColor
        fromSquare!!.piece = Piece.NONE
        fromSquare!!.pieceColor = PieceColor.NONE

        whiteToMove = !whiteToMove

        legalMoves.calculate(this)
    }
}