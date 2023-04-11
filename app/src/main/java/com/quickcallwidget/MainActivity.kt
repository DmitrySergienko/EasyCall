package com.quickcallwidget

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.quickcallwidget.ui.screens.MainScreen
import com.quickcallwidget.ui.theme.quickcallwidgetTheme


class MainActivity : ComponentActivity() {

    //permission request
    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //permission request
        permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) {
            // permission granted launch code
            setContent {

                MyApp {
                    quickcallwidgetTheme() {
                        Image(
                            painter = painterResource(R.drawable.ic_background),
                            contentDescription = "imageBack",
                            modifier = Modifier
                                .fillMaxSize()
                                .alpha(0.7f),
                            contentScale = ContentScale.FillBounds
                        )
                        Column() {
                            MainScreen(stringResource(id = R.string.first_widget_title))
                            Divider(color = Color.White, thickness = 1.dp, modifier = Modifier.padding(10.dp))
                        }
                    }
                }
            }
        }


        window.statusBarColor = Color.Transparent.toArgb()

        permissionLauncher.launch(
            arrayOf(
                Manifest.permission.CALL_PHONE,
                Manifest.permission.READ_CONTACTS
            )
        )
    }
}

@Composable
fun MyApp(content: @Composable () -> Unit) {
    MaterialTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.White // Change this to your app's background color
        ) {
            content()
        }
    }
}