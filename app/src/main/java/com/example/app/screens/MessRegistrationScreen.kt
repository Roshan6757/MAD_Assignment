package com.example.app.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.app.screens.models.Mess
import com.example.app.screens.models.Student
import java.time.LocalTime

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MessRegistrationScreen(student: Student, onRegistrationSuccess: (String) -> Unit) {
    val messes = listOf(
        Mess("Mess G", "11:00 AM", "1:00 PM", "7:30 PM", "9:00 PM"),
        Mess("Mess Telugu", "11:00 AM", "1:00 PM", "7:30 PM", "9:00 PM"),
        Mess("Mess H", "11:00 AM", "1:00 PM", "7:30 PM", "9:00 PM"),
        Mess("Mess Uma", "11:00 AM", "1:00 PM", "7:30 PM", "9:00 PM")
    )

    var selectedMess by remember { mutableStateOf<Mess?>(null) }
    var selectedMealTime by remember { mutableStateOf("Lunch") }
    var registrationMessage by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Welcome, ${student.enrollmentNumber}", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        Text("Choose a mess:")

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn {
            items(messes) { mess ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clickable { selectedMess = mess }
                        .background(if (selectedMess == mess) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface)
                        .padding(16.dp)
                ) {
                    Text(mess.name)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("Choose meal time:")
        Row {
            RadioButton(
                selected = selectedMealTime == "Lunch",
                onClick = { selectedMealTime = "Lunch" }
            )
            Text("Lunch", modifier = Modifier.align(Alignment.CenterVertically))

            Spacer(modifier = Modifier.width(16.dp))

            RadioButton(
                selected = selectedMealTime == "Dinner",
                onClick = { selectedMealTime = "Dinner" }
            )
            Text("Dinner", modifier = Modifier.align(Alignment.CenterVertically))
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val selectedMessName = selectedMess?.name
                val mealTime = if (selectedMealTime == "Lunch") selectedMess?.lunchStartTime else selectedMess?.dinnerStartTime

                if (selectedMess != null && selectedMessName != null && mealTime != null) {
                    val currentTime = LocalTime.now()
                    val registrationTime = LocalTime.parse(mealTime)

                    if (currentTime.isBefore(registrationTime.minusHours(4))) {
                        student.registeredMess = selectedMessName
                        onRegistrationSuccess("Registered successfully for $selectedMessName ($selectedMealTime)!")
                    } else {
                        registrationMessage = "You can register only 4 hours before the meal time."
                    }
                } else {
                    registrationMessage = "Please select a mess and meal time."
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Register")
        }

        Spacer(modifier = Modifier.height(8.dp))

        if (registrationMessage.isNotEmpty()) {
            Text(text = registrationMessage, color = Color.Red)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun MessRegistrationScreenPreview() {
    MessRegistrationScreen(student = Student("12345", "Hostel A")) {
        // Simulate registration success
    }
}
