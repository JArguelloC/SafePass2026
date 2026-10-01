package com.example.safepass2026

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.safepass2026.logic.procesarRegistro
import com.example.safepass2026.state.RegistroState
import com.example.safepass2026.ui.RegistroScreen
import com.example.safepass2026.ui.ResultadoRegistro
import com.example.safepass2026.ui.theme.SafePass2026Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SafePass2026Theme {
                var state by remember { mutableStateOf<RegistroState>(RegistroState.Idle) }

                RegistroScreen(
                    onRegistrar = { nombre, edadTexto, tipoEntrada ->
                        state = procesarRegistro(nombre, edadTexto, tipoEntrada)
                    },
                    contenidoEstado = { ResultadoRegistro(state) }
                )
            }
        }
    }
}