package com.gmail.ryanwickham259.quizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val userName: String? = intent.getStringExtra(Constants.USER_NAME)
        val correctAnswerAmount: String = intent.getIntExtra(Constants.CORRECT_ANSWERS_AMOUNT, 0).toString()
        val totalQuestionAmount: String = intent.getIntExtra(Constants.TOTAL_QUESTION_AMOUNT, 0).toString()

        val tvUserName: TextView = findViewById(R.id.tvUserName)
        val tvScore: TextView = findViewById(R.id.tvScore)
        val btnFinished: Button = findViewById(R.id.btnFinish)

        tvUserName.text = userName
        tvScore.text = "Your Score is $correctAnswerAmount our of $totalQuestionAmount"

        btnFinished.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}