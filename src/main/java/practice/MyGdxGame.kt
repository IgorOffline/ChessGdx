package practice

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter
import com.badlogic.gdx.utils.ScreenUtils
import practice.ui.Window

class MyGdxGame : ApplicationAdapter() {

    private var spriteBatch: SpriteBatch? = null
    private var font12: BitmapFont? = null

    override fun create() {

        spriteBatch = SpriteBatch()

        val generator = FreeTypeFontGenerator(Gdx.files.internal("fonts/Roboto/Roboto-Regular.ttf"))
        val parameter = FreeTypeFontParameter()
        parameter.size = 12
        font12 = generator.generateFont(parameter)
        generator.dispose()
    }

    override fun render() {
        ScreenUtils.clear(0F, 0F, 0.2F, 1F)
        spriteBatch!!.begin()
        font12!!.draw(spriteBatch, "Hello, World!", 15F, Window.FIXED_HEIGHT - 15F)
        spriteBatch!!.end()
    }

    override fun dispose() {
    }

    override fun resize(width: Int, height: Int) {
    }
}