package practice.board

data class Square(val letter: Letter, val number: Number, var piece: Piece, var pieceColor: PieceColor) {
    companion object {
        fun letterNumberEqual(letter1: Letter, letter2: Letter, number1: Number, number2: Number): Boolean {
            if (letter1 == letter2 && number1 == number2) {
                return true
            }

            return false
        }
    }
}
