package com.example.stopwatch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Stopwatch()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Stopwatch() {
    var timeInMilliseconds by remember { mutableStateOf(0L) }
    var startTime by remember { mutableStateOf(0L) }
    var isRunning by remember { mutableStateOf(false) }

    LaunchedEffect(isRunning) {
        if (isRunning) {
            startTime = System.currentTimeMillis() - timeInMilliseconds
            while (isRunning) {
                delay(50)
                timeInMilliseconds = System.currentTimeMillis() - startTime
            }
        }
    }

    val minutes = (timeInMilliseconds / 1000) / 60
    val seconds = (timeInMilliseconds / 1000) % 60
    val milliseconds = (timeInMilliseconds % 1000) / 50 * 50

    Scaffold(
        topBar = {
            Surface(
                shadowElevation = 10.dp,
            ) {
                TopAppBar(
                    title = {
                        Text(
                            "Cronômetro",
                        )
                    },
                    colors = TopAppBarDefaults.mediumTopAppBarColors(
                        containerColor = Color(0xFF6200EE),
                        titleContentColor = Color.White,
                    ),
                )
            }
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .wrapContentSize(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "%02d:%02d:%03d".format(minutes, seconds, milliseconds),
                fontSize = 48.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Button(
                onClick = {
                    isRunning = !isRunning
                    if (!isRunning) {
                        timeInMilliseconds = 0L
                    }
                },
                modifier = Modifier.padding(
                    bottom = 8.dp,
                ),
            ) {
                Text(
                    text = if (isRunning) "Parar" else "Iniciar",
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Stopwatch()
}