package practice

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.utils.ScreenUtils
import practice.board.Board
import practice.ui.Window

class MyGdxGame : ApplicationAdapter() {

    private var spriteBatch: SpriteBatch? = null
    private var font12: BitmapFont? = null
    private var shapeRenderer: ShapeRenderer? = null
    private var renderUtil: RenderUtil? = null
    private var textures: Textures? = null
    private var board: Board? = null
    private var myClickListener: MyClickListener? = null

    override fun create() {

        spriteBatch = SpriteBatch()

        val generator = FreeTypeFontGenerator(Gdx.files.internal("fonts/Roboto/Roboto-Regular.ttf"))
        val parameter = FreeTypeFontParameter()
        parameter.size = 12
        font12 = generator.generateFont(parameter)
        generator.dispose()

        shapeRenderer = ShapeRenderer()
        renderUtil = RenderUtil()

        textures = Textures()
        board = Board()
        myClickListener = MyClickListener(board!!)

        Gdx.input.inputProcessor = myClickListener
    }

    override fun render() {
        ScreenUtils.clear(0F, 0F, 0.2F, 1F)

        renderUtil!!.renderLettersNumbers(shapeRenderer!!, spriteBatch!!)
        renderUtil!!.renderFonts(spriteBatch!!, font12!!)
        renderUtil!!.renderBoard(spriteBatch!!, board!!, textures!!)

        if (myClickListener!!.fromSquare != null && myClickListener!!.toSquare != null) {

            val fromToString = fromToString()

            spriteBatch!!.begin()
            font12!!.draw(spriteBatch, fromToString, 15F, Window.FIXED_HEIGHT - 15F)
            spriteBatch!!.end()
        }
    }

    private fun fromToString(): String {
        return myClickListener!!.fromSquare!!.letter.toString() +
                myClickListener!!.fromSquare!!.number.toString() +
                " " +
                myClickListener!!.fromSquare!!.piece.toString() +
                " " +
                myClickListener!!.fromSquare!!.pieceColor.toString() +
                " -> " +
                myClickListener!!.toSquare!!.letter.toString() +
                myClickListener!!.toSquare!!.number.toString() +
                " " +
                myClickListener!!.toSquare!!.piece.toString() +
                " " +
                myClickListener!!.toSquare!!.pieceColor.toString()
    }

    override fun dispose() {
        spriteBatch!!.dispose()
        shapeRenderer!!.dispose()
        textures!!.dispose()
    }

    override fun resize(width: Int, height: Int) {
    }
}