package practice

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.InputProcessor
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import practice.board.Board
import practice.board.Heading
import practice.board.Piece
import practice.board.Square
import practice.board.ui.LetterNumber

class MyClickListener(val board: Board) : ClickListener(), InputProcessor {

    var dragDrop = 0L
    var lastDragDrop = 0L

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
            for (i in 0..7) {
                for (j in 0..7) {
                    moveLogic(i, j, board)
                }
            }
        }
        return true
    }

    override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        if (lastDragDrop < dragDrop) {
            println("$screenX $screenY")
        }
        lastDragDrop = dragDrop
        return true
    }

    override fun touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean {
        ++dragDrop
        return true
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

    override fun keyDown(keycode: Int): Boolean {
        return true
    }

    override fun keyUp(keycode: Int): Boolean {
        return true
    }

    override fun keyTyped(character: Char): Boolean {
        return true
    }

    override fun mouseMoved(screenX: Int, screenY: Int): Boolean {
        return true
    }

    override fun scrolled(amountX: Float, amountY: Float): Boolean {
        return true
    }
}