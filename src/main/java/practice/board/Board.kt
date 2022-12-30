package practice.board

class Board(var board: List<Square>) {

    init {
        val filledSquares = listOf(
            Square(Letter.E, Number.N3, Piece.KING, PieceColor.WHITE),
            Square(Letter.F, Number.N2, Piece.ROOK, PieceColor.WHITE),
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

        println("--- findNextNumberSquare ---")
        board.forEach {
            println("${it.letter}${it.number}:${findNextNumberSquare(it.letter, it.number)}")
        }
        println("--- findPreviousNumberSquare ---")
        board.forEach {
            println("${it.letter}${it.number}:${findPreviousNumberSquare(it.letter, it.number)}")
        }
        println("--- findNextLetterSquare ---")
        board.forEach {
            println("${it.letter}${it.number}:${findNextLetterSquare(it.letter, it.number)}")
        }
        println("--- findPreviousLetterSquare ---")
        board.forEach {
            println("${it.letter}${it.number}:${findPreviousLetterSquare(it.letter, it.number)}")
        }
        println("--- ---")
    }

    fun squareFound(i: Int, j: Int, square: Square): Boolean {
        if (i == square.letter.index && j == square.number.index) {
            return true
        }
        return false
    }

    fun findNextNumberSquare(letter: Letter, number: Number): Square? {
        val nextNumberIndex = number.index + 1
        if (LetterNumber.isEnumLegal(LetterNumber.getNumberEnum(nextNumberIndex))) {
            val squareIndex = (8 * (7 - nextNumberIndex)) + letter.index
            return board[squareIndex]
        }

        return null
    }

    fun findPreviousNumberSquare(letter: Letter, number: Number): Square? {
        val previousNumberIndex = number.index - 1
        if (LetterNumber.isEnumLegal(LetterNumber.getNumberEnum(previousNumberIndex))) {
            val squareIndex = (8 * (7 - previousNumberIndex)) + letter.index
            return board[squareIndex]
        }

        return null
    }

    fun findNextLetterSquare(letter: Letter, number: Number): Square? {
        val nextLetterIndex = letter.index + 1
        if (LetterNumber.isEnumLegal(LetterNumber.getLetterEnum(nextLetterIndex))) {
            val squareIndex = (8 * (7 - number.index)) + nextLetterIndex
            return board[squareIndex]
        }

        return null
    }

    fun findPreviousLetterSquare(letter: Letter, number: Number): Square? {
        val previousLetterIndex = letter.index - 1
        if (LetterNumber.isEnumLegal(LetterNumber.getLetterEnum(previousLetterIndex))) {
            val squareIndex = (8 * (7 - number.index)) + previousLetterIndex
            return board[squareIndex]
        }

        return null
    }
}