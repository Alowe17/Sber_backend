package com.example.myapplication.presentation.view.profileScreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.myapplication.navigation.Screen
import com.example.myapplication.presentation.viewmodel.ProfileUiState
import com.example.myapplication.presentation.viewmodel.ProfileViewModel
import com.example.myapplication.session.UserSession
import com.example.myapplication.ui.theme.*

@Composable
fun ProfileScreen(
    navController: NavHostController,
    viewModel: ProfileViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    LaunchedEffect(Unit) { viewModel.loadProfile() }

    val scrollState = rememberScrollState()
    val brush = Brush.linearGradient(
        colors = listOf(Color(0xFFACCBB7), Color(0xFFDCEADD), Color(0xFFF2F5F3))
    )

    when (val state = uiState) {
        is ProfileUiState.Loading -> Box(Modifier.fillMaxSize().background(brush), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(color = SberGreen)
        }
        is ProfileUiState.Error -> Box(Modifier.fillMaxSize().background(brush), contentAlignment = Alignment.Center) {
            Text(state.message, color = Color.Red)
        }
        is ProfileUiState.Success -> ProfileContent(state.profile, scrollState, navController)
    }
}

@Composable
private fun ProfileContent(
    profile: com.example.myapplication.data.model.EmployeeDto,
    scrollState: androidx.compose.foundation.ScrollState,
    navController: NavHostController
) {
    val backgroundGradient = Brush.linearGradient(
        colors = listOf(Color(0xFFACCBB7), Color(0xFFDCEADD), Color(0xFFF2F5F3))
    )

    Box(modifier = Modifier.fillMaxSize().background(backgroundGradient)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding() // Убирает белую полосу, накладывая контент под StatusBar
                .verticalScroll(scrollState)
        ) {
            // --- Шапка профиля ---
            Box(
                modifier = Modifier.fillMaxWidth().padding(top = 24.dp, bottom = 24.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(contentAlignment = Alignment.BottomEnd) {
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = "Avatar",
                            modifier = Modifier
                                .size(100.dp)
                                .clip(CircleShape)
                                .background(Color.White)
                                .border(3.dp, Color.White, CircleShape),
                            tint = Color.Gray
                        )
                        Surface(
                            modifier = Modifier.size(28.dp),
                            shape = CircleShape,
                            color = Color.White,
                            shadowElevation = 4.dp
                        ) {
                            Icon(Icons.Default.Fingerprint, contentDescription = null, modifier = Modifier.padding(4.dp), tint = SberGreen)
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(profile.fullName ?: "—", fontWeight = FontWeight.Black, fontSize = 22.sp, color = SberBlack)
                    Text(
                        "Код ДЦ: ${profile.dealerCenter?.code ?: "—"}",
                        fontSize = 14.sp,
                        color = TextSecondaryGray
                    )
                }
            }


            Column(modifier = Modifier.padding(horizontal = 20.dp)) {

                ProfileInfoCard {
                    ProfileDetailRow(Icons.Default.Work, "Должность", profile.role ?: "—")
                    HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), color = DividerGray.copy(alpha = 0.5f))
                    ProfileDetailRow(Icons.Default.Phone, "Телефон", profile.phone ?: "—")
                }

                Spacer(modifier = Modifier.height(24.dp))

                ProfileServiceItem("Привилегии уровня", Icons.Default.Stars) { navController.navigate(Screen.Privilege.route) }
                ProfileServiceItem("Финансовый эффект", Icons.Default.AccountBalanceWallet) { navController.navigate(Screen.FinancialEffect.route) }
                ProfileServiceItem("Рейтинг (Топ-10)", Icons.Default.EmojiEvents) { navController.navigate(Screen.Rating.route) }
                ProfileServiceItem("Обучение", Icons.Default.School) { navController.navigate(Screen.Education.route) }
                ProfileServiceItem("Новости", Icons.Default.NewReleases) { navController.navigate(Screen.News.route) }
                ProfileServiceItem("Поддержка", Icons.Default.SupportAgent) { navController.navigate(Screen.Support.route) }

                Spacer(modifier = Modifier.height(24.dp))

                // --- КНОПКА ВЫХОДА ---
                OutlinedButton(
                    onClick = {
                        UserSession.logout()
                        navController.navigate(Screen.Login.route) {
                            popUpTo(0) { inclusive = true }
                        }
                    },
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(1.dp, Color.Red.copy(alpha = 0.5f)),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Red)
                ) {
                    Text("Выйти из системы", fontWeight = FontWeight.Bold)
                }

                // Отступ снизу, чтобы Bottom Navigation не перекрывал кнопку выхода
                Spacer(modifier = Modifier.height(100.dp))
            }
        }
    }
}

@Composable
fun ProfileSectionTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.labelLarge,
        color = SberGreen,
        fontWeight = FontWeight.Black,
        modifier = Modifier.padding(vertical = 8.dp, horizontal = 4.dp)
    )
}

@Composable
fun ProfileServiceItem(title: String, icon: ImageVector, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        onClick = onClick,
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.6f)),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(icon, contentDescription = null, tint = SberGreen, modifier = Modifier.size(24.dp))
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = title, fontWeight = FontWeight.Bold, color = SberBlack, fontSize = 15.sp)
            Spacer(modifier = Modifier.weight(1f))
            Icon(Icons.Default.ChevronRight, contentDescription = null, tint = Color.Gray)
        }
    }
}

@Composable
fun ProfileInfoCard(content: @Composable ColumnScope.() -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.9f)),
        shape = RoundedCornerShape(24.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            content()
        }
    }
}

@Composable
fun ProfileDetailRow(icon: ImageVector, label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            modifier = Modifier.size(36.dp),
            shape = RoundedCornerShape(10.dp),
            color = SberGreen.copy(alpha = 0.1f)
        ) {
            Icon(imageVector = icon, contentDescription = null, modifier = Modifier.padding(8.dp), tint = SberGreen)
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(text = label, fontSize = 12.sp, color = TextSecondaryGray, fontWeight = FontWeight.Medium)
            Text(text = value, fontSize = 15.sp, color = SberBlack, fontWeight = FontWeight.Bold)
        }
    }
}