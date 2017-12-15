package semaphor.swoop.fragments;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.ButterKnife;
import semaphor.swoop.R;
import semaphor.swoop.activities.HomeActivity;
import semaphor.swoop.database.PostModel;
import semaphor.swoop.database.PostsDatabaseHandler;

public class PostFragment extends BaseFragment{
    private static final String TAG = "PostFragment";

    private  PostsDatabaseHandler postsDatabaseHandler;

    private LinearLayout linearLayout;

    private TextInputEditText _post_question, _post_option_0, _post_option_1;
    private Button _post_add_option_button, _post_remove_option_button, _post_submit_button;

    private ArrayList<String> listAnswers = new ArrayList<>();
    private ArrayList<TextInputEditText> listOptions = new ArrayList<>();
    private int numberOfOptionButtons;

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

    /**
     *
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        linearLayout = (LinearLayout) view.findViewById(R.id.post_options_layout);

        _post_question = view.findViewById(R.id.post_question);

        // initial state for options in the fragment
        _post_option_0 = view.findViewWithTag("post_option_0");
        _post_option_1 = view.findViewWithTag("post_option_1");
        listOptions.add(_post_option_0);
        listOptions.add(_post_option_1);
        numberOfOptionButtons = 2;

        _post_add_option_button = view.findViewById(R.id.post_add_option_button);

        _post_remove_option_button = view.findViewById(R.id.post_remove_option_button);

        _post_submit_button = view.findViewById(R.id.post_submit_button);

        //reset(view);

        _post_add_option_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addOption(view);
            }
        });

        _post_remove_option_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeOption(view);
            };
        });

        _post_submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit(view);
            }
        });
    }

    /**
     *
     */
    public void reset(View view) {
        _post_question.getText().clear();
        _post_option_0.getText().clear();
        _post_option_1.getText().clear();

        for (int i = 2; i < numberOfOptionButtons; i++) {
            TextInputEditText postOption = view.findViewWithTag("post_option_" + i);
            if (postOption != null) {
                postOption.getText().clear();
            }
        }
    }

    public int getMarginBottom() {
        // convert dp and int to set margins for layout of the option button
        float scaleRatio = getResources().getDisplayMetrics().density;
        float dpPix = getResources().getDimension(R.dimen.component_margin_bottom);
        float dpValue = dpPix/scaleRatio;

        int marginBottom = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpValue, getResources().getDisplayMetrics()));

        return marginBottom;
    }

    /**
     *
     * @param view
     */
    public void addOption(View view) {
        Log.d(TAG, "Add New Option");

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        layoutParams.setMargins(0, 0,0, getMarginBottom());

        // design the editText
        TextInputEditText editText = new TextInputEditText(this.getActivity());
        String tag = "post_option_" + numberOfOptionButtons;
        numberOfOptionButtons++;
        editText.setTag(tag);
        listOptions.add(editText);

        // design the editText
        editText.setLayoutParams(layoutParams);
        editText.setBackgroundResource(R.drawable.rounded_editext);
        editText.setHintTextColor(getResources().getColor(R.color.iron, null));
        Drawable button = getResources().getDrawable(R.drawable.close_circular_button, null);
        editText.setCompoundDrawables(button, button, button, button);

        // set the hint for the editText
        CharSequence hint = getResources().getString(R.string.add_an_option);
        editText.setHint(hint);

        // add the editText to the specified layout
        linearLayout.addView(editText);
    }

    /**
     *
     * @param view
     */
    public void removeOption(View view) {
        int size = listOptions.size();
        if (size > 2) {
            Log.d(TAG, "Remove Most Recent Option");

            TextInputEditText removed = listOptions.remove(size - 1);
            linearLayout.removeView(removed);
        }
    }

    /**
     *
     */
    public void submit(View view) {
        Log.d(TAG,"Submit New Post");

        _post_submit_button.setEnabled(false);

        String username = "Hung";
        String textQuestion = _post_question.getText().toString();
        String answer_1 = _post_option_0.getText().toString();
        String answer_2 = _post_option_1.getText().toString();

        // create the list of answers
        listAnswers.add(answer_1);
        listAnswers.add(answer_2);
        for (int i = 2; i < numberOfOptionButtons; i++) {
            TextInputEditText postOption = view.findViewWithTag("post_option_" + i);
            String answer = postOption.getText().toString();
            listAnswers.add(answer);
        }

        // get array by list
        String[] answers = new String[listAnswers.size()];
        for (int i = 0; i < listAnswers.size(); i++) {
            answers[i] = listAnswers.get(i);
        }

        if (validate(view)) {
            PostModel newPost = new PostModel(username, textQuestion,
                    answers, Calendar.getInstance().getTime());

            postsDatabaseHandler.addPost(newPost);

            // after the new post is added to the database
            onPostSuccess(view);
        }
    }

    /**
     *
     * @return
     */
    public boolean validate(View view) {
        boolean valid = true;

        String textQuestion = _post_question.getText().toString();
        String answer_1 = _post_option_0.getText().toString();
        String answer_2 = _post_option_1.getText().toString();
        if (answer_1.isEmpty() || answer_2.isEmpty()) {
            return false;
        }

        for (int i = 2; i < numberOfOptionButtons; i++) {
            TextInputEditText postOption = view.findViewWithTag("post_option_" + i);
            String answer = postOption.getText().toString();

            // question can be empty but answers cannot
            if (answer.isEmpty()) {
                valid = false;
            }
        }

        return valid;
    }

    /**
     * handle stuff after the success post
     */
    public void onPostSuccess(View view) {
        reset(view);

        _post_submit_button.setEnabled(true);

        // transition back to the home activity
        Intent intent = new Intent(getActivity().getApplicationContext(), HomeActivity.class);
        getActivity().finish();
        startActivity(intent);

//        Fragment fragment = new HomeFragment();
//        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.replace(R.id.content_frame, fragment);
//        fragmentTransaction.addToBackStack(null);
//        fragmentTransaction.commit();
    }
}