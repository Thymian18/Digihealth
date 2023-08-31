package com.github.thymian18.digihealth

import android.app.usage.UsageEvents
import android.app.usage.UsageStatsManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.getSystemService
import com.github.thymian18.digihealth.ui.theme.DigihealthTheme

class SettingsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DigihealthTheme(dynamicColor = false) {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ConfigurationsPage(this)
                }
            }
        }
    }
}

@Composable
fun ConfigurationsPage(context: Context) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TitleAndTabs()

        // TODO: insert list of apps depending on selected tab
        ScreenTimeList(context)

    }

    BottomRow(context = context)
}

enum class Tab(val title: String) {
    TIME("Time"),
    LAUNCHES("Launches");
}

@Composable
private fun TitleAndTabs() {
    Spacer(modifier = Modifier.padding(top = 20.dp))
    Title(text = "Configurations")
    Spacer(modifier = Modifier.padding(top = 20.dp))

    var selectedTab by remember { mutableStateOf(Tab.TIME) }

    TabRow(
        selectedTabIndex = selectedTab.ordinal,
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.primary
    ) {
        Tab(
            selected = selectedTab == Tab.TIME,
            onClick = { selectedTab = Tab.TIME },
            text = { Text(Tab.TIME.title) }
        )
        Tab(
            selected = selectedTab == Tab.LAUNCHES,
            onClick = { selectedTab = Tab.LAUNCHES },
            text = { Text(Tab.LAUNCHES.title) }
        )
    }
}

@Composable
private fun ScreenTimeList(context: Context) {
    Column(
        horizontalAlignment = Alignment.Start
    ) {
        val currentTime = System.currentTimeMillis()
        val usageStatsManager = getSystemService(context, UsageStatsManager::class.java)

        println("UsageStatsManager: $usageStatsManager")
        println("Current time: $currentTime")
        println("Start time: ${currentTime - 1000 * 60 * 60 * 24}")

        val usageEvents = usageStatsManager?.queryEvents(
            currentTime - 1000 * 60 * 60 * 24,
            currentTime
        )
        val usageEvent = UsageEvents.Event()
        // TODO: no events are returned, why???
        while (usageEvents?.hasNextEvent() == true) {
            usageEvents.getNextEvent(usageEvent)
            println("${usageEvent.packageName} ${usageEvent.timeStamp}")
            Log.e("APP", "${usageEvent.packageName} ${usageEvent.timeStamp}")
        }
    }
}

/*
private fun checkUsageStatsPermission() : Boolean {
    val appOpsManager = getSystemService(AppCompatActivity.APP_OPS_SERVICE) as AppOpsManager
    // `AppOpsManager.checkOpNoThrow` is deprecated from Android Q
    val mode = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        appOpsManager.unsafeCheckOpNoThrow(
            "android:get_usage_stats",
            Process.myUid(), packageName
        )
    }
    else {
        appOpsManager.checkOpNoThrow(
            "android:get_usage_stats",
            Process.myUid(), packageName
        )
    }
    return mode == AppOpsManager.MODE_ALLOWED
}*/
