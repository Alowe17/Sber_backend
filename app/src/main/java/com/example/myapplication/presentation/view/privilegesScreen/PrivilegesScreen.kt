package com.example.myapplication.presentation.view.privilegesScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.myapplication.R
import com.example.myapplication.navigation.Screen
import com.example.myapplication.ui.theme.*

// Модель остается внешней
data class Privilege(
    val title: String,
    val description: String,
    val financialEffect: String,
    val isLocked: Boolean,
    val icon: ImageVector
)

@Composable
fun PrivilegesScreen(navHostController: NavHostController) {
    val allPrivileges = listOf(
        Privilege("Скидка на ипотеку", "Снижение ставки на 1.5%", "Экономия ~240 000 ₽", false, Icons.Default.Star),
        Privilege("Подписка СберПрайм+", "Все сервисы экосистемы", "Выгода 3 990 ₽/год", false, Icons.Default.Star),
        Privilege("ДМС софинансирование", "Стоматология включена", "Бесплатно для Gold", true, Icons.Default.Lock),
        Privilege("Доп. мотивация", "Ежемесячная выплата Gold", "+30 000 ₽ к доходу", true, Icons.Default.Lock),
        Privilege("Premium ДМС", "Лучшие клиники города", "Стоимость 120 000 ₽", true, Icons.Default.Lock)
    )

    val activePrivileges = allPrivileges.filter { !it.isLocked }
    val lockedPrivileges = allPrivileges.filter { it.isLocked }

    val diagonalGradient = Brush.linearGradient(
        colors = listOf(
            Color(0xFFACCBB7),
            Color(0xFFDCEADD),
            Color(0xFFF2F5F3)
        ),
        start = Offset(0f, 0f),
        end = Offset.Infinite
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(diagonalGradient)
    ) {
        // --- ШАПКА ---
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(top = 40.dp, bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Привилегии",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = SberBlack
            )

            Image(
                painter = painterResource(id = R.drawable.sber_logo),
                contentDescription = "Sber Logo",
                modifier = Modifier.height(28.dp),
                contentScale = ContentScale.Fit
            )
        }

        // --- СПИСОК С КОТОМ ---
        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Text(
                    "ВАШ ТЕКУЩИЙ УРОВЕНЬ",
                    style = MaterialTheme.typography.labelSmall,
                    color = SberGreen,
                    fontWeight = FontWeight.Black
                )
            }

            items(activePrivileges) { privilege ->
                PrivilegeCard(privilege)
            }

            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White.copy(alpha = 0.4f) // Очень легкая подложка
                    ),
                    shape = RoundedCornerShape(24.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(2.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.cat_happy),
                            contentDescription = "СберКот",
                            modifier = Modifier
                                .size(120.dp)
                                .offset(x = (-5).dp),
                            contentScale = ContentScale.Crop
                        )

                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = "Ты почти у цели!",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Black,
                                color = SberBlack
                            )
                            Text(
                                text = "Осталось всего 12 баллов до уровня Gold. Это откроет тебе ДМС и бонусы к доходу!",
                                fontSize = 13.sp,
                                color = SberBlack.copy(alpha = 0.7f),
                                lineHeight = 18.sp
                            )
                        }
                    }
                }
            }

            item {
                Text(
                    "ДОСТУПНО НА GOLD",
                    style = MaterialTheme.typography.labelSmall,
                    color = TextSecondaryGray,
                    fontWeight = FontWeight.Black
                )
            }

            items(lockedPrivileges) { privilege ->
                PrivilegeCard(privilege)
            }
        }

        // --- КНОПКА ---
        Box(modifier = Modifier.padding(16.dp)) {
            Button(
                onClick = { navHostController.navigate(Screen.Calculator.route) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = SberGreen),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Рассчитать новый статус", fontWeight = FontWeight.Bold, color = SberWhite)
            }
        }
    }
}

@Composable
fun PrivilegeCard(privilege: Privilege) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (privilege.isLocked) Color.White.copy(alpha = 0.5f) else Color.White.copy(alpha = 0.9f)
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier.size(44.dp),
                shape = RoundedCornerShape(10.dp),
                color = if (privilege.isLocked) DividerGray.copy(alpha = 0.5f) else SberGreen.copy(alpha = 0.1f)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = privilege.icon,
                        contentDescription = null,
                        tint = if (privilege.isLocked) TextSecondaryGray else SberGreen,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = privilege.title,
                    fontWeight = FontWeight.Bold,
                    color = if (privilege.isLocked) TextSecondaryGray else SberBlack,
                    fontSize = 15.sp
                )
                Text(
                    text = privilege.financialEffect,
                    fontWeight = FontWeight.ExtraBold,
                    color = if (privilege.isLocked) TextSecondaryGray else SberGreen,
                    fontSize = 13.sp
                )
            }
        }
    }
}