package com.di.aprendizajepalabras

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform