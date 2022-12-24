package practice

import practice.board.Piece
import practice.board.PieceColor
import practice.board.Square
import practice.piece.King
import practice.piece.Rook

data class LegalMoves(var legalMoves: Map<Square, List<Square>>) {

    fun calculate(gameMaster: GameMaster) {

        val pieceColor = if (gameMaster.whiteToMove) PieceColor.WHITE else PieceColor.BLACK
        val oppositePieceColor = if (gameMaster.whiteToMove) PieceColor.BLACK else PieceColor.WHITE

        var king: Square? = null

        gameMaster.board.board.forEach {
            if (it.piece == Piece.KING && it.pieceColor == pieceColor) {
                king = it
                return@forEach
            }
        }

        var kingLegalMoves = King.kingMoves(king!!, gameMaster.board)
        kingLegalMoves = kingLegalMoves.toMutableList()

        gameMaster.board.board.forEach { boardSquare ->
            if (boardSquare.piece == Piece.ROOK && boardSquare.pieceColor == oppositePieceColor) {
                val oppositeRookMoves = Rook.rookMoves(boardSquare)
                kingLegalMoves.removeAll(oppositeRookMoves)
            }
        }

        legalMoves = mapOf(king!! to kingLegalMoves)

        var legalMovesToStr = "$pieceColor: "

        legalMoves.keys.forEach {
            legalMovesToStr += "${legalMoves[it]!!.size}, "
        }

        println("$legalMovesToStr")
    }
}
