package practice.piece

import practice.board.*

class Rook {
    companion object {
        fun rookMoves(rookSquare: Square, board: Board) : List<Square> {

            val list = mutableListOf<Square>()

            list.addAll(findSquare(rookSquare, board, RookMovement.NEXT_NUMBER))
            list.addAll(findSquare(rookSquare, board, RookMovement.PREVIOUS_NUMBER))
            list.addAll(findSquare(rookSquare, board, RookMovement.NEXT_LETTER))
            list.addAll(findSquare(rookSquare, board, RookMovement.PREVIOUS_LETTER))

            return list
        }

        private fun findSquare(rookSquare: Square, board: Board, rookMovement: RookMovement) : List<Square> {

            val moves = mutableListOf<Square>()

            var square : Square? = Square(rookSquare.letter, rookSquare.number, Piece.NONE, PieceColor.NONE)

            do {
                square = when (rookMovement) {
                    RookMovement.NEXT_NUMBER -> board.findNextNumberSquare(square!!.letter, square.number)
                    RookMovement.PREVIOUS_NUMBER -> board.findPreviousNumberSquare(square!!.letter, square.number)
                    RookMovement.NEXT_LETTER -> board.findNextLetterSquare(square!!.letter, square.number)
                    RookMovement.PREVIOUS_LETTER -> board.findPreviousLetterSquare(square!!.letter, square.number)
                }
                square?.let {
                    moves.add(it)
                }
            } while (square != null)

            return moves
        }
    }
}