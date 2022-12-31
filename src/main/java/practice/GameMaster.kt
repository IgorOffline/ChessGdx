package practice

import practice.board.*

data class GameMaster(val board: Board,
                      val legalMoves: LegalMoves,
                      var fromSquare: Square? = null,
                      var toSquare: Square? = null,
                      var whiteToMove: Boolean = true,
                      var whiteKingInCheck: Boolean = false,
                      var blackKingInCheck: Boolean = false) {

    fun moveAndCalculate() {
        if (legalMoves.legalMoves.containsKey(fromSquare)) {
            legalMoves.legalMoves[fromSquare]!!.forEach { pieceLegalMove ->
                if (toSquareEquals(pieceLegalMove)) {
                    moveAndCalculateInner()

                    return
                }
            }
        }
    }

    private fun toSquareEquals(square: Square): Boolean {
        return Square.letterNumberEqual(toSquare!!.letter, square.letter, toSquare!!.number, square.number)
    }

    private fun moveAndCalculateInner() {
        move()
        calculate()
    }

    fun move() {
        toSquare!!.piece = fromSquare!!.piece
        toSquare!!.pieceColor = fromSquare!!.pieceColor
        fromSquare!!.piece = Piece.NONE
        fromSquare!!.pieceColor = PieceColor.NONE
    }

    private fun calculate() {
        whiteToMove = !whiteToMove

        legalMoves.calculate(this)
    }
}