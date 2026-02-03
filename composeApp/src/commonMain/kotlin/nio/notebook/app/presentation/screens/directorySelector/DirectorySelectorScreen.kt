package nio.notebook.app.presentation.screens.directorySelector

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import io.github.vinceglb.filekit.FileKit
import io.github.vinceglb.filekit.dialogs.openDirectoryPicker
import io.github.vinceglb.filekit.path
import nio.notebook.app.domain.model.DirectoryAccessStatus
import nio.notebook.app.presentation.common.FilePathPresenter
import nio.notebook.app.presentation.navigation.AppRouter
import nio_app.composeapp.generated.resources.Res
import nio_app.composeapp.generated.resources.app_name
import nio_app.composeapp.generated.resources.dir_status_idle
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun DirectorySelectorScreen(
    viewModel: DirectorySelectorViewModel = koinViewModel(),
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    val uiState = viewModel.uiState.collectAsState()
    val nextScreen = viewModel.screen.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadAndCheckRootDirectory()
    }

    LaunchedEffect(uiState.value.showDialog) {
        if (uiState.value.showDialog) {
            val dir = FileKit.openDirectoryPicker(
                title = Res.string.dir_status_idle.toString(),
                directory = uiState.value.selected,
            )
            viewModel.onDirectoryPicked(dir)
        }
    }

    LaunchedEffect(nextScreen.value){
        if (nextScreen.value){
            navController.navigate(AppRouter.Registration.route)
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .background(Color.Gray)
        )

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .offset(y = (-16).dp),
            contentAlignment = Alignment.TopCenter
        ) {
            Column {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    shape = RoundedCornerShape(corner = CornerSize(8.dp))
                ) {
                    Column(
                        modifier = Modifier.padding(36.dp),
                        verticalArrangement = Arrangement.Bottom,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            statusText(uiState.value.status),
                            style = MaterialTheme.typography.headlineSmall,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )

                        FilePathPresenter(
                            filePath = uiState.value.selected?.path,
                            checkResult = uiState.value.status == DirectoryAccessStatus.Ok,
                            openFolderClick = viewModel::openDialog
                        )

                        Button(
                            onClick = viewModel::saveAsRootIfOk,
                            modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp),
                            shape = RoundedCornerShape(corner = CornerSize(8.dp)),
                            enabled = uiState.value.status == DirectoryAccessStatus.Ok
                        ) {
                            Text("Continue")
                        }
                    }
                }
            }
        }
    }
}