package practice.piece

import practice.GameMaster
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
    }
}