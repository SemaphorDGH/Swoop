package semaphor.swoop.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import butterknife.ButterKnife;
import semaphor.swoop.R;
import semaphor.swoop.activities.HomeActivity;
import semaphor.swoop.database.PostModel;
import semaphor.swoop.database.PostsDatabaseHandler;

public class PostFragment extends BaseFragment{
    private static final String TAG = "PostFragment";

    private  PostsDatabaseHandler postsDatabaseHandler;

    TextInputEditText _post_question, _post_answer_1, _post_answer_2;
    Button _post_submit_button;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        postsDatabaseHandler = new PostsDatabaseHandler(this.getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_post, container, false);

        Log.d(TAG, "onCreateView is called");

        ButterKnife.bind(this, view);

        ((HomeActivity)getActivity()).updateToolbarTitle("Post");

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        _post_question = view.findViewById(R.id.post_question);
        _post_answer_1 = view.findViewById(R.id.post_answer_1);
        _post_answer_2 = view.findViewById(R.id.post_answer_2);
        _post_submit_button = view.findViewById(R.id.post_submit_button);

        _post_submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
            }
        });
    }

    /**
     *
     */
    public void reset() {
        _post_question.getText().clear();
        _post_answer_1.getText().clear();
        _post_answer_2.getText().clear();
    }

    /**
     *
     */
    public void submit() {
        Log.d(TAG,"Submit New Post");

        _post_submit_button.setEnabled(false);

        String username = "Hung";
        String textQuestion = _post_question.getText().toString();
        String answer_1 = _post_answer_1.getText().toString();
        String answer_2 = _post_answer_2.getText().toString();

        String[] answers = new String[2];
        answers[0] = answer_1;
        answers[1] = answer_2;
        String textAnswer = convertAnswersToTextAnswer(answers);

        if (validate()) {
            PostModel newPost = new PostModel(username, textQuestion, textAnswer);
            postsDatabaseHandler.addPost(newPost);

            reset();
        }
    }

    /**
     *
     * @return
     */
    public boolean validate() {
        boolean valid = true;

        String textQuestion = _post_question.getText().toString();
        String answer_1 = _post_answer_1.getText().toString();
        String answer_2 = _post_answer_2.getText().toString();

        // question can be empty but answers cannot
        if (!answer_1.isEmpty() && !answer_2.isEmpty()) {
            return true;
        } else return false;
    }

    /**
     *
     * @param answers
     * @return
     */
    public String convertAnswersToTextAnswer(String[] answers) {
        String textAnswer = "";
        for (int i = 0; i < answers.length; i++) {
            String answer = answers[i];
            if (i != answers.length - 1) {
                textAnswer += answer + ";";
            } else {
                textAnswer += answer;
            }
        }
        return textAnswer;
    }
}