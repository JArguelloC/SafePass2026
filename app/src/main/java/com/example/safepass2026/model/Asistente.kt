package com.example.safepass2026.model

/**
 * Modelo inmutable para el asistente al TechEvent 2026.
 * Cumple con el requerimiento de propiedades inmutables (val) y edad anulable.
 */

data class Asistente(
    val nombre: String,
    val edad: Int?,
    val tipoEntrada: String
)