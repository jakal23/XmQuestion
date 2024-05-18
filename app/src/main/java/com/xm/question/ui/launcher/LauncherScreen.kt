package com.xm.question.ui.launcher

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.xm.question.R
import com.xm.question.ui.launcher.model.LauncherState
import okio.IOException

@Composable
@Preview(showBackground = true)
private fun Preview() {
    LauncherScreen(
        state = LauncherState.Complete(
            Result.failure(IOException("Error 404"))
        )
    )
}

@Composable
fun LauncherScreen(
    modifier: Modifier = Modifier,
    state: LauncherState,
    submit: () -> Unit = {},
    retry: () -> Unit = {}
) {

    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))

        OutlinedButton(
            modifier = Modifier
                .fillMaxWidth()
                .testTag(TAG_BTN_SUBMIT),
            onClick = { submit() },
            enabled = state.isSuccess(),
            shape = RoundedCornerShape(5.dp)
        ) {
            if (state.isLoading()) {
                CircularProgressIndicator()
            } else {
                Text(stringResource(R.string.start_survey))
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        if (state.isFailure()) {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag(TAG_BTN_RETRY),
                onClick = { retry() },
                shape = RoundedCornerShape(5.dp)
            ) {
                Text(stringResource(R.string.retry))
            }
        }
    }
}

const val TAG_BTN_SUBMIT = "btn_submit"
const val TAG_BTN_RETRY = "btn_retry"
