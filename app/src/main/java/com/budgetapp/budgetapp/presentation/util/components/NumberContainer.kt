package com.budgetapp.budgetapp.presentation.util.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun NumberContainer(
    number: Double,
    modifier: Modifier = Modifier,
    onUncheckAllClick: () -> Unit,
    insertBudget: () -> Unit,

) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Large button box on the left
        Box(
            modifier = Modifier
                .weight(1f) // This makes the large box take more space compared to the right Column
                .height(200.dp) // Set desired height
                .padding(start = 16.dp)
                .background(Color(0xFFBBDEFB), shape = RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = "Amount Spent", // Add text inside the large box
                style = MaterialTheme.typography.titleMedium,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 50.dp) // Space between texts
            )
            Text(
                text = "%.2f".format(number),
                style = MaterialTheme.typography.titleMedium,
                color = Color.Black
            )
        }

        // Two smaller button boxes on the right
        Column(
            modifier = Modifier
                .weight(1f) // Distribute remaining space evenly between Row components
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceEvenly // Space buttons evenly in the column
        ) {
            // Save Budget Box
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(90.dp)
                    .background(Color.Green, shape = RoundedCornerShape(8.dp))
                    .clickable { insertBudget() },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Save Budget",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.Black
                )

            }

            Spacer(modifier = Modifier.height(16.dp)) // Space between the two smaller boxes

            // Uncheck All Box
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(90.dp)
                    .background(Color(0xFFFFCDD2), shape = RoundedCornerShape(8.dp))
                    .clickable { onUncheckAllClick() },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Uncheck All",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.Black
                )
            }
        }
    }
}