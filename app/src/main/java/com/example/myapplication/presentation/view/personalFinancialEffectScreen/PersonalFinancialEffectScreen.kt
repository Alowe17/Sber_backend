package com.example.myapplication.presentation.view.personalFinancialEffectScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.myapplication.R
import com.example.myapplication.navigation.Screen
import com.example.myapplication.ui.theme.*

@Composable
fun PersonalFinancialEffectScreen(
    navHostController: NavHostController
) {
    val diagonalGradient = Brush.linearGradient(
        colors = listOf(Color(0xFFACCBB7), Color(0xFFDCEADD), Color(0xFFF2F5F3)),
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
                .padding(horizontal = 20.dp)
                .padding(top = 44.dp, bottom = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.AccountBalanceWallet, null, tint = SberGreen, modifier = Modifier.size(28.dp))
                Spacer(modifier = Modifier.width(12.dp))
                Text("Выгода", fontWeight = FontWeight.ExtraBold, fontSize = 22.sp, color = SberBlack)
            }
            Image(
                painter = painterResource(id = R.drawable.sber_logo),
                contentDescription = null,
                modifier = Modifier.height(26.dp),
                contentScale = ContentScale.Fit
            )
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)
        ) {
            // ГЛАВНЫЙ БАННЕР С ОТСТУПОМ МЕЖДУ ТЕКСТОМ И ЦИФРОЙ
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = BackgroundLightGray.copy(alpha = 0.7f)),
                shape = RoundedCornerShape(28.dp)
            ) {
                Column(modifier = Modifier.padding(24.dp)) {
                    Text(
                        "Ваша общая выгода в 2026 году:",
                        fontSize = 15.sp,
                        color = TextSecondaryGray,
                        fontWeight = FontWeight.Medium
                    )
                    // ОТСТУП, чтобы цифра не липла к надписи выше
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        "312 400 ₽",
                        fontSize = 40.sp,
                        fontWeight = FontWeight.Black,
                        color = SberGreen
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // СПИСОК КАТЕГОРИЙ (с увеличенным пространством между текстом и суммой)
            FinancialBenefitItem("Доп. доход от бонусов", "120 000 ₽")
            FinancialBenefitItem("Экономия по ипотеке", "145 000 ₽")
            FinancialBenefitItem("Кэшбэк по картам", "12 400 ₽")
            FinancialBenefitItem("Стоимость ДМС", "35 000 ₽")

            Spacer(modifier = Modifier.height(24.dp))

            // КАРТОЧКА С КОТОМ
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = BackgroundLightGray.copy(alpha = 0.4f)),
                shape = RoundedCornerShape(24.dp)
            ) {
                Row(
                    modifier = Modifier.padding(0.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.cat_like),
                        contentDescription = "Кот",
                        modifier = Modifier
                            .size(135.dp)
                            .clip(RoundedCornerShape(20.dp)),
                        contentScale = ContentScale.Crop
                    )
                    // Расстояние от Кота до текста
                    Spacer(modifier = Modifier.width(20.dp))
                    Column {
                        Text("На уровне Gold", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        Spacer(modifier = Modifier.height(4.dp)) // Отступ между строками
                        Text("450 000 ₽", color = SberGreen, fontWeight = FontWeight.Black, fontSize = 20.sp)
                    }
                }
            }
            Spacer(modifier = Modifier.height(30.dp))
        }

        // КНОПКА
        Box(modifier = Modifier.padding(16.dp)) {
            Button(
                onClick = { navHostController.navigate(Screen.Calculator.route) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(58.dp),
                colors = ButtonDefaults.buttonColors(containerColor = SberGreen),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text("Смоделировать Gold", fontWeight = FontWeight.ExtraBold, fontSize = 16.sp)
            }
        }
    }
}

@Composable
fun FinancialBenefitItem(label: String, amount: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp),
        colors = CardDefaults.cardColors(containerColor = BackgroundLightGray.copy(alpha = 0.9f)),
        shape = RoundedCornerShape(18.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            // Это максимально разносит текст влево, а цифры вправо
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Ограничиваем ширину текста, чтобы он не наезжал на цифры при длинных названиях
            Text(
                text = label,
                modifier = Modifier.weight(1f, fill = false),
                fontWeight = FontWeight.Bold,
                color = SberBlack,
                fontSize = 15.sp
            )
            // Дополнительный фиксированный зазор перед цифрой
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = amount,
                fontWeight = FontWeight.Black,
                color = SberBlack,
                fontSize = 17.sp
            )
        }
    }
}