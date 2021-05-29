package practice

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture

data class Textures(
    var txWK: Texture? = null,
    var txBK: Texture? = null) {

    init {
        txWK = Texture(Gdx.files.internal("icons/wK.png"))
        txBK = Texture(Gdx.files.internal("icons/bK.png"))
    }

    fun dispose() {
        txWK!!.dispose()
    }
}