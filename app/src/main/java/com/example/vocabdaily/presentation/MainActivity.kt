package com.example.vocabdaily.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.vocabdaily.presentation.add_edit_words.component.AddEditWordScreen
import com.example.vocabdaily.presentation.routes.Screen
import com.example.vocabdaily.presentation.words.component.WordsScreen
import com.example.vocabdaily.ui.theme.VocabDailyTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VocabDailyTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.WordsScreen.route
                    ){
                        composable(route = Screen.WordsScreen.route){
                            WordsScreen(navController = navController)
                        }
                        composable(
                            route = Screen.AddEditWordScreen.route + "?wordId={wordId}&wordColor={wordColor}",
                            arguments = listOf(
                                navArgument("wordId") {
                                    type = NavType.IntType
                                    defaultValue = -1
                                },
                                navArgument("wordColor") {
                                    type = NavType.IntType
                                    defaultValue = -1
                                }
                            )
                        ) { backStackEntry ->
                            val wordColor = backStackEntry.arguments?.getInt("wordColor") ?: -1
                            AddEditWordScreen(
                                navController = navController,
                                wordColor = wordColor
                            )
                        }
                    }
                }

            }
        }
    }
}




