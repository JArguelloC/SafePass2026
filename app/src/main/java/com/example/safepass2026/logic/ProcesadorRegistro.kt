package com.example.safepass2026.logic

import com.example.safepass2026.model.Asistente
import com.example.safepass2026.state.RegistroState

/**
 * Higher-Order Function: recibe una lambda `regla` y la aplica al asistente.
 * Ejemplo de lambda: `{ it.copy(tipoEntrada = "VIP") }`
 */
fun aplicarRegla(asistente: Asistente, regla: (Asistente) -> Asistente): Asistente =
    regla(asistente)

/**
 * Valida los datos del formulario y devuelve un [RegistroState]. Nunca lanza excepciones.
 *
 * Errores (en orden): nombre vacío, edad vacía, edad no numérica, edad negativa y menor de edad.
 * Si todo es válido devuelve [RegistroState.Success] con el asistente ya procesado por `regla`.
 *
 * @param regla lambda opcional para modificar al asistente, por ejemplo
 * `{ it.copy(tipoEntrada = "VIP") }`. Por defecto no cambia nada.
 */
fun procesarRegistro(
    nombre: String?,
    edadTexto: String?,
    tipoEntrada: String?,
    regla: (Asistente) -> Asistente = { it }
): RegistroState {
    // 1. Nombre: usa la extension function esNombreValido()
    if (!nombre.esNombreValido()) {
        return RegistroState.Error("El nombre no puede estar vacío")
    }
    val nombreLimpio = nombre!!.trim()

    // 2. Edad vacía: let solo se ejecuta si edadTexto no es null
    val textoEdad = edadTexto?.trim()?.let { it.ifEmpty { null } }
        ?: return RegistroState.Error("La edad no puede estar vacía")

    // 3. Edad numérica: toIntOrNull devuelve null en vez de lanzar excepción
    val edad = textoEdad.toIntOrNull()
        ?: return RegistroState.Error("La edad '$textoEdad' no es un número válido")

    // 4. Edad negativa
    if (edad < 0) {
        return RegistroState.Error("La edad no puede ser negativa ($edad)")
    }

    // 5. Menor de edad: usa la extension function esMayorDeEdad()
    if (!edad.esMayorDeEdad()) {
        return RegistroState.Error("$nombreLimpio es menor de edad ($edad años)")
    }

    // Elvis: si el tipo de entrada es null o está vacío, el default es "General"
    val tipo = tipoEntrada?.trim()?.takeIf { it.isNotEmpty() } ?: "General"

    // apply: las propiedades de Asistente son val, por eso apply no las modifica;
    // solo se usa para un log y devuelve el mismo objeto (this).
    val asistente = Asistente(nombreLimpio, edad, tipo).apply {
        println("Asistente creado: ${this.nombre}, edad ${this.edad}, entrada ${this.tipoEntrada}")
    }

    // Higher-order function: aplica la regla opcional recibida por lambda
    val final = aplicarRegla(asistente, regla)

    return RegistroState.Success(
        final,
        "Registro exitoso: ${final.nombre} (${final.edad} años) - entrada ${final.tipoEntrada}"
    )
}
