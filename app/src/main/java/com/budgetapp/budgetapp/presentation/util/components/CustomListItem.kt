package com.budgetapp.budgetapp.presentation.util.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ListItemColors
import androidx.compose.material3.ListItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun CustomListItem(
    headlineContent: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    overlineContent: (@Composable () -> Unit)? = null,
    supportingContent: (@Composable () -> Unit)? = null,
    leadingContent: (@Composable () -> Unit)? = null,
    trailingContent: (@Composable () -> Unit)? = null,
    colors: ListItemColors = ListItemDefaults.colors(),
    tonalElevation: Dp = ListItemDefaults.Elevation,
    shadowElevation: Dp = ListItemDefaults.Elevation,
    checkboxState: Boolean = false, // Add checkboxState parameter
    onCheckboxCheckedChange: (Boolean) -> Unit = {}, // Callback for checkbox state change
) {
    Row(
        modifier = modifier
            .padding(8.dp)
            .clip(RoundedCornerShape(4.dp))
            .background(color = colors.containerColor.copy(alpha = 0.1f))
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .shadow(tonalElevation, RoundedCornerShape(4.dp))
    ) {
        leadingContent?.invoke()
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = if (leadingContent != null) 16.dp else 0.dp)
        ) {
            overlineContent?.invoke()
            headlineContent()
            supportingContent?.invoke()
        }
        trailingContent?.invoke()
        Checkbox(
            checked = checkboxState,
            onCheckedChange = onCheckboxCheckedChange
        )
    }
}