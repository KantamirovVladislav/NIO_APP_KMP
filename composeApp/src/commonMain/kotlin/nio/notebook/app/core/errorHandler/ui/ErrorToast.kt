package nio.notebook.app.core.errorHandler.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay


enum class ErrorViewPosition { TopLeft, TopRight, BottomLeft, BottomRight, Center }

@Composable
fun ErrorToast(
    message: String,
    position: ErrorViewPosition,
    onDismiss: () -> Unit = {}
) {
    var visible by remember { mutableStateOf(true) }

    LaunchedEffect(message) {
        delay(3000)
        visible = false
        onDismiss()
    }

    if (!visible) return

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Surface(
            shape = RoundedCornerShape(8.dp),
            tonalElevation = 6.dp,
            modifier = Modifier
                .align(
                    when (position) {
                        ErrorViewPosition.TopLeft -> Alignment.TopStart
                        ErrorViewPosition.TopRight -> Alignment.TopEnd
                        ErrorViewPosition.BottomLeft -> Alignment.BottomStart
                        ErrorViewPosition.BottomRight -> Alignment.BottomEnd
                        ErrorViewPosition.Center -> Alignment.Center
                    }
                )
                .clickable {
                    visible = false
                    onDismiss()
                }
        ) {
            Text(
                text = message,
                modifier = Modifier.padding(12.dp)
            )
        }
    }
}
