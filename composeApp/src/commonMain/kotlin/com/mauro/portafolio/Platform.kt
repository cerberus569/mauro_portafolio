package com.mauro.portafolio

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform