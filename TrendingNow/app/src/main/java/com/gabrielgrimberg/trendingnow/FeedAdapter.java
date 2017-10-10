package com.gabrielgrimberg.trendingnow;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by GabrielGrimberg on 08/10/2017.
 */

public class FeedAdapter extends ArrayAdapter
{
    //Use for debugging.
    private static final String TAG = "FeedAdapter";

    private final int layoutRes;
    private final LayoutInflater layoutInf;
    private List<FeedContainer> apps;

    public FeedAdapter(@NonNull Context context, @LayoutRes int resource, List<FeedContainer> apps)
    {
        super(context, resource);
        this.layoutRes = resource;
        this.layoutInf = LayoutInflater.from(context);
        this.apps = apps;


    }

    @Override
    public int getCount()
    {
        return apps.size(); //Return number of entries in app list.
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        ViewHolder viewHolder;

        //Making sure we do not create new views every-time.
        if(convertView == null)
        {
            convertView = layoutInf.inflate(layoutRes, parent, false);

            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }


//        //Creating a view, uses layout inflator.
//        View view = layoutInf.inflate(layoutRes, parent, false);
//
//        //Find the text widgets.
//        TextView tvName = (TextView) convertView.findViewById(tvName);
//        TextView tvArist = (TextView) convertView.findViewById(R.id.tvArist);
//        TextView tvSummary = (TextView) convertView.findViewById(R.id.tvSummary);

        FeedContainer currentApp = apps.get(position);

        //Set into the corresponding textview.
        viewHolder.tvName.setText(currentApp.getName());
        viewHolder.tvArist.setText(currentApp.getArtist());
        viewHolder.tvSummary.setText(currentApp.getSummary());

        return convertView;
    }

    private class ViewHolder
    {
        final TextView tvName;
        final TextView tvArist;
        final TextView tvSummary;

        //Uses to find widgets.
        ViewHolder(View v)
        {
            this.tvName = (TextView) v.findViewById(R.id.tvName);
            this.tvArist = (TextView) v.findViewById(R.id.tvArist);
            this.tvSummary = (TextView) v.findViewById(R.id.tvSummary);
        }
    }
}
