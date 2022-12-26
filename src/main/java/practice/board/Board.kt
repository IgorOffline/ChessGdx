package practice.board

import practice.board.ui.LetterNumber

class Board(var board: List<Square>) {

    init {
        val filledSquares = listOf(
            Square(Letter.E, Number.N3, Piece.KING, PieceColor.WHITE),
            Square(Letter.E, Number.N6, Piece.KING, PieceColor.BLACK),
            Square(Letter.F, Number.N5, Piece.ROOK, PieceColor.BLACK))

        createBoard(filledSquares)
    }

    private fun createBoard(filledSquares: List<Square>) {

        val boardMutable = mutableListOf<Square>()

        for (j in 0..7) {
            for (i in 0..7) {
                val letter = LetterNumber.getLetterEnum(i)
                val number = LetterNumber.getNumberEnumReverse(j)
                var letterNumberInFilled = false
                for (filled in filledSquares) {
                    if (filled.letter == letter && filled.number == number) {
                        boardMutable.add(filled)
                        letterNumberInFilled = true
                        break
                    }
                }
                if (!letterNumberInFilled) {
                    val square = Square(letter, number, Piece.NONE, PieceColor.NONE)
                    boardMutable.add(square)
                }
            }
        }

        board = boardMutable

//        board.forEach {
//            println("${it.letter}${it.number}:${findSquare(it.letter, it.number)}")
//        }
    }

    fun squareFound(i: Int, j: Int, square: Square): Boolean {
        if (i == square.letter.index && j == square.number.index) {
            return true
        }
        return false
    }

    fun findSquare(letter: Letter, number: Number): Square {
        val index = (8 * (7 - number.index)) + letter.index
        return board[index]
    }
}