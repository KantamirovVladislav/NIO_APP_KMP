package nio.notebook.app.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import nio.notebook.app.domain.model.PasswordStrengthState
import org.jetbrains.compose.resources.stringResource

@Composable
fun PasswordStrengthIndicator(
    state: PasswordStrengthState,
    modifier: Modifier = Modifier,
) {
    val label = stringResource(state.labelRes)

    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        StrengthBars(levelIndex = state.levelIndex, modifier = Modifier.fillMaxWidth())
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}

@Composable
private fun StrengthBars(
    levelIndex: Int,
    modifier: Modifier = Modifier,
) {
    val activeColor = when (levelIndex) {
        0 -> Color(0xFFD32F2F)
        1 -> Color(0xFFF57C00)
        2 -> Color(0xFFFBC02D)
        else -> Color(0xFF2E7D32)
    }
    val inactiveColor = MaterialTheme.colorScheme.secondary

    Row(
        modifier = modifier.clip(RoundedCornerShape(999.dp)),
        horizontalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        repeat(4) { i ->
            val isActive = i <= levelIndex
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(4.dp)
                    .background(
                        color = if (isActive) activeColor else inactiveColor,
                    )
            )
        }
    }
}