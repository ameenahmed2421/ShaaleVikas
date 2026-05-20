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
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LoginScreen(
    selectedRole: String,
    onLoginSuccess: (String) -> Unit
) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isSignup by remember { mutableStateOf(false) }
    var message by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {

        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize()
        ) {

            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = if (isSignup) {
                    "Create Account"
                } else {
                    "Login"
                },
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Role: ${selectedRole.replaceFirstChar { it.uppercase() }}",
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = email,
                onValueChange = {
                    email = it
                },
                label = {
                    Text("Email")
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = password,
                onValueChange = {
                    password = it
                },
                label = {
                    Text("Password")
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                visualTransformation = PasswordVisualTransformation()
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {

                    if (email.isBlank()) {
                        message = "Please enter email"
                        return@Button
                    }

                    if (password.length < 6) {
                        message = "Password must be at least 6 characters"
                        return@Button
                    }

                    isLoading = true
                    message = ""

                    // SIGN UP
                    if (isSignup) {

                        AuthRepository.signup(
                            email = email.trim(),
                            password = password
                        ) { success, resultMessage ->

                            if (success) {

                                val uid =
                                    AuthRepository.getCurrentUserId()

                                val userEmail =
                                    AuthRepository.getCurrentUserEmail()

                                FirebaseRepository.saveUserRole(
                                    uid = uid,
                                    email = userEmail,
                                    role = selectedRole.lowercase()
                                ) { roleSaved, roleMessage ->

                                    isLoading = false

                                    if (roleSaved) {

                                        onLoginSuccess(
                                            selectedRole.lowercase()
                                        )

                                    } else {
                                        message = roleMessage
                                    }
                                }

                            } else {

                                isLoading = false
                                message = resultMessage
                            }
                        }

                    }

                    // LOGIN
                    else {

                        AuthRepository.login(
                            email = email.trim(),
                            password = password
                        ) { success, resultMessage ->

                            isLoading = false

                            if (success) {

                                val uid =
                                    AuthRepository.getCurrentUserId()

                                FirebaseRepository.getUserRole(uid) { role ->

                                    onLoginSuccess(
                                        role.lowercase()
                                    )
                                }

                            } else {

                                message = resultMessage
                            }
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = !isLoading
            ) {

                Text(
                    text = if (isLoading) {
                        "Please wait..."
                    } else if (isSignup) {
                        "Sign Up"
                    } else {
                        "Login"
                    }
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            TextButton(
                onClick = {
                    isSignup = !isSignup
                    message = ""
                }
            ) {

                Text(
                    text = if (isSignup) {
                        "Already have an account? Login"
                    } else {
                        "Don't have an account? Sign Up"
                    }
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            if (message.isNotEmpty()) {
                Text(message)
            }
        }
    }
}