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
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.quickcallwidget.data.db.DatabaseProvider
import com.quickcallwidget.ui.navigation.SetupNavGraph
import com.quickcallwidget.ui.theme.QuickcallwidgetTheme

class MainActivity : ComponentActivity() {

    //permission request
    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>

    //navigation
    private lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //permission request
        permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()

        ) {
            // permission granted launch code
            // room instance
            val databaseProvider = DatabaseProvider(this@MainActivity)

            setContent {
                navController = rememberNavController()
                //SetupNavGraph(navController = navController)

                MyApp {
                    QuickcallwidgetTheme() {
                        Image(
                            painter = painterResource(R.drawable.ic_background),
                            contentDescription = "imageBack",
                            modifier = Modifier
                                .fillMaxSize()
                                .alpha(0.7f),
                            contentScale = ContentScale.FillBounds
                        )
                        Column() {
                            SetupNavGraph(
                                navController = navController,
                                myDao = databaseProvider.myDao)
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



