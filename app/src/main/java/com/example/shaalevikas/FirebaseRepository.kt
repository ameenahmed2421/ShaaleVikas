package com.example.shaalevikas

import android.net.Uri
import com.google.firebase.firestore.FirebaseFirestore

object FirebaseRepository {

    private val db = FirebaseFirestore.getInstance()

    fun saveUserRole(
        uid: String,
        email: String,
        role: String,
        onResult: (Boolean, String) -> Unit
    ) {
        val userMap = hashMapOf(
            "uid" to uid,
            "email" to email,
            "role" to role
        )

        db.collection("users")
            .document(uid)
            .set(userMap)
            .addOnSuccessListener {
                onResult(true, "User role saved")
            }
            .addOnFailureListener { exception ->
                onResult(false, exception.message ?: "Failed to save user role")
            }
    }

    fun getUserRole(
        uid: String,
        onResult: (String) -> Unit
    ) {
        db.collection("users")
            .document(uid)
            .get()
            .addOnSuccessListener { document ->
                val role = document.getString("role") ?: "alumni"
                onResult(role)
            }
            .addOnFailureListener {
                onResult("alumni")
            }
    }

   

    fun addNeed(need: Need) {
        val needMap = hashMapOf(
            "id" to need.id,
            "title" to need.title,
            "description" to need.description,
            "estimatedCost" to need.estimatedCost,
            "collectedAmount" to need.collectedAmount,
            "imageUrl" to need.imageUrl
        )

        db.collection("needs")
            .add(needMap)
    }

    fun getNeedsRealtime(
        onNeedsChanged: (List<Need>) -> Unit
    ) {
        db.collection("needs")
            .addSnapshotListener { snapshot, error ->

                if (error != null || snapshot == null) {
                    return@addSnapshotListener
                }

                val needs = snapshot.documents.mapNotNull { doc ->
                    val id = doc.getLong("id")?.toInt() ?: 0
                    val title = doc.getString("title") ?: ""
                    val description = doc.getString("description") ?: ""
                    val estimatedCost = doc.getLong("estimatedCost")?.toInt() ?: 0
                    val collectedAmount = doc.getLong("collectedAmount")?.toInt() ?: 0
                    val imageUrl = doc.getString("imageUrl") ?: ""

                    Need(
                        id = id,
                        title = title,
                        description = description,
                        estimatedCost = estimatedCost,
                        collectedAmount = collectedAmount,
                        imageUrl = imageUrl
                    )
                }

                onNeedsChanged(needs)
            }
    }

    fun updateNeedAmount(
        needId: Int,
        newCollectedAmount: Int
    ) {
        db.collection("needs")
            .whereEqualTo("id", needId)
            .get()
            .addOnSuccessListener { snapshot ->
                for (document in snapshot.documents) {
                    db.collection("needs")
                        .document(document.id)
                        .update("collectedAmount", newCollectedAmount)
                }
            }
    }

    fun addDonor(donor: Donor) {
        val donorMap = hashMapOf(
            "name" to donor.name,
            "amount" to donor.amount,
            "needTitle" to donor.needTitle,
            "message" to donor.message
        )

        db.collection("donors")
            .add(donorMap)
    }

    fun getDonorsRealtime(
        onDonorsChanged: (List<Donor>) -> Unit
    ) {
        db.collection("donors")
            .addSnapshotListener { snapshot, error ->

                if (error != null || snapshot == null) {
                    return@addSnapshotListener
                }

                val donors = snapshot.documents.mapNotNull { doc ->
                    val name = doc.getString("name") ?: ""
                    val amount = doc.getLong("amount")?.toInt() ?: 0
                    val needTitle = doc.getString("needTitle") ?: ""
                    val message = doc.getString("message") ?: ""

                    Donor(
                        name = name,
                        amount = amount,
                        needTitle = needTitle,
                        message = message
                    )
                }

                onDonorsChanged(donors)
            }
    }
}