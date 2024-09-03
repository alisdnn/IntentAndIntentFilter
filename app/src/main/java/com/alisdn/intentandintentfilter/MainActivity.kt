package com.alisdn.intentandintentfilter

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import coil.compose.AsyncImage
import com.alisdn.intentandintentfilter.ui.theme.IntentAndIntentFilterTheme

class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<ImageViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            IntentAndIntentFilterTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        viewmodel = viewModel,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        val uri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(Intent.EXTRA_STREAM, Uri::class.java)
        } else {
            intent.getParcelableExtra(Intent.EXTRA_STREAM)
        }
        viewModel.updateUri(uri)
    }
}

@Composable
fun Greeting(viewmodel: ImageViewModel, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {

        viewmodel.uri?.let {
            AsyncImage(
                model = viewmodel.uri,
                contentDescription = null
            )
        }

        Button(onClick = {
//            Intent(context, SecondActivity::class.java).also {
//                context.startActivity(it)
//            }

//            Intent(Intent.ACTION_MAIN).also {
//                it.`package` = "com.google.android.youtube"
//                try {
//                    context.startActivity(it)
//                }catch (e:ActivityNotFoundException){
//                    e.printStackTrace()
//                }
//            }

            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_EMAIL, arrayOf("aaa@aa.com"))
                putExtra(Intent.EXTRA_SUBJECT, "This is the subject!")
                putExtra(Intent.EXTRA_TEXT, "This is the body.")
            }
            if (intent.resolveActivity(context.packageManager) != null) {
                context.startActivity(intent)
            }

        }) {
            Column {
                Text(text = "Click me")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    IntentAndIntentFilterTheme {
//        Greeting()
    }
}