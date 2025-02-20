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
    private var playerChoiceImage : ImageView? = null
    private var computerChoiceImage : ImageView? = null
    private var winningChoiceImage : ImageView? = null

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

        playerChoiceImage = findViewById(R.id.playerChoiceImage)
        computerChoiceImage = findViewById(R.id.computerChoiceImage)
        winningChoiceImage = findViewById(R.id.winningChoiceImage)

        //button events
        paper?.setOnClickListener {
            playerChoiceImage?.setImageResource(R.drawable.paper)
            checkPlayerChoice("paper")
            setInstructionText("Player chose Paper")
            disableAllButtons()
        }
        rock?.setOnClickListener {
            playerChoiceImage?.setImageResource(R.drawable.rock)
            checkPlayerChoice("rock")
            setInstructionText("Player chose Rock")
            disableAllButtons()
        }
        scissors?.setOnClickListener {
            playerChoiceImage?.setImageResource(R.drawable.scissors)
            checkPlayerChoice("scissors")
            setInstructionText("Player chose Scissors")
            disableAllButtons()
        }
        resetButton?.setOnClickListener {
            playerScore = 0
            computerScore = 0
            goToNextRound()
            resetButton?.isEnabled = false
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

        //update text views
        updateScore(playerText, "Player Score: $playerScore")
        updateScore(computerText, "Computer Score: $computerScore")
        setInstructionText(winnerText)

        //end game and enable reset button if there is a winner, else go to next round
        if(playerScore == 10 || computerScore == 10){
            val finalWinningText: String = if(playerScore == 10){
                "Player Wins the game!"
            } else {
                "Computer Wins the game!"
            }
            setInstructionText(finalWinningText)
            //reset game
            CoroutineScope(Dispatchers.Main).launch {
                delay(2000)
                resetButton?.isEnabled = true
            }
        } else {
            //go to next round after short delay
            CoroutineScope(Dispatchers.Main).launch {
                delay(3000)
                goToNextRound()
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

    private fun updateScore(textView : TextView, score : String){
        textView.text = score
    }

    private fun goToNextRound(){
        setInstructionText("Please choose either Rock, Paper, or Scissors")
        paper?.isEnabled = true
        rock?.isEnabled = true
        scissors?.isEnabled = true

        playerWins = false
        computerWins = false

        //remove images
        //im not exactly sure what this syntax means, it was recommended by the ide
        playerChoiceImage?.let { resetImages(it) }
        computerChoiceImage?.let { resetImages(it) }
        winningChoiceImage?.let { resetImages(it) }
    }

    private fun resetImages(imageView: ImageView){
        //remove all images
        imageView.setImageResource(0)
    }
}