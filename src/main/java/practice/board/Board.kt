package practice.board

class Board(val board: List<Square> = listOf(Square(Letter.E, Number.N3, Piece.KING, PieceColor.WHITE))) {

    fun squareFound(i: Int, j: Int, square: Square): Boolean {
        if (i == square.letter.index && j == square.number.index) {
            return true
        }
        return false
    }
}