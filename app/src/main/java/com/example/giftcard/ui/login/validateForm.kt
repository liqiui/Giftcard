package com.example.giftcard.ui.login


abstract class Validator {
    abstract fun execute(input: String): ValidationResult
}

class ValidateUsername : Validator() {
    override fun execute(input: String): ValidationResult {
        // username validation logic
        if(input.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "The username can't be blank"
            )
        }
        val usernamePattern = Regex("^[a-zA-Z0-9]{4,20}$")
        if (!usernamePattern.matches(input)) {
            return ValidationResult(
                successful = false,
                errorMessage = "That's not a valid username, please input 4-20 letters"
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}

class ValidatePassword : Validator() {
    override fun execute(input: String): ValidationResult {
        // password validation logic
        if(input.length < 8) {
            return ValidationResult(
                successful = false,
                errorMessage = "The password needs to consist of at least 8 characters"
            )
        }
        val containsLetterAndDigits = input.any { it.isLetter() } && input.any { it.isDigit() }

        if (!containsLetterAndDigits) {
            return ValidationResult(
                successful = false,
                errorMessage = "The password needs to contain at least one letter and digit"
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}
