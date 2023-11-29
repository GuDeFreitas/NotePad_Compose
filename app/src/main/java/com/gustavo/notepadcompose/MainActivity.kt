package com.gustavo.notepadcompose

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.FloatingActionButtonDefaults
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gustavo.notepadcompose.datastore.StoreAnnotation
import com.gustavo.notepadcompose.ui.theme.BLACK
import com.gustavo.notepadcompose.ui.theme.GOLD
import com.gustavo.notepadcompose.ui.theme.NotePadComposeTheme
import com.gustavo.notepadcompose.ui.theme.WHITE
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NotePadComponents()
        }
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun NotePadComponents() {

    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val storeAnnotation = StoreAnnotation(context)
    val annotationSaved = storeAnnotation.getAnnotation.collectAsState(initial = "")

    var annotation by remember {
        mutableStateOf("")
    }

    annotation = annotationSaved.value

    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = GOLD
            ) {
                Text(
                    text = "NotePad",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = BLACK
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    scope.launch {
                        storeAnnotation.saveAnnotation(annotation)
                        Toast.makeText(
                            context, "Annotation saved successfully!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                },
                backgroundColor = GOLD,
                elevation = FloatingActionButtonDefaults.elevation(
                    8.dp
                )
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_save),
                    contentDescription = "Save Annotation Icon"
                )
            }
        }
    ) {
        Column() {
            TextField(
                value = annotation,
                onValueChange = {
                    annotation = it
                },
                label = {
                    Text("Please type your note")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = WHITE,
                    cursorColor = GOLD,
                    focusedLabelColor = WHITE
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NotePadComposeTheme {
        NotePadComponents()
    }
}