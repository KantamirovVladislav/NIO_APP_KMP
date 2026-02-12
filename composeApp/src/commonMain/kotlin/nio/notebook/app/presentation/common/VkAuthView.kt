package nio.notebook.app.presentation.common

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import nio_app.composeapp.generated.resources.Res
import nio_app.composeapp.generated.resources.continue_with_google
import nio_app.composeapp.generated.resources.continue_with_vk_id
import nio_app.composeapp.generated.resources.icon_google
import nio_app.composeapp.generated.resources.icon_vk
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource

@Composable
fun VkAuthView(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedButton(
        onClick = onClick,
        shape = RoundedCornerShape(12.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = vectorResource(Res.drawable.icon_vk),
                contentDescription = "Vk Icon",
                modifier = Modifier.align(Alignment.CenterVertically).size(24.dp),
                tint = Color.Unspecified
            )

            Spacer(Modifier.width(8.dp))

            Text(
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.align(Alignment.CenterVertically),
                text = stringResource(Res.string.continue_with_vk_id),
            )
        }
    }
}