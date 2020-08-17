package com.iamdj.triviaquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button falseButton;
    private Button trueButton;
    private TextView questionTextView;
    private ImageButton nextButton;
    private ImageButton previousButton;

    private int currentQuestionIndex = 0;
    Vibrator vibe;  // Using Vibration services.

    private Question[] questionBank = new Question[]{
            // question_cyclone is initialised with first index.
            new Question(R.string.question_cyclone, true),
            new Question(R.string.question_goldfish, false),
            new Question(R.string.question_octopus, false),
            new Question(R.string.question_stephen_hawking, true),
            new Question(R.string.question_religion, true),
            new Question(R.string.question_great_wall, false),
            new Question(R.string.question_aeroplane, true),
            new Question(R.string.question_government_senators, true),
            new Question(R.string.question_developer_name, true)
            // later on, might be added more.
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Uses system service to perform vibration on wrong answer chosen.
        vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        // For one question,but not gonna work for more question.
       // Question question = new Question(R.string.question_declaration, true);

        falseButton = findViewById(R.id.false_button);
        trueButton = findViewById(R.id.true_button);
        questionTextView = findViewById(R.id.answer_text_view);
        nextButton = findViewById(R.id.next_button);
        previousButton = findViewById(R.id.previous_button);

        falseButton.setOnClickListener(this);
        trueButton.setOnClickListener(this);
        nextButton.setOnClickListener(this);
        previousButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.false_button:
                checkAnswer(false);
                break;

            case R.id.true_button:
                checkAnswer(true);
                break;

            case R.id.next_button:
                // Go to next question.
                currentQuestionIndex = (currentQuestionIndex + 1) % questionBank.length; // avoiding to cross last index.
                updateQuestion();
                break;

            case R.id.previous_button:
                // Go to Previous question.
                if(currentQuestionIndex > 0)  // avoiding under bound of array.
                {
                    currentQuestionIndex = (currentQuestionIndex - 1) % questionBank.length;
                    updateQuestion();
                }else{
                    Toast.makeText(MainActivity.this, "You reached at first question.", Toast.LENGTH_SHORT).show();
                }
        }

    }

    private void updateQuestion(){
        questionTextView.setText(questionBank[currentQuestionIndex].getAnswerResId());
    }

    private void checkAnswer(boolean userChooseCorrect){
        boolean answerIsTrue = questionBank[currentQuestionIndex].isAnswerTrue();  // get the answer is true.

        int toastMessageId = 0;
        if(userChooseCorrect == answerIsTrue){
            toastMessageId = R.string.correct_answer;
        }
        else{
            toastMessageId = R.string.wrong_answer;
            vibe.vibrate(100); // make vibration on wrong answer chosen.
        }
        Toast.makeText(this, toastMessageId, Toast.LENGTH_SHORT).show();
    }
}