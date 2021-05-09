package practice

import com.badlogic.gdx.graphics.Color

//#f0d9b5
//240,217,181
//#b58863
//181,136,99
//3::
//52504C
//82,80,76

class ColorUtil {
    companion object {
        private const val R1 = (240.0 / 255.0).toFloat()
        private const val G1 = (217.0 / 255.0).toFloat()
        private const val B1 = (181.0 / 255.0).toFloat()
        private const val R2 = (181.0 / 255.0).toFloat()
        private const val G2 = (136.0 / 255.0).toFloat()
        private const val B2 = (99.0 / 255.0).toFloat()
        private const val R3 = (82.0 / 255.0).toFloat()
        private const val G3 = (80.0 / 255.0).toFloat()
        private const val B3 = (76.0 / 255.0).toFloat()
        val COLOR_1 = Color(R1, G1, B1, 1f)
        val COLOR_2 = Color(R2, G2, B2, 1f)
        val COLOR_3 = Color(R3, G3, B3, 1f)
    }
}