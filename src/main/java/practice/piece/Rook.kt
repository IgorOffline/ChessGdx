package practice.piece

import practice.board.Number
import practice.board.Piece
import practice.board.PieceColor
import practice.board.Square
import practice.board.ui.LetterNumber

class Rook {
    companion object {
        fun rookMoves(rookSquare: Square) : List<Square> {

            val moves = mutableListOf<Square>()

            for (i in rookSquare.number.index - 1 downTo practice.board.Number.N1.index) {
                moves.add(Square(rookSquare.letter, LetterNumber.getNumberEnum(i), Piece.NONE, PieceColor.NONE))
            }

            return moves
        }
    }
}