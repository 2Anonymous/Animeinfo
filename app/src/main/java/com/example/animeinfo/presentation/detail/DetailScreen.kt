package com.example.animeinfo.presentation.detail

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Tv
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.animeinfo.domain.model.Anime
import com.example.animeinfo.ui.theme.AccentBlue
import com.example.animeinfo.ui.theme.AccentGold
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

@Composable
fun DetailScreen(
    onBackClick: () -> Unit,
    viewModel: DetailViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when {
                state.isLoading && state.anime == null -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = AccentGold)
                    }
                }

                state.error != null && state.anime == null -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(32.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = state.error ?: "Unknown error",
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontSize = 14.sp
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = { viewModel.onEvent(DetailEvent.Retry) },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = AccentGold
                            )
                        ) {
                            Text("Retry", color = Color.Black)
                        }
                    }
                }

                state.anime != null -> {
                    DetailContent(
                        anime = state.anime!!,
                        onBackClick = onBackClick
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class, ExperimentalGlideComposeApi::class)
@Composable
fun DetailContent(
    anime: Anime,
    onBackClick: () -> Unit
) {
    val scrollState = rememberScrollState()
    var isPlaying by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .background(MaterialTheme.colorScheme.background)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1.4f)
        ) {
            if (anime.youtubeId != null && isPlaying) {
                YouTubePlayerComposable(
                    youtubeId = anime.youtubeId,
                    onClose = { isPlaying = false }
                )
            } else {
                GlideImage(
                    model = anime.largeImageUrl ?: anime.imageUrl,
                    contentDescription = anime.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                if (anime.youtubeId != null) {
                    Surface(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(64.dp)
                            .clickable { isPlaying = true },
                        shape = CircleShape,
                        color = Color.Red.copy(alpha = 0.9f)
                    ) {
                        Icon(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = "Play trailer",
                            tint = Color.White,
                            modifier = Modifier
                                .padding(12.dp)
                                .size(40.dp)
                        )
                    }
                }
            }
            IconButton(
                onClick = onBackClick,
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.TopStart)
                    .background(
                        Color.Black.copy(alpha = 0.5f),
                        CircleShape
                    )
                    .size(40.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = anime.title,
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 28.sp
            )
            anime.titleEnglish?.let { englishTitle ->
                if (englishTitle != anime.title) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = englishTitle,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontSize = 14.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                StatItem(
                    icon = Icons.Default.Star,
                    value = anime.score?.toString() ?: "N/A",
                    iconTint = AccentGold
                )
                StatItem(
                    icon = Icons.Default.Tv,
                    value = "${anime.episodes ?: "?"} eps",
                    iconTint = AccentBlue
                )
                StatItem(
                    icon = Icons.Default.CalendarMonth,
                    value = anime.year?.toString() ?: "N/A",
                    iconTint = AccentBlue
                )
                StatItem(
                    icon = Icons.Default.Groups,
                    value = formatNumber(anime.scoredBy ?: 0),
                    iconTint = AccentBlue
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
            if (anime.genres.isNotEmpty()) {
                Text(
                    text = "Genres",
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(12.dp))
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    anime.genres.forEach { genre ->
                        GenreChip(genre = genre)
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
            }
            Text(
                text = "Synopsis",
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = anime.synopsis ?: "No synopsis available.",
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 14.sp,
                lineHeight = 22.sp
            )
            Spacer(modifier = Modifier.height(24.dp))
            InfoSection(anime = anime)
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun StatItem(
    icon: ImageVector,
    value: String,
    iconTint: Color
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = iconTint,
            modifier = Modifier.size(28.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = value,
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
fun GenreChip(genre: String) {
    Surface(
        shape = RoundedCornerShape(20.dp),
        color = MaterialTheme.colorScheme.surfaceVariant
    ) {
        Text(
            text = genre,
            color = AccentBlue,
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
    }
}

@Composable
fun InfoSection(anime: Anime) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(12.dp))
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.weight(1f)) {
                InfoItem(label = "Status", value = anime.status ?: "Unknown")
                Spacer(modifier = Modifier.height(16.dp))
                InfoItem(label = "Rating", value = anime.rating ?: "Unknown")
            }
            Column(modifier = Modifier.weight(1f)) {
                InfoItem(label = "Type", value = anime.type ?: "Unknown")
                Spacer(modifier = Modifier.height(16.dp))
                InfoItem(label = "Source", value = anime.source ?: "Unknown")
            }
        }
        if (anime.studios.isNotEmpty()) {
            Spacer(modifier = Modifier.height(16.dp))
            InfoItem(label = "Studios", value = anime.studios.joinToString(", "))
        }
        anime.airedString?.let {
            Spacer(modifier = Modifier.height(16.dp))
            InfoItem(label = "Aired", value = it)
        }
    }
}

@Composable
fun InfoItem(label: String, value: String) {
    Column {
        Text(
            text = label,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontSize = 12.sp
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = value,
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun YouTubePlayerComposable(
    youtubeId: String,
    onClose: () -> Unit
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    var youTubePlayerView: YouTubePlayerView? by remember { mutableStateOf(null) }
    var isLoading by remember { mutableStateOf(true) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        AndroidView(
            factory = { context ->
                YouTubePlayerView(context).apply {
                    youTubePlayerView = this
                    enableAutomaticInitialization = false

                    val options = IFramePlayerOptions.Builder()
                        .controls(1)
                        .rel(0)
                        .build()

                    initialize(object : AbstractYouTubePlayerListener() {
                        override fun onReady(youTubePlayer: YouTubePlayer) {
                            isLoading = false
                            youTubePlayer.loadVideo(youtubeId, 0f)
                        }
                    }, options)
                }
            },
            modifier = Modifier.fillMaxSize()
        )
        AnimatedVisibility(
            visible = isLoading,
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = Modifier.align(Alignment.Center)
        ) {
            CircularProgressIndicator(
                color = Color.White,
                modifier = Modifier.size(48.dp)
            )
        }
        IconButton(
            onClick = {
                youTubePlayerView?.release()
                onClose()
            },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(8.dp)
                .background(
                    Color.Black.copy(alpha = 0.6f),
                    CircleShape
                )
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Close video",
                tint = Color.White
            )
        }
    }
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_PAUSE -> youTubePlayerView?.release()
                else -> {}
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
            youTubePlayerView?.release()
        }
    }
}

private fun formatNumber(number: Int): String {
    return when {
        number >= 1_000_000 -> String.format("%.1fM", number / 1_000_000.0)
        number >= 1_000 -> String.format("%.0fK", number / 1_000.0)
        else -> number.toString()
    }
}
