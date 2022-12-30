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

        val kingLegalMoves = King.kingMoves(king!!, gameMaster.board).toMutableList()

        val kingAndRooksLegalMoves = mutableMapOf<Square, List<Square>>()

        gameMaster.board.board.forEach { boardSquare ->
            if (boardSquare.piece == Piece.ROOK && boardSquare.pieceColor == pieceColor) {
                val rookMoves = Rook.rookMoves(boardSquare, gameMaster.board)
                kingAndRooksLegalMoves[boardSquare] = rookMoves
                kingLegalMoves.removeIf { it.letter == boardSquare.letter && it.number == boardSquare.number }
            } else if (boardSquare.piece == Piece.ROOK && boardSquare.pieceColor == oppositePieceColor) {
                val oppositeRookMoves = Rook.rookMoves(boardSquare, gameMaster.board)
                kingLegalMoves.removeAll(oppositeRookMoves)
            }
        }

        kingAndRooksLegalMoves[king!!] = kingLegalMoves

        legalMoves = kingAndRooksLegalMoves

        var legalMovesToStr = "$pieceColor: "

        legalMoves.keys.forEach {
            legalMovesToStr += "${legalMoves[it]!!.size}, "
        }

        println(legalMovesToStr)
    }
}
