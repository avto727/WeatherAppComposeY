package com.example.weatherappcomposey.screens
// https://neco-desarrollo.es/2022/06/weather-app-jetpack-compose-2
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.weatherappcomposey.R
import com.example.weatherappcomposey.ui.theme.BlueLight
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch
import com.example.weatherappcomposey.data.WeatherModel


//@Preview(showBackground = true)
@Composable
fun MainCard(currentDay: MutableState<WeatherModel>) {
    // поверх рисунка
    Column(
        modifier = Modifier
            .padding(5.dp)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(), // Занять всю ширину
            backgroundColor = BlueLight,
            elevation = 0.dp, // Тень
            shape = RoundedCornerShape(10.dp) // Скругление углов
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = currentDay.value.time,
                        modifier = Modifier.padding(top = 8.dp, start = 8.dp),
                        style = TextStyle(fontSize = 15.sp),//размер шрифта
                        color = Color.White // Цвет шрифта
                    )
                    AsyncImage(
                        model = "https:${currentDay.value.time}",
                        contentDescription = "im2",
                        modifier = Modifier
                            .size(35.dp)
                            .padding(top = 3.dp, end = 8.dp)
                    )
                }
                Text(// Название города
                    text = currentDay.value.city,
                    style = TextStyle(fontSize = 24.sp),//размер шрифта
                    color = Color.White // Цвет шрифта
                )
                Text(
//                    text = currentDay.value.currentTemp,
                    text = "${currentDay.value.currentTemp.toFloat().toInt()} ºС",
                    style = TextStyle(fontSize = 65.sp),//размер шрифта
                    color = Color.White // Цвет шрифта
                )
                Text(
                    text = currentDay.value.condition,
                    style = TextStyle(fontSize = 16.sp),//размер шрифта
                    color = Color.White // Цвет шрифта
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(
                        onClick = {

                        }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_search),
                            contentDescription = "im3",
                            tint = Color.White

                        )
                    }
                    Text(
                        text = "${currentDay.value.maxTemp.toFloat().toInt()} ºС/${currentDay.value.minTemp.toFloat().toInt()} ºC",
                        style = TextStyle(fontSize = 16.sp),//размер шрифта
                        color = Color.White // Цвет шрифта
                    )
                    IconButton(
                        onClick = {

                        }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_cloud_sync),
                            contentDescription = "im3",
                            tint = Color.White
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun TabLayout(daysList: MutableState<List<WeatherModel>>) {
    val tabList = listOf("HOURS", "DAYS")
    val pagerState = rememberPagerState()
    val tabIndex = pagerState.currentPage
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .padding(start = 5.dp, end = 5.dp)
            .clip(RoundedCornerShape(5.dp))
    ) {
        TabRow(
            selectedTabIndex = tabIndex,
            indicator = { pos ->
                TabRowDefaults.Indicator(
                    Modifier.pagerTabIndicatorOffset(pagerState, pos)
                )
            },
            backgroundColor = BlueLight,
            contentColor = Color.White
        ) {
            tabList.forEachIndexed { index, text ->
                Tab(
                    selected = false,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
                    text = {
                        Text(text = text)
                    }
                )
            }
        }
        HorizontalPager(
            count = tabList.size,
            state = pagerState,
            modifier = Modifier.weight(1.0f)
        ) { index ->
            LazyColumn(modifier = Modifier.fillMaxSize()
            ){
                itemsIndexed(
                    daysList.value
                ){
                    _, item-> ListItem(item)
                }
            }
        }
    }
}