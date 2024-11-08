package com.budgetapp.budgetapp.presentation.util.components

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppBar(
    title: String,
    toBudgetScreen: () -> Unit,
    toAccessScreen:() -> Unit,
    showNavigationIcon: Boolean,
    showBudgetScreen: Boolean,
    content: @Composable (Modifier) -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),

        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(
                        "Budget App",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    if (showNavigationIcon) {
                        IconButton(onClick = { toAccessScreen() }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Localized description"
                            )
                        }
                    }
                },
                actions = {
                    IconButton(onClick = { toBudgetScreen}) {
                        Icon(
                            imageVector = Icons.Filled.Menu,
                            contentDescription = "Localized description"
                        )
                    }
                },
                scrollBehavior = scrollBehavior,
            )
        },
    ) { innerPadding ->
        content(Modifier.padding(innerPadding))
    }
//    TopAppBar(
//        title = { Text(text = title) },
//
//        navigationIcon = {
//            if(showNavigationIcon) {
//            Button(onClick = { toAccessScreen()}) {
//                Icon(
//                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
//                    contentDescription = "Go back"
//                )
//            }}},
//        actions = {
//            if(showBudgetScreen){
//            Button(onClick = {toBudgetScreen()}) {
//                Icon(imageVector = Icons.Filled.Menu, contentDescription = "Budget")
//            }
//            }},
//        modifier = Modifier
//            .shadow(5.dp),
//
//    )

}

//@Composable
//@Preview(showBackground = true)
//fun MyTopAppBarPreview() {
//    MyTopAppBar(
//        title = "Budget App",
//        toBudgetScreen = {},
//        toAccessScreen = {},
//        showNavigationIcon = false,
//        showBudgetScreen = true
//    )
//}