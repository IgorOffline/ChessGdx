package practice

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer

class RenderUtil {

    private var colorSwitch = false
    private val xOffset = 125F
    private val yOffset = 125F
    private val textureSize = 34F

    fun renderBoard(shapeRenderer: ShapeRenderer, spriteBatch: SpriteBatch) {
        shapeRenderer.projectionMatrix = spriteBatch.projectionMatrix
        for (i in 0..8) {
            for (j in 0..8) {
                shapeRenderer.begin(ShapeRenderer.ShapeType.Filled)
                shapeRenderer.color = if (colorSwitch) ColorUtil.COLOR_1 else ColorUtil.COLOR_2
                colorSwitch = !colorSwitch

                shapeRenderer.rect(xOffset + 50F * i.toFloat(), yOffset + 50F * j.toFloat(), 50F, 50F)
                shapeRenderer.end()
            }
        }
        colorSwitch = !colorSwitch
    }

    fun renderFonts(spriteBatch: SpriteBatch, font: BitmapFont) {
        for (i in 0..8) {
            for (j in 0..8) {
                spriteBatch.begin()
                font.color = ColorUtil.COLOR_3
                var drawx = xOffset + 50F * i.toFloat()
                var drawy = yOffset + 50F * j.toFloat()
                drawx += 3F
                drawy -= 3F
                drawy += 50F
                font.draw(spriteBatch, "A0", drawx, drawy)
                spriteBatch.end()
            }
        }
    }

    fun renderTextures(spriteBatch: SpriteBatch, texture: Texture) {
        for (i in 0..8) {
            for (j in 0..8) {
                var drawx = xOffset + 50F * i.toFloat()
                var drawy = yOffset + 50F * j.toFloat()
                drawx += 8F
                drawy -= 8F
                drawy += 16F
                spriteBatch.begin()
                spriteBatch.draw(texture, drawx, drawy, textureSize, textureSize)
                spriteBatch.end()
            }
        }
    }
}