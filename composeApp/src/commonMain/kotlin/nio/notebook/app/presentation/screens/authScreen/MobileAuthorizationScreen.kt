package nio.notebook.app.presentation.screens.authScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Login
import androidx.compose.material3.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.AndroidUiModes.UI_MODE_NIGHT_YES
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.compose.NIOTheme
import nio.notebook.app.presentation.common.EmailViewChecker
import nio.notebook.app.presentation.common.GoogleAuthView
import nio.notebook.app.presentation.common.SimplePasswordView
import nio.notebook.app.presentation.common.VkAuthView
import nio.notebook.app.presentation.navigation.AppRouter
import nio.notebook.app.presentation.screens.regScreen.MobileRegistrationScreen
import nio_app.composeapp.generated.resources.*
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun MobileAuthorizationScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    var rememberMe by rememberSaveable { mutableStateOf(true) }

    Scaffold(modifier = modifier.fillMaxSize()) {
        Box(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(

                                MaterialTheme.colorScheme.primaryContainer,
                                MaterialTheme.colorScheme.secondaryContainer,
                                MaterialTheme.colorScheme.tertiary,

                                ),
                            start = Offset(0f, 0f)
                        )
                    )
            )
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center

            ) {
                Card(
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 8.dp
                    ),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White

                    ),
                    shape = RoundedCornerShape(corner = CornerSize(32.dp)),
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp)
                ) {
                    Column(modifier = Modifier.fillMaxWidth().padding(12.dp)) {
                        Icon(
                            painterResource(Res.drawable.app_logo),
                            contentDescription = "App logo",
                            tint = Color.Unspecified,
                            modifier = Modifier.size(64.dp)
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        Text(
                            text = stringResource(Res.string.auth_label),
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = stringResource(Res.string.reg_sub_label),
                            style = MaterialTheme.typography.titleSmall
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        EmailViewChecker(
                            {}
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        SimplePasswordView({})

                        Spacer(modifier = Modifier.height(3.dp))

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start,
                        ) {
                            Checkbox(checked = true, onCheckedChange = {
                                rememberMe = !rememberMe
                            }, modifier = Modifier.size(24.dp).scale(0.8f))
                            Spacer(modifier = Modifier.width(3.dp))
                            Text(
                                style = MaterialTheme.typography.bodyMedium,
                                text = stringResource(Res.string.auth_remember_me),
                            )
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        Button(onClick = { /*TODO*/ }, modifier = Modifier.fillMaxWidth().height(44.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(text = stringResource(Res.string.auth_sign_in))
                                Spacer(modifier = Modifier.width(8.dp))
                                Icon(imageVector = Icons.AutoMirrored.Outlined.Login, contentDescription = null)
                            }

                        }

                        Spacer(modifier = Modifier.height(24.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = stringResource(Res.string.auth_dont_have_account),
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.width(2.dp))
                            Text(
                                text = stringResource(Res.string.reg_sign_up),
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary,
                                textDecoration = TextDecoration.Underline,
                                modifier = Modifier.clickable{
                                    navController.navigate(AppRouter.Registration.route)
                                }
                            )
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = stringResource(Res.string.forgot_password),
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            HorizontalDivider(
                                modifier = Modifier.weight(1f),
                                thickness = 2.dp,
                                color = MaterialTheme.colorScheme.surfaceDim
                            )

                            Text(text = "or", modifier = Modifier.padding(horizontal = 8.dp))

                            HorizontalDivider(
                                modifier = Modifier.weight(1f),
                                thickness = 2.dp,
                                color = MaterialTheme.colorScheme.surfaceDim
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        GoogleAuthView({})

                        Spacer(modifier = Modifier.height(2.dp))

                        VkAuthView({})
                    }
                }
            }
        }
    }
}

@Preview(locale = "EN", uiMode = UI_MODE_NIGHT_YES)
@Composable
fun RegPreview() {
    NIOTheme {  MobileAuthorizationScreen(rememberNavController()) }
}