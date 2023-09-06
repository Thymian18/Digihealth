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
import java.util.Calendar
import java.util.concurrent.CompletableFuture
import java.util.concurrent.TimeUnit

class SettingsActivity : ComponentActivity() {

    private lateinit var usageStatsManager: UsageStatsManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val context = this

        val usageStatsManagerFuture = CompletableFuture<UsageStatsManager>().thenApply {
            getSystemService(context, UsageStatsManager::class.java).takeUnless { it == null }
                ?: throw IllegalStateException("UsageStatsManager service not supported")
        }

        setContent {
            DigihealthTheme(dynamicColor = false) {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    println("UsageStatsManager initialized")
                    ConfigurationsPage(context, usageStatsManagerFuture)
                }
            }
        }
    }
}

@Composable
fun ConfigurationsPage(context: Context, usageStatsManagerFuture: CompletableFuture<UsageStatsManager>) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TitleAndTabs()

        // TODO: insert list of apps depending on selected tab
        ScreenTimeList(usageStatsManagerFuture)

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
private fun ScreenTimeList(usageStatsManagerFuture: CompletableFuture<UsageStatsManager>) {
    Column(
        horizontalAlignment = Alignment.Start
    ) {
        val calendar = Calendar.getInstance()
        val startTime = System.currentTimeMillis() - 1000 * 60 * 60 * 12
        calendar.timeInMillis = startTime
        val startTimeString = "${calendar.get(Calendar.HOUR_OF_DAY)}:${calendar.get(Calendar.MINUTE)}:${calendar.get(Calendar.SECOND)}"
        calendar.timeInMillis = startTime + 1000 * 60 * 60 * 12
        val endTimeString = "${calendar.get(Calendar.HOUR_OF_DAY)}:${calendar.get(Calendar.MINUTE)}:${calendar.get(Calendar.SECOND)}"


        val usm = usageStatsManagerFuture.get(5, TimeUnit.SECONDS)
        println("UsageStatsManager: $usm")

       /* usageStatsManagerFuture.thenAccept {
            println("Future completed")
            *//*println("UsageStatsManager: $it")
            println("Start time: $startTimeString")
            println("End time: $endTimeString")

            val usageEvents = it.queryEvents(
                startTime,
                startTime + 1000 * 60 * 60 * 12   // this is currentTime plus one day
            )
            val usageEvent = UsageEvents.Event()    // output object for getNextEvent
            // TODO: no events are returned, why???
            while (usageEvents?.hasNextEvent() == true) {
                usageEvents.getNextEvent(usageEvent)
                println("WE DID IT: ${usageEvent.packageName} ${usageEvent.timeStamp}")
                Log.e("APP", "${usageEvent.packageName} ${usageEvent.timeStamp}")
            }*//*
        }*/
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
