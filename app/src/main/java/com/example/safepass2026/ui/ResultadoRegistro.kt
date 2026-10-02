package com.example.safepass2026.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.safepass2026.model.Asistente
import com.example.safepass2026.state.RegistroState

@Composable
fun ResultadoRegistro(state: RegistroState, modifier: Modifier = Modifier) {
    // when exhaustivo: sin "else". Si alguien agrega un estado nuevo, no compila.
    when (state) {
        is RegistroState.Idle -> TarjetaIdle(modifier)
        is RegistroState.Success -> TarjetaExito(state.asistente, state.mensajeResumen, modifier)
        is RegistroState.Error -> TarjetaError(state.mensajeError, modifier)
    }
}

@Composable
private fun TarjetaIdle(modifier: Modifier = Modifier) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        modifier = modifier.fillMaxWidth()
    ) {
        Column(Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text("Bienvenido a SafePass 2026", fontWeight = FontWeight.SemiBold)
            Text("Completa el formulario y pulsa Registrar.")
        }
    }
}

@Composable
private fun TarjetaExito(asistente: Asistente, mensaje: String, modifier: Modifier = Modifier) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
        modifier = modifier.fillMaxWidth()
    ) {
        Column(Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text("¡Registro exitoso!", fontWeight = FontWeight.SemiBold)
            Text("Nombre: ${asistente.nombre}")
            Text("Edad: ${asistente.edad ?: "No indicada"}")
            Text("Entrada: ${asistente.tipoEntrada}")
            Text(mensaje)
        }
    }
}

@Composable
private fun TarjetaError(mensajeError: String, modifier: Modifier = Modifier) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer),
        modifier = modifier.fillMaxWidth()
    ) {
        Column(Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text("No se pudo registrar", fontWeight = FontWeight.SemiBold)
            Text(mensajeError)
        }
    }
}