package semaphor.swoop.fragments;

/**
 * Created by derek-w on 12/6/17.
 */

import android.content.Context;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import semaphor.swoop.R;
import semaphor.swoop.database.PostModel;


public class PostListAdapter extends BaseAdapter {
    private static final String TAG = "PostLipAdapter";

    private Context hContext;

    private List<PostModel> mPostList;

    public PostListAdapter(Context hContext, List<PostModel> mPostList) {
        this.hContext = hContext;
        this.mPostList = mPostList;
    }

    @Override
    public int getCount() {
        return mPostList.size();
    }

    @Override
    public Object getItem(int position) {
        return mPostList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = View.inflate(hContext, R.layout.list_post, null);

        ImageView profilePic = v.findViewById(R.id.profilePic);
        TextView userName = v.findViewById(R.id.userName);
        TextView userQuestion = v.findViewById(R.id.userQuestion);
        TextView postDate = v.findViewById(R.id.post_date);
        postDate.setText(mPostList.get(position).getStringDate());
        userQuestion.setText(mPostList.get(position).getTextQuestion());
        userName.setText(mPostList.get(position).getUsername());

        // from answers, create buttons and add them to options of posts
        LinearLayout linearLayout = (LinearLayout) v.findViewById(R.id.list_options_layout);
        String[] answers = mPostList.get(position).getArrayTextAnswer();
        Button[] options = new Button[answers.length];

        for (int i = 0; i < answers.length; i++) {
            // get the int id for R.id. from the string variable
            //int intOptionID = hContext.getResources().getIdentifier(strOptionID, "id", hContext.getPackageName());
            CharSequence answer = mPostList.get(position).getArrayTextAnswer()[i];
            options[i] = createNewOption(answer, i);

            // add the option button to the specified layout
            linearLayout.addView(options[i]);
        }

        v.setTag(mPostList.get(position).getID());
        return v;
    }

    public int getMarginBottom() {
        // convert dp and int to set margins for layout of the option button
        float scaleRatio = hContext.getResources().getDisplayMetrics().density;
        float dpPix = hContext.getResources().getDimension(R.dimen.component_margin_bottom);
        float dpValue = dpPix/scaleRatio;

        int marginBottom = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpValue, hContext.getResources().getDisplayMetrics()));

        return marginBottom;
    }

    public Button createNewOption(CharSequence answer, int index) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        layoutParams.setMargins(0, 0, 0, getMarginBottom());

        Button option = new Button(hContext);
        String tag = "list_option_" + index;
        option.setTag(tag);

        // design the option button
        option.setLayoutParams(layoutParams);
        option.setText(answer);
        option.setBackgroundResource(R.drawable.option_button);
        option.setTextColor(hContext.getResources().getColor(R.color.dark_blue, null));

        return option;
    }
}
