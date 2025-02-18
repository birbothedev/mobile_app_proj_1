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

    private var playerChoiceImage: ImageView? = null
    private var computerChoiceImage: ImageView? = null
    private var winningChoiceImage: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val paper : Button = findViewById(R.id.paper)
        val rock : Button = findViewById(R.id.rock)
        val scissors : Button = findViewById(R.id.scissors)
        val resetButton : Button = findViewById(R.id.resetGame)

        val playerChoiceImage : ImageView = findViewById<ImageView>(R.id.playerChoiceImage)
        val computerChoiceImage : ImageView = findViewById(R.id.computerChoiceImage)
        val winningChoiceImage : ImageView = findViewById(R.id.winningChoiceImage)


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

        resetButton.setOnClickListener {
            resetGame(playerChoiceImage)
            resetGame(winningChoiceImage)
            resetGame(computerChoiceImage)
            setInstructionText("Please choose either Rock, Paper, or Scissors")
        }

    }

    private fun checkPlayerChoice(choice : String){
        playerChoice = choice
        //add delay before making computer choice
        CoroutineScope(Dispatchers.Main).launch {
            delay(2000)
            checkComputerChoice()
            //another delay before checking the winner
            delay(2000)
            checkWinner()
        }
    }

    private fun setInstructionText(text : String){
        val instructions : TextView = findViewById(R.id.instructions)
            instructions.text = text
    }

    private fun checkComputerChoice(){
        val computerChoiceOptions = listOf("rock", "paper", "scissors")
        val computerChoiceImage : ImageView = findViewById(R.id.computerChoiceImage)
        computerChoice = computerChoiceOptions.random()
        if (computerChoice == "rock"){
            computerChoiceImage.setImageResource(R.drawable.rock)
            setInstructionText("Computer chose Rock!")
        } else if (computerChoice == "paper"){
            computerChoiceImage.setImageResource(R.drawable.paper)
            setInstructionText("Computer chose Paper!")
        } else if (computerChoice == "scissors"){
            computerChoiceImage.setImageResource(R.drawable.scissors)
            setInstructionText("Computer chose Scissors!")
        }
    }

    private fun checkWinner(){
        val winnerText: String
        if(playerChoice == computerChoice){
            winnerText = "Tie!"
        } else if (playerChoice == "rock" && computerChoice == "scissors"){
            winnerText = "Player Wins!"
            winningChoiceImage.setImageResource(R.drawable.rockwinner)
        } else if (playerChoice == "paper" && computerChoice == "rock"){
            winnerText = "Player Wins!"
            winningChoiceImage.setImageResource(R.drawable.paperwinner)
        } else if (playerChoice == "scissors" && computerChoice == "paper"){
            winnerText = "Player Wins!"
            winningChoiceImage.setImageResource(R.drawable.scissorswinner)
        } else {
            winnerText = "Computer Wins!"
        }
        setInstructionText(winnerText)
    }

    fun resetGame(imageView : ImageView){
        imageView.setImageResource(0)
    }

}