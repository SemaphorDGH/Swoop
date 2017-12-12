package semaphor.swoop.database;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by hungnguyen on 10/21/2017.
 */

public class PostModel {
    private int id;
    private String[] answers;
    private int[] votes;
    private String username, textQuestion;
    private Date date;

    // Empty constructor
    public PostModel() {

    }

    public PostModel(int id, String username, String textQuestion, String textAnswer) {
        this.id = id;
        this.username = username;
        this.textQuestion = textQuestion;
        this.answers = textAnswer.split(";");
    }

    public PostModel(String username, String textQuestion, String textAnswer) {
        this.username = username;
        this.textQuestion = textQuestion;
        this.answers = textAnswer.split(";");
    }

    public PostModel(int id, String username, String textQuestion, String textAnswer, Date date) {
        this.id = id;
        this.username = username;
        this.textQuestion = textQuestion;
        this.answers = textAnswer.split(";");
        this.date = date;
    }

    public PostModel(int id, String username, String textQuestion, String textAnswer, String date) throws ParseException {
        this.id = id;
        this.username = username;
        this.textQuestion = textQuestion;
        this.answers = textAnswer.split(";");
        this.date = getDateByStringDate(date);
    }

    public PostModel(String username, String textQuestion, String textAnswer, Date date) {
        this.username = username;
        this.textQuestion = textQuestion;
        this.answers = textAnswer.split(";");
        this.date = date;
    }

    public PostModel(String username, String textQuestion, String textAnswer, String date) throws ParseException {
        this.username = username;
        this.textQuestion = textQuestion;
        this.answers = textAnswer.split(";");
        this.date = getDateByStringDate(date);
    }

    public PostModel(int id, String username, String textQuestion, String[] textAnswer, Date date) {
        this.id = id;
        this.username = username;
        this.textQuestion = textQuestion;
        this.answers = textAnswer;
        this.date = date;
    }

    public PostModel(String username, String textQuestion, String[] textAnswer, Date date) {
        this.username = username;
        this.textQuestion = textQuestion;
        this.answers = textAnswer;
        this.date = date;
    }

    // Get id
    public int getID() {
        return this.id;
    }

    // Set id
    public void setID(int id) {
        this.id = id;
    }

    // Get username
    public String getUsername() {
        return this.username;
    }

    // Set username ?

    // Get textQuestion
    public String getTextQuestion() {
        return this.textQuestion;
    }

    // Get textQuestion
    public void setTextQuestion(String textQuestion) {
        this.textQuestion = textQuestion;
    }

    // Get date
    public Date getDate() {
        return this.date;
    }

    // Convert from formatted string to Date date
    public Date getDateByStringDate(String stringDate) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("EEEE, MMMM d, yyyy 'at' h:mm a");
        return format.parse(stringDate);
    }

    // Get date
    public String getStringDate() {
        SimpleDateFormat format = new SimpleDateFormat("EEEE, MMMM d, yyyy 'at' h:mm a");
        return format.format(this.date);
    }

    // Set date
    public void setDate(Date date) {
        this.date = date;
    }

    // Get array text answer
    public String[] getArrayTextAnswer() {
        return this.answers;
    }

    // Get string text answer
    public String getStringTextAnswer() {
        String textAnswer = "";
        int n = this.answers.length;
        for (int i = 0; i < n; i++) {
            if (i == n - 1) {
                textAnswer += this.answers[i];
            } else {
                textAnswer += this.answers[i] + ";";
            }
        }
        return textAnswer;
    }

    // Set text answer by array of string
    public void setAnswers(String[] answers) {
        this.answers = answers;
    }

    // Set text answer by string
    public void setTextAnswer(String text) {
        String[] textAnswer = text.split(";");
        this.answers = textAnswer;
    }
}
