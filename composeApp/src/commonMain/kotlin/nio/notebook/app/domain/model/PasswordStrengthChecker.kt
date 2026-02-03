package nio.notebook.app.domain.model

import nio_app.composeapp.generated.resources.Res
import nio_app.composeapp.generated.resources.password_strength_medium
import nio_app.composeapp.generated.resources.password_strength_strong
import nio_app.composeapp.generated.resources.password_strength_very_weak
import nio_app.composeapp.generated.resources.password_strength_weak
import org.jetbrains.compose.resources.StringResource

enum class PasswordStrengthLevel {
    VERY_WEAK, WEAK, MEDIUM, STRONG
}

data class PasswordStrengthState(
    val level: PasswordStrengthLevel,
    val labelRes: StringResource,
    val levelIndex: Int,
    val suggestions: List<PasswordStrengthChecker.Suggestion> = emptyList()
)

class PasswordStrengthChecker(
    private val minLenMedium: Int = 8,
    private val minLenStrong: Int = 12,
) {
    enum class Suggestion {
        MIN_LENGTH,
        UPPERCASE,
        LOWERCASE,
        NUMBERS,
    }

    fun evaluate(password: String): PasswordStrengthState {
        val level = strengthLevel(password)
        val suggestions = getSuggestions(password)
        return PasswordStrengthState(
            level = level,
            labelRes = level.toRes(),
            levelIndex = level.toIndex(),
            suggestions = suggestions
        )
    }

    private fun getSuggestions(pw: String): List<Suggestion> {
        val suggestions = mutableListOf<Suggestion>()
        if (pw.length < minLenStrong) {
            suggestions.add(Suggestion.MIN_LENGTH)
        }
        if (!pw.any { it.isUpperCase() }) {
            suggestions.add(Suggestion.UPPERCASE)
        }
        if (!pw.any { it.isLowerCase() }) {
            suggestions.add(Suggestion.LOWERCASE)
        }
        if (!pw.any { it.isDigit() }) {
            suggestions.add(Suggestion.NUMBERS)
        }
        return suggestions
    }

    private fun strengthLevel(pw: String): PasswordStrengthLevel {
        if (pw.isBlank()) return PasswordStrengthLevel.VERY_WEAK

        var points = 0

        if (pw.length >= minLenMedium) points++
        if (pw.length >= minLenStrong) points++

        val hasLower = pw.any { it.isLowerCase() }
        val hasUpper = pw.any { it.isUpperCase() }
        val hasDigit = pw.any { it.isDigit() }
        val hasSpecial = pw.any { !it.isLetterOrDigit() }

        val classes = listOf(hasLower, hasUpper, hasDigit, hasSpecial).count { it }
        if (classes >= 2) points++
        if (classes >= 3) points++

        if (isTooRepetitive(pw) || hasSimpleSequence(pw)) points = (points - 1).coerceAtLeast(0)

        return when {
            points <= 1 -> PasswordStrengthLevel.VERY_WEAK
            points == 2 -> PasswordStrengthLevel.WEAK
            points == 3 -> PasswordStrengthLevel.MEDIUM
            else -> PasswordStrengthLevel.STRONG
        }
    }

    private fun isTooRepetitive(pw: String): Boolean {
        if (pw.length < 6) return true
        val freq = pw.groupingBy { it }.eachCount()
        val max = freq.values.maxOrNull() ?: 0
        return max.toDouble() / pw.length.toDouble() >= 0.7
    }

    private fun hasSimpleSequence(pw: String): Boolean {
        val s = pw.lowercase()
        val sequences = listOf("0123456789", "abcdefghijklmnopqrstuvwxyz")
        return sequences.any { seq ->
            (0..seq.length - 4).any { i ->
                val sub = seq.substring(i, i + 4)
                s.contains(sub)
            }
        }
    }
}

private fun PasswordStrengthLevel.toIndex(): Int = when (this) {
    PasswordStrengthLevel.VERY_WEAK -> 0
    PasswordStrengthLevel.WEAK -> 1
    PasswordStrengthLevel.MEDIUM -> 2
    PasswordStrengthLevel.STRONG -> 3
}

private fun PasswordStrengthLevel.toRes(): StringResource = when (this) {
    PasswordStrengthLevel.VERY_WEAK -> Res.string.password_strength_very_weak
    PasswordStrengthLevel.WEAK -> Res.string.password_strength_weak
    PasswordStrengthLevel.MEDIUM -> Res.string.password_strength_medium
    PasswordStrengthLevel.STRONG -> Res.string.password_strength_strong
}