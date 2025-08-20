@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package org.example.app

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DirectionsRun
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

// PUBLIC_INTERFACE
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FitBuddyApp()
        }
    }
}

private enum class FitTab(val label: String, @DrawableRes val icon: Int) {
    Workouts("Workouts", 0),
    Steps("Steps", 0),
    Goals("Goals", 0),
    History("History", 0)
}

// PUBLIC_INTERFACE
@Composable
fun FitBuddyApp() {
    FitBuddyTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            Dashboard()
        }
    }
}

@Composable
private fun Dashboard() {
    val tabs = listOf(FitTab.Workouts, FitTab.Steps, FitTab.Goals, FitTab.History)
    var selectedTab by remember { mutableStateOf(FitTab.Workouts) }

    val workouts = remember { mutableStateListOf<WorkoutEntry>() }
    val goals = remember { mutableStateListOf<GoalEntry>() }
    val stepsToday = remember { mutableIntStateOf(0) }

    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("FitBuddy", fontWeight = FontWeight.Bold) }
            )
        },
        floatingActionButton = {
            if (selectedTab == FitTab.Workouts) {
                FloatingActionButton(
                    onClick = {
                        scope.launch {
                            workouts.add(
                                WorkoutEntry(
                                    title = "Quick Run",
                                    durationMinutes = 20,
                                    calories = 180,
                                    date = LocalDate.now()
                                )
                            )
                        }
                    }
                ) {
                    Icon(Icons.Filled.DirectionsRun, contentDescription = "Log workout")
                }
            }
        },
        bottomBar = {
            NavigationBar(containerColor = MaterialTheme.colorScheme.surface) {
                tabs.forEach { tab ->
                    val icon = when (tab) {
                        FitTab.Workouts -> Icons.Filled.DirectionsRun
                        FitTab.Steps -> Icons.Filled.BarChart
                        FitTab.Goals -> Icons.Filled.CheckCircle
                        FitTab.History -> Icons.Filled.History
                    }
                    NavigationBarItem(
                        selected = selectedTab == tab,
                        onClick = { selectedTab = tab },
                        icon = { Icon(icon, contentDescription = tab.label) },
                        label = { Text(tab.label) }
                    )
                }
            }
        }
    ) { inner ->
        Box(Modifier.padding(inner)) {
            when (selectedTab) {
                FitTab.Workouts -> WorkoutsScreen(workouts)
                FitTab.Steps -> StepsScreen(stepsToday)
                FitTab.Goals -> GoalsScreen(goals, stepsToday.intValue)
                FitTab.History -> HistoryScreen(workouts)
            }
        }
    }
}

data class WorkoutEntry(
    val title: String,
    val durationMinutes: Int,
    val calories: Int,
    val date: LocalDate
)

data class GoalEntry(
    val type: String, // e.g., "Steps" or "Workouts"
    val target: Int,  // steps count or workouts per week
    val progress: Int // current progress
)

@Composable
private fun SectionTitle(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
        color = MaterialTheme.colorScheme.primary
    )
}

@Composable
private fun WorkoutsScreen(workouts: List<WorkoutEntry>) {
    Column(Modifier.fillMaxSize()) {
        SectionTitle("Recent Workouts")
        if (workouts.isEmpty()) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("No workouts yet. Tap + to log your first workout.")
            }
        } else {
            LazyColumn {
                items(workouts) { w ->
                    WorkoutCard(w)
                }
            }
        }
    }
}

@Composable
private fun WorkoutCard(entry: WorkoutEntry) {
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .background(color = MaterialTheme.colorScheme.surface)
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Text(entry.title, fontWeight = FontWeight.SemiBold)
        Spacer(Modifier.height(4.dp))
        Text("${entry.durationMinutes} min • ${entry.calories} kcal")
        Spacer(Modifier.height(4.dp))
        Text(entry.date.format(DateTimeFormatter.ofPattern("MMM d")))
    }
}

@Composable
private fun StepsScreen(stepsToday: MutableIntState) {
    val context = LocalContext.current
    val sensorManager = remember {
        context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }
    val hasCounter = remember {
        sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER) != null ||
            sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR) != null
    }

    DisposableEffect(Unit) {
        val listener = object : SensorEventListener {
            private var baseSteps: Float? = null
            override fun onSensorChanged(event: SensorEvent) {
                when (event.sensor.type) {
                    Sensor.TYPE_STEP_COUNTER -> {
                        val total = event.values.firstOrNull() ?: return
                        if (baseSteps == null) baseSteps = total
                        val today = (total - (baseSteps ?: total)).toInt()
                        stepsToday.intValue = maxOf(today, 0)
                    }
                    Sensor.TYPE_STEP_DETECTOR -> {
                        stepsToday.intValue = stepsToday.intValue + 1
                    }
                }
            }
            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
        }

        val counter = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
        val detector = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR)
        if (counter != null) {
            sensorManager.registerListener(listener, counter, SensorManager.SENSOR_DELAY_NORMAL)
        } else if (detector != null) {
            sensorManager.registerListener(listener, detector, SensorManager.SENSOR_DELAY_NORMAL)
        }

        onDispose { sensorManager.unregisterListener(listener) }
    }

    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        SectionTitle("Today's Steps")
        if (!hasCounter) {
            Text("Step sensor not available on this device.")
            return@Column
        }
        BigNumber(stepsToday.intValue)
        Spacer(Modifier.height(16.dp))
        SectionTitle("Last 7 days")
        StepsBarChart(values = generateWeekData(stepsToday.intValue))
    }
}

@Composable
private fun BigNumber(value: Int) {
    Text(
        text = "%,d".format(value),
        style = MaterialTheme.typography.displaySmall,
        color = MaterialTheme.colorScheme.onBackground
    )
}

private fun generateWeekData(today: Int): List<Int> {
    val base = listOf(4500, 6200, 5200, 3000, 8000, 7100)
    return base + today
}

@Composable
private fun StepsBarChart(values: List<Int>) {
    val max = (values.maxOrNull() ?: 1).coerceAtLeast(1)
    Row(
        Modifier
            .fillMaxWidth()
            .height(120.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.Bottom
    ) {
        values.forEach { v ->
            val ratio = v.toFloat() / max.toFloat()
            Box(
                Modifier
                    .weight(1f)
                    .fillMaxHeight(ratio)
                    .background(MaterialTheme.colorScheme.primary)
            )
        }
    }
}

@Composable
private fun GoalsScreen(goals: MutableList<GoalEntry>, todaySteps: Int) {
    if (goals.isEmpty()) {
        goals.add(GoalEntry("Steps", target = 8000, progress = todaySteps))
        goals.add(GoalEntry("Workouts/week", target = 4, progress = 1))
    } else {
        goals.replaceAll { if (it.type == "Steps") it.copy(progress = todaySteps) else it }
    }
    Column(Modifier.fillMaxSize()) {
        SectionTitle("Your Goals")
        LazyColumn {
            items(goals) { g -> GoalCard(g) }
        }
    }
}

@Composable
private fun GoalCard(goal: GoalEntry) {
    val percent = (goal.progress.toFloat() / goal.target.coerceAtLeast(1)) * 100f
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Text("${goal.type} • Target ${goal.target}")
        Spacer(Modifier.height(4.dp))
        Text("Progress: ${goal.progress} (${percent.toInt()}%)")
        Spacer(Modifier.height(8.dp))
        Box(
            Modifier
                .fillMaxWidth()
                .height(8.dp)
                .background(Color(0xFFE0E0E0))
        ) {
            Box(
                Modifier
                    .fillMaxHeight()
                    .fillMaxWidth((percent / 100f).coerceIn(0f, 1f))
                    .background(MaterialTheme.colorScheme.secondary)
            )
        }
    }
}

@Composable
private fun HistoryScreen(workouts: List<WorkoutEntry>) {
    Column(Modifier.fillMaxSize()) {
        SectionTitle("History")
        if (workouts.isEmpty()) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("No history yet.")
            }
        } else {
            LazyColumn {
                items(workouts) { w -> WorkoutCard(w) }
            }
        }
    }
}
