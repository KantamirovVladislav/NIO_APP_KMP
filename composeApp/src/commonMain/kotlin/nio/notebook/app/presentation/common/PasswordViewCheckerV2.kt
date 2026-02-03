package nio.notebook.app.presentation.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.outlined.Password
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import nio.notebook.app.domain.model.PasswordRulesChecker
import nio.notebook.app.domain.model.PasswordStrengthChecker
import nio_app.composeapp.generated.resources.Res
import nio_app.composeapp.generated.resources.password
import nio_app.composeapp.generated.resources.password_strength_suggestion_lowercase
import nio_app.composeapp.generated.resources.password_strength_suggestion_min_length
import nio_app.composeapp.generated.resources.password_strength_suggestion_numbers
import nio_app.composeapp.generated.resources.password_strength_suggestion_uppercase
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PasswordViewCheckerV2(
    onPasswordValid: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    var password by rememberSaveable { mutableStateOf("") }
    var touched by rememberSaveable { mutableStateOf(false) }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    val checker = PasswordRulesChecker()
    val ruleStates = checker.evaluate(password)
    val isPasswordValid = checker.isPasswordValid(password)

    Column(modifier = modifier) {
        // --- ЗАГОЛОВОК ---
        Text(
            text = stringResource(Res.string.password),
            modifier = Modifier.fillMaxWidth(),
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.size(12.dp))

        // --- ПОЛЕ ВВОДА ПАРОЛЯ ---
        OutlinedTextField(
            value = password,
            onValueChange = {
                touched = true
                password = it
            },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text(stringResource(Res.string.password)) },
            leadingIcon = { Icon(Icons.Outlined.Password, contentDescription = null) },
            trailingIcon = {
                val image = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                val description = if (passwordVisible) "Hide password" else "Show password"
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(imageVector = image, contentDescription = description)
                }
            },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            shape = RoundedCornerShape(32.dp),
            singleLine = true,
            isError = touched && password.isNotEmpty() && !isPasswordValid,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    if (isPasswordValid) onPasswordValid(password)
                }
            )
        )

        Spacer(modifier = Modifier.size(8.dp))

        // --- ФИШКИ С ПРАВИЛАМИ ---
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            ruleStates.forEach { state ->
                val text = when (state.suggestion) {
                    PasswordStrengthChecker.Suggestion.MIN_LENGTH -> stringResource(Res.string.password_strength_suggestion_min_length)
                    PasswordStrengthChecker.Suggestion.UPPERCASE -> stringResource(Res.string.password_strength_suggestion_uppercase)
                    PasswordStrengthChecker.Suggestion.LOWERCASE -> stringResource(Res.string.password_strength_suggestion_lowercase)
                    PasswordStrengthChecker.Suggestion.NUMBERS -> stringResource(Res.string.password_strength_suggestion_numbers)
                }

                val icon = Icons.Default.Check
                val chipColors = if (state.isValid) {
                    AssistChipDefaults.assistChipColors(
                        containerColor = Color(0xFF4CAF50).copy(alpha = 0.1f),
                        labelColor = MaterialTheme.colorScheme.onSurface,
                        leadingIconContentColor = Color(0xFF4CAF50)
                    )
                } else {
                    AssistChipDefaults.assistChipColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant,
                        labelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        leadingIconContentColor = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                AssistChip(
                    modifier = Modifier.height(28.dp),
                    onClick = { },
                    label = { Text(text, style = MaterialTheme.typography.labelMedium) },
                    leadingIcon = {
                        Icon(
                            imageVector = icon,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                    },
                    shape = RoundedCornerShape(32.dp),
                    colors = chipColors
                )

                Spacer(modifier = Modifier.size(2.dp))
            }
        }
    }
}