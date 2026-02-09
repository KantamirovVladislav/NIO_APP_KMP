package nio.notebook.app.presentation.screens.regScreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Login
import androidx.compose.material.icons.outlined.Login
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import nio.notebook.app.presentation.common.PasswordViewChecker
import nio.notebook.app.presentation.common.PasswordViewCheckerV2
import nio.notebook.app.presentation.common.UserNameViewChecker
import nio.notebook.app.presentation.common.VkAuthView
import nio.notebook.app.presentation.navigation.AppRouter
import nio_app.composeapp.generated.resources.Res
import nio_app.composeapp.generated.resources.app_logo
import nio_app.composeapp.generated.resources.auth_sign_in
import nio_app.composeapp.generated.resources.compose_multiplatform
import nio_app.composeapp.generated.resources.reg_already_have_account
import nio_app.composeapp.generated.resources.reg_label
import nio_app.composeapp.generated.resources.reg_sign_up
import nio_app.composeapp.generated.resources.reg_sub_label
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun MobileRegistrationScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
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
                    Column(
                        modifier = Modifier.fillMaxWidth().padding(12.dp)
                    ) {
                        Icon(
                            painterResource(Res.drawable.app_logo),
                            contentDescription = "App logo",
                            tint = Color.Unspecified,
                            modifier = Modifier.size(64.dp)
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        Text(
                            text = stringResource(Res.string.reg_label),
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = stringResource(Res.string.reg_sub_label),
                            style = MaterialTheme.typography.titleSmall
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        UserNameViewChecker(
                            {}
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        EmailViewChecker(
                            {}
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        PasswordViewChecker({})

                        Spacer(modifier = Modifier.height(24.dp))

                        Button(onClick = { /*TODO*/ }, modifier = Modifier.fillMaxWidth().height(44.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(text = stringResource(Res.string.reg_sign_up))
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
                                text = stringResource(Res.string.reg_already_have_account),
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.width(2.dp))
                            Text(
                                text = stringResource(Res.string.auth_sign_in),
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary,
                                textDecoration = TextDecoration.Underline,
                                modifier = Modifier.clickable {
                                    navController.navigate(AppRouter.Login.route)
                                }
                            )
                        }

                        Spacer(modifier = Modifier.height(24.dp))

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
    NIOTheme {
        MobileRegistrationScreen(rememberNavController())
    }

}