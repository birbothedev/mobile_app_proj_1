package com.example.project1

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }

        val paper : Button = findViewById(R.id.paper)
        val rock : Button = findViewById(R.id.rock)
        val scissors : Button = findViewById(R.id.scissors)
        var playerChoice: String = ""
        var computerChoice: String = ""
        var winningChoice: String = ""

        paper.setOnClickListener {
            println("Paper Clicked")
        }

        rock.setOnClickListener {
            println("Rock Clicked")
        }

        scissors.setOnClickListener {
            println("Scissors Clicked")
        }

    }
}