package practice.piece

import practice.board.*

class Rook {
    companion object {
        fun rookMoves(rookSquare: Square, board: Board) : List<Square> {

            val moves = mutableListOf<Square>()

            val squareNullable = board.findNextNumberSquare(rookSquare.letter, rookSquare.number)

            squareNullable?.let {
                moves.add(it)
            }

            return moves
        }
    }
}