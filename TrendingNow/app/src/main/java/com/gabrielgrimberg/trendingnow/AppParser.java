package com.gabrielgrimberg.trendingnow;

import java.util.ArrayList;

/**
 * Created by GabrielGrimberg on 30/09/2017.
 */

public class AppParser
{
    private static final String TAG = "AppParser";
    private ArrayList<FeedContainer> apps;

    public AppParser()
    {
        //Initilise array list.
        this.apps = new ArrayList<>();
    }

    public ArrayList<FeedContainer> getApps()
    {
        return apps;
    }
}
