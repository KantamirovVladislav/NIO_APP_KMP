package nio.notebook.app.presentation.screens.regScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Login
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.AndroidUiModes.UI_MODE_NIGHT_YES
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import nio.notebook.app.presentation.common.EmailViewChecker
import nio.notebook.app.presentation.common.GoogleAuthView
import nio.notebook.app.presentation.common.PasswordViewChecker
import nio.notebook.app.presentation.common.UserNameViewChecker
import nio.notebook.app.presentation.common.VkAuthView
import nio.notebook.app.presentation.navigation.AppRouter
import nio_app.composeapp.generated.resources.Res
import nio_app.composeapp.generated.resources.app_logo
import nio_app.composeapp.generated.resources.app_name
import nio_app.composeapp.generated.resources.auth_sign_in
import nio_app.composeapp.generated.resources.reg_already_have_account
import nio_app.composeapp.generated.resources.reg_label
import nio_app.composeapp.generated.resources.reg_sign_up
import nio_app.composeapp.generated.resources.reg_sub_label
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun ExtendedRegistrationScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    var rememberMe by rememberSaveable { mutableStateOf(true) }

    Surface(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        color = MaterialTheme.colorScheme.background,
        shape = RoundedCornerShape(32.dp)
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = Color.Transparent
        ) { padding ->
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .padding(6.dp)
                        .background(MaterialTheme.colorScheme.background, shape = RoundedCornerShape(32.dp))
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            painterResource(Res.drawable.app_logo),
                            contentDescription = "App logo",
                            tint = Color.Unspecified,
                            modifier = Modifier.size(64.dp)
                        )

                        Spacer(Modifier.width(8.dp))

                        Text(
                            text = stringResource(Res.string.app_name),
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold
                        )
                    }


                    Column(
                        modifier = Modifier.fillMaxSize().padding(horizontal = 86.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
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

                        Button(
                            onClick = { /*TODO*/ },
                            modifier = Modifier.fillMaxWidth().height(52.dp),
                            shape = RoundedCornerShape(12.dp)
                        ) {
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


                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            GoogleAuthView({}, modifier = Modifier.weight(1f).height(52.dp))

                            Spacer(modifier = Modifier.width(8.dp))

                            VkAuthView({}, modifier = Modifier.weight(1f).height(52.dp))
                        }
                    }
                }

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .padding(6.dp)
                        .background(MaterialTheme.colorScheme.primaryContainer, shape = RoundedCornerShape(32.dp))
                )
            }
        }
    }
}

@Preview(locale = "EN", uiMode = UI_MODE_NIGHT_YES, widthDp = 1500, heightDp = 1000)
@Composable
fun ExtendedRegPreview() {
    ExtendedRegistrationScreen(navController = rememberNavController())
}
