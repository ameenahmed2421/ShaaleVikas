package com.example.shaalevikas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HomeScreen(
    onHeadmasterClick: () -> Unit,
    onAlumniClick: () -> Unit
) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Shaale-Vikas",
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                color = PrimaryGreen
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "School-Alumni Bridge",
                fontSize = 18.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Support school repairs, infrastructure needs, and community-driven development.",
                fontSize = 15.sp
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = onHeadmasterClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Continue as Headmaster")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onAlumniClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Continue as Alumni")
            }
        }
    }
}