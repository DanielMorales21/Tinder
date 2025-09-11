package com.example.tinder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tinder.ui.theme.TinderTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TinderTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                ) { innerPadding ->
                    MainScreen(Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    var pantalla by remember { mutableStateOf("main") }
    val personas = remember {
        mutableStateListOf(
            Persona(1, "Camila Ibañez", "19", listOf(R.drawable.img1, R.drawable.img2, R.drawable.img3, R.drawable.img4)),
            Persona(2, "Samir Toledo", "21", listOf(R.drawable.img5, R.drawable.img6, R.drawable.img7, R.drawable.img8)),
            Persona(3, "Klauss Ortiz", "21", listOf(R.drawable.img9, R.drawable.img10, R.drawable.img11, R.drawable.img12)),
            Persona(4, "Adrian Vaca", "21", listOf(R.drawable.img13, R.drawable.img14, R.drawable.img15, R.drawable.img16)),
            Persona(5, "Bruna Rodriguez", "26", listOf(R.drawable.img17, R.drawable.img18, R.drawable.img19, R.drawable.img20)),
            Persona(6, "Ernesto Paz", "23", listOf(R.drawable.img21, R.drawable.img22, R.drawable.img23, R.drawable.img24)),
            Persona(7, "Daniel Morales", "24", listOf(R.drawable.img25, R.drawable.img26, R.drawable.img27, R.drawable.img28)),
            Persona(8, "Eminem", "24", listOf(R.drawable.img29, R.drawable.img30, R.drawable.img31, R.drawable.img32)),
            Persona(9, "Miros", "18", listOf(R.drawable.img33, R.drawable.img34, R.drawable.img35, R.drawable.img36)),
            Persona(10, "Mary Gutierrez", "22", listOf(R.drawable.img37, R.drawable.img38, R.drawable.img39, R.drawable.img40)),

            )
    }
    val liked = remember { mutableStateListOf<Persona>() }

    when (pantalla) {
        "main" -> Column(
            modifier = modifier.fillMaxSize()
        ) {
            if (personas.isNotEmpty()) {
                ElementoCard(
                    persona = personas.first(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Ya no hay personas xd")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            ActionButtons(
                onDislike = {
                    if (personas.isNotEmpty()) personas.removeAt(0)
                },
                onLike = {
                    if (personas.isNotEmpty()) {
                        val primero = personas[0]
                        liked.add(primero)
                        personas.removeAt(0)
                    }
                },
                onSeeLikes = { pantalla = "likes" }
            )

            Spacer(modifier = Modifier.height(12.dp))
        }

        "likes" -> LikesScreen(
            liked = liked,
            volver = { pantalla = "main" },
            modifier = modifier
        )
    }
}

@Composable
fun ElementoCard(persona: Persona, modifier: Modifier = Modifier) {
    var imagenActual by remember { mutableStateOf(0) }

    Card(
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize(),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            if (persona.imagenes.isNotEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    Image(
                        painter = painterResource(id = persona.imagenes[imagenActual]),
                        contentDescription = "Foto de ${persona.nombre}",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(12.dp))
                    )

                    // Degradado inferior
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp)
                            .align(Alignment.BottomCenter)
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(
                                        Color.Transparent,
                                        Color.Black.copy(alpha = 0.7f)
                                    )
                                )
                            )
                    )

                    // flecha izquierda
                    IconButton(
                        onClick = {
                            imagenActual =
                                if (imagenActual > 0) imagenActual - 1 else persona.imagenes.size - 1
                        },
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .padding(16.dp)
                            .background(Color.Black.copy(alpha = 0.5f), CircleShape)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Anterior",
                            tint = Color.White
                        )
                    }

                    //flecha derecha
                    IconButton(
                        onClick = {
                            imagenActual = (imagenActual + 1) % persona.imagenes.size
                        },
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .padding(16.dp)
                            .background(Color.Black.copy(alpha = 0.5f), CircleShape)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowForward,
                            contentDescription = "Siguiente",
                            tint = Color.White
                        )
                    }

                    Text(
                        text = "${persona.nombre}, ${persona.edad} años",
                        color = Color.White,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(16.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun ActionButtons(onDislike: () -> Unit, onLike: () -> Unit, onSeeLikes: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Button(onClick = onDislike, modifier = Modifier.weight(1f)) {
            Text("Dislike")
        }
        Spacer(modifier = Modifier.width(12.dp))
        Button(onClick = onSeeLikes, modifier = Modifier.weight(1f)) {
            Text("Likes ❤️")
        }
        Spacer(modifier = Modifier.width(12.dp))
        Button(onClick = onLike, modifier = Modifier.weight(1f)) {
            Text("Like")
        }
    }
}

@Composable
fun LikesScreen(liked: List<Persona>, volver: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            "Personas que te gustaron:",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            items(liked) { persona ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (persona.imagenes.isNotEmpty()) {
                        Image(
                            painter = painterResource(id = persona.imagenes[0]),
                            contentDescription = "Foto de ${persona.nombre}",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(60.dp)
                                .clip(RoundedCornerShape(12.dp))
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Text(
                        text = "${persona.nombre}, ${persona.edad} años",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }

        Button(
            onClick = volver,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text("Volver")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    TinderTheme {
        MainScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun CardPreview() {
    TinderTheme {
        ElementoCard(Persona(1, "Camila Ibañez", "20", listOf()))
    }
}

