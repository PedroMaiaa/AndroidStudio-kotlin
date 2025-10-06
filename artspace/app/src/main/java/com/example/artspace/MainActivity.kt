package com.example.artspace

import android.os.Bundle
import androidx.compose.ui.tooling.preview.Preview
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.artspace.ui.theme.ArtSpaceTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ArtSpaceTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ArtGalleryScreen()
                }
            }
        }
    }
}

data class Artwork(val imageRes: Int, val title: Int, val artist: String, val year: Int)

@Composable
fun ArtGalleryScreen() {
    val artworks = listOf(
        Artwork(imageRes = R.drawable.barleta, title = R.string.primeira, artist = "barlleta", year = 2025),
        Artwork(imageRes = R.drawable.zelucas, title = R.string.outro, artist = "ze lucas", year = 2025),
    )

    var currentIndex by remember { mutableStateOf(0) }
    val currentArt = artworks[currentIndex]

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Spacer(modifier = Modifier.height(8.dp))

        // Exibição da arte (com “quadro” branco ao redor)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .padding(16.dp)
                    .background(Color.White)
                    .clip(RoundedCornerShape(8.dp))
                    .padding(8.dp)
            ) {
                Image(
                    painter = painterResource(currentArt.imageRes),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                )
            }
        }

        // Título, artista e ano
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(currentArt.title),
                fontSize = 22.sp,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "${currentArt.artist} (${currentArt.year})",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
            )
        }

        // Botões Previous / Next
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Previous",
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier
                    .clickable {
                        currentIndex = if (currentIndex > 0) currentIndex - 1 else artworks.lastIndex
                    }
                    .padding(8.dp)
            )
            Text(
                text = "Next",
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier
                    .clickable {
                        currentIndex = if (currentIndex < artworks.lastIndex) currentIndex + 1 else 0
                    }
                    .padding(8.dp)
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun ArtGalleryPreview() {
    ArtSpaceTheme {
        ArtGalleryScreen()
    }
}
