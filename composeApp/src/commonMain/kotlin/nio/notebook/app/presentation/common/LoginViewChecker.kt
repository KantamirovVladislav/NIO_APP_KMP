package nio.notebook.app.presentation.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.unit.dp
import nio_app.composeapp.generated.resources.Res
import nio_app.composeapp.generated.resources.incorrect_login
import nio_app.composeapp.generated.resources.login
import org.jetbrains.compose.resources.stringResource

@Composable
fun UserNameViewChecker(
    onUserNameValid: (String) -> Unit = {},
    modifier: Modifier = Modifier,
) {
    var userName by rememberSaveable { mutableStateOf("") }
    var touched by rememberSaveable { mutableStateOf(false) }

    val trimmed = userName.trim()
    val isValid = trimmed.isNotEmpty() && (trimmed.length > 3)
    val showError = touched && trimmed.isNotEmpty() && !isValid

    Column(modifier = modifier) {
        Text(text = stringResource(Res.string.login), modifier = Modifier.fillMaxWidth(), fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.size(8.dp))

        OutlinedTextField(
            value = userName,
            onValueChange = {
                touched = true
                userName = it
            },
            textStyle = MaterialTheme.typography.titleSmall,
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("my name") },
            leadingIcon = { Icon(Icons.Outlined.AccountCircle, contentDescription = null) },
            shape = RoundedCornerShape(12.dp),
            singleLine = true,
            isError = showError,
            supportingText = {
                if (showError) Text(stringResource(Res.string.incorrect_login))
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    if (isValid) onUserNameValid(trimmed)
                }
            )
        )
    }

}


@androidx.compose.ui.tooling.preview.Preview(locale = "EN")
@Composable
fun LoginPreview() {
    MaterialTheme {
        UserNameViewChecker({})
    }
}