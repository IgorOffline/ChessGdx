package practice

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.InputAdapter
import practice.board.*
import practice.board.Number
import practice.board.ui.LetterNumber
import java.lang.IllegalArgumentException

class MyClickListener(val board: Board, val gameMaster: GameMaster) : InputAdapter() {

    private var dragDrop = 0L
    private var lastDragDrop = 0L

    private val clickable = mutableListOf<Heading>()

    init {
        fillClickable(clickable)
    }

    private fun fillClickable(clickable: MutableList<Heading>) {
        for (i in 0..8) {
            var drawx = RenderUtil.X_OFFSET + 50F * i.toFloat()
            drawx += 8F
            clickable.add(Heading(drawx.toInt(), mutableListOf()))
            for (j in 1..9) {
                var drawy = RenderUtil.Y_OFFSET + 50F * j.toFloat()
                drawy -= 8F
                drawy += 16F
                clickable[i].body.add(drawy.toInt())
            }
        }
    }

    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        if (button == Input.Buttons.LEFT) {
            onTouch(false)
        }
        return true
    }

    override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        if (lastDragDrop < dragDrop) {
            onTouch(true)
        }
        lastDragDrop = dragDrop
        return true
    }

    private fun onTouch(moveToSquare: Boolean) {
        for (i in 0..7) {
            for (j in 0..7) {
                moveLogic(i, j, moveToSquare)
            }
        }
    }

    override fun touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean {
        ++dragDrop
        return true
    }

    private fun moveLogic(i: Int, j: Int, moveToSquare: Boolean) {
        val xBetween = Gdx.input.x > clickable[i].head && Gdx.input.x < clickable[i + 1].head
        val yBetween = Gdx.input.y > clickable[i].body[j] && Gdx.input.y < clickable[i].body[j + 1]
        if (xBetween && yBetween) {
            for (square in board.board) {
                between(i, j, square, moveToSquare)
            }
        }
    }

    private fun between(i: Int, j: Int, square: Square, moveToSquare: Boolean) {
        val letter = LetterNumber.getLetterEnum(i)
        val number = LetterNumber.getNumberEnumReverse(j)
        val letterOk = square.letter == letter
        val numberOk = square.number == number
        if (letterOk && numberOk) {
            if (moveToSquare) {
                gameMaster.toSquare = square
                if (squareNotFriendly() && squareMoveable() && squareLegal()) {

                    val toSquareOriginalPiece = gameMaster.toSquare!!.piece
                    val toSquareOriginalPieceColor = gameMaster.toSquare!!.pieceColor
                    val fromSquareOriginalPiece = gameMaster.fromSquare!!.piece
                    val fromSquareOriginalPieceColor = gameMaster.fromSquare!!.pieceColor

                    gameMaster.toSquare!!.piece = gameMaster.fromSquare!!.piece
                    gameMaster.toSquare!!.pieceColor = gameMaster.fromSquare!!.pieceColor
                    gameMaster.fromSquare!!.piece = Piece.NONE
                    gameMaster.fromSquare!!.pieceColor = PieceColor.NONE

                    if (!gameMaster.whiteToMove) {
                        isBlackKingInCheck()
                    }

                    if (gameMaster.blackKingInCheck) {
                        // revert
                        gameMaster.toSquare!!.piece = toSquareOriginalPiece
                        gameMaster.toSquare!!.pieceColor = toSquareOriginalPieceColor
                        gameMaster.fromSquare!!.piece = fromSquareOriginalPiece
                        gameMaster.fromSquare!!.pieceColor = fromSquareOriginalPieceColor

                        gameMaster.whiteToMove = !gameMaster.whiteToMove
                    }

                    gameMaster.whiteToMove = !gameMaster.whiteToMove
                }
            } else {
                gameMaster.fromSquare = square
            }
        }
    }

    private fun squareNotFriendly(): Boolean {

        val pieceColor = if (gameMaster.whiteToMove) PieceColor.WHITE else PieceColor.BLACK

        if (gameMaster.toSquare!!.pieceColor == pieceColor) {
            return false
        }

        return true
    }

    private fun squareMoveable(): Boolean {
        val pieceColor = if (gameMaster.whiteToMove) PieceColor.WHITE else PieceColor.BLACK

        if (gameMaster.fromSquare!!.pieceColor != pieceColor) {
            return false
        }

        val square = gameMaster.fromSquare!!

        return when (square.piece) {
            Piece.KING -> kingMovement(square)
            Piece.ROOK -> rookMovement(square)
            Piece.NONE -> throw IllegalArgumentException("Unknown fromSquare Piece")
        }
    }

    private fun squareLegal(): Boolean {

        if (gameMaster.fromSquare!!.piece == Piece.KING) {

            val pieceColor = if (gameMaster.whiteToMove) PieceColor.BLACK else PieceColor.WHITE

            board.board.forEach {
                if (it.piece == Piece.KING && it.pieceColor == pieceColor) {

                    val kingMovement = kingMovement(it)
                    val reverse = !kingMovement

                    return reverse
                }
            }
        }

        return true
    }

    private fun isBlackKingInCheck() {

        gameMaster.blackKingInCheck = false

        var sqBlackKing: Square? = null

        board.board.forEach board@ {
            if (it.piece == Piece.KING && it.pieceColor == PieceColor.BLACK) {
                sqBlackKing = it
                return@board
            }
        }

        val whiteRookChecksBlackKingTop = whiteRookChecksBlackKingTop(sqBlackKing!!)
        val whiteRookChecksBlackKingBottom = whiteRookChecksBlackKingBottom(sqBlackKing!!)
        val whiteRookChecksBlackKingLeft = whiteRookChecksBlackKingLeft(sqBlackKing!!)
        val whiteRookChecksBlackKingRight = whiteRookChecksBlackKingRight(sqBlackKing!!)

        gameMaster.blackKingInCheck = whiteRookChecksBlackKingTop || whiteRookChecksBlackKingBottom || whiteRookChecksBlackKingLeft || whiteRookChecksBlackKingRight
    }

    private fun whiteRookChecksBlackKingTop(sqBlackKing: Square): Boolean {
        for (i in sqBlackKing.number.index - 1 downTo 0) {
            board.board.forEach board@ {
                val letter = sqBlackKing.letter
                val number = LetterNumber.getNumberEnum(i)
                if (it.letter == letter && it.number == number) {
                    if (it.piece == Piece.ROOK && it.pieceColor == PieceColor.WHITE) {
                        return true
                    } else if (it.piece == Piece.ROOK || it.piece == Piece.KING) {
                        return false
                    }
                }
            }
        }

        return false
    }

    private fun whiteRookChecksBlackKingBottom(sqBlackKing: Square): Boolean {
        for (i in sqBlackKing.number.index + 1 .. 7) {
            board.board.forEach board@ {
                val letter = sqBlackKing.letter
                val number = LetterNumber.getNumberEnum(i)
                if (it.letter == letter && it.number == number) {
                    if (it.piece == Piece.ROOK && it.pieceColor == PieceColor.WHITE) {
                        return true
                    } else if (it.piece == Piece.ROOK || it.piece == Piece.KING) {
                        return false
                    }
                }
            }
        }

        return false
    }

    private fun whiteRookChecksBlackKingLeft(sqBlackKing: Square): Boolean {
        for (i in sqBlackKing.letter.index - 1 downTo 0) {
            board.board.forEach board@ {
                val letter = LetterNumber.getLetterEnum(i)
                val number = sqBlackKing.number
                if (it.letter == letter && it.number == number) {
                    if (it.piece == Piece.ROOK && it.pieceColor == PieceColor.WHITE) {
                        return true
                    } else if (it.piece == Piece.ROOK || it.piece == Piece.KING) {
                        return false
                    }
                }
            }
        }

        return false
    }

    private fun whiteRookChecksBlackKingRight(sqBlackKing: Square): Boolean {
        for (i in sqBlackKing.letter.index + 1 .. 7) {
            board.board.forEach board@ {
                val letter = LetterNumber.getLetterEnum(i)
                val number = sqBlackKing.number
                if (it.letter == letter && it.number == number) {
                    if (it.piece == Piece.ROOK && it.pieceColor == PieceColor.WHITE) {
                        return true
                    } else if (it.piece == Piece.ROOK || it.piece == Piece.KING) {
                        return false
                    }
                }
            }
        }

        return false
    }

    private fun kingMovement(square: Square): Boolean {

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

        val equal1 = equalLetterNumber(letterMinus, number)
        val equal2 = equalLetterNumber(letterMinus, numberPlus)
        val equal3 = equalLetterNumber(letterMinus, numberMinus)

        val equal4 = equalLetterNumber(letterPlus, number)
        val equal5 = equalLetterNumber(letterPlus, numberPlus)
        val equal6 = equalLetterNumber(letterPlus, numberMinus)

        val equalTop = equalLetterNumber(letter, numberPlus)
        val equalBottom = equalLetterNumber(letter, numberMinus)

        if (equal1 || equal2 || equal3 || equal4 || equal5 || equal6 || equalTop || equalBottom) {
            return true
        }

        return false
    }

    private fun rookMovement(square: Square) : Boolean {
        return rookMovementTop(square) ||
                rookMovementBottom(square) ||
                rookMovementLeft(square) ||
                rookMovementRight(square)
    }

    private fun rookMovementTop(square: Square) : Boolean {

        val pieceColorFriendly = if (gameMaster.whiteToMove) PieceColor.WHITE else PieceColor.BLACK
        val pieceColorEnemy = if (gameMaster.whiteToMove) PieceColor.BLACK else PieceColor.WHITE

        var breakEnemy = false

        for (i in square.number.index + 1 .. 7) {
            val letter = square.letter
            val number = LetterNumber.getNumberEnum(i)
            board.board.forEach board@ {
                if (it.letter == letter && it.number == number) {
                    if (it.pieceColor == pieceColorFriendly) {
                        return false
                    } else if (it.pieceColor == pieceColorEnemy) {
                        breakEnemy = true
                        return@board
                    }
                }
            }
            if (equalLetterNumber(letter, number)) {
                return true
            }
            if (breakEnemy) {
                return false
            }
        }

        return false
    }

    private fun rookMovementBottom(square: Square) : Boolean {

        val pieceColorFriendly = if (gameMaster.whiteToMove) PieceColor.WHITE else PieceColor.BLACK
        val pieceColorEnemy = if (gameMaster.whiteToMove) PieceColor.BLACK else PieceColor.WHITE

        var breakEnemy = false

        for (i in square.number.index - 1 downTo 0) {
            val letter = square.letter
            val number = LetterNumber.getNumberEnum(i)
            board.board.forEach board@ {
                if (it.letter == letter && it.number == number) {
                    if (it.pieceColor == pieceColorFriendly) {
                        return false
                    } else if (it.pieceColor == pieceColorEnemy) {
                        breakEnemy = true
                        return@board
                    }
                }
            }
            if (equalLetterNumber(letter, number)) {
                return true
            }
            if (breakEnemy) {
                return false
            }
        }

        return false
    }

    private fun rookMovementLeft(square: Square) : Boolean {

        val pieceColorFriendly = if (gameMaster.whiteToMove) PieceColor.WHITE else PieceColor.BLACK
        val pieceColorEnemy = if (gameMaster.whiteToMove) PieceColor.BLACK else PieceColor.WHITE

        var breakEnemy = false

        for (i in square.letter.index - 1 downTo 0) {
            val letter = LetterNumber.getLetterEnum(i)
            val number = square.number
            board.board.forEach board@ {
                if (it.letter == letter && it.number == number) {
                    if (it.pieceColor == pieceColorFriendly) {
                        return false
                    } else if (it.pieceColor == pieceColorEnemy) {
                        breakEnemy = true
                        return@board
                    }
                }
            }
            if (equalLetterNumber(letter, number)) {
                return true
            }
            if (breakEnemy) {
                return false
            }
        }

        return false
    }

    private fun rookMovementRight(square: Square) : Boolean {

        val pieceColorFriendly = if (gameMaster.whiteToMove) PieceColor.WHITE else PieceColor.BLACK
        val pieceColorEnemy = if (gameMaster.whiteToMove) PieceColor.BLACK else PieceColor.WHITE

        var breakEnemy = false

        for (i in square.letter.index + 1 .. 7) {
            val letter = LetterNumber.getLetterEnum(i)
            val number = square.number
            board.board.forEach board@ {
                if (it.letter == letter && it.number == number) {
                    if (it.pieceColor == pieceColorFriendly) {
                        return false
                    } else if (it.pieceColor == pieceColorEnemy) {
                        breakEnemy = true
                        return@board
                    }
                }
            }
            if (equalLetterNumber(letter, number)) {
                return true
            }
            if (breakEnemy) {
                return false
            }
        }

        return false
    }

    private fun equalLetterNumber(letter: Letter, number: Number) : Boolean {
        val equal = Square.letterNumberEqual(gameMaster.toSquare!!.letter, letter, gameMaster.toSquare!!.number, number)
        return equal
    }
}