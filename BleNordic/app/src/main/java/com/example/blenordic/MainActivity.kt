package com.example.blenordic

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.blenordic.ui.theme.BleNordicTheme

class MainActivity : ComponentActivity() {
    private val mainActivityModel by viewModels<MainActivityViewModel>()
    val TAG = this.javaClass.simpleName //重要

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivityModel.initializeBluetoothOrRequestPermission(this@MainActivity) //Permission
        setContent {
            BleNordicTheme {
                ComposeNavigation()
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {  //Permission
        when (requestCode) {
            BLUETOOTH_PERMISSION_REQUEST_CODE -> {
                if (grantResults.none { it != PackageManager.PERMISSION_GRANTED }) {
                    // all permissions are granted
                    mainActivityModel.initializeBluetooth()
                } else {
                    // some permissions are not granted
                }
            }
            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    companion object {
        const val BLUETOOTH_PERMISSION_REQUEST_CODE = 9999   //Permission

        const val SCAN_START = 1
        const val SCAN_STOP = 0
    }
}

@Composable
fun ComposeNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController,
        startDestination = "first_screen") {
        composable("first_screen") {
            FirstScreen(navController = navController)
        }
        composable("second_screen") {
            SecondScreen(navController = navController)
        }
        composable("third_screen") {
            ThirdScreen(navController = navController)
        }
    }
}

@Composable
fun FirstScreen(navController: NavController) {
    val viewModel : FirstViewModel = viewModel()
    Log.v("Main" , "FirstScreen start")
    viewModel.start()
    Column (
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Text(
          text = "First Screen\nTo Second screen",
          color = Color.Blue,
          style = TextStyle(textAlign = TextAlign.Center),
          modifier = Modifier.padding(24.dp).clickable(onClick = {
              navController.navigate("second_screen")
          })
      )
    }
}

@Composable
fun SecondScreen(navController: NavController) {
    val viewModel : SecondViewModel = viewModel()
    Log.v("Main" , "SecondScreen start")
    viewModel.start()
    Column (
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Second Screen\nTo Third screen",
            color = Color.Blue,
            style = TextStyle(textAlign = TextAlign.Center),
            modifier = Modifier.padding(24.dp).clickable(onClick = {
                navController.navigate("third_screen")
            })
        )
    }
}


@Composable
fun ThirdScreen(navController: NavController) {
    val viewModel : ThirdViewModel = viewModel()
    viewModel.start()
    Column (
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Third Screen\nTo First screen",
            color = Color.Blue,
            style = TextStyle(textAlign = TextAlign.Center),
            modifier = Modifier.padding(24.dp).clickable(onClick = {
                navController.navigate("first_screen")
            })
        )
        Button(
            modifier = Modifier
                .weight(0.1f)
                .padding(2.dp),
            onClick = {
                viewModel.scanBLE(MainActivity.SCAN_START)  //Scan
            })
        {
            Text("Scan test")
        }
        Button(
            modifier = Modifier
                .weight(0.1f)
                .padding(2.dp),
            onClick = {
                viewModel.scanBLE(MainActivity.SCAN_STOP)   //Scan
            })
        {
            Text("Stop test")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    BleNordicTheme {
        ComposeNavigation()
    }
}