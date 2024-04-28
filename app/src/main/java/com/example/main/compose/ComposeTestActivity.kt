package com.example.main.compose

import android.os.Bundle
import android.os.PersistableBundle
import android.view.ViewGroup
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.main.util.interceptor.RealCall

class ComposeTestActivity : ComponentActivity(){

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent{
            val text = Text("Hello world")
        }
        val content = findViewById<ViewGroup>(android.R.id.content)
        content.setOnClickListener {
            RealCall().execute()
        }

    }

    @Composable
    fun MessageCard(name: String) {
        Text(text = "Hello $name")
    }
}

