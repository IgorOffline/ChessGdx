package practice

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.InputAdapter
import practice.board.*
import practice.board.ui.LetterNumber
import practice.piece.King
import practice.piece.Rook
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
                isBlackKingCheckmated()
            }
        }
    }

    private fun between(i: Int, j: Int, square: Square, moveToSquare: Boolean) {
        val letter = LetterNumber.getLetterEnum(i)
        val number = LetterNumber.getNumberEnumReverse(j)
        val letterOk = square.letter == letter
        val numberOk = square.number == number
        if (letterOk && numberOk) {
            if (!moveToSquare) {
                gameMaster.fromSquare = square
            } else {
                gameMaster.toSquare = square
                if (squareNotFriendly() && squareMovable() && squareLegal()) {

                    val toSquareOriginalPiece = gameMaster.toSquare!!.piece
                    val toSquareOriginalPieceColor = gameMaster.toSquare!!.pieceColor
                    val fromSquareOriginalPiece = gameMaster.fromSquare!!.piece
                    val fromSquareOriginalPieceColor = gameMaster.fromSquare!!.pieceColor

                    gameMaster.toSquare!!.piece = gameMaster.fromSquare!!.piece
                    gameMaster.toSquare!!.pieceColor = gameMaster.fromSquare!!.pieceColor
                    gameMaster.fromSquare!!.piece = Piece.NONE
                    gameMaster.fromSquare!!.pieceColor = PieceColor.NONE

                    isKingInCheck()

                    if (gameMaster.whiteKingInCheck || gameMaster.blackKingInCheck) {
                        // revert
                        gameMaster.toSquare!!.piece = toSquareOriginalPiece
                        gameMaster.toSquare!!.pieceColor = toSquareOriginalPieceColor
                        gameMaster.fromSquare!!.piece = fromSquareOriginalPiece
                        gameMaster.fromSquare!!.pieceColor = fromSquareOriginalPieceColor

                        gameMaster.whiteToMove = !gameMaster.whiteToMove
                    }

                    gameMaster.whiteToMove = !gameMaster.whiteToMove
                }
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

    private fun squareMovable(): Boolean {
        val pieceColor = if (gameMaster.whiteToMove) PieceColor.WHITE else PieceColor.BLACK

        if (gameMaster.fromSquare!!.pieceColor != pieceColor) {
            return false
        }

        val square = gameMaster.fromSquare!!

        return when (square.piece) {
            Piece.KING -> King.kingMovement(gameMaster, square)
            Piece.ROOK -> Rook.rookMovement(board, gameMaster, square)
            Piece.NONE -> throw IllegalArgumentException("Unknown fromSquare Piece")
        }
    }

    private fun squareLegal(): Boolean {

        if (gameMaster.fromSquare!!.piece == Piece.KING) {

            val pieceColor = if (gameMaster.whiteToMove) PieceColor.BLACK else PieceColor.WHITE

            board.board.forEach {
                if (it.piece == Piece.KING && it.pieceColor == pieceColor) {

                    val kingMovement = King.kingMovement(gameMaster, it)

                    return !kingMovement
                }
            }
        }

        return true
    }

    private fun isKingInCheck() {

        val kingPieceColor: PieceColor
        val rookPieceColor: PieceColor

        if (gameMaster.whiteToMove) {
            gameMaster.whiteKingInCheck = false
            kingPieceColor = PieceColor.WHITE
            rookPieceColor = PieceColor.BLACK
        } else {
            gameMaster.blackKingInCheck = false
            kingPieceColor = PieceColor.BLACK
            rookPieceColor = PieceColor.WHITE
        }

        var sqKing: Square? = null

        board.board.forEach board@ {
            if (it.piece == Piece.KING && it.pieceColor == kingPieceColor) {
                sqKing = it
                return@board
            }
        }

        val rookChecksKingTop = Rook.rookChecksKingTop(board, sqKing!!, rookPieceColor)
        val rookChecksKingBottom = Rook.rookChecksKingBottom(board, sqKing!!, rookPieceColor)
        val rookChecksKingLeft = Rook.rookChecksKingLeft(board, sqKing!!, rookPieceColor)
        val rookChecksKingRight = Rook.rookChecksKingRight(board, sqKing!!, rookPieceColor)

        if (kingPieceColor == PieceColor.WHITE) {
            gameMaster.whiteKingInCheck = rookChecksKingTop || rookChecksKingBottom || rookChecksKingLeft || rookChecksKingRight
        } else if (kingPieceColor == PieceColor.BLACK) {
            gameMaster.blackKingInCheck = rookChecksKingTop || rookChecksKingBottom || rookChecksKingLeft || rookChecksKingRight
        }
    }

    // TODO write this
    private fun isBlackKingCheckmated() {

        var blackKing: Square? = null

        board.board.forEach board@ {
            if (it.piece == Piece.KING && it.pieceColor == PieceColor.BLACK) {
                blackKing = it
                return@board
            }
        }

        if (blackKing == null) {
            throw IllegalStateException("Black King not found!")
        }

        val kingMoves = King.kingMoves(blackKing!!)

        gameMaster.blackKingLegalMoves = kingMoves.size
    }
}
