package practice.piece

import practice.board.*

class Rook {
    companion object {
        fun rookMoves(rookSquare: Square, board: Board) : List<Square> {

            val list = mutableListOf<Square>()

            list.addAll(findNextNumberSquare(rookSquare, board))
            list.addAll(findPreviousNumberSquare(rookSquare, board))

            return list
        }

        private fun findNextNumberSquare(rookSquare: Square, board: Board) : List<Square> {

            val moves = mutableListOf<Square>()

            var squareNullable : Square? = Square(rookSquare.letter, rookSquare.number, Piece.NONE, PieceColor.NONE)

            do {
                squareNullable = board.findNextNumberSquare(squareNullable!!.letter, squareNullable.number)
                squareNullable?.let {
                    moves.add(it)
                }
            } while (squareNullable != null)

            return moves
        }

        private fun findPreviousNumberSquare(rookSquare: Square, board: Board) : List<Square> {

            val moves = mutableListOf<Square>()

            var squareNullable : Square? = Square(rookSquare.letter, rookSquare.number, Piece.NONE, PieceColor.NONE)

            do {
                squareNullable = board.findPreviousNumberSquare(squareNullable!!.letter, squareNullable.number)
                squareNullable?.let {
                    moves.add(it)
                }
            } while (squareNullable != null)

            return moves
        }
    }
}