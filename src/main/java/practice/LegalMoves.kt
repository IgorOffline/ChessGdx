package practice

import practice.board.Piece
import practice.board.PieceColor
import practice.board.Square
import practice.piece.King

data class LegalMoves(var legalMoves: Map<Square, List<Square>>) {

    fun calculate(gameMaster: GameMaster) {

        val pieceColor = if (gameMaster.whiteToMove) PieceColor.WHITE else PieceColor.BLACK

        var king: Square? = null

        gameMaster.board.board.forEach {
            if (it.piece == Piece.KING && it.pieceColor == pieceColor) {
                king = it
                return@forEach
            }
        }

        val kingLegalMoves = King.kingMoves(king!!, gameMaster.board)

        legalMoves = mapOf(king!! to kingLegalMoves)

        var legalMovesToStr = "$pieceColor: "

        legalMoves.keys.forEach {
            legalMovesToStr += "${legalMoves[it]!!.size}, "
        }

        println("$legalMovesToStr")
    }
}
