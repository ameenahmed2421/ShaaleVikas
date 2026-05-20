package com.example.shaalevikas

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun NeedDetailsScreen(
    need: Need,
    onBackClick: () -> Unit,
    onPledgeClick: () -> Unit
) {
    val progress = if (need.estimatedCost > 0) {
        need.collectedAmount.toFloat() / need.estimatedCost.toFloat()
    } else {
        0f
    }

    val progressPercent = (progress * 100).toInt()

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize()
        ) {
            Button(onClick = onBackClick) {
                Text("Back")
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = need.title,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = PrimaryGreen
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = need.description,
                fontSize = 16.sp
            )



            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Estimated Cost: ₹${need.estimatedCost}",
                fontSize = 18.sp
            )

            Text(
                text = "Collected Amount: ₹${need.collectedAmount}",
                fontSize = 18.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            LinearProgressIndicator(
                progress = progress.coerceIn(0f, 1f),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(10.dp),
                color = PrimaryGreen,
                trackColor = LightGreen
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text("$progressPercent% funds collected")

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = onPledgeClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Pledge Support")
            }
        }
    }
}