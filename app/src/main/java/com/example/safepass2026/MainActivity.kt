package com.example.safepass2026

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.safepass2026.ui.RegistroScreen
import com.example.safepass2026.ui.theme.SafePass2026Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SafePass2026Theme {
                // Carlos conecta aquí RegistroState y procesarRegistro (onRegistrar / contenidoEstado)
                RegistroScreen(onRegistrar = { _, _, _ -> })
            }
        }
    }
}