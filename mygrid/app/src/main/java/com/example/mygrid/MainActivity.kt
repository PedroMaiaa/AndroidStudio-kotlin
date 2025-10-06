package com.example.mygrid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mygrid.ui.theme.MyGridTheme

data class Category(
    val imageRes: Int,
    val titleRes: Int,
    val count: Int
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyGridTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CategoryGridScreen()
                }
            }
        }
    }
}

@Composable
fun CategoryGridScreen() {
    val categories = listOf(
        Category(imageRes = R.drawable.sport, titleRes = R.string.sport, count = 58),
        Category(imageRes = R.drawable.lucaslima, titleRes = R.string.lucaslima, count = 30),
        Category(imageRes = R.drawable.zelucas, titleRes = R.string.zelucas, count = 90),
        Category(imageRes = R.drawable.barleta, titleRes = R.string.barleta, count = 121)
    )

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        content = {
            items(categories) { cat ->
                CategoryItem(cat)
            }
        }
    )
}

@Composable
fun CategoryItem(cat: Category) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { }
    ) {
        Image(
            painter = painterResource(id = cat.imageRes),
            contentDescription = null,
            modifier = Modifier.size(80.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(id = cat.titleRes),
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center
        )
        Text(
            text = "${cat.count}",
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
        )
    }
}

@Composable
fun MyGridTheme(content: @Composable () -> Unit) {
    val LightColors = androidx.compose.material3.lightColorScheme(
        primary = Color(0xFF2E7D32),
        secondary = Color(0xFF81C784),
        background = Color(0xFFE8F5E9),
        surface = Color.White,
        onPrimary = Color.White,
        onSecondary = Color.White,
        onBackground = Color(0xFF1B5E20),
        onSurface = Color.Black
    )

    androidx.compose.material3.MaterialTheme(
        colorScheme = LightColors,
        typography = androidx.compose.material3.Typography(),
        content = content
    )
}
