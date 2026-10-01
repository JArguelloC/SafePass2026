package com.example.safepass2026.logic

/**
 * Extension function sobre `Int?`: indica si la edad corresponde a un mayor de edad.
 * Devuelve `true` solo si la edad no es nula y es >= 18.
 */
fun Int?.esMayorDeEdad(): Boolean = this != null && this >= 18

/**
 * Extension function sobre `String?`: indica si el nombre es válido.
 * Es válido si no es nulo, no está vacío y no contiene solo espacios.
 */
fun String?.esNombreValido(): Boolean = this != null && this.isNotBlank()
