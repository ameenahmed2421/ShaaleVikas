package com.example.shaalevikas

data class Need(
    val id: Int = 0,
    val title: String = "",
    val description: String = "",
    val estimatedCost: Int = 0,
    val collectedAmount: Int = 0,
    val imageUrl: String = ""
)

data class Donor(
    val name: String = "",
    val amount: Int = 0,
    val needTitle: String = "",
    val message: String = ""
)

data class AppUser(
    val uid: String = "",
    val email: String = "",
    val role: String = ""
)