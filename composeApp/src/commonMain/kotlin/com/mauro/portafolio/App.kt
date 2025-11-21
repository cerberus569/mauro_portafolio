package com.mauro.portafolio

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.ui.tooling.preview.Preview

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.HorizontalDivider

// --- 1. DATOS DE TUS PROYECTOS ---
// Aquí es donde editarás la información de tus 3 proyectos
data class Proyecto(
    val titulo: String,
    val descripcion: String,
    val urlGithub: String,
    val tags: List<String>,
    // En el futuro, aquí usarás painterResource(Res.drawable.imagen)
    val iconoPlaceholder: ImageVector = Icons.Default.Code
)

val misProyectos = listOf(
    Proyecto(
        titulo = "Proyecto 1: App Existente",
        descripcion = "Descripción breve de tu primer proyecto subido a GitHub. Explica qué problema resuelve.",
        urlGithub = "https://github.com/TU_USUARIO/PROYECTO_1", // <--- PON TU LINK AQUÍ
        tags = listOf("Kotlin", "Android", "Firebase")
    ),
    Proyecto(
        titulo = "Proyecto 2: App Secundaria",
        descripcion = "Descripción de tu segundo proyecto. Menciona las tecnologías clave que usaste.",
        urlGithub = "https://github.com/TU_USUARIO/PROYECTO_2", // <--- PON TU LINK AQUÍ
        tags = listOf("Java", "Room", "MVVM")
    ),
    Proyecto(
        titulo = "Portafolio Multiplataforma (Este proyecto)",
        descripcion = "Una web progresiva y app nativa creada con Compose Multiplatform. Funciona en Android, iOS, Desktop y Web (Wasm).",
        urlGithub = "https://github.com/TU_USUARIO/mauro_portafolio", // <--- PON TU LINK AQUÍ
        tags = listOf("KMP", "Compose", "Wasm", "Web")
    )
)

// --- 2. UI PRINCIPAL ---

@Composable
@Preview
fun App() {
    MaterialTheme(
        colorScheme = darkColorScheme(
            primary = Color(0xFF64B5F6), // Azul claro moderno
            background = Color(0xFF121212), // Fondo oscuro estilo "Pro"
            surface = Color(0xFF1E1E1E), // Tarjetas un poco más claras
            onPrimary = Color.Black,
            onBackground = Color.White,
            onSurface = Color.White
        )
    ) {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            BoxWithConstraints {
                // Diseño Responsivo: Si la pantalla es ancha (> 800dp) usa diseño escritorio
                if (maxWidth > 800.dp) {
                    DesktopLayout()
                } else {
                    MobileLayout()
                }
            }
        }
    }
}

// --- 3. DISEÑOS (Escritorio vs Movil) ---

@Composable
fun DesktopLayout() {
    Row(modifier = Modifier.fillMaxSize()) {
        // Columna Izquierda (Perfil fijo)
        Column(
            modifier = Modifier
                .weight(0.35f) // Ocupa el 35% del ancho
                .fillMaxHeight()
                .padding(32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            PerfilSection()
        }

        // Columna Derecha (Lista de Proyectos con scroll)
        LazyColumn(
            modifier = Modifier
                .weight(0.65f) // Ocupa el 65% restante
                .fillMaxHeight()
                .padding(32.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            item {
                Text(
                    "Mis Proyectos",
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }
            items(misProyectos) { proyecto ->
                ProyectoCard(proyecto)
            }
        }
    }
}

@Composable
fun MobileLayout() {
    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Spacer(modifier = Modifier.height(32.dp))
            PerfilSection()
            Spacer(modifier = Modifier.height(32.dp))
            HorizontalDivider(color = Color.Gray.copy(alpha = 0.3f))
            Spacer(modifier = Modifier.height(16.dp))
            Box(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Mis Proyectos",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.primary,
                    // Ahora sí podemos usar align porque estamos dentro de un Box
                    modifier = Modifier.align(Alignment.CenterStart)
                )
            }
        }
        items(misProyectos) { proyecto ->
            ProyectoCard(proyecto)
        }
        item {
            Spacer(modifier = Modifier.height(64.dp))
        }
    }
}

// --- 4. COMPONENTES INDIVIDUALES ---

@Composable
fun PerfilSection() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        // FOTO DE PERFIL (Placeholder)
        // Para poner tu foto real:
        // 1. Pon tu foto 'perfil.jpg' en composeApp/src/commonMain/composeResources/drawable/
        // 2. Cambia Image(...) por: Image(painterResource(Res.drawable.perfil), ...)
        Box(
            modifier = Modifier
                .size(150.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Foto Perfil",
                modifier = Modifier.size(80.dp),
                tint = MaterialTheme.colorScheme.primary
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Hola, soy Mauro",
            style = MaterialTheme.typography.displaySmall.copy(fontWeight = FontWeight.Bold)
        )

        Text(
            text = "Desarrollador Multiplataforma",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Especializado en Kotlin, Android y Compose Multiplatform. Creo experiencias fluidas para móvil, escritorio y web.",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Tags de habilidades
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            SuggestionChip(onClick = {}, label = { Text("Android") })
            SuggestionChip(onClick = {}, label = { Text("iOS") })
            SuggestionChip(onClick = {}, label = { Text("KMP") })
        }
    }
}

@Composable
fun ProyectoCard(proyecto: Proyecto) {
    val uriHandler = LocalUriHandler.current

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(24.dp)) {
            // IMAGEN DEL PROYECTO (Placeholder)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.DarkGray), // Color de fondo mientras no hay imagen
                contentAlignment = Alignment.Center
            ) {
                // Aquí iría la imagen real del proyecto
                Icon(
                    imageVector = proyecto.iconoPlaceholder,
                    contentDescription = null,
                    modifier = Modifier.size(64.dp),
                    tint = Color.LightGray
                )
                Text("Captura del Proyecto", color = Color.LightGray, modifier = Modifier.padding(top=80.dp))
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = proyecto.titulo,
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = proyecto.descripcion,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Tags del proyecto
            Row(modifier = Modifier.horizontalScroll(androidx.compose.foundation.rememberScrollState())) {
                proyecto.tags.forEach { tag ->
                    AssistChip(
                        onClick = {},
                        label = { Text(tag) },
                        modifier = Modifier.padding(end = 8.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    // Esto abre el navegador web
                    uriHandler.openUri(proyecto.urlGithub)
                },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Ver en GitHub")
            }
        }
    }
}