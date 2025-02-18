package com.example.project1

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    var playerChoice: String = ""
    var computerChoice : String = ""
    var winningChoice: String = ""
    val paper : Button = findViewById(R.id.paper)
    val rock : Button = findViewById(R.id.rock)
    val scissors : Button = findViewById(R.id.scissors)
    val playerChoiceImage : ImageView = findViewById<ImageView>(R.id.playerChoiceImage)
    val winningChoiceImage : ImageView = findViewById<ImageView>(R.id.winningChoiceImage)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)


        paper.setOnClickListener {
            playerChoiceImage.setImageResource(R.drawable.paper)
            checkPlayerChoice("paper")
            setInstructionText("Player chose Paper")
        }

        rock.setOnClickListener {
            playerChoiceImage.setImageResource(R.drawable.rock)
            checkPlayerChoice("rock")
            println("Rock Clicked")
            setInstructionText("Player chose Rock")
        }

        scissors.setOnClickListener {
            playerChoiceImage.setImageResource(R.drawable.scissors)
            checkPlayerChoice("scissors")
            println("Scissors Clicked")
            setInstructionText("Player chose Scissors")
        }
        if(playerChoice == ""){
            setInstructionText("Please choose either Rock, Paper, or Scissors")
        }

        val computerChoiceOptions = listOf("rock", "paper", "scissors")
        computerChoice = computerChoiceOptions.random()
        if (computerChoice == "rock"){
            checkComputerChoice("Rock")
        } else if (computerChoice == "paper"){
            checkComputerChoice("Paper")
        } else if (computerChoice == "scissors"){
            checkComputerChoice("Scissors")
        }

    }

    private fun checkPlayerChoice(choice : String){
        playerChoice = choice
        //add delay before making computer choice
        CoroutineScope(Dispatchers.Main).launch {
            delay(2000)  // Wait for 2 seconds
            checkComputerChoice()
        }
        //add another delay before checking the winner
        CoroutineScope(Dispatchers.Main).launch {
            delay(2000)  // Wait for 2 seconds
            checkWinner()
        }
    }

    private fun setInstructionText(text : String){
        val instructions : TextView = findViewById(R.id.instructions)
            instructions.text = text
    }

    private fun checkComputerChoice(choice: String){
        val computerChoiceImage : ImageView = findViewById(R.id.computerChoiceImage)
        computerChoiceImage.setImageResource(R.drawable.choice)
        setInstructionText("Computer chose Scissors!")
    }

    private fun checkWinner(){
        val winnerText: String
        if(playerChoice == computerChoice){
            winnerText = "Tie!"
        } else if (playerChoice == "rock" && computerChoice == "scissors"){
            winnerText = "Player Wins!"
        } else if (playerChoice == "paper" && computerChoice == "rock"){
            winnerText = "Player Wins!"
        } else if (playerChoice == "scissors" && computerChoice == "paper"){
            winnerText = "Player Wins!"
        } else {
            winnerText = "Computer Wins!"
        }
        setInstructionText(winnerText)
    }

}