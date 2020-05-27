package com.eatanapple.famousmonsters;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.eatanapple.famousmonsters.dto.QuizQuestion;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.question_tv)
    TextView quizQuestionTextView;

    @BindView(R.id.next_btn)
    Button nextQuestionButton;

    @BindView(R.id.retry_btn)
    Button retryButton;

    @BindView(R.id.option1)
    RadioButton option1;
    @BindView(R.id.option2)
    RadioButton option2;
    @BindView(R.id.option3)
    RadioButton option3;
    @BindView(R.id.option4)
    RadioButton option4;

    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;

    @BindView(R.id.user_input_et)
    EditText userInputEditText;

    @BindView(R.id.option1_chk)
    CheckBox optionOneCheckbox;

    @BindView(R.id.option2_chk)
    CheckBox optionTwoCheckbox;

    @BindView(R.id.option3_chk)
    CheckBox optionThreeCheckbox;

    @BindView(R.id.option4_chk)
    CheckBox optionFourCheckbox;

    @BindView(R.id.checkbox_ll)
    LinearLayout checkBoxLinearLayout;

    List<QuizQuestion> questions = new ArrayList<>();
    int currScore = 0;
    int currQuestion = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setupQuizData();
        setupFirstQuestion();

        nextQuestionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            // Check if game is over OR update UI
            if (currQuestion <= 5) {
                int checked = radioGroup.getCheckedRadioButtonId();
                // First verify that something was selected
                if( checked == -1) {
                    Toast.makeText(getApplicationContext(), "Please select one of the options", Toast.LENGTH_SHORT).show();
                } else {
                    // Next calculate and update the score
                    String answer = questions.get(currQuestion).getAnswer();
                    String userAnswer = ((RadioButton)findViewById(checked)).getText().toString();
                    if (answer.equals(userAnswer)) {
                        currScore += 1;
                        Toast.makeText(getApplicationContext(), "Correct!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Incorrect", Toast.LENGTH_SHORT).show();
                    }
                    updateQuestion();
                    System.out.println("Value of currQuestion: " + currQuestion);
                }
            } else if (currQuestion == 6) {
                String userInput = userInputEditText.getText().toString();
                if (userInput.length() == 0) {
                    Toast.makeText(getApplicationContext(), "You must enter an answer", Toast.LENGTH_SHORT).show();
                } else {
                    if (userInput.trim().toLowerCase().equals(questions.get(currQuestion).getAnswer().toLowerCase())) {
                        currScore += 1;
                        Toast.makeText(getApplicationContext(), "Correct!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Incorrect", Toast.LENGTH_SHORT).show();
                    }
                    updateQuestion();
                }
            } else if (currQuestion == 7) {
//                nextQuestionButton.setVisibility(View.GONE);
//                retryButton.setVisibility(View.VISIBLE);
//                Toast.makeText(getApplicationContext(), "Game Over! You got " + currScore + " out of " + questions.size() + " correct!", Toast.LENGTH_LONG).show();
            }

            }
        });

        retryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currQuestion = 0;
                currScore = 0;
                radioGroup.clearCheck();
                setupFirstQuestion();
                nextQuestionButton.setVisibility(View.VISIBLE);
                retryButton.setVisibility(View.GONE);
            }
        });
    }

    private void updateQuestion() {
        radioGroup.clearCheck();
        currQuestion += 1;
        quizQuestionTextView.setText(questions.get(currQuestion).getQuestion());
        if (currQuestion <= 5) {
            option1.setText(questions.get(currQuestion).getOptions()[0]);
            option2.setText(questions.get(currQuestion).getOptions()[1]);
            option3.setText(questions.get(currQuestion).getOptions()[2]);
            option4.setText(questions.get(currQuestion).getOptions()[3]);
            System.out.println("should only print for radio questions..");
        } else if (currQuestion == 6) {
            radioGroup.setVisibility(View.GONE);
            userInputEditText.setVisibility(View.VISIBLE);
        } else {
            userInputEditText.setVisibility(View.GONE);
            checkBoxLinearLayout.setVisibility(View.VISIBLE);
            optionOneCheckbox.setText(questions.get(currQuestion).getOptions()[0]);
            optionTwoCheckbox.setText(questions.get(currQuestion).getOptions()[1]);
            optionThreeCheckbox.setText(questions.get(currQuestion).getOptions()[2]);
            optionFourCheckbox.setText(questions.get(currQuestion).getOptions()[3]);
            System.out.println("In seventh question..");
        }
    }

    private void setupFirstQuestion() {
        quizQuestionTextView.setText(questions.get(0).getQuestion());
        option1.setText(questions.get(0).getOptions()[0]);
        option2.setText(questions.get(0).getOptions()[1]);
        option3.setText(questions.get(0).getOptions()[2]);
        option4.setText(questions.get(0).getOptions()[3]);
    }

    private void setupQuizData() {

        questions.add(new QuizQuestion("Who was the murderer in the first Friday The 13th movie?",
                "Mrs. Voorhees", new String[] {
                "Jason", "Mrs. Voorhees", "Water", "A Woman's Scorn"}
        ));

        questions.add(new QuizQuestion("What was the first horror film to receive a Best Picture nomination at the Academy Awards?",
                "The Exorcist (1973)", new String[] {
                "The Thing (1982)", "The Silence of The Lambs (1992)", "The Babadook (2014)", "The Exorcist (1973)"}
        ));

        questions.add(new QuizQuestion("What famously low-budget blockbuster is credited with popularising the ‘found footage’ film genre?",
                "The Blair Witch Project (1999)", new String[] {
                "Paranormal Activity (2007)", "REC (2007)", "The Blair Witch Project (1999)", "CREEP (2014)"}
        ));

        questions.add(new QuizQuestion("What movie served up audiences’ first taste of cannibalistic zombies, with a side of social commentary?",
                "Night of The Living Dead", new String[] {
                "Night of The Living Dead", "The Green Inferno", "White Zombie", "28 Days Later"}
        ));

        questions.add(new QuizQuestion("What cult movie franchise is said to be cursed following a series of accidents and tragedies involving actors in the various films? ",
                "Poltergeist", new String[] {
                "Jaws", "Jeepers Creepers", "Poltergeist", "Halloween"}
        ));

        questions.add(new QuizQuestion("Which one of these is NOT a Stephen King adaptation?",
                "Stranger Things", new String[] {
                "IT", "Cujo", "Stranger Things", "Pet Sematary"}
        ));

        questions.add(new QuizQuestion("What is the name of the killer doll in the Child's Play series?",
                "Chucky", null
        ));

        questions.add(new QuizQuestion("Which one of these movies is not about ghosts?",
                "Get Out", new String[] {
                "The Sixth Sense", "Get Out", "Paranormal Activity", "Poltergeist"}
        ));

    }
}
