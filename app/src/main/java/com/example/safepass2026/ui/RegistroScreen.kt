package com.example.safepass2026.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.safepass2026.ui.theme.SafePass2026Theme

val tiposEntrada = listOf("General", "VIP", "Estudiante", "Prensa")

/**
 * Pantalla principal: Scaffold + Column con el formulario de registro.
 *
 * Manejo local de los inputs; al pulsar "Registrar" entrega los valores crudos a [onRegistrar]
 * (nombre, edad como texto y tipo de entrada) para que la capa de estado/lógica los procese.
 * [contenidoEstado] permite que quien conecta el [RegistroState] pinte su resultado bajo el formulario.
 */
@Composable
fun RegistroScreen(
    onRegistrar: (nombre: String, edadTexto: String, tipoEntrada: String) -> Unit,
    modifier: Modifier = Modifier,
    contenidoEstado: @Composable () -> Unit = {}
) {
    Scaffold(modifier = modifier.fillMaxSize()) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 16.dp)
        ) {
            Text("SafePass 2026", style = MaterialTheme.typography.headlineMedium)
            Text(
                "Check-in de asistentes · TechEvent 2026",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(Modifier.height(24.dp))

            FormularioAsistente(onRegistrar = onRegistrar)

            Spacer(Modifier.height(24.dp))
            contenidoEstado()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FormularioAsistente(
    onRegistrar: (nombre: String, edadTexto: String, tipoEntrada: String) -> Unit
) {
    var nombre by rememberSaveable { mutableStateOf("") }
    var edadTexto by rememberSaveable { mutableStateOf("") }
    var tipoEntrada by rememberSaveable { mutableStateOf(tiposEntrada.first()) }
    var menuAbierto by rememberSaveable { mutableStateOf(false) }

    // Conversión segura: toIntOrNull nunca lanza; null significa texto no numérico.
    val edadInvalida = edadTexto.isNotBlank() && edadTexto.trim().toIntOrNull() == null

    OutlinedTextField(
        value = nombre,
        onValueChange = { nombre = it },
        label = { Text("Nombre del asistente") },
        singleLine = true,
        modifier = Modifier.fillMaxWidth()
    )
    Spacer(Modifier.height(16.dp))

    OutlinedTextField(
        value = edadTexto,
        onValueChange = { edadTexto = it },
        label = { Text("Edad") },
        singleLine = true,
        isError = edadInvalida,
        supportingText = { if (edadInvalida) Text("Ingresa solo números") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        modifier = Modifier.fillMaxWidth()
    )
    Spacer(Modifier.height(8.dp))

    ExposedDropdownMenuBox(
        expanded = menuAbierto,
        onExpandedChange = { menuAbierto = it }
    ) {
        OutlinedTextField(
            value = tipoEntrada,
            onValueChange = {},
            readOnly = true,
            label = { Text("Tipo de entrada") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = menuAbierto) },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )
        ExposedDropdownMenu(
            expanded = menuAbierto,
            onDismissRequest = { menuAbierto = false }
        ) {
            tiposEntrada.forEach { tipo ->
                DropdownMenuItem(
                    text = { Text(tipo) },
                    onClick = {
                        tipoEntrada = tipo
                        menuAbierto = false
                    }
                )
            }
        }
    }
    Spacer(Modifier.height(24.dp))

    Button(
        onClick = { onRegistrar(nombre, edadTexto, tipoEntrada) },
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp)
    ) {
        Text("Registrar")
    }
}

@Preview(showBackground = true)
@Composable
private fun RegistroScreenPreview() {
    SafePass2026Theme {
        RegistroScreen(onRegistrar = { _, _, _ -> })
    }
}
