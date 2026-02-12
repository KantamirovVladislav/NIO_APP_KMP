package nio.notebook.app.presentation.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.outlined.Password
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedSecureTextField
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import nio.notebook.app.domain.model.PasswordStrengthChecker
import nio_app.composeapp.generated.resources.Res
import nio_app.composeapp.generated.resources.password
import nio_app.composeapp.generated.resources.password_strength_suggestion_lowercase
import nio_app.composeapp.generated.resources.password_strength_suggestion_min_length
import nio_app.composeapp.generated.resources.password_strength_suggestion_numbers
import nio_app.composeapp.generated.resources.password_strength_suggestion_uppercase
import org.jetbrains.compose.resources.stringResource

@Composable
fun PasswordViewChecker(
    onPasswordValid: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    var password by rememberSaveable { mutableStateOf("") }
    var touched by rememberSaveable { mutableStateOf(false) }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    val checker = PasswordStrengthChecker()
    val state = checker.evaluate(password)

    val trimmed = password.trim()
    val isValid = trimmed.isNotEmpty() && state.levelIndex == 3

    Column(modifier = modifier) {
        Text(
            text = stringResource(Res.string.password),
            modifier = Modifier.fillMaxWidth(),
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.size(12.dp))

        OutlinedTextField(
            value = password,
            onValueChange = {
                touched = true
                password = it
            },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("password") },
            leadingIcon = { Icon(Icons.Outlined.Password, contentDescription = null) },
            trailingIcon = {
                val image = if (passwordVisible)
                    Icons.Filled.Visibility
                else Icons.Filled.VisibilityOff

                val description = if (passwordVisible) "Hide password" else "Show password"

                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(imageVector = image, contentDescription = description)
                }
            },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            shape = RoundedCornerShape(12.dp),
            singleLine = true,
            isError = touched && password.isNotEmpty() && !isValid,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    if (isValid) onPasswordValid(trimmed)
                }
            )
        )

        Spacer(modifier = Modifier.size(8.dp))

        PasswordStrengthIndicator(
            state = state,
            modifier = Modifier.fillMaxWidth()
        )
    }
}