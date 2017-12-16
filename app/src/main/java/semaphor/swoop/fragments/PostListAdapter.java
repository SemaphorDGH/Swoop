package semaphor.swoop.fragments;

/**
 * Created by derek-w on 12/6/17.
 */

import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import semaphor.swoop.R;
import semaphor.swoop.database.PostModel;


public class PostListAdapter extends BaseAdapter {
    private static final String TAG = "PostLipAdapter";

    private Context hContext;
    private int[] voteRlt;
    private int totalVote;
    private List<PostModel> mPostList;
    private int width;
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
        final LinearLayout linearLayout = (LinearLayout) v.findViewById(R.id.list_options_layout);
        width = linearLayout.getWidth();
        String[] answers = mPostList.get(position).getArrayTextAnswer();
        Button[] options = new Button[answers.length];
        voteRlt = new int[answers.length];
        totalVote = mPostList.get(position).getTotalVotes();
        for (int i = 0; i < answers.length; i++) {
            // get the int id for R.id. from the string variable
            //int intOptionID = hContext.getResources().getIdentifier(strOptionID, "id", hContext.getPackageName());
            CharSequence answer = mPostList.get(position).getArrayTextAnswer()[i];
            voteRlt[i] = mPostList.get(position).getVoteResult()[i];
            options[i] = createNewOption(answer, i);

            // add the option button to the specified layout
            linearLayout.addView(options[i]);
        }
        final Button[] fOptions = options;
        for (int i = 0; i < answers.length; i++) {
            final int fI = i;
            options[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    totalVote++;
                    voteRlt[fI]++;
                    TextView[] pb = new TextView[fOptions.length];
                    for(int j=0;j<fOptions.length;j++){
                        fOptions[j].setVisibility(View.GONE);
                        pb[j] = createResultBar(j,fI);
                        linearLayout.addView(pb[j]);
                    }
                }
            });
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

    public TextView createResultBar(int index, int chosen){
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        int scaleRatio = (int)(1150 * (1 - (double)voteRlt[index]/(double)totalVote));
        layoutParams.setMargins(0, 0, 100+scaleRatio, getMarginBottom());
        layoutParams.height = 140;
        TextView resultBar = new TextView(hContext);
        String tag = "resultBar_" + index;
        resultBar.setTag(tag);


        resultBar.setLayoutParams(layoutParams);
        resultBar.setText(voteRlt[index]+"/"+totalVote);
        resultBar.setTextColor(Color.BLACK);
        if(index == chosen) {
            resultBar.setBackgroundColor(Color.GREEN);
        }else{
            resultBar.setBackgroundColor(Color.RED);
        }
        return resultBar;
    }
}
