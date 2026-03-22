package com.example.myapplication.presentation.view.currentStatusScreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
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
fun CurrentStatusScreen(
    navHostController: NavHostController
) {
    val scrollState = rememberScrollState() // Состояние для скролла

    val backgroundGradient = Brush.verticalGradient(

        colors = listOf(
            Color(0xFFACCBB7),
            Color(0xFFDCEADD),
            Color(0xFFF2F5F3)
        )
    )

    Box(modifier = Modifier
        .fillMaxSize()
        .background(backgroundGradient)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding() // Чтобы не было белой полосы сверху
        ) {
            // --- ШАПКА ---
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Мой статус",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.ExtraBold,
                    color = SberBlack
                )
                Image(
                    painter = painterResource(id = R.drawable.sber_logo),
                    contentDescription = null,
                    modifier = Modifier.height(24.dp)
                )
            }

            // --- КОНТЕНТ СО СКРОЛЛОМ ---
            Column(
                modifier = Modifier
                    .weight(1f) // Занимает всё место до кнопки
                    .verticalScroll(scrollState) // ВКЛЮЧАЕМ СКРОЛЛ
                    .padding(horizontal = 20.dp)
            ) {
                // 1. Бейдж
                Box(modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp), contentAlignment = Alignment.Center) {
                    Surface(
                        modifier = Modifier.size(130.dp),
                        shape = CircleShape,
                        color = Color.White.copy(alpha = 0.8f),
                        shadowElevation = 0.dp,
                        border = BorderStroke(1.dp, Color.White)
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                            Icon(Icons.Default.WorkspacePremium, null, modifier = Modifier.size(40.dp), tint = Color(0xFFADADAD))
                            Text("SILVER", fontWeight = FontWeight.Black, fontSize = 18.sp, color = SberBlack)
                        }
                    }
                }

                // 2. Прогресс
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.9f)),
                    shape = RoundedCornerShape(24.dp)
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("До GOLD осталось 12 баллов", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                            Text("88%", color = SberGreen, fontWeight = FontWeight.ExtraBold)
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                        LinearProgressIndicator(
                            progress = { 0.88f },
                            modifier = Modifier.fillMaxWidth().height(8.dp),
                            color = SberGreen,
                            trackColor = Color(0xFFE0E0E0),
                            strokeCap = StrokeCap.Round
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // 3. Прогнозы
                Text("Ваш потенциал при переходе:", fontWeight = FontWeight.Black, fontSize = 15.sp, color = SberBlack)
                Spacer(modifier = Modifier.height(12.dp))
                ForecastCard("Годовой доход", "+ 180 000 ₽", "Бонусы Gold", SberGreen)
                Spacer(modifier = Modifier.height(8.dp))
                ForecastCard("Экономия на ипотеке", "740 000 ₽", "Ставка -1.5%", Color(0xFF007AFF))

                Spacer(modifier = Modifier.height(20.dp))

                // --- ИСПРАВЛЕННЫЙ БЛОК С КОТОМ ---
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.5f)),
                    shape = RoundedCornerShape(28.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(0.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.cat_like),
                            contentDescription = "Кот",
                            modifier = Modifier
                                .size(135.dp) // Фиксированный размер — кот не сплющится
                                .clip(RoundedCornerShape(20.dp)),
                            contentScale = ContentScale.Crop // Сохраняем пропорции
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Column(
                            modifier = Modifier.weight(1f) // Текст заберет всё свободное место
                        ) {
                            Text("Совет от СберКота", fontWeight = FontWeight.Black, fontSize = 12.sp, color = SberGreen)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Жми на кнопку ниже — я подскажу, как закрыть эти 12 баллов быстрее!",
                                fontSize = 13.sp,
                                color = SberBlack,
                                lineHeight = 18.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }

                // Запас снизу, чтобы скролл позволял увидеть всё над кнопкой
                Spacer(modifier = Modifier.height(40.dp))
            }

            // --- КНОПКА (Всегда внизу) ---
            Box(modifier = Modifier.padding(20.dp)) {
                Button(
                    onClick = { navHostController.navigate(Screen.Calculator.route) },
                    modifier = Modifier.fillMaxWidth().height(58.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = SberGreen),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text("Как ускорить переход", fontSize = 16.sp, fontWeight = FontWeight.ExtraBold, color = Color.White)
                }
            }
        }
    }
}

@Composable
fun ForecastCard(title: String, value: String, description: String, accentColor: Color) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(4.dp, 40.dp)
                    .background(accentColor, RoundedCornerShape(2.dp))
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(text = title, fontSize = 12.sp, color = TextSecondaryGray)
                Text(text = value, fontSize = 18.sp, fontWeight = FontWeight.Black, color = SberBlack)
                Text(text = description, fontSize = 11.sp, color = TextSecondaryGray)
            }
        }
    }
}