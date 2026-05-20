package com.example.shaalevikas

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
fun AddNeedScreen(
    onBackClick: () -> Unit,
    onSubmitClick: (String, String, Int) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var cost by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var isUploading by remember { mutableStateOf(false) }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        selectedImageUri = uri
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
                text = "Add New School Need",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = PrimaryGreen
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {

                    if (
                        title.isNotBlank() &&
                        description.isNotBlank() &&
                        cost.isNotBlank()
                    ) {

                        onSubmitClick(
                            title,
                            description,
                            cost.toInt()
                        )

                    } else {
                        errorMessage = "Please fill all fields"
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Submit Need")
            }

            Spacer(modifier = Modifier.height(12.dp))

            if (errorMessage.isNotEmpty()) {
                Text(
                    text = errorMessage
                )
            }

            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Need Title") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Need Description") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = cost,
                onValueChange = { cost = it },
                label = { Text("Estimated Cost") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    imagePickerLauncher.launch("image/*")
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Pick Need Image")
            }



            Spacer(modifier = Modifier.height(24.dp))

        }
    }
}
