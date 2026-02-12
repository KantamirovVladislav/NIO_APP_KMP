package nio.notebook.app.presentation.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.AndroidUiModes.UI_MODE_NIGHT_YES
import androidx.compose.ui.unit.dp
import nio_app.composeapp.generated.resources.Res
import nio_app.composeapp.generated.resources.email_address
import org.jetbrains.compose.resources.stringResource
import androidx.compose.runtime.*
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.automirrored.outlined.Login
import androidx.compose.material.icons.outlined.Login
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import nio_app.composeapp.generated.resources.incorrect_email
import nio_app.composeapp.generated.resources.login
import org.jetbrains.compose.resources.stringResource


private val emailAddressRegex = Regex(
    "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
            "\\@" +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
            "(" +
            "\\." +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
            ")+"
)

fun isValidEmail(email: String): Boolean {
    return email.matches(emailAddressRegex)
}


@Composable
fun EmailViewChecker(
    onEmailValid: (String) -> Unit = {},
    modifier: Modifier = Modifier,
) {
    var email by rememberSaveable { mutableStateOf("") }
    var touched by rememberSaveable { mutableStateOf(false) }

    val trimmed = email.trim()
    val isValid = trimmed.isNotEmpty() && isValidEmail(trimmed)
    val showError = touched && trimmed.isNotEmpty() && !isValid
    Column(modifier = modifier) {
        Text(text = stringResource(Res.string.email_address), modifier = Modifier.fillMaxWidth(), fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.size(12.dp))

        OutlinedTextField(
            value = email,
            onValueChange = {
                touched = true
                email = it
            },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("example@gmail.com") },
            leadingIcon = { Icon(Icons.Outlined.Email, contentDescription = null) },
            shape = RoundedCornerShape(12.dp),
            singleLine = true,
            isError = showError,
            supportingText = {
                if (showError) Text(stringResource(Res.string.incorrect_email))
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    if (isValid) onEmailValid(trimmed)
                }
            )
        )
    }

}


@androidx.compose.ui.tooling.preview.Preview(locale = "EN")
@Composable
fun EmailAddressPreview() {
    MaterialTheme {
        EmailViewChecker({})
    }
}