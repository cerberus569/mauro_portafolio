package com.mauro.portafolio

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "mauro_portafolio",
    ) {
        App()
    }
}