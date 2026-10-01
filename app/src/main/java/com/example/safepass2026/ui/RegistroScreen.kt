package com.example.safepass2026.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.safepass2026.ui.theme.SafePass2026Theme

val tiposEntrada = listOf("General", "VIP", "Estudiante", "Prensa")

private val RadioCampo = RoundedCornerShape(12.dp)
private val RadioCard = RoundedCornerShape(20.dp)

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
    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Cabecera()
            FormularioAsistente(onRegistrar = onRegistrar)
            contenidoEstado()
        }
    }
}

@Composable
private fun Cabecera() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                Icons.Filled.Lock,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.size(24.dp)
            )
        }
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(
                "SafePass 2026",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                "Check-in de asistentes · TechEvent 2026",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
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

    Card(
        shape = RadioCard,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    "Datos del asistente",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    "Completa los campos para validar el acceso.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre completo") },
                leadingIcon = { Icon(Icons.Filled.Person, contentDescription = null) },
                singleLine = true,
                shape = RadioCampo,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = edadTexto,
                onValueChange = { edadTexto = it },
                label = { Text("Edad") },
                leadingIcon = { Icon(Icons.Filled.DateRange, contentDescription = null) },
                singleLine = true,
                isError = edadInvalida,
                supportingText = { if (edadInvalida) Text("Ingresa solo números") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                shape = RadioCampo,
                modifier = Modifier.fillMaxWidth()
            )

            ExposedDropdownMenuBox(
                expanded = menuAbierto,
                onExpandedChange = { menuAbierto = it }
            ) {
                OutlinedTextField(
                    value = tipoEntrada,
                    onValueChange = {},
                    readOnly = true,
                    singleLine = true,
                    label = { Text("Tipo de entrada") },
                    leadingIcon = { Icon(Icons.Filled.Star, contentDescription = null) },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = menuAbierto) },
                    shape = RadioCampo,
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
                            text = { Text(tipo, maxLines = 1, overflow = TextOverflow.Ellipsis) },
                            onClick = {
                                tipoEntrada = tipo
                                menuAbierto = false
                            }
                        )
                    }
                }
            }

            Button(
                onClick = { onRegistrar(nombre, edadTexto, tipoEntrada) },
                shape = RadioCampo,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
            ) {
                Text(
                    "Registrar",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun RegistroScreenPreview() {
    SafePass2026Theme(dynamicColor = false) {
        RegistroScreen(onRegistrar = { _, _, _ -> })
    }
}
