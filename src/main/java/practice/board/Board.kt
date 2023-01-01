package practice.board

class Board(var board: List<Square>) {

    companion object {
        fun defaultSquares(): List<Square> {
//            return listOf(
//                Square(Letter.E, Number2.N3, Piece.KING, PieceColor.WHITE),
//                Square(Letter.F, Number2.N2, Piece.ROOK, PieceColor.WHITE),
//                Square(Letter.E, Number2.N6, Piece.KING, PieceColor.BLACK),
//                Square(Letter.C, Number2.N5, Piece.ROOK, PieceColor.BLACK)
//            )
            return listOf(
                Square(Letter.F, Number2.N4, Piece.KING, PieceColor.WHITE),
                Square(Letter.F, Number2.N2, Piece.ROOK, PieceColor.WHITE),
                Square(Letter.E, Number2.N6, Piece.KING, PieceColor.BLACK),
                Square(Letter.C, Number2.N1, Piece.ROOK, PieceColor.BLACK)
            )
        }
    }

    fun createBoard(filledSquares: List<Square>) {

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

    fun findNextNumberSquare(letter: Letter, number: Number2): Square? {
        val nextNumberIndex = number.index + 1
        if (LetterNumber.isEnumLegal(LetterNumber.getNumberEnum(nextNumberIndex))) {
            val squareIndex = (8 * (7 - nextNumberIndex)) + letter.index
            return board[squareIndex]
        }

        return null
    }

    fun findPreviousNumberSquare(letter: Letter, number: Number2): Square? {
        val previousNumberIndex = number.index - 1
        if (LetterNumber.isEnumLegal(LetterNumber.getNumberEnum(previousNumberIndex))) {
            val squareIndex = (8 * (7 - previousNumberIndex)) + letter.index
            return board[squareIndex]
        }

        return null
    }

    fun findNextLetterSquare(letter: Letter, number: Number2): Square? {
        val nextLetterIndex = letter.index + 1
        if (LetterNumber.isEnumLegal(LetterNumber.getLetterEnum(nextLetterIndex))) {
            val squareIndex = (8 * (7 - number.index)) + nextLetterIndex
            return board[squareIndex]
        }

        return null
    }

    fun findPreviousLetterSquare(letter: Letter, number: Number2): Square? {
        val previousLetterIndex = letter.index - 1
        if (LetterNumber.isEnumLegal(LetterNumber.getLetterEnum(previousLetterIndex))) {
            val squareIndex = (8 * (7 - number.index)) + previousLetterIndex
            return board[squareIndex]
        }

        return null
    }

    fun deepCopy(): Board {
        val list = mutableListOf<Square>()
        for (i in 0 until this.board.size) {
            val oldSquare = this.board[i]
            list.add(oldSquare.copy())
        }
        return Board(list)
    }
}