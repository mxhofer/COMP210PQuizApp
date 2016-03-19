package com.example.maximilianhofer.triviality;
import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

//retrieved framework for the database and adapted it from: https://www.developerfeed.com/simple-quiz-game-andriod/
public class DbHelper extends SQLiteOpenHelper {
	private static final int DATABASE_VERSION = 1;
	// Database Name
	private static final String DATABASE_NAME = "triviaQuiz1";
	// tasks table name
	private static final String TABLE_QUEST = "quest";
	// tasks Table Columns names
	private static final String KEY_ID = "id";
	private static final String KEY_QUES = "question";
	private static final String KEY_ANSWER = "answer"; //correct option
	private static final String KEY_OPTA= "opta"; //option a
	private static final String KEY_OPTB= "optb"; //option b
	private static final String KEY_OPTC= "optc"; //option c
	private SQLiteDatabase dbase; //found SQLiteDatabase package on stackoverflow
	public DbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	@Override
	public void onCreate(SQLiteDatabase db) {
		dbase=db;
		String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_QUEST + " ( "
				+ KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_QUES
				+ " TEXT, " + KEY_ANSWER+ " TEXT, "+KEY_OPTA +" TEXT, "
				+KEY_OPTB +" TEXT, "+KEY_OPTC+" TEXT)";
		db.execSQL(sql);		
		addQuestions();
	}

	//add question method and define 10 questions and answer options for the database
	private void addQuestions()
	{
		Question q1=new Question("A primitive data type can be:","String", "array", "int", "int");
		this.addQuestion(q1);
		Question q2=new Question("The difference between type float and type double is:",
				"Variables of type float cannot store negative values",
				"Variables of type double are only accurate to about 7 decimal places",
				"Values of type double can be assigned to variables of type float, but not the other way around",
				"Variables of type double are only accurate to about 7 decimal places");
		this.addQuestion(q2);
		Question q3=new Question("The java virtual machine:","Compiles Java source code",
				"Replaces the operating system","Runs the compiled Java code","Runs the compiled Java code");
		this.addQuestion(q3);
		Question q4=new Question("for(int i = 0; i < 10; i++) System.out.print(\"Hello\");",
				"Will print \"Hello\" 9 times", "Will print \"Hello\" 10 times",
				"Will print the value of i 10 times", "Will print \"Hello\" 10 times");
		this.addQuestion(q4);
		Question q5=new Question("Who is the inventor of Java?","Matt Damon","Ryan Gosling",
				"James Gosling","James Gosling");
		this.addQuestion(q5);

		Question q6=new Question("Who is the lecturer of COMP210P?","Rae Harbird","Dan Bilzarian","Lynda",
				"Rae Harbird");
		this.addQuestion(q6);
		Question q7=new Question("What is polymorphism?",
				"Ability of programming languages to present the same interface for differing underlying data types",
				"Re-usability of code and can be used to add additional features to an existing class, without modifying it",
				"Process of separating ideas from specific instances",
				"Ability of programming languages to present the same interface for differing underlying data types");
		this.addQuestion(q7);
		Question q8=new Question("What is the Java compiler called?",
				"JVM","javac","Android Studio","javac");
		this.addQuestion(q8);
		Question q9=new Question("Which of the following is an operator?",
				"#","_","tgif","#");
		this.addQuestion(q9);
		Question q10=new Question("The result of expression (int) -34.6 is:",
				"-34","-34.6","-35","-34");
		this.addQuestion(q10);
	}

	// create a new SQL table
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUEST);
		// Create tables again
		onCreate(db);
	}
	// Adding new question
	public void addQuestion(Question quest) {
		//SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_QUES, quest.getQUESTION()); 
		values.put(KEY_ANSWER, quest.getANSWER());
		values.put(KEY_OPTA, quest.getOPTA());
		values.put(KEY_OPTB, quest.getOPTB());
		values.put(KEY_OPTC, quest.getOPTC());
		// Inserting Row
		dbase.insert(TABLE_QUEST, null, values);
	}

	//returns a list of questions
	public List<Question> getAllQuestions() {
		List<Question> quesList = new ArrayList<Question>();
		// "Select All" Query
		String selectQuery = "SELECT  * FROM " + TABLE_QUEST;
		dbase=this.getReadableDatabase();
		Cursor cursor = dbase.rawQuery(selectQuery, null);
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Question quest = new Question();
				quest.setID(cursor.getInt(0));
				quest.setQUESTION(cursor.getString(1));
				quest.setANSWER(cursor.getString(2));
				quest.setOPTA(cursor.getString(3));
				quest.setOPTB(cursor.getString(4));
				quest.setOPTC(cursor.getString(5));
				quesList.add(quest);
			} while (cursor.moveToNext());
		}
		// return question list
		return quesList;
	}
}
