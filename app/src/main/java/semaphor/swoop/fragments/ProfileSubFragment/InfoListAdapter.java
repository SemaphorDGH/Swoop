package semaphor.swoop.fragments.ProfileSubFragment;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import semaphor.swoop.R;
import semaphor.swoop.database.PostModel;

/**
 * Created by derek-w on 12/12/17.
 */

public class InfoListAdapter extends BaseAdapter {
    private static final String TAG = "InfoLipAdapter";

    private Context hContext;

    private List<InfoModel> infoList;
    
    public InfoListAdapter(Context hContext, List<InfoModel> infoList) {
        this.hContext = hContext;
        this.infoList = infoList;
    }

    @Override
    public int getCount() {
        return infoList.size();
    }

    @Override
    public Object getItem(int position) {
        return infoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = View.inflate(hContext, R.layout.list_info, null);

        ImageView profilePic = v.findViewById(R.id.infoIconPic);
        TextView info = v.findViewById(R.id.infos);

        info.setText(infoList.get(position).getInfo());
        return v;
    }
}
