package com.example.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.app.screens.LoginScreen
import com.example.app.screens.MessRegistrationScreen
import com.example.app.screens.models.Student
import com.example.app.ui.theme.AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme{
                Surface(color = MaterialTheme.colorScheme.background) {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun MainScreen() {
    var student by remember { mutableStateOf<Student?>(null) }

    if (student == null) {
        LoginScreen(onLoginSuccess = {
            student = it
        })
    } else {
        MessRegistrationScreen(student = student!!) {
            // Handle registration success
        }
    }
}
