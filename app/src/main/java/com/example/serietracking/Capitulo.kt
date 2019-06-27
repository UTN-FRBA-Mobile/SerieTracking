package com.example.serietracking

data class Capitulo(var id: String, val titulo: String, val serie: String, val episodio: String, var temporada: String, var seen: Boolean = false )
