package practice.piece

import practice.GameMaster
import practice.board.Piece
import practice.board.PieceColor
import practice.board.Square
import practice.board.ui.LetterNumber

class King {
    companion object {
        fun kingMovement(gameMaster: GameMaster, square: Square): Boolean {

            val letterIndexMinus = square.letter.index - 1
            val letterIndexPlus = square.letter.index + 1
            val numberIndexMinus = square.number.index - 1
            val numberIndexPlus = square.number.index + 1

            val letter = square.letter
            val letterMinus = LetterNumber.getLetterEnum(letterIndexMinus)
            val letterPlus = LetterNumber.getLetterEnum(letterIndexPlus)
            val number = square.number
            val numberMinus = LetterNumber.getNumberEnum(numberIndexMinus)
            val numberPlus = LetterNumber.getNumberEnum(numberIndexPlus)

            val equal1 = gameMaster.equalLetterNumber(letterMinus, number)
            val equal2 = gameMaster.equalLetterNumber(letterMinus, numberPlus)
            val equal3 = gameMaster.equalLetterNumber(letterMinus, numberMinus)

            val equal4 = gameMaster.equalLetterNumber(letterPlus, number)
            val equal5 = gameMaster.equalLetterNumber(letterPlus, numberPlus)
            val equal6 = gameMaster.equalLetterNumber(letterPlus, numberMinus)

            val equalTop = gameMaster.equalLetterNumber(letter, numberPlus)
            val equalBottom = gameMaster.equalLetterNumber(letter, numberMinus)

            if (equal1 || equal2 || equal3 || equal4 || equal5 || equal6 || equalTop || equalBottom) {
                return true
            }

            return false
        }

        fun kingMoves(kingSquare: Square): List<Square> {

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
