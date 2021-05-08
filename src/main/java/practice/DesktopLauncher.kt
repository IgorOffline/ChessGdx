package practice

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration
import practice.ui.Window

fun main() {
    val configuration = Lwjgl3ApplicationConfiguration()
    configuration.setWindowedMode(1280, Window.FIXED_HEIGHT)
    configuration.setResizable(false)
    configuration.setIdleFPS(15)
    configuration.setForegroundFPS(144)
    Lwjgl3Application(MyGdxGame(), configuration)
}