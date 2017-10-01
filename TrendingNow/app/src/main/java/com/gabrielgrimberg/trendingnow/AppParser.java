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

    //Returning true if data passed, false if not passed.
    public boolean parse(String xmlData)
    {
        boolean status = true;
        boolean inEntry = false; //Keeping track of tags.

        FeedContainer currentRec = null; //Store details.
        String textValue = ""; //Store value of a current tag.

        try
        {
            //Parsing functionality.
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new StringReader(xmlData) );

            //Events can happen such as entering a tag.
            int eventType = xpp.getEventType();

            //Read until end of file.
            while(eventType != XmlPullParser.END_DOCUMENT)
            {
                String tagName = xpp.getName();

                switch(eventType)
                {
                    case XmlPullParser.START_TAG:

                        Log.d(TAG, "prase: Starting tag for: " + tagName);

                        if("entry".equalsIgnoreCase(tagName) )
                        {
                            inEntry = true;
                            currentRec = new FeedContainer();
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

                        Log.d(TAG, "parse: Ending tag for: " + tagName);

                        //if(tagName.equalsIgnoreCase(tagName)) -> Ignored as it can return null.
                        if("entry".equalsIgnoreCase(tagName) )
                        {
                            apps.add(currentRec);
                            inEntry = false;

                            else if("name".equalsIgnoreCase(tagName))
                            {
                            currentRec.setName(textValue);
                            }
                            else if ("artist".equalsIgnoreCase(tagName))
                            {
                                currentRec.setArtist(textValue);
                            }
                            else if ("releaseDate".equalsIgnoreCase(tagName))
                            {
                                currentRec.setReleaseData(textValue);
                            }
                            else if ("summary".equalsIgnoreCase(tagName))
                            {
                                currentRec.setSummary(textValue);
                            }
                            else if ("image".equalsIgnoreCase(tagName))
                            {
                                currentRec.setImageURL(textValue);
                            }
                        }

                        break;

                    default:
                        //Nothing needed.

                }

                //Continue working through the XML until next event happens.
                eventType = xpp.next();

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
