package nio.notebook.app.domain.model

import nio.notebook.app.domain.model.PasswordStrengthChecker.Suggestion

/**
 * Represents the validation state of a single password rule.
 *
 * @param suggestion The password rule being checked.
 * @param isValid True if the password meets the rule's criteria, false otherwise.
 */
data class RuleValidationState(
    val suggestion: Suggestion,
    val isValid: Boolean
)

/**
 * A checker that evaluates a password against a predefined set of rules.
 * Instead of calculating a general strength level, it returns the validation status for each individual rule.
 *
 * @param minLength The minimum required length for the password.
 */
class PasswordRulesChecker(
    private val minLength: Int = 12
) {
    // A list of all rules that will be checked.
    private val rules = listOf(
        Suggestion.MIN_LENGTH,
        Suggestion.UPPERCASE,
        Suggestion.LOWERCASE,
        Suggestion.NUMBERS
    )

    /**
     * Evaluates the given password against all defined rules.
     *
     * @param password The password string to evaluate.
     * @return A list of [RuleValidationState] objects, one for each rule.
     */
    fun evaluate(password: String): List<RuleValidationState> {
        // If the password is empty, all rules are considered not met.
        if (password.isEmpty()) {
            return rules.map { RuleValidationState(suggestion = it, isValid = false) }
        }

        return rules.map { suggestion ->
            val isValid = when (suggestion) {
                Suggestion.MIN_LENGTH -> password.length >= minLength
                Suggestion.UPPERCASE -> password.any { it.isUpperCase() }
                Suggestion.LOWERCASE -> password.any { it.isLowerCase() }
                Suggestion.NUMBERS -> password.any { it.isDigit() }
            }
            RuleValidationState(suggestion = suggestion, isValid = isValid)
        }
    }

    /**
     * Checks if the password is fully valid by ensuring it meets all defined rules.
     *
     * @param password The password string to check.
     * @return True if all rules are met, false otherwise.
     */
    fun isPasswordValid(password: String): Boolean {
        if (password.isEmpty()) return false
        return evaluate(password).all { it.isValid }
    }
}