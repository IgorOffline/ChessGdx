package practice.piece

import practice.board.*
import practice.piece.movement.*

class Rook {
    companion object {
        fun rookMoves(rookSquare: Square, board: Board) : MovementOpponentCheck {

            val list = mutableListOf<Square>()

            val movement1 = findSquare(rookSquare, board, RookMovement.NEXT_NUMBER)
            val movement2 = findSquare(rookSquare, board, RookMovement.PREVIOUS_NUMBER)
            val movement3 = findSquare(rookSquare, board, RookMovement.NEXT_LETTER)
            val movement4 = findSquare(rookSquare, board, RookMovement.PREVIOUS_LETTER)

            val movements = listOf(movement1, movement2, movement3, movement4)
            movements.forEach { list.addAll(it.squares) }
            val opponentsKingInCheck = movements.any { it.contact == Contact.OPPONENT_KING }

            return MovementOpponentCheck(list, opponentsKingInCheck)
        }

        private fun findSquare(rookSquare: Square, board: Board, rookMovement: RookMovement) : MovementContact {

            val moves = mutableListOf<Square>()

            val oppositePieceColor = if (rookSquare.pieceColor == PieceColor.WHITE) PieceColor.BLACK else PieceColor.WHITE

            var square : Square? = Square(rookSquare.letter, rookSquare.number, Piece.NONE, PieceColor.NONE)
            var contact = Contact.NONE

            do {
                square = when (rookMovement) {
                    RookMovement.NEXT_NUMBER -> board.findNextNumberSquare(square!!.letter, square.number)
                    RookMovement.PREVIOUS_NUMBER -> board.findPreviousNumberSquare(square!!.letter, square.number)
                    RookMovement.NEXT_LETTER -> board.findNextLetterSquare(square!!.letter, square.number)
                    RookMovement.PREVIOUS_LETTER -> board.findPreviousLetterSquare(square!!.letter, square.number)
                }
                square?.let {
                    if (square.piece == Piece.NONE) {
                        moves.add(it)
                    }
                    if (square.pieceColor == oppositePieceColor) {
                        contact = if (square.piece == Piece.KING) {
                            Contact.OPPONENT_KING
                        } else {
                            moves.add(it)
                            Contact.OPPONENT_NON_KING
                        }
                    }
                }
            } while (square != null && contact == Contact.NONE)

            return MovementContact(moves, contact)
        }
    }
}