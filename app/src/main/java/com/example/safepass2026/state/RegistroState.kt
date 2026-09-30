package com.example.safepass2026.state

import com.example.safepass2026.model.Asistente

/**
 * Gestión de estados de la interfaz de usuario (UI State).
 * Modela de forma exhaustiva los posibles escenarios de la pantalla.
 */

sealed class RegistroState {
    object Idle : RegistroState()
    data class Success(val asistente: Asistente, val mensajeResumen: String) : RegistroState()
    data class Error(val mensajeError: String) : RegistroState()
}