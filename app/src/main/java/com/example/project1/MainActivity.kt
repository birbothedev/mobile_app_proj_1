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

    private var paper : Button? = null
    private var rock : Button? = null
    private var scissors : Button? = null
    private var resetButton : Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        paper = findViewById(R.id.paper)
        rock = findViewById(R.id.rock)
        scissors = findViewById(R.id.scissors)
        resetButton = findViewById(R.id.resetGame)

        //dont allow user to reset the game until its over
        resetButton?.isEnabled = false

        val playerChoiceImage : ImageView = findViewById(R.id.playerChoiceImage)
        val computerChoiceImage : ImageView = findViewById(R.id.computerChoiceImage)
        val winningChoiceImage : ImageView = findViewById(R.id.winningChoiceImage)


        paper?.setOnClickListener {
            playerChoiceImage.setImageResource(R.drawable.paper)
            checkPlayerChoice("paper")
            setInstructionText("Player chose Paper")
            disableAllButtons()
        }

        rock?.setOnClickListener {
            playerChoiceImage.setImageResource(R.drawable.rock)
            checkPlayerChoice("rock")
            println("Rock Clicked")
            setInstructionText("Player chose Rock")
            disableAllButtons()
        }

        scissors?.setOnClickListener {
            playerChoiceImage.setImageResource(R.drawable.scissors)
            checkPlayerChoice("scissors")
            println("Scissors Clicked")
            setInstructionText("Player chose Scissors")
            disableAllButtons()
        }
        if(playerChoice == ""){
            setInstructionText("Please choose either Rock, Paper, or Scissors")
        }

        resetButton?.setOnClickListener {
            resetGame(playerChoiceImage)
            resetGame(winningChoiceImage)
            resetGame(computerChoiceImage)
            setInstructionText("Please choose either Rock, Paper, or Scissors")
            paper?.isEnabled = true
            rock?.isEnabled = true
            scissors?.isEnabled = true
            resetButton?.isEnabled = false
        }

    }
    private fun disableAllButtons() {
        paper?.isEnabled = false
        rock?.isEnabled = false
        scissors?.isEnabled = false
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
        val winningChoiceImage : ImageView = findViewById(R.id.winningChoiceImage)
        var winnerText = ""
        var computerWins = false
        var playerWins= false
        if(playerChoice == computerChoice){
            winnerText = "Tie!"
            setWinningImages(winningChoiceImage, playerChoice)
        } else if (playerChoice == "rock" && computerChoice == "scissors"){
            playerWins = true
        } else if (playerChoice == "paper" && computerChoice == "rock"){
            playerWins = true
        } else if (playerChoice == "scissors" && computerChoice == "paper"){
            playerWins = true
        } else {
            computerWins = true
        }
        if (playerWins){
            winnerText = "Player Wins!"
            setWinningImages(winningChoiceImage, playerChoice)
        } else if (computerWins){
            winnerText = "Computer Wins!"
            setWinningImages(winningChoiceImage, computerChoice)
        }
        setInstructionText(winnerText)
        CoroutineScope(Dispatchers.Main).launch {
            delay(2000)
            resetButton?.isEnabled = true
        }
    }

    private fun setWinningImages(imageView : ImageView, imageName : String){
        //create map to store all the images
        val images = mapOf("rock" to R.drawable.rockwinner,
            "paper" to R.drawable.paperwinner,
            "scissors" to R.drawable.scissorswinner)
        //match the imageName to the map
        val imageResources = images[imageName]
        if (imageResources != null) {
            imageView.setImageResource(imageResources)
        }


    }

    private fun resetGame(imageView : ImageView){
        //remove all images
        imageView.setImageResource(0)
    }

}