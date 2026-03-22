package com.example.myapplication.presentation.view.supportScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.myapplication.ui.theme.*

@Composable
fun SupportScreen(navController: NavHostController) {
    val backgroundGradient = Brush.linearGradient(
        colors = listOf(Color(0xFFACCBB7), Color(0xFFDCEADD), Color(0xFFF2F5F3)),
        start = Offset(0f, 0f),
        end = Offset.Infinite
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundGradient)
    ) {
        // Шапка
        Column(modifier = Modifier.padding(top = 48.dp, start = 20.dp, end = 20.dp, bottom = 16.dp)) {
            Text(
                text = "Поддержка",
                fontSize = 28.sp,
                fontWeight = FontWeight.Black,
                color = SberBlack
            )
            Text(
                text = "Ваш персональный менеджер всегда на связи",
                fontSize = 14.sp,
                color = TextSecondaryGray
            )
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Блок Персонального менеджера
            item {
                ManagerCard()
            }

            // Быстрая линия (Hotline)
            item {
                HotlineCard()
            }

            // История обращений
            item {
                Text(
                    text = "История обращений",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            items(3) { index ->
                TicketItem(
                    id = "№ 284-${10 - index}",
                    title = if (index == 0) "Проблема с начислением баллов" else "Заявка по ипотеке КСО",
                    status = if (index == 0) "В работе" else "Решено",
                    date = "1${5-index}.03.2026"
                )
            }
        }
    }
}

@Composable
fun ManagerCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.9f)),
        shape = RoundedCornerShape(24.dp)
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Заглушка фото менеджера
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
                    .background(SberGreen.copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.SupportAgent, contentDescription = null, tint = SberGreen, modifier = Modifier.size(32.dp))
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text("Анна Викторовна", fontWeight = FontWeight.Black, fontSize = 17.sp, color = SberBlack)
                Text("Персональный куратор", fontSize = 13.sp, color = TextSecondaryGray)
            }

            IconButton(
                onClick = { /* Начать чат */ },
                colors = IconButtonDefaults.iconButtonColors(containerColor = SberGreen, contentColor = Color.White)
            ) {
                Icon(Icons.Default.Chat, contentDescription = "Чат")
            }
        }
    }
}

@Composable
fun HotlineCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = SberBlack),
        shape = RoundedCornerShape(24.dp)
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Default.FlashOn,
                contentDescription = null,
                tint = Color(0xFFFFD700),
                modifier = Modifier.size(30.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text("Быстрая линия", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text("Для горячих заявок", color = Color.White.copy(alpha = 0.6f), fontSize = 12.sp)
            }
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = { /* Звонок */ },
                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                shape = RoundedCornerShape(12.dp),
                contentPadding = PaddingValues(horizontal = 12.dp)
            ) {
                Text("SOS", color = SberBlack, fontWeight = FontWeight.Black)
            }
        }
    }
}

@Composable
fun TicketItem(id: String, title: String, status: String, date: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.6f)),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = id, fontSize = 11.sp, color = TextSecondaryGray, fontWeight = FontWeight.Bold)
                Text(text = title, fontSize = 15.sp, fontWeight = FontWeight.Bold, color = SberBlack)
                Text(text = date, fontSize = 12.sp, color = TextSecondaryGray)
            }

            Surface(
                color = if (status == "Решено") SberGreen.copy(alpha = 0.1f) else Color(0xFFFF9800).copy(alpha = 0.1f),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = status,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    color = if (status == "Решено") SberGreen else Color(0xFFFF9800),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}