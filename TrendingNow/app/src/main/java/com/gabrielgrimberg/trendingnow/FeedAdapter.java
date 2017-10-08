package com.gabrielgrimberg.trendingnow;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Created by GabrielGrimberg on 08/10/2017.
 */

public class FeedAdapter extends ArrayAdapter
{
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
}
