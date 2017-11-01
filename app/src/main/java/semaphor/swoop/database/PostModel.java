package semaphor.swoop.database;

/**
 * Created by hungnguyen on 10/21/2017.
 */

public class PostModel {
    private int id;
    private String[] textAnswer;
    private String username, textQuestion, date;

    // Empty constructor
    public PostModel() {

    }

    public PostModel(int id, String username, String textQuestion, String textAnswer) {
        this.id = id;
        this.username = username;
        this.textQuestion = textQuestion;
        this.textAnswer = textAnswer.split(";");
    }

    public PostModel(String username, String textQuestion, String textAnswer) {
        this.username = username;
        this.textQuestion = textQuestion;
        this.textAnswer = textAnswer.split(";");
    }

    public PostModel(int id, String username, String textQuestion, String textAnswer, String date) {
        this.id = id;
        this.username = username;
        this.textQuestion = textQuestion;
        this.textAnswer = textAnswer.split(";");
        this.date = date;
    }

    public PostModel(String username, String textQuestion, String textAnswer, String date) {
        this.username = username;
        this.textQuestion = textQuestion;
        this.textAnswer = textAnswer.split(";");
        this.date = date;
    }

    public PostModel(int id, String username, String textQuestion, String[] textAnswer) {
        this.id = id;
        this.username = username;
        this.textQuestion = textQuestion;
        this.textAnswer = textAnswer;
    }

    public PostModel(String username, String textQuestion, String[] textAnswer) {
        this.username = username;
        this.textQuestion = textQuestion;
        this.textAnswer = textAnswer;
    }

    public PostModel(int id, String username, String textQuestion, String[] textAnswer, String date) {
        this.id = id;
        this.username = username;
        this.textQuestion = textQuestion;
        this.textAnswer = textAnswer;
        this.date = date;
    }

    public PostModel(String username, String textQuestion, String[] textAnswer, String date) {
        this.username = username;
        this.textQuestion = textQuestion;
        this.textAnswer = textAnswer;
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
    public String getDate() {
        return this.date;
    }

    // Set date
    public void setDate(String date) {
        this.date = date;
    }

    // Get array text answer
    public String[] getArrayTextAnswer() {
        return this.textAnswer;
    }

    // Get string text answer
    public String getStringTextAnswer() {
        String textAnswer = "";
        int n = this.textAnswer.length;
        for (int i = 0; i < n; i++) {
            if (i == n - 1) {
                textAnswer += this.textAnswer[i];
            } else {
                textAnswer += this.textAnswer[i] + ";";
            }
        }
        return textAnswer;
    }

    // Set text answer by array of string
    public void setTextAnswer(String[] textAnswer) {
        this.textAnswer = textAnswer;
    }

    // Set text answer by string
    public void setTextAnswer(String text) {
        String[] textAnswer = text.split(";");
        this.textAnswer = textAnswer;
    }
}
