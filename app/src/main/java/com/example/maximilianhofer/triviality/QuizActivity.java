package com.example.maximilianhofer.triviality;

import java.util.List;
import java.util.Random;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.triviality.R;

// creates the activity to play the 5 questions, picked randomly
public class QuizActivity extends Activity {
	public static List<Question> quesList;
	int score=0;
	Random r = new Random();
	int randomInput = 10; //hard-coded number of questions!!!
	int qid = r.nextInt(randomInput);
	int qCounter = 0;
	Question currentQ;
	TextView txtQuestion;
	RadioButton rda, rdb, rdc;
	Button butNext;
	@Override

	// onCreate method starts the activity
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_quiz);
		DbHelper db=new DbHelper(this);
		quesList=db.getAllQuestions();
		currentQ=quesList.get(qid);
		txtQuestion=(TextView)findViewById(R.id.textView1);
		rda=(RadioButton)findViewById(R.id.radio0);
		rdb=(RadioButton)findViewById(R.id.radio1);
		rdc=(RadioButton)findViewById(R.id.radio2);
		butNext=(Button)findViewById(R.id.button1);
		setQuestionView();
		butNext.setOnClickListener(new View.OnClickListener() {		
			@Override
			public void onClick(View v) {
				RadioGroup grp=(RadioGroup)findViewById(R.id.radioGroup1);
				RadioButton answer=(RadioButton)findViewById(grp.getCheckedRadioButtonId());
				Log.d("yourans", currentQ.getANSWER()+" "+answer.getText());
				if(currentQ.getANSWER().equals(answer.getText()))
				{
					score++;
					Log.d("score", "Your score"+score);
				}
				if(qCounter<5){
					currentQ=quesList.get(qid);
					setQuestionView();
				}else{
					Intent intent = new Intent(QuizActivity.this, ResultActivity.class);
					Bundle b = new Bundle();
					b.putInt("score", score); //Your score
					intent.putExtras(b); //Put your score to your next Intent
					startActivity(intent);
					finish();
				}
			}
		});
	}

	// sets each question, including getting the question itself and all answers
	private void setQuestionView()
	{
		txtQuestion.setText(currentQ.getQUESTION());
		rda.setText(currentQ.getOPTA());
		rdb.setText(currentQ.getOPTB());
		rdc.setText(currentQ.getOPTC());
		quesList.remove(qid);
		randomInput--;
		qid = r.nextInt(randomInput);
		qCounter++;
	}
}
