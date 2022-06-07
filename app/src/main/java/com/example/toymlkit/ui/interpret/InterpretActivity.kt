package com.example.toymlkit.ui.interpret

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.toymlkit.R
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class InterpretActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_interpret)
    }

}