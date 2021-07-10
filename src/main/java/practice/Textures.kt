package practice

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture

data class Textures(
    var txWK: Texture? = null,
    var txBK: Texture? = null,
    var txWR: Texture? = null,
    var txBR: Texture? = null) {

    init {
        txWK = Texture(Gdx.files.internal("icons/wK.png"))
        txBK = Texture(Gdx.files.internal("icons/bK.png"))
        txWR = Texture(Gdx.files.internal("icons/wR.png"))
        txBR = Texture(Gdx.files.internal("icons/bR.png"))
    }

    fun dispose() {
        txWK!!.dispose()
        txBK!!.dispose()
        txWR!!.dispose()
        txBR!!.dispose()
    }
}