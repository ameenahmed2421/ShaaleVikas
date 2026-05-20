package com.example.shaalevikas

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun NeedsDashboardScreen(
    needsList: List<Need>,
    role: String,
    onNeedClick: (Need) -> Unit,
    onAddNeedClick: () -> Unit,
    onDonorHallClick: () -> Unit,
    onImpactPhotosClick: () -> Unit,
    onBackClick: () -> Unit
) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Button(onClick = onBackClick) {
                Text("Logout / Back")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Current School Needs",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = PrimaryGreen
            )

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = onDonorHallClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("View Donor Hall of Fame")
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = onImpactPhotosClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("View Impact Photos")
            }

            Spacer(modifier = Modifier.height(8.dp))

            if (true) {
                Button(
                    onClick = onAddNeedClick,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Add New School Need")
                }

                Spacer(modifier = Modifier.height(16.dp))
            } else {
                Spacer(modifier = Modifier.height(16.dp))
            }

            if (needsList.isEmpty()) {
                Text("No school needs added yet.")
            } else {
                LazyColumn {
                    items(needsList) { need ->
                        NeedCard(
                            need = need,
                            onClick = { onNeedClick(need) }
                        )

                        Spacer(modifier = Modifier.height(12.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun NeedCard(
    need: Need,
    onClick: () -> Unit
) {
    val progress = if (need.estimatedCost > 0) {
        need.collectedAmount.toFloat() / need.estimatedCost.toFloat()
    } else {
        0f
    }

    val progressPercent = (progress * 100).toInt()

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = need.title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = PrimaryGreen
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(need.description)



            Spacer(modifier = Modifier.height(8.dp))

            Text("Estimated Cost: ₹${need.estimatedCost}")
            Text("Collected: ₹${need.collectedAmount}")

            Spacer(modifier = Modifier.height(8.dp))

            LinearProgressIndicator(
                progress = progress.coerceIn(0f, 1f),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(10.dp),
                color = PrimaryGreen,
                trackColor = LightGreen
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text("$progressPercent% funds collected")
        }
    }
}