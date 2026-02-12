package nio.notebook.app.presentation.screens.authScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.AndroidUiModes.UI_MODE_NIGHT_YES

import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.compose.NIOTheme

@Composable
fun LongAuthorizationScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    var rememberMe by rememberSaveable { mutableStateOf(true) }

    Scaffold(modifier = modifier.fillMaxSize()) {
        Column{
            Column(
                modifier = Modifier.weight(1f),
            ) {

            }
            Column(
                modifier = Modifier.weight(1f).background(Color.LightGray),
            ) {

            }
        }
    }
}

@Preview(locale = "EN", uiMode = UI_MODE_NIGHT_YES)
@Composable
fun LongAuthPreview() {
    NIOTheme {  LongAuthorizationScreen(rememberNavController()) }
}