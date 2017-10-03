package com.gabrielgrimberg.trendingnow;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
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
        //Initialise array list.
        this.apps = new ArrayList<>();
    }

    public ArrayList<FeedContainer> getApps()
    {
        return apps;
    }

    //Returning true if data passed, failed if not passed.
    public boolean parse(String xmlData)
    {
        boolean status = true;
        boolean inEntry = false; //Keeping track of tags.

        FeedContainer currentRecord = null; //Store details.
        String textValue = ""; //Store value of current tag.

        try
        {
            //Parsing functionality.
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new StringReader(xmlData));

            //Events can happen such as entering a tag.
            int eventType = xpp.getEventType();

            //Read until end of file.
            while(eventType != XmlPullParser.END_DOCUMENT)
            {
                String tagName = xpp.getName();

                switch (eventType)
                {
                    case XmlPullParser.START_TAG:
                        Log.d(TAG, "parse: Starting tag for " + tagName);
                        if("entry".equalsIgnoreCase(tagName))
                        {
                            inEntry = true;
                            currentRecord = new FeedContainer();
                        }
                        break;

                    //If text data available
                    //Pass it onto the textValue string to store.
                    case XmlPullParser.TEXT:
                        textValue = xpp.getText();
                        break;

                    //When the end tag has been reached.
                    //Pass the data to a specific field.
                    case XmlPullParser.END_TAG:
                        Log.d(TAG, "parse: Ending tag for " + tagName);

                        if(inEntry)
                        {
                            //if(tagName.equalsIgnoreCase(tagName)) -> Ignored as it can return a null.
                            if("entry".equalsIgnoreCase(tagName))
                            {
                                apps.add(currentRecord);
                                inEntry = false;
                            }
                            else if("name".equalsIgnoreCase(tagName))
                            {
                                currentRecord.setName(textValue);
                            }
                            else if("artist".equalsIgnoreCase(tagName))
                            {
                                currentRecord.setArtist(textValue);
                            }
                            else if("releaseDate".equalsIgnoreCase(tagName))
                            {
                                currentRecord.setReleaseDate(textValue);
                            }
                            else if("summary".equalsIgnoreCase(tagName))
                            {
                                currentRecord.setSummary(textValue);
                            }
                            else if("image".equalsIgnoreCase(tagName))
                            {
                                currentRecord.setImageURL(textValue);
                            }
                        }
                        break;

                    default:
                }

                //Continue working through the XML until next event happens.
                eventType = xpp.next();

            }

            //Test to see if information displays.
            for (FeedContainer app: apps)
            {
                Log.d(TAG, "TESTING BELOW: APPS SHOULD SHOW");
                Log.d(TAG, app.toString());
            }

        }
        catch(Exception e)
        {
            status = false;
            e.printStackTrace();
        }

        return status;
    }
}