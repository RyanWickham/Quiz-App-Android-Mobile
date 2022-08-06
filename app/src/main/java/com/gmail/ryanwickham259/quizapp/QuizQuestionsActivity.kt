package com.gmail.ryanwickham259.quizapp

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.content.ContextCompat

class QuizQuestionsActivity : AppCompatActivity(), View.OnClickListener {

    private var userName: String? = null
    private var questionList: ArrayList<Question>? = null
    private var currentPosition: Int = 1
    private var selectedOptionPosition: Int = 0
    private var correctAnswerAmount: Int = 0

    private var progressBar: ProgressBar? = null
    private var tvProgress: TextView? = null
    private var tvQuestion: TextView? = null
    private var ivImage: ImageView? = null
    private var btnSubmit: Button? = null

    private var tvOptionOne: TextView? = null
    private var tvOptionTwo: TextView? = null
    private var tvOptionThree: TextView? = null
    private var tvOptionFour: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_questions)

        userName = intent.getStringExtra(Constants.USER_NAME)

        progressBar = findViewById(R.id.progressBar)
        tvProgress = findViewById(R.id.tvProgress)
        tvQuestion = findViewById(R.id.tvQuestion)
        ivImage = findViewById(R.id.ivImage)
        btnSubmit = findViewById(R.id.btnSubmit)

        questionList = Constants.getQuestions()

        tvOptionOne = findViewById(R.id.tvOptionOne)
        tvOptionTwo = findViewById(R.id.tvOptionTwo)
        tvOptionThree = findViewById(R.id.tvOptionThree)
        tvOptionFour = findViewById(R.id.tvOptionFour)

        tvOptionOne?.setOnClickListener(this)
        tvOptionTwo?.setOnClickListener(this)
        tvOptionThree?.setOnClickListener(this)
        tvOptionFour?.setOnClickListener(this)

        btnSubmit?.setOnClickListener(this)

        setQuestion()
    }

    private fun setQuestion(){
        val question: Question = questionList!![currentPosition - 1]
        defaultOptionView()

        if(currentPosition == questionList!!.size){
            btnSubmit?.text = "FINISH"
        }else{
            btnSubmit?.text = "SUBMIT"
        }

        progressBar?.progress = currentPosition
        tvProgress?.text = "$currentPosition / ${progressBar?.max}"

        tvQuestion?.text = question.question
        ivImage?.setImageResource(question.image)

        tvOptionOne?.text = question.optionOne
        tvOptionTwo?.text = question.optionTwo
        tvOptionThree?.text = question.optionThree
        tvOptionFour?.text = question.optionFour
    }

    override fun onClick(view: View?){
        when(view?.id){
            R.id.tvOptionOne -> {
                tvOptionOne?.let{
                    selectedOptionView(it, 1)
                }
            }
            R.id.tvOptionTwo -> {
                tvOptionTwo?.let{
                    selectedOptionView(it, 2)
                }
            }
            R.id.tvOptionThree -> {
                tvOptionThree?.let{
                    selectedOptionView(it, 3)
                }
            }
            R.id.tvOptionFour -> {
                tvOptionFour?.let{
                    selectedOptionView(it, 4)
                }
            }
            R.id.btnSubmit -> {
                if(selectedOptionPosition == 0){
                    currentPosition++

                    when{
                        currentPosition <= questionList!!.size -> {
                            setQuestion()
                        }
                        else -> {
                            val intent = Intent(this, ResultActivity::class.java)

                            intent.putExtra(Constants.USER_NAME, userName)
                            intent.putExtra(Constants.CORRECT_ANSWERS_AMOUNT, correctAnswerAmount)
                            intent.putExtra(Constants.TOTAL_QUESTION_AMOUNT, questionList?.size)

                            startActivity(intent)
                            finish()
                        }
                    }
                }else{
                    val question: Question? = questionList?.get(currentPosition - 1)
                    if(question!!.correctAnswer != selectedOptionPosition){
                        answerView(selectedOptionPosition, R.drawable.wrong_option_border_bg)

                    }else{
                        correctAnswerAmount++
                    }
                    answerView(question.correctAnswer, R.drawable.correct_option_border_bg)

                    if(currentPosition == questionList!!.size){
                        btnSubmit?.text = "Finish"
                    }else{
                        btnSubmit?.text = "GO TO NET QUESTION"
                    }

                    selectedOptionPosition = 0
                }
            }
        }
    }

    private fun defaultOptionView(){
        val options = ArrayList<TextView>()

        tvOptionOne?.let{
            options.add(0, it)
        }
        tvOptionTwo?.let{
            options.add(1, it)
        }
        tvOptionThree?.let{
            options.add(2, it)
        }
        tvOptionFour?.let{
            options.add(3, it)
        }

        for(option in options){
            option.setTextColor(Color.parseColor("#7A8089"))
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(
                this,
                R.drawable.default_option_border_bg
            )
        }
    }

    private fun selectedOptionView(tv: TextView, selectedOptionNum: Int){
        defaultOptionView()

        selectedOptionPosition = selectedOptionNum

        tv.setTextColor(Color.parseColor("#363A43"))
        tv.setTypeface(tv.typeface, Typeface.BOLD)

        tv.background = ContextCompat.getDrawable(
            this,
            R.drawable.selected_option_border_bg
        )
    }



    private fun answerView(answer: Int, drawableView: Int){
        when(answer){
            1 -> tvOptionOne?.background = ContextCompat.getDrawable(this, drawableView)
            2 -> tvOptionTwo?.background = ContextCompat.getDrawable(this, drawableView)
            3 -> tvOptionThree?.background = ContextCompat.getDrawable(this, drawableView)
            4 -> tvOptionFour?.background = ContextCompat.getDrawable(this, drawableView)
        }
    }
}