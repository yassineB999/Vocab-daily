package com.example.vocabdaily.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.vocabdaily.ui.theme.VocabDailyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VocabDailyTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    VocabDailyTheme {
        Greeting("Android")
    }
}

/*
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VocabDailyTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = "words"
                ) {
                    composable("words") {
                        WordsScreen(navController = navController)
                    }
                    composable("add_edit_word") {
                        AddEditWordScreen(navController = navController)
                    }
                }
            }
        }
    }
}

@Composable
fun AddEditWordScreen(navController: androidx.navigation.NavController) {
    // Simple placeholder UI; replace with your actual add/edit form.
    androidx.compose.material3.Text(
        text = "Add/Edit Word",
        style = MaterialTheme.typography.titleLarge
    )
}*/





