package com.example.shaalevikas

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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DonorHallScreen(
    donorList: List<Donor>,
    onBackClick: () -> Unit
) {
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
                text = "Donor Hall of Fame",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = PrimaryGreen
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (donorList.isEmpty()) {
                Text("No pledges yet.")
            } else {
                LazyColumn {
                    items(donorList) { donor ->
                        DonorCard(donor = donor)

                        Spacer(modifier = Modifier.height(12.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun DonorCard(
    donor: Donor
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = donor.name,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = PrimaryGreen
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text("Supported: ${donor.needTitle}")
            Text("Pledged Amount: ₹${donor.amount}")

            if (donor.message.isNotBlank()) {
                Spacer(modifier = Modifier.height(6.dp))
                Text("Message: ${donor.message}")
            }
        }
    }
}