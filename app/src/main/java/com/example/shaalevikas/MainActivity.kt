package com.example.shaalevikas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

val PrimaryGreen = Color(0xFF2E7D32)
val LightGreen = Color(0xFFE8F5E9)
val SoftOrange = Color(0xFFFFF3E0)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShaaleVikasApp()
        }
    }
}

@Composable
fun ShaaleVikasApp() {
    val navController = rememberNavController()

    var selectedNeed by remember { mutableStateOf<Need?>(null) }
    var currentRole by remember { mutableStateOf("") }

    val needsList = remember {
        mutableStateListOf<Need>()
    }

    val donorList = remember {
        mutableStateListOf<Donor>()
    }

    LaunchedEffect(Unit) {
        FirebaseRepository.getNeedsRealtime { firebaseNeeds ->
            needsList.clear()
            needsList.addAll(firebaseNeeds)
        }
    }

    LaunchedEffect(Unit) {
        FirebaseRepository.getDonorsRealtime { firebaseDonors ->
            donorList.clear()
            donorList.addAll(firebaseDonors)
        }
    }

    MaterialTheme(
        colorScheme = lightColorScheme(
            primary = PrimaryGreen,
            background = LightGreen
        )
    ) {
        NavHost(
            navController = navController,
            startDestination = Screens.HOME
        ) {
            composable(Screens.HOME) {
                HomeScreen(
                    onHeadmasterClick = {
                        currentRole = "headmaster"
                        navController.navigate(Screens.LOGIN)
                    },
                    onAlumniClick = {
                        currentRole = "alumni"
                        navController.navigate(Screens.LOGIN)
                    }
                )
            }

            composable(Screens.LOGIN) {
                LoginScreen(
                    selectedRole = currentRole,
                    onLoginSuccess = { role ->
                        currentRole = role

                        navController.navigate(Screens.DASHBOARD) {
                            popUpTo(Screens.HOME) {
                                inclusive = false
                            }
                        }
                    }
                )
            }

            composable(Screens.DASHBOARD) {
                NeedsDashboardScreen(
                    needsList = needsList,
                    role = currentRole,
                    onNeedClick = { need ->
                        selectedNeed = need
                        navController.navigate(Screens.DETAILS)
                    },
                    onAddNeedClick = {
                        navController.navigate(Screens.ADD_NEED)
                    },
                    onDonorHallClick = {
                        navController.navigate(Screens.DONOR_HALL)
                    },
                    onImpactPhotosClick = {
                        navController.navigate(Screens.IMPACT_PHOTOS)
                    },
                    onBackClick = {
                        AuthRepository.logout()
                        navController.navigate(Screens.HOME) {
                            popUpTo(Screens.HOME) {
                                inclusive = true
                            }
                        }
                    }
                )
            }

            composable(Screens.DETAILS) {
                selectedNeed?.let { need ->
                    NeedDetailsScreen(
                        need = need,
                        onBackClick = {
                            navController.popBackStack()
                        },
                        onPledgeClick = {
                            navController.navigate(Screens.PLEDGE)
                        }
                    )
                }
            }

            composable(Screens.PLEDGE) {
                selectedNeed?.let { need ->
                    PledgeSupportScreen(
                        need = need,
                        onBackClick = {
                            navController.popBackStack()
                        },
                        onSubmitClick = { donorName, pledgeAmount, message ->
                            val index = needsList.indexOfFirst { it.id == need.id }

                            if (index != -1) {
                                val oldNeed = needsList[index]

                                val updatedNeed = oldNeed.copy(
                                    collectedAmount = oldNeed.collectedAmount + pledgeAmount
                                )

                                needsList[index] = updatedNeed
                                selectedNeed = updatedNeed

                                FirebaseRepository.updateNeedAmount(
                                    needId = updatedNeed.id,
                                    newCollectedAmount = updatedNeed.collectedAmount
                                )
                            }

                            val donor = Donor(
                                name = donorName,
                                amount = pledgeAmount,
                                needTitle = need.title,
                                message = message
                            )

                            FirebaseRepository.addDonor(donor)

                            navController.navigate(Screens.THANK_YOU)
                        }
                    )
                }
            }

            composable(Screens.ADD_NEED) {
                AddNeedScreen(
                    onBackClick = {
                        navController.popBackStack()
                    },
                    onSubmitClick = { title, description, cost ->
                        val newNeed = Need(
                            id = System.currentTimeMillis().toInt(),
                            title = title,
                            description = description,
                            estimatedCost = cost,
                            collectedAmount = 0,
                            imageUrl = ""
                        )

                        FirebaseRepository.addNeed(newNeed)

                        navController.popBackStack()
                    }
                )
            }

            composable(Screens.DONOR_HALL) {
                DonorHallScreen(
                    donorList = donorList,
                    onBackClick = {
                        navController.popBackStack()
                    }
                )
            }

            composable(Screens.IMPACT_PHOTOS) {
                ImpactPhotosScreen(
                    onBackClick = {
                        navController.popBackStack()
                    }
                )
            }

            composable(Screens.THANK_YOU) {
                ThankYouScreen(
                    onBackToDashboard = {
                        navController.navigate(Screens.DASHBOARD) {
                            popUpTo(Screens.DASHBOARD) {
                                inclusive = false
                            }
                        }
                    }
                )
            }
        }
    }
}