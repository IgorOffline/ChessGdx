package practice

import practice.board.PieceColor

class Pruning {

    companion object {

        fun prune(gameMaster: GameMaster, pieceColor: PieceColor) {

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