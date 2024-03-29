package practice.piece

import practice.board.Board
import practice.board.Piece
import practice.board.PieceColor
import practice.board.Square
import practice.board.LetterNumber

class King {
    companion object {

        fun kingMoves(kingSquare: Square, board: Board): List<Square> {

            val potentialMoves = kingMovesInner(kingSquare)

            val oppositeColor = if (kingSquare.pieceColor == PieceColor.WHITE) PieceColor.BLACK else PieceColor.WHITE

            var oppositeKingSquare: Square? = null

            board.board.forEach {
                if (it.piece == Piece.KING && it.pieceColor == oppositeColor) {
                    oppositeKingSquare = it
                    return@forEach
                }
            }

            val illegalMoves = kingMovesInner(oppositeKingSquare!!)

            potentialMoves.removeAll(illegalMoves)

            return potentialMoves
        }

        private fun kingMovesInner(kingSquare: Square): MutableList<Square> {

            val moves = mutableListOf<Square>()

            val letterIndexMinus = kingSquare.letter.index - 1
            val letterIndexPlus = kingSquare.letter.index + 1
            val numberIndexMinus = kingSquare.number.index - 1
            val numberIndexPlus = kingSquare.number.index + 1

            val letter = kingSquare.letter
            val letterMinus = LetterNumber.getLetterEnum(letterIndexMinus)
            val letterMinusLegal = LetterNumber.isEnumLegal(letterMinus)
            val letterPlus = LetterNumber.getLetterEnum(letterIndexPlus)
            val letterPlusLegal = LetterNumber.isEnumLegal(letterPlus)
            val number = kingSquare.number
            val numberMinus = LetterNumber.getNumberEnum(numberIndexMinus)
            val numberMinusLegal = LetterNumber.isEnumLegal(numberMinus)
            val numberPlus = LetterNumber.getNumberEnum(numberIndexPlus)
            val numberPlusLegal = LetterNumber.isEnumLegal(numberPlus)

            if (letterMinusLegal) {
                moves.add(Square(letterMinus, number, Piece.NONE, PieceColor.NONE))
                if (numberPlusLegal) {
                    moves.add(Square(letterMinus, numberPlus, Piece.NONE, PieceColor.NONE))
                }
                if (numberMinusLegal) {
                    moves.add(Square(letterMinus, numberMinus, Piece.NONE, PieceColor.NONE))
                }
            }
            if (letterPlusLegal) {
                moves.add(Square(letterPlus, number, Piece.NONE, PieceColor.NONE))
                if (numberPlusLegal) {
                    moves.add(Square(letterPlus, numberPlus, Piece.NONE, PieceColor.NONE))
                }
                if (numberMinusLegal) {
                    moves.add(Square(letterPlus, numberMinus, Piece.NONE, PieceColor.NONE))
                }
            }
            if (numberPlusLegal) {
                moves.add(Square(letter, numberPlus, Piece.NONE, PieceColor.NONE))
            }
            if (numberMinusLegal) {
                moves.add(Square(letter, numberMinus, Piece.NONE, PieceColor.NONE))
            }

            return moves
        }
    }
}
