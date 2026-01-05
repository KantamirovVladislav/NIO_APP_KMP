package nio.notebook.app.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.unit.dp
import nio_app.composeapp.generated.resources.Res
import nio_app.composeapp.generated.resources.cancel_svg
import nio_app.composeapp.generated.resources.confirm_svg
import nio_app.composeapp.generated.resources.folder_open_svg
import org.jetbrains.compose.resources.painterResource
@Composable
fun FilePathPresenter (
    filePath: String?,
    checkResult: Boolean,
    openFolderClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Row(
    modifier = modifier.clip(RoundedCornerShape(corner = CornerSize(12.dp)))
    .background(MaterialTheme.colorScheme.surfaceContainer).padding(6.dp),
    verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = filePath ?: "", modifier = Modifier.weight(0.8f).fillMaxWidth())

        var painter: Painter = painterResource(Res.drawable.cancel_svg)

        if (checkResult) {
            painter = painterResource(Res.drawable.confirm_svg)
        }

        Icon(
            painter,
            null,
            modifier = Modifier.size(32.dp).clip(CircleShape).padding(4.dp),
            tint = if (checkResult) Color.Green else Color.Red
        )

        Spacer(modifier = Modifier.width(6.dp))

        Icon(
            painter = painterResource(Res.drawable.folder_open_svg),
            null,
            modifier = Modifier.size(32.dp).clip(CircleShape).clickable {
                openFolderClick()
            }.pointerHoverIcon(PointerIcon.Hand).padding(4.dp)
        )
    }
}