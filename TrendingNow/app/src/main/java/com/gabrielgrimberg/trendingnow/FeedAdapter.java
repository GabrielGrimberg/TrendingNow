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
        //Creating a view, uses layout inflator.
        View view = layoutInf.inflate(layoutRes, parent, false);

        //Find the text widgets.
        TextView tvName = (TextView) view.findViewById(R.id.tvName);
        TextView tvArist = (TextView) view.findViewById(R.id.tvArist);
        TextView tvSummary = (TextView) view.findViewById(R.id.tvSummary);

        FeedContainer currentApp = apps.get(position);

        //Set into the corresponding textview.
        tvName.setText(currentApp.getName());
        tvArist.setText(currentApp.getArtist());
        tvSummary.setText(currentApp.getSummary());

        return view;
    }
}
