package practice.board

import practice.board.ui.LetterNumber

class Board(val board: MutableList<Square> = mutableListOf()) {

    init {
        val filledSquares = listOf(
            Square(Letter.E, Number.N3, Piece.KING, PieceColor.WHITE),
            Square(Letter.E, Number.N6, Piece.KING, PieceColor.BLACK),
            Square(Letter.B, Number.N3, Piece.ROOK, PieceColor.WHITE),
            Square(Letter.B, Number.N7, Piece.ROOK, PieceColor.BLACK))

        filledSquares(filledSquares)
    }

    fun squareFound(i: Int, j: Int, square: Square): Boolean {
        if (i == square.letter.index && j == square.number.index) {
            return true
        }
        return false
    }

    private fun filledSquares(filledSquares: List<Square>) {
        for (i in 0..7) {
            for (j in 0..7) {
                val letter = LetterNumber.getLetterEnum(i)
                val number = LetterNumber.getNumberEnumReverse(j)
                val square = Square(letter, number, Piece.NONE, PieceColor.NONE)
                board.add(square)
            }
        }

        for (filled in filledSquares) {
            val letter = filled.letter
            val number = filled.number
            board.removeIf { sq -> sq.letter == letter && sq.number == number }
        }

        for (filled in filledSquares) {
            board.add(filled)
        }
    }
}