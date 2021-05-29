package practice

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import practice.board.Heading

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

    fun move() {
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            for (i in 0..7) {
                for (j in 0..7) {
                    val xBetween = Gdx.input.x > clickable[i].head && Gdx.input.x < clickable[i + 1].head
                    val yBetween = Gdx.input.y > clickable[i].body[j] && Gdx.input.y < clickable[i].body[j + 1]
                    if (xBetween && yBetween) {
                        println("$i, $j")
                    }
                }
            }
        }
    }
}