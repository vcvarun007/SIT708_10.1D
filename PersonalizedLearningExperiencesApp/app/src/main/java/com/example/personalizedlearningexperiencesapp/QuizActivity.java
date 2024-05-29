package com.example.personalizedlearningexperiencesapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class QuizActivity extends AppCompatActivity {

    public interface MyApiService {
        @GET("api.php?amount=5&type=multiple")
        Call<QuizQuestionDataModel> getDataModel();
    }

    private TextView questionTextView, questionNumberTextView, scoreSummaryTextView;
    private ProgressBar progressBar;
    private RadioGroup radioGroupOptions;
    private Button nextButton, restartButton;
    private List<QuizQuestion> questions;
    private int currentQuestionIndex = 0;
    private int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        initializeUI();

        nextButton.setOnClickListener(v -> handleNextButtonClick());

        loadQuizData();
    }

    private void initializeUI() {
        questionTextView = findViewById(R.id.quesContent);
        questionNumberTextView = findViewById(R.id.quesNum);
        progressBar = findViewById(R.id.progressBar);
        radioGroupOptions = findViewById(R.id.radioGroupOptions);
        nextButton = findViewById(R.id.nextButton2);
        scoreSummaryTextView = findViewById(R.id.scoreSummary);
        restartButton = findViewById(R.id.restartButton);

        restartButton.setOnClickListener(v -> finish());
    }

    private void handleNextButtonClick() {
        RadioButton selectedRadioButton = findViewById(radioGroupOptions.getCheckedRadioButtonId());
        if (selectedRadioButton != null && questions != null && !questions.isEmpty()) {
            if (selectedRadioButton.getText().toString().equals(questions.get(currentQuestionIndex).getCorrectAnswer())) {
                score++;
            }
        }

        if (currentQuestionIndex < questions.size() - 1) {
            currentQuestionIndex++;
            displayQuestion(questions.get(currentQuestionIndex));
        } else {
            showFinalScore();
        }
    }

    private void displayQuestion(QuizQuestion question) {
        questionTextView.setText(question.getQuestion());
        questionNumberTextView.setText("Question #" + (currentQuestionIndex + 1));
        radioGroupOptions.removeAllViews();

        List<String> options = new ArrayList<>(question.getIncorrectAnswers());
        options.add(question.getCorrectAnswer());
        Collections.shuffle(options);

        for (String option : options) {
            RadioButton radioButton = new RadioButton(this);
            radioButton.setText(option);
            radioGroupOptions.addView(radioButton);
        }
        radioGroupOptions.clearCheck(); // Clear any previous selection
    }

    private void showFinalScore() {
        progressBar.setVisibility(View.GONE);
        questionTextView.setVisibility(View.GONE);
        radioGroupOptions.setVisibility(View.GONE);
        questionNumberTextView.setVisibility(View.GONE);
        nextButton.setVisibility(View.GONE);

        scoreSummaryTextView.setText("Quiz completed! Your score: " + score + "/" + questions.size());
        scoreSummaryTextView.setVisibility(View.VISIBLE);
        restartButton.setVisibility(View.VISIBLE);
        restartButton.setText("Return to Main Screen");
    }

    private void loadQuizData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://opentdb.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MyApiService myApiService = retrofit.create(MyApiService.class);

        myApiService.getDataModel().enqueue(new Callback<QuizQuestionDataModel>() {
            @Override
            public void onResponse(Call<QuizQuestionDataModel> call, Response<QuizQuestionDataModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    questions = response.body().getResults();
                    if (!questions.isEmpty()) {
                        displayQuestion(questions.get(currentQuestionIndex));
                    } else {
                        questionTextView.setText("No questions available.");
                        nextButton.setEnabled(false);
                    }
                } else {
                    questionTextView.setText("Failed to load questions. Response Error: " + response.code());
                    nextButton.setEnabled(false);
                }
            }

            @Override
            public void onFailure(Call<QuizQuestionDataModel> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                questionTextView.setText("Failed to load quiz data: " + t.getMessage());
                nextButton.setEnabled(false);
            }
        });
    }
}
