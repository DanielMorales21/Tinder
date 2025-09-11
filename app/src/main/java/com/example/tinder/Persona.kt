package com.example.tinder

class Persona(
    val id: Int,
    val nombre: String,
    val edad: String,
    val imagenes: List<Int>,
    var imagenActual: Int = 0
)