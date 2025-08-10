package ru.vasilev.domain.usecases

import java.util.regex.Pattern

class ValidateEmailUseCase {
    operator fun invoke(email: String): Boolean {
        if (email.isBlank()) {
            return false
        }


        if (email.contains(Regex("[а-яА-Я]"))) {
            return false
        }


        val emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\$"
        val pattern = Pattern.compile(emailRegex)
        return pattern.matcher(email).matches()
    }
}