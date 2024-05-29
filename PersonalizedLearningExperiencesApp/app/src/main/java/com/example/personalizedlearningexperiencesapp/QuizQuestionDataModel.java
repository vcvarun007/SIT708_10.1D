package com.example.personalizedlearningexperiencesapp;

import java.util.List;

public class QuizQuestionDataModel {
    private int responseCode;
    private List<QuizQuestion> results;

    // Getters and setters
    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public List<QuizQuestion> getResults() {
        return results;
    }

    public void setResults(List<QuizQuestion> results) {
        this.results = results;
    }
}

// Model for each quiz question
class QuizQuestion {
    private String type;
    private String difficulty;
    private String category;
    private String question;
    private String correctAnswer;
    private List<String> incorrectAnswers;

    // Getters and setters
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public List<String> getIncorrectAnswers() {
        return incorrectAnswers;
    }

    public void setIncorrectAnswers(List<String> incorrectAnswers) {
        this.incorrectAnswers = incorrectAnswers;
    }
}
