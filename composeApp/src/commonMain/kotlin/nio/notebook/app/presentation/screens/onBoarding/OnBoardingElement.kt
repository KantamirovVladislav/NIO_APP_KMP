package nio.notebook.app.presentation.screens.onBoarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import nio_app.composeapp.generated.resources.Res
import nio_app.composeapp.generated.resources.get_started
import nio_app.composeapp.generated.resources.next
import org.jetbrains.compose.resources.stringResource

@Composable
fun OnBoardingElement(
    progress: Int = 0,
    progressCount: Int = 3,
    title: String,
    content: String,
    onNextClicked: () -> Unit = {},
    onBackClicked: () -> Unit = {},
    modifier: Modifier = Modifier
) {
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
                            text = title,
                            style = MaterialTheme.typography.headlineLarge,
                            textAlign = TextAlign.Center,
                            color = Color.Unspecified.copy(alpha = 0.8f),
                        )

                        Spacer(modifier = Modifier.height(28.dp))

                        Text(
                            text = content,
                            style = MaterialTheme.typography.bodySmall,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.8f),
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth().padding(horizontal = 36.dp)
                        ) {
                            Button(
                                onClick = onNextClicked,
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(corner = CornerSize(8.dp))
                            ) {
                                Text(text = if (progress < progressCount - 1) stringResource(Res.string.next) else stringResource(Res.string.get_started) )
                            }
                        }
                    }
                }

                Spacer(Modifier.height(24.dp))

//                CirclesProgressIndicator(
//                    progress = progress,
//                    maxCount = progressCount,
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(16.dp)
//                )
            }
        }
    }
}