package com.example.shaalevikas

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter

@Composable
fun ImpactPhotosScreen(
    onBackClick: () -> Unit
) {

    var beforeImageUri by remember {
        mutableStateOf<Uri?>(null)
    }

    var afterImageUri by remember {
        mutableStateOf<Uri?>(null)
    }

    val beforeLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent()
        ) { uri: Uri? ->
            beforeImageUri = uri
        }

    val afterLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent()
        ) { uri: Uri? ->
            afterImageUri = uri
        }

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
                text = "Impact Photos",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = PrimaryGreen
            )

            Spacer(modifier = Modifier.height(16.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
            ) {

                Column(
                    modifier = Modifier.padding(16.dp)
                ) {

                    Text(
                        text = "Toilet Repair Completed",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = PrimaryGreen
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Before Photo",
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    if (beforeImageUri != null) {

                        Image(
                            painter = rememberAsyncImagePainter(beforeImageUri),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(180.dp)
                                .clip(RoundedCornerShape(12.dp)),
                            contentScale = ContentScale.Crop
                        )

                    } else {

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(180.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("No Before Photo Selected")
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Button(
                        onClick = {
                            beforeLauncher.launch("image/*")
                        }
                    ) {
                        Text("Select Before Photo")
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        text = "After Photo",
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    if (afterImageUri != null) {

                        Image(
                            painter = rememberAsyncImagePainter(afterImageUri),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(180.dp)
                                .clip(RoundedCornerShape(12.dp)),
                            contentScale = ContentScale.Crop
                        )

                    } else {

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(180.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("No After Photo Selected")
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Button(
                        onClick = {
                            afterLauncher.launch("image/*")
                        }
                    ) {
                        Text("Select After Photo")
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Impact: The repaired toilet block is now safe and usable for students."
                    )
                }
            }
        }
    }
}