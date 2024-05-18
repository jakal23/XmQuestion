package com.xm.question.ui.navigation

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    title: String,
    optionItems: List<OptionItem>,
    onNavigationIconClick: (() -> Unit),
    onOptionItemClick: (Int) -> Unit = {},
) {
    TopAppBar(
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
            ) {
                Text(title, color = MaterialTheme.colorScheme.onSurface)

                Spacer(modifier = Modifier.weight(1f))

                OptionMenu(optionItems, onOptionItemClick)
            }
        }, navigationIcon = {
            IconButton(onClick = { onNavigationIconClick() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    tint = MaterialTheme.colorScheme.onSurface,
                    contentDescription = "Back button"
                )
            }
        })
}

@Composable
fun OptionMenu(
    optionItems: List<OptionItem>, onClick: (Int) -> Unit = {}
) {
    optionItems.forEach {
        TextButton(
            onClick = { onClick(it.id) },
            enabled = it.enabled
        ) {
            Text(
                text = it.text,
                modifier = Modifier.padding(end = 12.dp),
                maxLines = 1
            )
        }
    }

}