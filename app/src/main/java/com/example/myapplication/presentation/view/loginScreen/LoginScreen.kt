package com.example.myapplication.presentation.view.loginScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.myapplication.navigation.Screen
import com.example.myapplication.ui.theme.*

@Composable
fun LoginScreen(
    navHostController: NavHostController
) {
    // Фирменный градиент Sber
    val backgroundGradient = Brush.linearGradient(
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
            .background(backgroundGradient)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Логотип или Иконка безопасности
        Surface(
            modifier = Modifier.size(120.dp),
            shape = CircleShape,
            color = Color.White.copy(alpha = 0.6f)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = Icons.Default.Shield,
                    contentDescription = null,
                    modifier = Modifier.size(60.dp),
                    tint = SberGreen
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Приветствие
        Text(
            text = "Добро пожаловать",
            fontSize = 28.sp,
            fontWeight = FontWeight.Black,
            color = SberBlack
        )

        Text(
            text = "Программа привилегий для сотрудников",
            fontSize = 16.sp,
            color = TextSecondaryGray,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 8.dp)
        )

        Spacer(modifier = Modifier.height(48.dp))

        // Основная кнопка входа через Sber ID
        Button(
            onClick = { navHostController.navigate(Screen.Status.route) },
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            colors = ButtonDefaults.buttonColors(containerColor = SberGreen),
            shape = RoundedCornerShape(16.dp),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                // Имитация логотипа Sber ID через иконку отпечатка
                Icon(
                    imageVector = Icons.Default.Fingerprint,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "Войти по Sber ID",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Дополнительная информация о безопасности
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Lock,
                contentDescription = null,
                modifier = Modifier.size(14.dp),
                tint = TextSecondaryGray
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = "Безопасная авторизация через доменную сеть",
                fontSize = 12.sp,
                color = TextSecondaryGray
            )
        }

        Spacer(modifier = Modifier.height(60.dp))

        // Футер
        Text(
            text = "Версия 1.0.4 (2026)",
            fontSize = 12.sp,
            color = TextSecondaryGray.copy(alpha = 0.5f)
        )
    }
}