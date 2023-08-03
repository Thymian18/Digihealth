package com.github.thymian18.digihealth

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.thymian18.digihealth.ui.theme.DigihealthTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DigihealthTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    WelcomePage()
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
fun WelcomePage() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.padding(top = 20.dp))
        WelcomeTitleRow()
        Spacer(modifier = Modifier.padding(top = 40.dp))
        WelcomeTextRow()
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


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DigihealthTheme {
        WelcomePage()
    }
}