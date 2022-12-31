package practice.board

data class Square(val letter: Letter, val number: Number2, var piece: Piece, var pieceColor: PieceColor) {
    companion object {
        fun letterNumberEqual(letter1: Letter, letter2: Letter, number1: Number2, number2: Number2): Boolean {
            if (letter1 == letter2 && number1 == number2) {
                return true
            }

            return false
        }
    }
}
