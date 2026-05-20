package com.example.shaalevikas

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PledgeSupportScreen(
    need: Need,
    onBackClick: () -> Unit,
    onSubmitClick: (String, Int, String) -> Unit
) {
    var donorName by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

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
                text = "Pledge Support",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = PrimaryGreen
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "For: ${need.title}",
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = donorName,
                onValueChange = { donorName = it },
                label = { Text("Your Name") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = amount,
                onValueChange = { amount = it },
                label = { Text("Pledge Amount") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = message,
                onValueChange = { message = it },
                label = { Text("Message") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    val pledgeAmount = amount.toIntOrNull() ?: 0

                    if (donorName.isBlank()) {
                        errorMessage = "Please enter your name"
                    } else if (pledgeAmount <= 0) {
                        errorMessage = "Please enter a valid pledge amount"
                    } else {
                        errorMessage = ""
                        onSubmitClick(donorName, pledgeAmount, message)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Submit Pledge")
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(errorMessage)
        }
    }
}