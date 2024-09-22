package com.budgetapp.budgetapp.presentation.util.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppBar(
    title: String,
    toBudgetScreen: () -> Unit,
    toLaunchScreen:() -> Unit
) {
    TopAppBar(
        title = { Text(text = title) },
        navigationIcon = { Button(onClick = { toLaunchScreen()}) {
            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Go back")
        }},
        actions = { Button(onClick = {toBudgetScreen()}) {
            Icon(imageVector = Icons.Filled.Menu, contentDescription = "Budget")
        }},
        modifier = Modifier.shadow(5.dp))
}

@Composable
@Preview(showBackground = true)
fun MyTopAppBarPreview() {
    MyTopAppBar(
        title = "Budget App",
        toBudgetScreen = {},
        toLaunchScreen = {},
    )
}