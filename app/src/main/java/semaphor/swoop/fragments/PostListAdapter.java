package semaphor.swoop.fragments;

/**
 * Created by derek-w on 12/6/17.
 */
import android.content.Context;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
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
            Button option1 = v.findViewById(R.id.option1);
            Button option2 = v.findViewById(R.id.option2);
            Button option3 = v.findViewById(R.id.option3);

            userQuestion.setText(mPostList.get(position).getTextQuestion());
            userName.setText(mPostList.get(position).getUsername());
            option1.setText(mPostList.get(position).getArrayTextAnswer()[0]);
            option2.setText(mPostList.get(position).getArrayTextAnswer()[1]);
            option3.setText(mPostList.get(position).getArrayTextAnswer()[3]);


       option1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(hContext, "Button was clicked for list item " + position, Toast.LENGTH_SHORT).show();
            }
        });
        option2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(hContext, "Button was clicked for list item " + position, Toast.LENGTH_SHORT).show();
            }
        });
        option3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(hContext, "Button was clicked for list item " + position, Toast.LENGTH_SHORT).show();
            }
        });
        v.setTag(mPostList.get(position).getID());
       return v;
    }

}
