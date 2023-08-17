package com.github.thymian18.digihealth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.github.thymian18.digihealth.ui.theme.DigihealthTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DigihealthTheme(dynamicColor = false) {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    WelcomePage(this)
                }
            }
        }
    }
}

@Composable
fun Title(text: String) {
    Box() {
        Text(
            text = text,
            fontSize = MaterialTheme.typography.headlineMedium.fontSize,
            fontStyle = MaterialTheme.typography.headlineMedium.fontStyle,
            fontWeight = MaterialTheme.typography.headlineMedium.fontWeight,
        )
    }
}

@Composable
fun BottomRow(
    context: Context,
) {
    BottomAppBar(
        containerColor = MaterialTheme.colorScheme.primary,
        actions = {
            IconButton(
                onClick = {
                    val intent = Intent(context, MainActivity::class.java)
                    context.startActivity(intent)
                },
                modifier = Modifier
                    .weight(1f)
                    .testTag("HomeButton")
            ) {
                Icon(Icons.Filled.Home, contentDescription = "Home")
            }
            IconButton(
                onClick = {
                    val intent = Intent(context, SettingsActivity::class.java)
                    context.startActivity(intent)
                },
                modifier = Modifier
                    .weight(1f)
                    .testTag("ConfigurationsButton")
            ) {
                //Icon(Icons.Filled.Settings, contentDescription = "Configurations")
                Icon(painter = painterResource(id = R.drawable.baseline_dataset_24), contentDescription = "Configurations")
            }
            IconButton(
                onClick = {
                    val intent = Intent(context, InfoActivity::class.java)
                    context.startActivity(intent)
                },
                modifier = Modifier
                    .weight(1f)
                    .testTag("InfoButton")
            ) {
                Icon(Icons.Filled.Info, contentDescription = "Info")
            }
        },
    )
}

@Composable
fun WelcomePage(context: Context) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.padding(top = 20.dp))
        WelcomeTitleRow()
        Spacer(modifier = Modifier.padding(top = 40.dp))
        WelcomeTextRow()
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Bottom,
    ) {
        BottomRow(context = context)
    }

}

@Composable
fun WelcomeTitleRow() {
    Title(
        text = "Welcome to Digihealth!",
    )
}

@Composable
fun WelcomeTextRow() {
    Box(
        modifier = Modifier.padding(horizontal = 20.dp),
    ) {
        Text(
            text = "Congratulations. You have successfully installed Digihealth! This is the first step to a healthier you.\nWe will help you to find the right balance of digital consumption and real life.",
            textAlign = TextAlign.Center,
        )
    }

}


