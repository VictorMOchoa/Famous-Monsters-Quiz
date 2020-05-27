package com.eatanapple.famousmonsters.dto;

/**
 * Created by Victor on 5/26/2020.
 */

public class QuizQuestion {

    private String[] options;

    private String answer;

    private String question;

    public QuizQuestion(String question, String answer, String[] options) {
        this.question = question;
        this.answer = answer;
        this.options = options;
    }

    public String[] getOptions() {
        return options;
    }

    public void setOptions(String[] options) {
        this.options = options;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

}
