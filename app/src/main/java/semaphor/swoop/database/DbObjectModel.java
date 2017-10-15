package semaphor.swoop.database;

/**
 * Created by derek-w on 10/14/17.
 */

public class DbObjectModel {

    String word, meaning;

    public DbObjectModel(String word, String meaning) {

        this.word = word;
        this.mceaning = meaning;


    }

    public String getWord() {
        return word;
    }

    public String getMeaning() {
        return meaning;
    }

}