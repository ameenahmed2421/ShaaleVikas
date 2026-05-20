package com.example.shaalevikas

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

object AuthRepository {

    private val auth = FirebaseAuth.getInstance()

    fun signup(
        email: String,
        password: String,
        onResult: (Boolean, String) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onResult(true, "Signup successful")
                } else {
                    onResult(false, task.exception?.message ?: "Signup failed")
                }
            }
    }

    fun login(
        email: String,
        password: String,
        onResult: (Boolean, String) -> Unit
    ) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onResult(true, "Login successful")
                } else {
                    onResult(false, task.exception?.message ?: "Login failed")
                }
            }
    }

    fun logout() {
        auth.signOut()
    }

    fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }

    fun getCurrentUserId(): String {
        return auth.currentUser?.uid ?: ""
    }

    fun getCurrentUserEmail(): String {
        return auth.currentUser?.email ?: ""
    }

    fun isUserLoggedIn(): Boolean {
        return auth.currentUser != null
    }
}