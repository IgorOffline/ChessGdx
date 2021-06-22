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
    companion object {
        const val X_OFFSET = 125F
        const val Y_OFFSET = 125F
    }

    private var colorSwitch = false
    private val textureSize = 34F

    fun renderLettersNumbers(shapeRenderer: ShapeRenderer, spriteBatch: SpriteBatch) {
        shapeRenderer.projectionMatrix = spriteBatch.projectionMatrix
        for (i in 0..7) {
            for (j in 0..7) {
                shapeRenderer.begin(ShapeRenderer.ShapeType.Filled)
                shapeRenderer.color = if (colorSwitch) ColorUtil.COLOR_1 else ColorUtil.COLOR_2
                colorSwitch = !colorSwitch

                shapeRenderer.rect(X_OFFSET + 50F * i.toFloat(), Y_OFFSET + 50F * j.toFloat(), 50F, 50F)
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
                var drawx = X_OFFSET + 50F * i.toFloat()
                var drawy = Y_OFFSET + 50F * j.toFloat()
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
        var drawx = X_OFFSET + 50F * i.toFloat()
        var drawy = Y_OFFSET + 50F * j.toFloat()
        drawx += 8F
        drawy -= 8F
        drawy += 16F
        val texture = findTextureForSquare(square, textures)
        texture?.let {
            spriteBatch.begin()
            spriteBatch.draw(texture, drawx, drawy, textureSize, textureSize)
            spriteBatch.end()
        }
    }

    private fun findTextureForSquare(square: Square, textures: Textures): Texture? {
        return when (square.pieceColor) {
            PieceColor.WHITE -> {
                whitePieces(square, textures)
            }
            PieceColor.BLACK -> {
                blackPieces(square, textures)
            }
            else -> {
                return null
            }
        }
    }

    private fun whitePieces(square: Square, textures: Textures): Texture {
        return when (square.piece) {
            Piece.NONE -> throw IllegalArgumentException(Messages.UNKNOWN_WHITE_PIECE)
            Piece.KING -> textures.txWK!!
        }
    }

    private fun blackPieces(square: Square, textures: Textures): Texture {
        return when (square.piece) {
            Piece.NONE -> throw IllegalArgumentException(Messages.UNKNOWN_BLACK_PIECE)
            Piece.KING -> textures.txBK!!
        }
    }
}