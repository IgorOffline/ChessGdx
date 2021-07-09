package practice

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.InputProcessor
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import practice.board.*
import practice.board.Number
import practice.board.ui.LetterNumber

class MyClickListener(val board: Board) : ClickListener(), InputProcessor {

    private var dragDrop = 0L
    private var lastDragDrop = 0L

    private val clickable = mutableListOf<Heading>()

    var fromSquare: Square? = null
        private set
    var toSquare: Square? = null
        private set

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
                toSquare = square
                if (squareMoveable()) {
                    if (squareLegal()) {
                        toSquare!!.piece = fromSquare!!.piece
                        toSquare!!.pieceColor = fromSquare!!.pieceColor
                        fromSquare!!.piece = Piece.NONE
                        fromSquare!!.pieceColor = PieceColor.NONE
                    }
                }
            } else {
                fromSquare = square
            }
        }
    }

    private fun squareMoveable(): Boolean {
        if (!(fromSquare!!.piece == Piece.KING && fromSquare!!.pieceColor == PieceColor.WHITE)) {
            return false
        }

        val square = fromSquare!!

        val kingMovement = kingMovement(square)

        return kingMovement
    }

    private fun squareLegal(): Boolean {
        board.board.forEach {
            if (it.piece == Piece.KING && it.pieceColor == PieceColor.BLACK) {

                val kingMovement = kingMovement(it)
                val reverse = !kingMovement

                return reverse
            }
        }

        return true
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

    private fun equalLetterNumber(letter: Letter, number: Number) : Boolean {
        val equal = Square.letterNumberEqual(toSquare!!.letter, letter, toSquare!!.number, number)
        return equal
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