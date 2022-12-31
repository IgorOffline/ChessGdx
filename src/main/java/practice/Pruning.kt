package practice

import practice.board.Piece
import practice.board.PieceColor
import practice.piece.Rook

class Pruning {

    companion object {

        fun prune(gameMaster: GameMaster, pieceColor: PieceColor) {

            val oppositePieceColor = if (pieceColor == PieceColor.WHITE) PieceColor.BLACK else PieceColor.WHITE

            gameMaster.board.board.forEach { square ->
                if (square.pieceColor == oppositePieceColor && square.piece == Piece.ROOK) {
                    val rookMoves = Rook.rookMoves(square, gameMaster.board)
                    if (rookMoves.opponentsKingInCheck) {
                        kingStillInCheck(gameMaster, pieceColor)
                    }
                }
            }
        }

        private fun kingStillInCheck(gameMaster: GameMaster, pieceColor: PieceColor) {
            when (pieceColor) {
                PieceColor.WHITE -> {
                    gameMaster.whiteKingInCheck = true
                }
                PieceColor.BLACK -> {
                    gameMaster.blackKingInCheck = true
                }
                else -> {
                    throw IllegalArgumentException(Messages.UNKNOWN_PIECE_COLOR)
                }
            }
        }
    }
}