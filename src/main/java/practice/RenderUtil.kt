package practice

import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer

class RenderUtil {

    private var colorSwitch = false
    private val xOffset = 125F
    private val yOffset = 125F

    fun renderBoard(shapeRenderer: ShapeRenderer, spriteBatch: SpriteBatch) {
        shapeRenderer.projectionMatrix = spriteBatch.projectionMatrix
        for (i in 0..8) {
            for (j in 0..8) {
                shapeRenderer.begin(ShapeRenderer.ShapeType.Filled)
                shapeRenderer.color = if (colorSwitch) ColorUtil.COLOR_1 else ColorUtil.COLOR_2
                colorSwitch = !colorSwitch

                shapeRenderer.rect(xOffset + 50f * i.toFloat(), yOffset + 50f * j.toFloat(), 50f, 50f)
                shapeRenderer.end()
            }
        }
        colorSwitch = !colorSwitch
    }

    fun renderFont(spriteBatch: SpriteBatch, font: BitmapFont) {
        for (i in 0..8) {
            for (j in 0..8) {
                spriteBatch.begin()
                font.color = ColorUtil.COLOR_3
                var drawx = xOffset + 50F * i.toFloat()
                var drawy = yOffset + 50F * j.toFloat()
                drawx += 8f
                drawy -= 8f
                drawy += 50f
                font.draw(spriteBatch, "A0", drawx, drawy)
                spriteBatch.end()
            }
        }
    }
}