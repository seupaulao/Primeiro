package com.example.primeiro

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.primeiro.ui.theme.PrimeiroTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PrimeiroTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MyApp(modifier = Modifier.fillMaxSize())
                }
            }
        }
    }
}

@Composable
fun MyApp(modifier: Modifier = Modifier) {

    var deveMostrarOnboarding by rememberSaveable { mutableStateOf(true) }

    Surface(modifier) {
        if (deveMostrarOnboarding) {
            OnboardingScreen(onContinueClicked = { deveMostrarOnboarding = false })
        } else {
            Saudacoes()
        }
    }
}


@Composable
fun OnboardingScreen(onContinueClicked: () -> Unit,
                     modifier: Modifier = Modifier) {
   // var deveMostrarOnboarding by remember { mutableStateOf(true) }

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Bem-vindo o Codelabs Básico de")
        Text("Android + Compose")
        Button(
            modifier = Modifier.padding(vertical = 24.dp),
            onClick =  onContinueClicked
        ) {
            Text("Continue")
        }
    }
}

@Composable
private fun Saudacoes(modifier: Modifier = Modifier,
        names: List<String> = List(1000) {"$it"}//listOf("Mundo", "Compose")
) {
        LazyColumn(modifier = modifier.padding(vertical = 4.dp)) {
            items(names.size) {
                name -> Greeting(name = name.toString())
            }
        }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    //definindo um estado
    val expanded = remember { mutableStateOf(false) }

    //val extraPadding = if (expanded.value) 48.dp else 0.dp

    val extraPadding by animateDpAsState (
          if (expanded.value) 48.dp else 0.dp,
          animationSpec = spring(
              dampingRatio = Spring.DampingRatioMediumBouncy,
              stiffness = Spring.StiffnessLow
          )
    )


    Surface(color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)) {
        Row (Modifier.padding(24.dp)) {
            Column (
                Modifier
                    .weight(1f)
                    .padding(bottom = extraPadding.coerceAtLeast(0.dp))){
                Text(text = "Oi,")
                Text(text = name, style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.ExtraBold
                ))
            }
            ElevatedButton(onClick = { expanded.value = !expanded.value }) {
                Text(if (expanded.value) "Bem-vindo" else "Saudação")
            }
        }
    }
}

@Preview(
    showBackground = true,
    widthDp = 360,
    heightDp = 360,
    uiMode = UI_MODE_NIGHT_YES,
    name="Dark"
)
@Preview(showBackground = true, widthDp = 320, heightDp = 360)
@Composable
fun DefaultPreview() {
    PrimeiroTheme {
        Saudacoes()
    }
}


@Preview(showBackground = true, widthDp = 360)
@Composable
fun MyAppPreview() {
    PrimeiroTheme {
        MyApp(Modifier.fillMaxSize())
    }
}