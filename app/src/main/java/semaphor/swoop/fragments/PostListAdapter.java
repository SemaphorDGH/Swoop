package semaphor.swoop.fragments;

/**
 * Created by derek-w on 12/6/17.
 */

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import semaphor.swoop.R;
import semaphor.swoop.database.PostModel;


public class PostListAdapter extends BaseAdapter {

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
        View v = View.inflate(hContext,R.layout.list_item,null);

            ImageView profilePic =  v.findViewById(R.id.profilePic);
            TextView userName =  v.findViewById(R.id.userName);
            TextView userQuestion =  v.findViewById(R.id.userQuestion);

            userQuestion.setText(mPostList.get(position).getTextQuestion());
            userName.setText(mPostList.get(position).getUsername());

            String[] answers = mPostList.get(position).getArrayTextAnswer();
            Button[] options = new Button[answers.length];
            for (int i = 0; i < answers.length; i++) {
                String strOptionID = "option" + (i + 1);

                // get the int id for R.id. from the string variable
                int intOptionID = hContext.getResources().getIdentifier(strOptionID, "id", hContext.getPackageName());
                options[i] = v.findViewById(intOptionID);
                CharSequence charSequence = mPostList.get(position).getArrayTextAnswer()[i];

                if (charSequence != null) {
                    options[i].setText(charSequence.toString());
                    options[i].setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(hContext, "Button was clicked for list item " + position, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

        v.setTag(mPostList.get(position).getID());
       return v;
    }

}
