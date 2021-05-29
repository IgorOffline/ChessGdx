package practice

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import practice.board.Board
import practice.board.Piece
import practice.board.PieceColor
import practice.board.Square
import practice.board.ui.LetterNumber
import practice.ui.ColorUtil
import java.lang.IllegalArgumentException

class RenderUtil {

    private var colorSwitch = false
    private val xOffset = 125F
    private val yOffset = 125F
    private val textureSize = 34F

    fun renderLettersNumbers(shapeRenderer: ShapeRenderer, spriteBatch: SpriteBatch) {
        shapeRenderer.projectionMatrix = spriteBatch.projectionMatrix
        for (i in 0..7) {
            for (j in 0..7) {
                shapeRenderer.begin(ShapeRenderer.ShapeType.Filled)
                shapeRenderer.color = if (colorSwitch) ColorUtil.COLOR_1 else ColorUtil.COLOR_2
                colorSwitch = !colorSwitch

                shapeRenderer.rect(xOffset + 50F * i.toFloat(), yOffset + 50F * j.toFloat(), 50F, 50F)
                shapeRenderer.end()
            }
            colorSwitch = !colorSwitch
        }
    }

    fun renderFonts(spriteBatch: SpriteBatch, font: BitmapFont) {
        for (i in 0..7) {
            for (j in 0..7) {
                spriteBatch.begin()
                font.color = ColorUtil.COLOR_3
                var drawx = xOffset + 50F * i.toFloat()
                var drawy = yOffset + 50F * j.toFloat()
                drawx += 3F
                drawy -= 3F
                drawy += 50F
                val letterNumber = LetterNumber.getLetter(i) + LetterNumber.getNumber(j) // example: A0
                font.draw(spriteBatch, letterNumber, drawx, drawy)
                spriteBatch.end()
            }
        }
    }

    fun renderBoard(spriteBatch: SpriteBatch, board: Board, textures: Textures) {
        for (i in 0..7) {
            for (j in 0..7) {
                for (square in board.board) {
                    if (board.squareFound(i, j, square)) {
                        boardRendering(i, j, square, spriteBatch, textures)
                    }
                }
            }
        }
    }

    private fun boardRendering(i: Int, j: Int, square: Square, spriteBatch: SpriteBatch, textures: Textures) {
        var drawx = xOffset + 50F * i.toFloat()
        var drawy = yOffset + 50F * j.toFloat()
        drawx += 8F
        drawy -= 8F
        drawy += 16F
        spriteBatch.begin()
        val texture = findTextureForSquare(square, textures)
        spriteBatch.draw(texture, drawx, drawy, textureSize, textureSize)
        spriteBatch.end()
    }

    private fun findTextureForSquare(square: Square, textures: Textures): Texture {
        return when (square.pieceColor) {
            PieceColor.WHITE -> {
                whitePieces(square, textures)
            }
            PieceColor.BLACK -> {
                blackPieces(square, textures)
            }
            else -> {
                throw IllegalArgumentException("Unknown Piece Color")
            }
        }
    }

    private fun whitePieces(square: Square, textures: Textures): Texture {
        return when (square.piece) {
            Piece.NONE -> throw IllegalArgumentException("Unknown White Piece")
            Piece.KING -> textures.txWK!!
        }
    }

    private fun blackPieces(square: Square, textures: Textures): Texture {
        return when (square.piece) {
            Piece.NONE -> throw IllegalArgumentException("Unknown Black Piece")
            Piece.KING -> textures.txBK!!
        }
    }
}