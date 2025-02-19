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

    private var playerChoice: String = ""
    private var computerChoice : String = ""

    private var paper : Button? = null
    private var rock : Button? = null
    private var scissors : Button? = null
    private var resetButton : Button? = null

    private var computerWins = false
    private var playerWins= false
    private var playerScore = 0
    private var computerScore = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        paper = findViewById(R.id.paper)
        rock = findViewById(R.id.rock)
        scissors = findViewById(R.id.scissors)
        resetButton = findViewById(R.id.resetGame)

        //don't allow user to reset the game until its over
        resetButton?.isEnabled = false

        val playerChoiceImage : ImageView = findViewById(R.id.playerChoiceImage)
        val computerChoiceImage : ImageView = findViewById(R.id.computerChoiceImage)
        val winningChoiceImage : ImageView = findViewById(R.id.winningChoiceImage)

        //button events
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
        resetButton?.setOnClickListener {
            resetGame(playerChoiceImage)
            resetGame(winningChoiceImage)
            resetGame(computerChoiceImage)
        }


        if(playerChoice == ""){
            setInstructionText("Please choose either Rock, Paper, or Scissors")
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
        val playerText : TextView = findViewById(R.id.playerScore)
        val computerText :  TextView = findViewById(R.id.computerScore)
        var winnerText = ""
        playerWins = false
        computerWins = false

        //game conditions
        if(playerChoice == computerChoice){
            winnerText = "Tie!"
            setWinningImages(winningChoiceImage, playerChoice)
        } else if ((playerChoice == "rock" && computerChoice == "scissors") ||
            (playerChoice == "paper" && computerChoice == "rock") ||
            (playerChoice == "scissors" && computerChoice == "paper")){
            playerWins = true
        } else {
            computerWins = true
        }

        //set winner
        if (playerWins){
            winnerText = "Player Wins!"
            setWinningImages(winningChoiceImage, playerChoice)
            playerScore++
        } else if (computerWins){
            winnerText = "Computer Wins!"
            setWinningImages(winningChoiceImage, computerChoice)
            computerScore++
        }
        setInstructionText(winnerText)
        updateScore(playerText, "Player Score: $playerScore")
        updateScore(computerText, "Computer Score: $computerScore")

        if(playerScore == 10){
            winnerText = "Player wins the game!"
            CoroutineScope(Dispatchers.Main).launch {
                delay(2000)
                resetButton?.isEnabled = true
            }
        } else if(computerScore == 10){
            winnerText = "Computer Wins the game!"
            CoroutineScope(Dispatchers.Main).launch {
                delay(2000)
                resetButton?.isEnabled = true
            }
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
        playerWins = false
        computerWins = false

        setInstructionText("Please choose either Rock, Paper, or Scissors")
        paper?.isEnabled = true
        rock?.isEnabled = true
        scissors?.isEnabled = true
        resetButton?.isEnabled = false
    }

    private fun updateScore(textView : TextView, score : String){
        textView.text = score
    }

}