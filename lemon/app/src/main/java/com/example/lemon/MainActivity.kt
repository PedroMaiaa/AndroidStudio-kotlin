package com.example.lemonade

import android.R
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lemonade.ui.theme.LemonadeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LemonadeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LemonadeApp()
                }
            }
        }
    }
}

@Composable
fun LemonadeApp() {
    var step by remember { mutableStateOf(1) }
    var squeezeCount by remember { mutableStateOf(0) }

    val imageRes: Int
    val textRes: Int

    when (step) {
        1 -> {
            imageRes = R.drawable.arvore
            textRes = R.string.toque_para_cair
        }
        2 -> {
            imageRes = R.drawable.apertar
            textRes = R.string.toque_mais
        }
        3 -> {
            imageRes = R.drawable.suco_de_limao
            textRes = R.string.toque_para_tomar
        }
        else -> {
            imageRes = R.drawable.reiniciar
            textRes = R.string.toque_para_reiniciar
        }
    }

    val backgroundColor = when (step) {
        1 -> Color(0xFFFFF176)
        2 -> Color(0xFFFFF59D)
        3 -> Color(0xFFE1F5FE)
        else -> Color(0xFFE0E0E0)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .clickable {
                when (step) {
                    1 -> {
                        step = 2
                        squeezeCount = (2..4).random()
                    }
                    2 -> {
                        squeezeCount--
                        if (squeezeCount == 0) step = 3
                    }
                    3 -> step = 4
                    4 -> step = 1
                }
            },
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(imageRes),
                contentDescription = null
            )
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = stringResource(id = textRes),
                fontSize = 18.sp,
                color = Color(0xFF33691E)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LemonadeAppPreview() {
    LemonadeTheme {
        LemonadeApp()
    }
}
