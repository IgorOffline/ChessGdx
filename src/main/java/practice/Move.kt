package practice

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import practice.board.Board
import practice.board.Heading
import practice.board.Piece
import practice.board.Square
import practice.board.ui.LetterNumber

class Move {

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

    fun move(board: Board) {
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            for (i in 0..7) {
                for (j in 0..7) {
                    moveLogic(i, j, board)
                }
            }
        }
    }

    private fun moveLogic(i: Int, j: Int, board: Board) {
        val xBetween = Gdx.input.x > clickable[i].head && Gdx.input.x < clickable[i + 1].head
        val yBetween = Gdx.input.y > clickable[i].body[j] && Gdx.input.y < clickable[i].body[j + 1]
        if (xBetween && yBetween) {
            for (square in board.board) {
                between(i, j, square)
            }
        }
    }

    private fun between(i: Int, j: Int, square: Square) {
        val letter = LetterNumber.getLetterEnum(i)
        val number = LetterNumber.getNumberEnumReverse(j)
        val letterOk = square.letter == letter
        val numberOk = square.number == number
        val squareFilled = square.piece != Piece.NONE
        if (letterOk && numberOk && squareFilled) {
            println("$letter$number ${square.pieceColor} ${square.piece}")
        }
    }
}