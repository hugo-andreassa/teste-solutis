package com.example.android.testesolutis.utils

import android.util.Patterns

/**
 * Source: https://gist.github.com/trfiladelfo/92edd1cad568ae6bae6c026dac52dff2
 */
fun isCPF(cpf: String): Boolean {
    if (cpf.isEmpty())
        return false

    val numbers = cpf.filter { it.isDigit() }.map {
        it.toString().toInt()
    }

    if (numbers.size != 11)
        return false

    if (numbers.all { it == numbers[0] })
        return false

    val dv1 = ((0..8).sumOf { (it + 1) * numbers[it] }).rem(11).let {
        if (it >= 10)
            0
        else
            it
    }

    val dv2 = ((0..8).sumOf { it * numbers[it] }.let { (it + (dv1 * 9)).rem(11) }).let {
        if (it >= 10)
            0
        else
            it
    }

    return numbers[9] == dv1 && numbers[10] == dv2
}

/**
 * Source: https://stackoverflow.com/questions/1819142/how-should-i-validate-an-e-mail-address
 */
fun isEmail(email: String): Boolean {
    return email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

fun isPassword(password: String): Boolean {
    // campo password deve validar se a senha tem pelo menos uma
    // letra maiuscula, um caracter especial e um caracter alfanumérico

    // val upperCase = password.contains(Regex("[A-Z]"))
    val upperCase = true
    val specialCharacter = password.contains(Regex("[@#$%^/&+=]"))
    val alphaNumeric = password.contains(Regex("[a-zA-Z0-9]"))

    val result =  upperCase && specialCharacter && alphaNumeric

    return password.isNotEmpty() && result
}